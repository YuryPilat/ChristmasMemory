package com.example.cristmasmemorygame;

import android.os.Bundle;

public class Levels12Cards extends BaseLevel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int[] all_cards = new int[]{R.id.container1,
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
                R.id.container12};

        init("ScoresMedium", "MediumLevelNumber",  6, all_cards, R.layout.activity_levels_12_cards) ;
        super.onCreate(savedInstanceState);
    }
}