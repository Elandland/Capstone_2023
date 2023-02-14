package ac.kr.dankook.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserInfomation {

    private String mUserID;
    private String mUserName;
    private eMBTI mUserMBTI;
    static private UserInfomation _instance;

    private UserInfomation(){
        mUserMBTI=eMBTI.NONE;
    }
    public static synchronized void SetUserName(String userName){
        if(_instance==null){
            _instance=new UserInfomation();
        }
        _instance.mUserName=userName;
    }

    
    @NonNull
    static public synchronized void SetUserID(String userID){
        if(_instance==null){
            _instance=new UserInfomation();
        }
        _instance.mUserID=userID;
    }

    @NonNull
    static public synchronized void SetMBTIByString(String mbti){
        if(_instance==null){
            _instance=new UserInfomation();
        }
        _instance.mUserMBTI=eMBTI.valueOf(mbti);
    }

    @NonNull
    static public synchronized void SetMBTIByEnumUserMBTI(eMBTI mbti){
        if(_instance==null){
            _instance=new UserInfomation();
        }
        _instance.mUserMBTI=mbti;
    }

    static public synchronized String GetUserNameOrNull(){
        if(_instance == null){
            return null;
        }
        return _instance.mUserName;
    }

    static public synchronized String GetUserIDOrNull(){
        if(_instance == null){
            return null;
        }
        return _instance.mUserID;
    }

    static public synchronized String GetStringOfMBTI(){
        if(_instance == null){
            return null;
        }
        return _instance.mUserMBTI.toString();
    }

    static public synchronized eMBTI GetEnumOfMBTI(){
        if(_instance == null){
            return null;
        }
        return _instance.mUserMBTI;
    }

}
