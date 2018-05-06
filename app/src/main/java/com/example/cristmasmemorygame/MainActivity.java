package com.example.cristmasmemorygame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cristmasmemorygame.managers.MusicManager;

public class MainActivity extends BaseActivity {
    Button easy;
    Button medium;
    Button hard;
    Button gifts;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);
        gifts = findViewById(R.id.gifts);
        easy.setOnClickListener(myListener);
        medium.setOnClickListener(myListener);
        hard.setOnClickListener(myListener);
        gifts.setOnClickListener(myListener);

        MusicManager.play(this, R.raw.jinglebells);
}
    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.easy:
                    Root.setInteger("LayoutToLoad", R.layout.activity_levels_6_cards);
                    break;
                case R.id.medium:
                    Root.setInteger("LayoutToLoad", R.layout.activity_levels_12_cards);
                    break;
                case R.id.hard:
                    Root.setInteger("LayoutToLoad", R.layout.activity_levels_20_card);
                    break;

            }
            if (view.getId() == R.id.gifts) {
                intent = new Intent(MainActivity.this, ActivityGifts.class);
                pushActivity(intent);
            } else {
                intent = new Intent(MainActivity.this, BaseLevel.class);
                pushActivity(intent);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicManager.dispose();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
        quitDialog.setMessage(R.string.QuitGame);
        quitDialog.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        quitDialog.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
       quitDialog.show();
    }
}
