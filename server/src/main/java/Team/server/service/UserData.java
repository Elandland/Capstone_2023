package Team.server.service;

public class UserData {
    private int mLatitude;
    private int mLongitude;
    private int mMBTI;
    private String mUserID;
    private int mLastMatchingTime;

    public UserData(String userid, int latitude, int longitude, int mbti, int last_matching_time){
        mUserID=userid;
        mLatitude=latitude;
        mLongitude=longitude;
        mMBTI=mbti;
        mLastMatchingTime=last_matching_time;
    }

    public int GetLatitude(){
        return mLatitude;
    }

    public int GetLongitude(){
        return mLongitude;
    }

    public int GetMBTI(){
        return mMBTI;
    }

    public String GetUserID(){
        return mUserID;
    }

    public int GetLastMatchingTime(){
        return this.mLastMatchingTime;
    }

    public void SetLastMatchingTime(int currentLastMatchingTime){
        this.mLastMatchingTime=currentLastMatchingTime;
    }
    public void SetLatitude(int latitude){
        mLatitude=latitude;
    }

    public void SetLongitude(int longitude){
        mLongitude=longitude;
    }

    public void SetMBTI(int mbti){
        mMBTI=mbti;
    }

}
