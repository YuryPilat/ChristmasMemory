package com.example.cristmasmemorygame.managers;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicManager {

   private static MediaPlayer myPlayer;

    public static void play(Context context, int track) {
        myPlayer = MediaPlayer.create(context, track);
        myPlayer.setLooping(true);
        play();
    }

    public static void play() {
        if ((myPlayer != null) && !myPlayer.isPlaying()) {
            myPlayer.start();
        }
    }

    public static void pause(){
        if (myPlayer!=null && myPlayer.isPlaying()){
            myPlayer.pause();
        }
    }

    public static void stop(){
        if (myPlayer!=null && myPlayer.isPlaying()){
            myPlayer.stop();
        }
    }

    public static void dispose() {
        stop();
        if (myPlayer!=null){
            myPlayer.release();
            myPlayer=null;
        }
    }
}