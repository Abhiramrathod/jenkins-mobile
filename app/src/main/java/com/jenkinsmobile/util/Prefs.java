package com.jenkinsmobile.util;
import android.content.Context;
import android.content.SharedPreferences;
public class Prefs {
    private static final String NAME = "jenkins_prefs";
    public static final String KEY_URL = "jenkins_url";
    public static final String KEY_USER = "jenkins_user";
    public static final String KEY_TOKEN = "jenkins_token";
    public static final String KEY_LAST_JOB = "last_job_name";
    private final SharedPreferences sp;
    public Prefs(Context ctx) { sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE); }
    public void saveUrl(String u){ sp.edit().putString(KEY_URL, u).apply(); }
    public String getUrl(){ return sp.getString(KEY_URL, ""); }
    public void saveAuth(String u, String t){ sp.edit().putString(KEY_USER,u).putString(KEY_TOKEN,t).apply(); }
    public String getUser(){ return sp.getString(KEY_USER, ""); }
    public String getToken(){ return sp.getString(KEY_TOKEN, ""); }
    public void clearAuth(){ sp.edit().remove(KEY_USER).remove(KEY_TOKEN).apply(); }
}
