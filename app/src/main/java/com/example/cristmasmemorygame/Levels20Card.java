package com.example.cristmasmemorygame;

import android.os.Bundle;

public class Levels20Card extends BaseLevel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int[] all_cards = new int[]{
                R.id.container1,
                R.id.container2,
                R.id.container3,
                R.id.container4,
                R.id.container5,
                R.id.container6,
                R.id.container7,
                R.id.container8,
                R.id.container9,
                R.id.container10,
                R.id.container11,
                R.id.container12,
                R.id.container13,
                R.id.container14,
                R.id.container15,
                R.id.container16,
                R.id.container17,
                R.id.container18,
                R.id.container19,
                R.id.container20
        };

        init("ScoresHard", "HardLevelNumber", 10, all_cards, R.layout.activity_levels_20_card);
        super.onCreate(savedInstanceState);
    }
}