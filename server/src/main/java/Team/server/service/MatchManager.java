package Team.server.service;

import java.util.Comparator;
import java.util.Random;
import java.util.PriorityQueue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import Team.server.domain.User;

public class MatchManager {

    final private float THRESHOLD=0.0f;        //미터 단위로 입력
    final private int pointRatio=1000000;
    final private float ROUND_RATIO_LATITUDE=111195.0f;
    final private float ROUND_RATIO_LONGITUDE=111320.0f;

    private float[][] MBTIMatchingWeight;

    private Random mRand;
    private float mThreshold;
    private PriorityQueue<UserData> userDataSetByPosition;
    private HashSet<String> mMatchingWaitUserSet;
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
        userDataSetByPosition=new PriorityQueue<>(new Comparator<UserData>() {
            @Override
            public int compare(UserData u1, UserData u2){
                return u1.GetLastMatchingTime()-u2.GetLastMatchingTime();
            }
        });

        mMatchingWaitUserSet=new HashSet<>();
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
    
    public MatchManager GetManager(){
        if(manager==null){
            manager=new MatchManager();
        }
        return manager;
    }

    public UserData[] GetMatchGroupOrNull(int currentMatchingTime){
        UserData try_user=null;
        while(userDataSetByPosition.size()>0){
            try_user=userDataSetByPosition.poll();
            if(userDataMapByName.containsKey(try_user.GetUserID())){
                break;
            }
        }
        if(userDataSetByPosition.size()<1){
            if(try_user != null && userDataMapByName.containsKey(try_user.GetUserID())){
                userDataSetByPosition.add(try_user);
            }
            return null;
        }
        UserData[] temp=new UserData[userDataSetByPosition.size()];
        int temp_idx=0;

        UserData maxUser=null;
        while(userDataSetByPosition.size()>0){
            maxUser=userDataSetByPosition.poll();
            if(userDataMapByName.containsKey(try_user.GetUserID())){
                if(isAccept(try_user, maxUser)){
                    break;
                }
                temp[temp_idx++]=maxUser;
            }
            maxUser=null;
        }
        UserData[] ret=new UserData[2];
        for(int i=0;i<temp_idx;++i){
            userDataSetByPosition.add(temp[i]);
        }
        if(maxUser==null){
            try_user.SetLastMatchingTime(currentMatchingTime);
            userDataSetByPosition.add(try_user);
            ret=null;
        }
        else{
            ret[0]=try_user;
            ret[1]=maxUser;
        }
        return ret;
    }

    public void RemoveUser(String userId){
        if(userDataMapByName.containsKey(userId)==false){
            return;
        }
        userDataMapByName.remove(userId);
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
        target.SetLatitude(convertLatitude(currentLatitude));
        target.SetLongitude(convertLongitude(currentLongitude));
    }

    public void UpdateUserMBTI(String userId, int currentMBTI){
        if(userDataMapByName.containsKey(userId)==false){
            return;
        }
        UserData target=userDataMapByName.get(userId);
        target.SetMBTI(currentMBTI);
    }

    public void AddUserData(String userID, float latitude, float longitude, int mbti, int last_matching_time){
        if(userDataMapByName.containsKey(userID)){
            return;
        }
        UserData data=new UserData(userID, convertLatitude(latitude), convertLongitude(longitude), mbti, last_matching_time);
        userDataMapByName.put(data.GetUserID(), data);
        userDataSetByPosition.add(data);
    }

}
