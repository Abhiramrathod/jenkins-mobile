package com.jenkinsmobile;
import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
public class JenkinsApp extends Application {
    @Override public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}
