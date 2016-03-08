package cct.github.droid.android_mvc_sample.businessObjects;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Date;

public class SharedPrefManager {

    private String PREFS_NAME;

    public SharedPrefManager(String str) {
        PREFS_NAME = str;
    }

    public int getSettingsInt(Context ctx, String settingName){
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);
        int ret = settings.getInt(settingName, -1);
        return ret;
    }
    public String getSettingString(Context ctx, String settingName){
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);
        String ret = settings.getString(settingName, "");
        return ret;
    }
    public Date getSettingDate(Context ctx, String settingName){
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);
        Date ret = new Date(settings.getLong(settingName, 0));
        return ret;
    }
    public void saveSettingString(Context ctx, String settingName, String val){
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(settingName, val);
        editor.commit();
    }
    public void saveSettingsInt(Context ctx, String settingName, int val){
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(settingName, val);
        editor.commit();
    }
    public void saveSettingDate(Context ctx, String settingName, Date val){
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(settingName, val.getTime());
        editor.commit();
    }
    public void removeSetting(Context ctx, String settingName){
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(settingName);
        editor.commit();
    }
    public void clearAllSettings(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear();

        editor.commit();
    }

}
