package rnd.plani.co.kr.dadfarm.Manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import rnd.plani.co.kr.dadfarm.MyApplication;

/**
 * Created by RND on 2016-09-27.
 */

public class PropertyManager {
    private static PropertyManager instance;
    public static PropertyManager getInstance(){
        if(instance == null){
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;

    private PropertyManager(){
        mPref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        mEditor = mPref.edit();
    }

    private static final String REG_FIRST_NAME = "firstName";
    public void setFirstName(String firstName){
        mEditor.putString(REG_FIRST_NAME, firstName);
        mEditor.commit();
    }
    public String getFirstName(){
        return mPref.getString(REG_FIRST_NAME,"");
    }

    private static final String REG_LAST_NAME = "lastName";
    public void setLastName(String lastName){
        mEditor.putString(REG_LAST_NAME, lastName);
        mEditor.commit();
    }
    public String getLastName(){
        return mPref.getString(REG_LAST_NAME,"");
    }


    private static final String REG_PHONE = "phoneNumber";
    public void setPhoneNum(String phone){
        mEditor.putString(REG_PHONE,phone);
        mEditor.commit();
    }
    public String getPhoneNum(){
        return mPref.getString(REG_PHONE,"");
    }

    private static final String REG_PAFARM_TOKEN = "pafarm_token";
    public void setPafarmToken(String token){
        mEditor.putString(REG_PAFARM_TOKEN,token);
        mEditor.commit();
    }
    public String getPafarmToken(){
        return mPref.getString(REG_PAFARM_TOKEN,"");
    }

    private static final String REG_SYNC_DATE = "sync_date";
    public void setSyncDate(String date){
        mEditor.putString(REG_SYNC_DATE, date);
        mEditor.commit();
    }
    private static final String REG_USER_ID = "uid";
    public long getUserId(){
        return mPref.getLong(REG_USER_ID,-1);
    }

    public void setUserId(long uid){
        mEditor.putLong(REG_USER_ID,uid);
        mEditor.commit();
    }
    public String getSyncDate(){
        return mPref.getString(REG_SYNC_DATE,"");
    }

    private static final String REG_GCM_TOKEN = "registration_token";
    public String getRegistrationToken(){
        return mPref.getString(REG_GCM_TOKEN,"");
    }
    public void setRegistrationToken(String token){
        mEditor.putString(REG_GCM_TOKEN, token);
        mEditor.commit();
    }
}
