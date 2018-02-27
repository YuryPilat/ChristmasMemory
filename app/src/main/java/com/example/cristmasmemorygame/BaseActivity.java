package com.example.cristmasmemorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cristmasmemorygame.managers.MusicManager;

public class BaseActivity extends AppCompatActivity{

    public static boolean needPauseMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        needPauseMusic = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (needPauseMusic){
            MusicManager.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (needPauseMusic){
            MusicManager.play();
        }
    }

    protected void pushActivity(Intent intent){
        needPauseMusic = false;
        startActivity(intent);
        overridePendingTransition(R.anim.start_activity_on, R.anim.start_activity_off);
    }
}