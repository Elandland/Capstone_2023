package Team.server.service;

import java.util.Comparator;
import java.util.PriorityQueue;

import java.util.HashMap;

public class MatchManager {

    final private float THRESHOLD=0.0f;        //최소 거리 (미터 단위로 입력)
    final private int pointRatio=1000000;      //위도, 경도 정확도 보정()
    final private float ROUND_RATIO_LATITUDE=111195.0f;
    final private float ROUND_RATIO_LONGITUDE=111320.0f;

    private float[][] MBTIMatchingWeight;

    private float mThreshold;
    private PriorityQueue<UserData> userDataQueue;
    private HashMap<String, UserData> userDataMapByName;
    static private MatchManager manager;


    //생성자
    private MatchManager(){
        mThreshold=THRESHOLD*THRESHOLD;
        userDataMapByName=new HashMap<>();
        userDataQueue=new PriorityQueue<>(new Comparator<UserData>() {
            @Override
            public int compare(UserData u1, UserData u2){
                return u1.GetLastMatchingTime()-u2.GetLastMatchingTime();
            }
        });

        MBTIMatchingWeight=new float[16][16];
        for(int i=0;i<16;++i){
            for(int j=0;j<16;++j){
                MBTIMatchingWeight[i][j]=1.0f;
            }
        }

    }

    //from 유저와 to 유저가 허용 거리 이내에 있는가
    private boolean isAccept(UserData from, UserData to){
        return GetSquareDistanceByUserData(from, to)<=mThreshold;
    }

    //User 간의 제곱 거리 반환
    private float GetSquareDistanceByUserData(UserData from, UserData to){
        float latitude_dist=ROUND_RATIO_LATITUDE*(from.GetLatitude()-to.GetLatitude())/(float)(pointRatio);
        float longitude_dist=ROUND_RATIO_LONGITUDE*(from.GetLongitude()-to.GetLongitude())/(float)(pointRatio);
        return latitude_dist*latitude_dist+longitude_dist*longitude_dist;
    }
    
    // MatchManager 인스턴스 반환
    public MatchManager GetManager(){
        if(manager==null){
            manager=new MatchManager();
        }
        return manager;
    }

    //currentMatchingTIme => 매칭 시도 시간
    //매칭 성공시 매칭된 두 유저 반환
    //실패시, Null 반환
    public UserData[] GetMatchGroupOrNull(int currentMatchingTime){
        UserData try_user=null;
        while(userDataQueue.size()>0){
            try_user=userDataQueue.poll();
            if(userDataMapByName.containsKey(try_user.GetUserID())){
                break;
            }
        }
        if(userDataQueue.size()<1){
            if(try_user != null && userDataMapByName.containsKey(try_user.GetUserID())){
                userDataQueue.add(try_user);
            }
            return null;
        }
        UserData[] temp=new UserData[userDataQueue.size()];
        int temp_idx=0;

        UserData maxUser=null;
        while(userDataQueue.size()>0){
            maxUser=userDataQueue.poll();
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
            userDataQueue.add(temp[i]);
        }
        if(maxUser==null){
            try_user.SetLastMatchingTime(currentMatchingTime);
            userDataQueue.add(try_user);
            ret=null;
        }
        else{
            ret[0]=try_user;
            ret[1]=maxUser;
        }
        return ret;
    }


    //userId를 매칭 대기에서 제거
    public void RemoveUser(String userId){
        if(userDataMapByName.containsKey(userId)==false){
            return;
        }
        userDataMapByName.remove(userId);
    }

    //float latitude 를 정수 형태로 변환
    private int convertLatitude(float latitude){
        return (int)(latitude*pointRatio);
    }

    //float longitude 를 정수 형태로 변환
    private int convertLongitude(float longitude){
        return (int)(longitude*pointRatio);
    }


    //userId의 위치 정보를 currentLatitude, currentLongitude로 변경
    public void UpdateUserPosition(String userId, float currentLatitude, float currentLongitude){
        if(userDataMapByName.containsKey(userId)==false){
            return;
        }
        UserData target=userDataMapByName.get(userId);
        target.SetLatitude(convertLatitude(currentLatitude));
        target.SetLongitude(convertLongitude(currentLongitude));
    }


    //userId의 mbti를 currentMBTI로 변환
    public void UpdateUserMBTI(String userId, int currentMBTI){
        if(userDataMapByName.containsKey(userId)==false){
            return;
        }
        UserData target=userDataMapByName.get(userId);
        target.SetMBTI(currentMBTI);
    }


    //매칭 대기 상태의 유저 추가(userid, 위도, 경도, mbti, 마지막으로 매칭 시도한 시각)
    public void AddUserData(String userID, float latitude, float longitude, int mbti, int last_matching_time){
        if(userDataMapByName.containsKey(userID)){
            return;
        }
        UserData data=new UserData(userID, convertLatitude(latitude), convertLongitude(longitude), mbti, last_matching_time);
        userDataMapByName.put(data.GetUserID(), data);
        userDataQueue.add(data);
    }

}
