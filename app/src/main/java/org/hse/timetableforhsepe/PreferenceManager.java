package org.hse.timetableforhsepe;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private final static String PREFERENCE_FILE = "org.hse.android.file";
    private final static String PROFILE_NAME = "profileName";
    private final static String PHOTO_PATH = "photoPATH";

    private final SharedPreferences sharedPref;

    public  PreferenceManager(Context context) {
        sharedPref = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
    }

    public void saveProfileName(String newValue){
        saveValue(PROFILE_NAME, newValue);
    }

    public void savePhotoPATH(String newValue){
        saveValue(PHOTO_PATH, newValue);
    }

    public String getProfileName(){
        return getValue(PROFILE_NAME, "");
    }

    public String getPhotoPATH(){
        return getValue(PHOTO_PATH, "");//@drawable/profile_picture_basic");
    }

    private void saveValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String getValue(String key, String defaultValue) {
        return sharedPref.getString(key, defaultValue);
    }
}
