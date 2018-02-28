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
                    intent = new Intent(MainActivity.this, Levels6Cards.class);
                    pushActivity(intent);
                    break;
                case R.id.medium:
                    intent = new Intent(MainActivity.this, Levels12Cards.class);
                    pushActivity(intent);
                    break;
                case R.id.hard:
                    intent = new Intent(MainActivity.this, Levels20Card.class);
                    pushActivity(intent);
                    break;
                case R.id.gifts:
                    intent = new Intent(MainActivity.this, ActivityGifts.class);
                    pushActivity(intent);
                    break;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.QuitGame);
        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
