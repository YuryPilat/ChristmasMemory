package com.example.cristmasmemorygame;

import android.os.Bundle;

public class Levels6Cards extends BaseLevel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int[] all_cards = new int[]{R.id.container1,
                R.id.container2,
                R.id.container3,
                R.id.container4,
                R.id.container5,
                R.id.container6};

        init("ScoresEasy", "LevelNumber", 3, all_cards, R.layout.activity_levels_6_cards);
        super.onCreate(savedInstanceState);
    }
}