package com.example.cristmasmemorygame;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cristmasmemorygame.enums.AdsType;
import com.example.cristmasmemorygame.managers.AdsManager;
import com.example.cristmasmemorygame.managers.MusicManager;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.robertsimoes.shareable.Shareable;

public class ActivityGifts extends BaseActivity {
    private final String SCORES_EASY = "ScoresEasy";
    private final String SCORES_MEDIUM = "ScoresMedium";
    private final String SCORES_HARD = "ScoresHard";
    private final String TOTAL_REWARD = "TotalReward";

    private AdsManager adsManager;
    String gifts;
    Button btn_video_reward;
    Button btn_share_gifts;
    int current_reward;
    int total_gifts;
    Boolean rewarded;
    TextView text_instantgifts;
    TextView send_gift;
    TextView you_collected;
    TextView easy_gifts_number;
    TextView easy_gifts_text;
    TextView medium_gifts_number;
    TextView medium_gifts_text;
    TextView hard_gifts_number;
    TextView hard_gifts_text;
    TextView total_gifts_number;
    TextView total_gifts_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifts);
        rewarded=false;

        setScreenElements();

        adsManager = new AdsManager(getApplicationContext(), AdsType.Video);
        adsManager.setVideoListener(new RewardedVideoAdListener() {
            @Override
            public void onRewarded(RewardItem rewardItem) {
                current_reward = rewardItem.getAmount();
                int temp = Root.getInteger(TOTAL_REWARD, 0)+current_reward;
                Root.setInteger(TOTAL_REWARD, temp);
                rewarded=true;
            }

            @Override
            public void onRewardedVideoAdLoaded() {
            btn_video_reward.setEnabled(true);
            btn_video_reward.setBackgroundResource(R.drawable.christmasbutton_play);
            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {
                MusicManager.pause();
            }

            @Override
            public void onRewardedVideoAdClosed() {
                btn_video_reward.setEnabled(false);
                btn_video_reward.setBackgroundResource(R.drawable.christmasbutton_inactive);
                adsManager.requestNewVideo();
                MusicManager.play();
                if (rewarded) {
                    Toast.makeText(ActivityGifts.this, R.string.RewardText, Toast.LENGTH_LONG).show();
                    Animation scale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_view);
                    total_gifts_number.startAnimation(scale);
                }
                rewarded=false;
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }
        });
    }

    public void onSocialClick(View view){
        switch (view.getId()){
            case R.id.btn_share_gifts:
                String GAME_URL = "https://play.google.com/store/apps/details?id=com.cristmasmemorygame";
                Shareable imageShare = new Shareable.Builder(this)
                        .message(R.string.Icollected + gifts + R.string.Gifts)
                        .url(GAME_URL)
                        .socialChannel(Shareable.Builder.ANY)
                        .build();
                imageShare.share();
                break;

            case R.id.btn_video_reward:
                adsManager.showVideo();
                break;
        }
    }

    private void setScreenElements() {
        you_collected = findViewById(R.id.text_youcollected);
        easy_gifts_number = findViewById(R.id.total_easy_gifts_number);
        easy_gifts_text= findViewById(R.id.total_easy_gifts_text);
        medium_gifts_number = findViewById(R.id.total_medium_gifts_number);
        medium_gifts_text = findViewById(R.id.total_medium_gifts_text);
        hard_gifts_number = findViewById(R.id.total_hard_gifts_number);
        hard_gifts_text= findViewById(R.id.total_hard_gifts_text);
        total_gifts_number = findViewById(R.id.total_gifts_number_number);
        total_gifts_text = findViewById(R.id.total_gifts_number_text);
        text_instantgifts = findViewById(R.id.text_instantgifts);
        btn_share_gifts = findViewById(R.id.btn_share_gifts);
        send_gift = findViewById(R.id.text_send);

        btn_video_reward = findViewById(R.id.btn_video_reward);
        btn_video_reward.setEnabled(false);
        btn_video_reward.setBackgroundResource(R.drawable.christmasbutton_inactive);

        Typeface kerst = Typeface.createFromAsset(getAssets(), "fonts/kerst.ttf");
        Typeface engine = Typeface.createFromAsset(getAssets(), "fonts/engine.otf");

        easy_gifts_number.setTypeface(kerst);
        medium_gifts_number.setTypeface(kerst);
        hard_gifts_number.setTypeface(kerst);
        total_gifts_number.setTypeface(kerst);
        text_instantgifts.setTypeface(engine);
        easy_gifts_text.setTypeface(engine);
        medium_gifts_text.setTypeface(engine);
        hard_gifts_text.setTypeface(engine);
        you_collected.setTypeface(engine);
        total_gifts_text.setTypeface(engine);
        send_gift.setTypeface(engine);
    }

    @Override
    protected void onStart() {
        super.onStart();
        easy_gifts_number.setText(String.valueOf(Root.getInteger(SCORES_EASY,0)));
        medium_gifts_number.setText(String.valueOf(Root.getInteger(SCORES_MEDIUM, 0)));
        hard_gifts_number.setText(String.valueOf(Root.getInteger(SCORES_HARD, 0)));
        total_gifts = Root.getInteger(SCORES_EASY,0)
                    + Root.getInteger(SCORES_MEDIUM, 0)
                    + Root.getInteger(SCORES_HARD, 0)
                    + Root.getInteger(TOTAL_REWARD, 0);
        total_gifts_number.setText(String.valueOf(total_gifts));
        gifts = total_gifts_number.getText().toString();
    }

    @Override
    protected void onResume() {
        adsManager.onResume(this);
        super.onResume();
        total_gifts = Root.getInteger(SCORES_EASY,0)
                    + Root.getInteger(SCORES_MEDIUM, 0)
                    + Root.getInteger(SCORES_HARD, 0)
                    + Root.getInteger(TOTAL_REWARD, 0);
        total_gifts_number.setText(String.valueOf(total_gifts));
        gifts = total_gifts_number.getText().toString();
    }

    @Override
    protected void onPause() {
        adsManager.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        adsManager.onDestroy(this);
        super.onDestroy();
    }
}
