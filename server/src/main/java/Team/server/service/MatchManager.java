package Team.server.service;

import java.util.Comparator;
import java.util.Random;

import java.util.HashMap;
import java.util.Stack;


public class MatchManager {

    final private float THRESHOLD=0.0f;        //미터 단위로 입력
    final private int pointRatio=1000000;
    final private float ROUND_RATIO_LATITUDE=111195.0f;
    final private float ROUND_RATIO_LONGITUDE=111320.0f;

    private float[][] MBTIMatchingWeight;

    private Random mRand;
    private float mThreshold;
    private Tree<UserData> userDataSetByPosition;
    private HashMap<String, UserData> userDataMapByName;
    static private MatchManager manager;

    private class UserDataCompartorByPosition implements Comparator<UserData>{
        @Override
        public int compare(UserData u1, UserData u2){
            if(u1.GetLatitude()!=u2.GetLatitude()){
                return u1.GetLatitude()-u2.GetLatitude();
            }
            if(u1.GetLongitude()!=u2.GetLongitude()){
                return u1.GetLongitude()-u2.GetLongitude();
            }
            return u1.GetUserID().compareTo(u2.GetUserID());
        }
    }
    private MatchManager(){
        mThreshold=THRESHOLD*THRESHOLD;
        mRand=new Random();
        mRand.setSeed(3124);
        userDataMapByName=new HashMap<>();
        userDataSetByPosition=new Tree<>(new UserDataCompartorByPosition());
        MBTIMatchingWeight=new float[16][16];
        for(int i=0;i<16;++i){
            for(int j=0;j<16;++j){
                MBTIMatchingWeight[i][j]=1.0f;
            }
        }

    }

    private boolean isAccept(UserData from, UserData to){
        return GetSquareDistanceByUserData(from, to)<=mThreshold;
    }

    private float GetSquareDistanceByUserData(UserData from, UserData to){
        float latitude_dist=ROUND_RATIO_LATITUDE*(from.GetLatitude()-to.GetLatitude())/(float)(pointRatio);
        float longitude_dist=ROUND_RATIO_LONGITUDE*(from.GetLongitude()-to.GetLongitude())/(float)(pointRatio);
        return latitude_dist*latitude_dist+longitude_dist*longitude_dist;
    }
    
    private float GetSquareDistanceByPoint(int from_latitude, int from_longitude, int to_latitude, int to_longitude){
        float latitude_dist=ROUND_RATIO_LATITUDE*(from_latitude-to_latitude)/(float)(pointRatio);
        float longitude_dist=ROUND_RATIO_LONGITUDE*(from_longitude-to_longitude)/(float)(pointRatio);
        return latitude_dist*latitude_dist+longitude_dist*longitude_dist;
    }
    

    private float GetMatchingWeight(UserData from, UserData to){
        return MBTIMatchingWeight[from.GetMBTI()][to.GetMBTI()];
    }
    public MatchManager GetManager(){
        if(manager==null){
            manager=new MatchManager();
        }
        return manager;
    }

    public UserData[] GetMatchGroupOrNull(){
        if(userDataSetByPosition.Size()<1){
            return null;
        }
        int index=mRand.nextInt(userDataSetByPosition.Size())+1;
        TreeNode<UserData> pivot_user_node=userDataSetByPosition.GetNodebyOrder(index);
        TreeNode<UserData> previous=pivot_user_node.mPreviousNode;
        TreeNode<UserData> next=pivot_user_node.mNextNode;
        UserData pivot_user=pivot_user_node.GetKey();
        UserData maxUser=null;
        float maxWeight=Float.MIN_VALUE;
        UserData target_user=previous.GetKey();
        while(previous!=null &&
            GetSquareDistanceByPoint(
                pivot_user.GetLatitude(), 
                pivot_user.GetLongitude()
                ,previous.GetKey().GetLatitude(), 
                pivot_user.GetLongitude())<=mThreshold)
            {
                target_user=previous.GetKey();
                if(isAccept(pivot_user, target_user)){
                    if(maxWeight<GetMatchingWeight(target_user, pivot_user)){
                        maxUser=target_user;
                        maxWeight=GetMatchingWeight(target_user, pivot_user);
                    }
                }
                previous=previous.GetPrevious();
            }
        while(next!=null && 
            GetSquareDistanceByPoint(
                    pivot_user.GetLatitude(), 
                    pivot_user.GetLongitude()
                    ,next.GetKey().GetLatitude(), 
                    pivot_user.GetLongitude())<=mThreshold)
            {
                target_user=next.GetKey();
                if(isAccept(pivot_user, target_user)){
                    if(maxWeight<GetMatchingWeight(target_user, pivot_user)){
                        maxUser=target_user;
                        maxWeight=GetMatchingWeight(target_user, pivot_user);
                    }
                }
                next=next.GetNext();
            }
        if(maxUser==null){
            return null;
        }
        return new UserData[]{pivot_user,maxUser};
    }

    public void RemoveUser(String userId){
        if(userDataMapByName.containsKey(userId)==false){
            return;
        }
        UserData target=userDataMapByName.get(userId);
        if(userDataSetByPosition.Find(target)!=null){
            userDataSetByPosition.Remove(target);
            userDataMapByName.remove(userId);
        }
    }

    private int convertLatitude(float latitude){
        return (int)(latitude*pointRatio);
    }

    private int convertLongitude(float longitude){
        return (int)(longitude*pointRatio);
    }

    public void UpdateUserPosition(String userId, float currentLatitude, float currentLongitude){
        if(userDataMapByName.containsKey(userId)==false){
            return;
        }
        UserData target=userDataMapByName.get(userId);
        userDataSetByPosition.Remove(target);
        target.SetLatitude(convertLatitude(currentLatitude));
        target.SetLongitude(convertLongitude(currentLongitude));
        userDataSetByPosition.Insert(target);
    }

    public void UpdateUserMBTI(String userId, int currentMBTI){
        if(userDataMapByName.containsKey(userId)==false){
            return;
        }
        UserData target=userDataMapByName.get(userId);
        target.SetMBTI(currentMBTI);
    }

    public void AddUserData(String userID, float latitude, float longitude, int mbti){
        if(userDataMapByName.containsKey(userID)){
            return;
        }
        UserData data=new UserData(userID, convertLatitude(latitude), convertLongitude(longitude), mbti);

        userDataMapByName.put(data.GetUserID(), data);
        userDataSetByPosition.Insert(data);
    }


    private class TreeNode<KeyType>{
        TreeNode<KeyType> mLeft;
        TreeNode<KeyType> mRight;
        TreeNode<KeyType> mParent;

        TreeNode<KeyType> mNextNode;

        TreeNode<KeyType> mPreviousNode;
        boolean mIsRed;
        int nodeCnt;
        KeyType mKey;

        public TreeNode(KeyType key, TreeNode<KeyType> parent){
            mKey=key;
            mParent=parent;
            mNextNode=null;
            mPreviousNode=null;
            mLeft=null;
            mRight=null;
            nodeCnt=1;
            mIsRed=true;
        }

        public TreeNode<KeyType> GetRightChild(){
            return mRight;
        }

        public TreeNode<KeyType> GetLeftChild(){
            return mLeft;
        }

        public TreeNode<KeyType> GetParent(){
            return mParent;
        }

        public int GetNodeCnt(){
            return nodeCnt;
        }

        public KeyType GetKey(){
            return mKey;
        }

        public void SetColor(boolean isRed){
            mIsRed=isRed;
        }

        public boolean isRed(){
            return mIsRed;
        }

        public void adjust(){
            nodeCnt=1;
            if(mLeft!=null){
                nodeCnt+=mLeft.GetNodeCnt();
            }
            if(mRight!=null){
                nodeCnt+=mRight.GetNodeCnt();
            }
        }

        public TreeNode<KeyType> GetSibling(){
            if(mParent==null){
                return null;
            }
            if(mParent.mLeft==this){
                return mParent.mRight;
            }
            else{
                return mParent.mLeft;
            }
        }

        public TreeNode<KeyType> GetNext(){
            return mNextNode;
        }

        public TreeNode<KeyType> GetPrevious(){
            return mPreviousNode;
        }

    }

    private class Tree<KeyType> {
        TreeNode<KeyType> mRoot;
        TreeNode<KeyType> mLeafNode;
        Comparator<KeyType> mCompare;
        public Tree(Comparator<KeyType> compare){
            mLeafNode=new TreeNode<KeyType>(null, null);
            mLeafNode.SetColor(false);
            mLeafNode.nodeCnt=0;
            mRoot=mLeafNode;
            mCompare=compare;
        }

        public void inorder(){
            System.out.println("inorder");
            inorder(mRoot);
            System.out.println("End");
        }

        private void inorder(TreeNode<KeyType> root){
            if(root!=mLeafNode){
                inorder(root.mLeft);
                System.out.print(root.mKey.toString()+" ");
                inorder(root.mRight);
            }
        }



        private void replace(TreeNode<KeyType> u, TreeNode<KeyType> v){

            TreeNode<KeyType> uNextNode=u.mNextNode;
            if(u.mPreviousNode!=null){
                u.mPreviousNode.mNextNode=uNextNode;
            }
            if(uNextNode!=null){
                uNextNode.mPreviousNode=u.mPreviousNode;
            }

            if(u.mParent==null){
                mRoot=v;
            }
            else if(u.mParent.mLeft==u){
                u.mParent.mLeft=v;
            }
            else if(u.mParent.mRight==u){
                u.mParent.mRight=v;
            }
            v.mParent=u.mParent;
            TreeNode<KeyType> temp=v;
            while(temp.mParent!=null){
                temp.mParent.adjust();
                temp=temp.mParent;
            }
        }
        public boolean Remove(KeyType e){
            TreeNode<KeyType> target=Find(e);   //삭제할 노드
            TreeNode<KeyType> temp;
            if(target==null){
                return false;
            }
            TreeNode<KeyType> x,y;
            boolean removeIsRed=target.isRed();
            temp=target;
            if(target.mLeft==mLeafNode) {
                x = target.mRight;
                replace(target,target.mRight);
            }
            else if(target.mRight==mLeafNode){
                x=target.mLeft;
                replace(target,target.mLeft);
            }
            else{
                y=target.mRight;
                while(y.mLeft!=mLeafNode){
                    y=y.mLeft;
                }
                removeIsRed=y.isRed();
                temp=y;
                x=y.mRight;
                if(target==y.mParent){
                    x.mParent=y;
                }
                else{
                    replace(y,y.mRight);
                    y.mRight=target.mRight;
                    y.mRight.mParent=y;
                    y.adjust();
                }
                replace(target,y);
                y.mLeft=target.mLeft;
                y.mLeft.mParent=y;
                y.SetColor(target.isRed());
                temp=y;
                while(temp!=null){
                    temp.adjust();
                    temp=temp.mParent;
                }
            }
            temp=target;
            while(temp!=null){
                temp.adjust();;
                temp=temp.mParent;
            }
            if(removeIsRed==false){
                DeleteFix(x);
            }
            return true;
        }


        public TreeNode<KeyType> GetNodebyOrder(int order){
            if(!(1 <= order && order <= Size())){
                return null;
            }
            TreeNode<KeyType> now=mRoot;
            while(true){
                if(now.mLeft!=mLeafNode){
                    order-=now.mLeft.GetNodeCnt();
                }
                if(order==1){
                    return now;
                }
                if(order<=0){
                    order+=now.mLeft.GetNodeCnt();
                    now=now.mLeft;
                }
                else{
                    order-=1;
                    now=now.mRight;
                }
            }
        }


        private TreeNode<KeyType> Find(TreeNode<KeyType> root, KeyType e){
            if(root==mLeafNode){
                return null;
            }
            int val=mCompare.compare(root.mKey, e);
            if(val==0){
                return root;
            }
            if(val>0){
                return Find(root.mLeft,e);
            }
            return Find(root.mRight,e);
        }

        public TreeNode<KeyType> Find(KeyType e){
            return Find(mRoot,e);
        }

        public boolean Insert(KeyType e){
            TreeNode<KeyType> prev, now;
            TreeNode<KeyType> insert_node;
            Stack<TreeNode<KeyType>> st= new Stack<>();
            prev=null;
            now=mRoot;
            while(now!=mLeafNode){
                prev=now;
                st.add(now);
                int comp_val=mCompare.compare(now.GetKey(), e);
                if(comp_val<0){
                    now=now.mRight;
                }
                else if(comp_val>0){
                    now=now.mLeft;
                }
                else{
                    return false;
                }
            }

            insert_node=new TreeNode<>(e, prev);
            insert_node.mLeft=mLeafNode;
            insert_node.mRight=mLeafNode;
            if(prev==null){
                mRoot=insert_node;
                mRoot.SetColor(false);
                return true;
            }
            int comp_val=mCompare.compare(prev.GetKey(), e);
            if(comp_val>0){
                prev.mLeft=insert_node;
            }
            else{
                prev.mRight=insert_node;
            }
            while(st.empty()==false){
                st.peek().adjust();
                st.pop();
            }
            if(insert_node.mParent.mParent!=null){
                insertFix(insert_node);
            }
            TreeNode<KeyType> nextNode;
            if(insert_node.mRight!=mLeafNode){
                nextNode=insert_node.mRight;
                while(nextNode.mLeft!=mLeafNode){
                    nextNode=nextNode.mLeft;
                }
            }
            else{
                nextNode=insert_node.mParent;
                while(nextNode!=null&&mCompare.compare(nextNode.mKey,e)<0){
                    nextNode=nextNode.mParent;
                }
            }
            if(nextNode==null){
                TreeNode<KeyType> previous=GetNodebyOrder(Size()-1);
                previous.mNextNode=insert_node;
                insert_node.mPreviousNode=previous;
            }
            else{
                insert_node.mNextNode=nextNode;
                if(nextNode.mPreviousNode!=null){
                    nextNode.mPreviousNode.mNextNode=insert_node;
                }
                insert_node.mPreviousNode=nextNode.mPreviousNode;
                nextNode.mPreviousNode=insert_node;
            }
            return true;
        }


        private void LeftRotate(TreeNode<KeyType> x){
            TreeNode<KeyType> y=x.mRight;

            x.mRight=y.mLeft;
            if(y.mLeft!=mLeafNode){
                y.mLeft.mParent=x;
            }

            y.mParent=x.mParent;
            if(x.mParent==null){
                mRoot=y;
            }
            else if(x==x.mParent.mLeft){
                x.mParent.mLeft=y;
            }
            else{
                x.mParent.mRight=y;
            }
            y.mLeft=x;
            x.mParent=y;

            x.adjust();
            y.adjust();
        }

        private void RightRotate(TreeNode<KeyType> y){
            TreeNode<KeyType> x=y.mLeft;
            y.mLeft=x.mRight;
            if(x.mRight!=mLeafNode){
                x.mRight.mParent=y;
            }
            x.mParent=y.mParent;
            if(y.mParent==null){
                mRoot=x;
            }
            else if(y.mParent.mLeft==y){
                y.mParent.mLeft=x;
            }
            else{
                y.mParent.mRight=x;
            }
            x.mRight=y;
            y.mParent=x;

            y.adjust();
            x.adjust();
        }


        private void DeleteFix(TreeNode<KeyType> pivot){
            while(pivot!=mRoot && pivot.isRed()==false){
                TreeNode<KeyType> s = pivot.GetSibling();
                boolean isLeftChild=(pivot==pivot.mParent.mLeft);
                if(isLeftChild){
                    if(s.isRed()){
                        s.SetColor(false);
                        pivot.mParent.SetColor(true);
                        LeftRotate(pivot.mParent);
                        s=pivot.mParent.mRight;
                    }

                    if(s.mLeft.isRed()==false && s.mRight.isRed()==false){
                        s.SetColor(true);
                        pivot=pivot.mParent;
                    }
                    else{
                        if(s.mRight.isRed()==false){
                            s.mLeft.SetColor(false);
                            s.SetColor(true);
                            RightRotate(s);
                            s=pivot.mParent.mRight;
                        }

                        s.SetColor(pivot.mParent.isRed());
                        pivot.mParent.SetColor(false);
                        s.mRight.SetColor(false);
                        LeftRotate(pivot.mParent);
                        pivot=mRoot;
                    }
                }
                else{
                    if(s.isRed()){
                        s.SetColor(false);
                        pivot.mParent.SetColor(true);
                        RightRotate(pivot.mParent);
                        s=pivot.mParent.mLeft;
                    }

                    if(s.mLeft.isRed()==false && s.mRight.isRed()==false){
                        s.SetColor(true);
                        pivot=pivot.mParent;
                    }
                    else{
                        if(s.mLeft.isRed()==false){
                            s.mRight.SetColor(false);
                            s.SetColor(true);
                            LeftRotate(s);
                            s=pivot.mParent.mLeft;
                        }
                        s.SetColor(pivot.mParent.isRed());
                        pivot.mParent.SetColor(false);
                        s.mLeft.SetColor(false);
                        RightRotate(pivot.mParent);
                        pivot=mRoot;
                    }
                }
            }
            pivot.SetColor(false);
            mRoot.SetColor(false);
        }


        private void insertFix(TreeNode<KeyType> pivot){
            //pivot always red
            while(pivot.mParent!=null && pivot.mParent.isRed()){
                TreeNode<KeyType> pp=pivot.mParent;
                TreeNode<KeyType> ppp=pp.mParent;
                TreeNode<KeyType> uncleNode = (pivot.mParent==ppp.mLeft?ppp.mRight:ppp.mLeft);
                boolean pp_is_ppp_left = (pp==ppp.mLeft);
                if(uncleNode!=null && uncleNode.isRed()){
                    uncleNode.SetColor(false);
                    pp.SetColor(false);
                    ppp.SetColor(true);
                    pivot=ppp;
                }
                else{
                    if(pivot==(pp_is_ppp_left?pp.mRight:pp.mLeft)){
                        pivot=pp;
                        if(pp_is_ppp_left){
                            LeftRotate(pivot);
                        }
                        else{
                            RightRotate(pivot);
                        }
                    }
                    pivot.mParent.SetColor(false);
                    ppp.SetColor(true);
                    if(pp_is_ppp_left){
                        RightRotate(ppp);
                    }
                    else{
                        LeftRotate(ppp);
                    }
                }
            }
            mRoot.SetColor(false);
        }


        public int Size(){
            if(mRoot==mLeafNode){
                return 0;
            }
            return mRoot.nodeCnt;
        }
    }
}
