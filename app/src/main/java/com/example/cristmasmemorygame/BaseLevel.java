package com.example.cristmasmemorygame;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cristmasmemorygame.enums.AdsType;
import com.example.cristmasmemorygame.managers.AdsManager;
import com.luolc.emojirain.EmojiRainLayout;

import java.util.Random;

@SuppressLint("Registered")
public class BaseLevel extends BaseActivity {

    private final String CURRENT_CARD = "CurrentCard";
    private final String OPENED_CARD = "OpenedCard";
    private final String CURRENT_CONTAINER = "CurrentContainer";
    private final String LAST_CONTAINER = "LastContainer";
    private String SCORES;
    private String LEVEL_NUMBER;

    private int counter;
    private int[] pictures;
    private int[] all_cards;
    private EmojiRainLayout mContainer;
    private ImageView results;
    private TextView results_text;
    private Button btn_next;
    private AdsManager adsManager;

    private TextView level;
    private TextView scores_number;
    private ImageView deer;
    private int finish_level;
    private int current_scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        pictures = new int[]{
                R.layout.cards_boy,
                R.layout.cards_candies,
                R.layout.cards_dedmoroz,
                R.layout.cards_elka,
                R.layout.cards_kolokola,
                R.layout.cards_mishka,
                R.layout.cards_olenishka,
                R.layout.cards_pinguin,
                R.layout.cards_snegovik,
                R.layout.cards_snegovik_2,
                R.layout.cards_snegovik_3,
                R.layout.cards_snowball,
                R.layout.cards_wreth,
                R.layout.cards_giftbox,
                R.layout.cards_ginger,
                R.layout.cards_nicesanta,
                R.layout.cards_elf,
                R.layout.cards_owl,
                R.layout.cards_panda,
                R.layout.cards_dog,
                R.layout.cards_umka
        };

        int[] backgrounds = new int[]{
                R.layout.fragment_card_back,
                R.layout.fragment_card_back2,
                R.layout.fragment_card_back3,
                R.layout.fragment_card_back4,
                R.layout.fragment_card_back5,
        };


        shuffleArray(backgrounds);
        String CARD_BACK = "CardBack";
        Root.setInteger(CARD_BACK, backgrounds[0]);
        Root.setInteger(CURRENT_CARD, 0);
        Root.setInteger(OPENED_CARD, 1);
        adsManager = new AdsManager(getApplicationContext(), AdsType.Banner);

        prepareScreen();
        setFallingPics();
        setCardsBacks();
        randomizeCards();
        startScreen(0);
    }

    public void init(String scores, String levelNumber, int finishLevel, int[] allCards, int activityLayout) {
        SCORES = scores;
        LEVEL_NUMBER = levelNumber;
        finish_level = finishLevel;
        all_cards = allCards;
        setContentView(activityLayout);
    }

    private void animateBack(final int container) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.card_flip_right_enter, R.animator.card_flip_right_exit,
                                R.animator.card_flip_left_enter, R.animator.card_flip_left_exit)
                        .replace(container, new CardFrontFragment())
                        .addToBackStack(null)
                        .commit();
            }
        }, 500);
    }

    private void explodeCards(int card_one, int card_two) {
        final FrameLayout one = findViewById(card_one);
        final FrameLayout two = findViewById(card_two);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation scale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
                one.startAnimation(scale);
                two.startAnimation(scale);
            }
        }, 300);
    }

    private void checkForFinish(String scores_level, String level_number, String score) {
        if (finish_level == 0) {
            showPlayersResult(scores_level, level_number, score);
        }
    }

    private void showPlayersResult(String scores_level, String level_number, final String score) {
        int nextlevel = Root.getInteger(level_number, 1) + 1;
        Root.setInteger(level_number, nextlevel);
        int scores = Integer.parseInt(score);
        Root.setInteger(scores_level, scores);
        setDropping(15, 20000, 2400, 600);
        mContainer.startDropping();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                results.setVisibility(View.VISIBLE);
                results_text.setText(score);
                results_text.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.VISIBLE);

            }
        }, 1000);
    }

    private void setDropping(int setPer, int duration, int dropDuration, int frequency) {
        mContainer.setPer(setPer);
        mContainer.setDuration(duration);
        mContainer.setDropDuration(dropDuration);
        mContainer.setDropFrequency(frequency);
    }

    private void enableCards(int currentContainer, int lastContainer) {
        FrameLayout one = findViewById(currentContainer);
        one.setEnabled(true);
        FrameLayout two = findViewById(lastContainer);
        two.setEnabled(true);
    }

    private void randomizeCards() {
        shuffleArray(all_cards);
        shuffleArray(pictures);
    }

    private void shuffleArray(int[] array) {
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private void toNextLevel(BaseLevel activityClass) {
        Intent intent = new Intent(this, activityClass.getClass());
        this.finish();
        startActivity(intent);
        overridePendingTransition(R.anim.start_activity_on, R.anim.start_activity_off);
    }

    private void setFallingPics() {
        mContainer.addEmoji(R.drawable.emoji_1_3);
        mContainer.addEmoji(R.drawable.emoji_2_3);
        mContainer.addEmoji(R.drawable.emoji_3_3);
        mContainer.addEmoji(R.drawable.emoji_4_3);
        mContainer.addEmoji(R.drawable.emoji_5_3);
    }

    private void setCardsBacks() {
        for (int all_card : all_cards) {
            getFragmentManager()
                    .beginTransaction()
                    .add(all_card, new CardFrontFragment())
                    .commit();
        }
    }

    private void prepareScreen() {
        LinearLayout bannerLayout = findViewById(R.id.adView);
        bannerLayout.addView(adsManager.getBannerView());

        Typeface snowtop = Typeface.createFromAsset(getAssets(), "fonts/snowtop.ttf");
        Typeface kerst = Typeface.createFromAsset(getAssets(), "fonts/kerst.ttf");

        level = findViewById(R.id.level_number);
        level.setTypeface(kerst);
        level.setTextColor(Color.GREEN);

        TextView text_level = findViewById(R.id.text_level);
        text_level.setTextColor(Color.BLUE);

        TextView text_scores = findViewById(R.id.text_scores);
        text_scores.setTextColor(Color.RED);

        results_text = findViewById(R.id.text_show_results);
        results_text.setVisibility(View.INVISIBLE);
        results_text.setTextColor(Color.RED);
        results_text.setTypeface(snowtop);

        results = findViewById(R.id.resuls_image);
        results.setVisibility(View.INVISIBLE);

        btn_next = findViewById(R.id.btn_next);
        btn_next.setVisibility(View.INVISIBLE);

        scores_number = findViewById(R.id.scores_number);
        scores_number.setTextColor(Color.CYAN);
        scores_number.setTypeface(kerst);

        mContainer = findViewById(R.id.group_emoji_container);

        deer = findViewById(R.id.deer);
        deer.setVisibility(View.INVISIBLE);
    }

    public void onButtonClick(View view) {
        toNextLevel(this);
    }

    private View.OnClickListener oclBtn = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            counter++;
            FrameLayout currentLay = (FrameLayout) view;
            currentLay.setEnabled(false);
            prepareToCheckMatches(view);
            checkForFinish(SCORES, LEVEL_NUMBER, scores_number.getText().toString());
        }
    };

    private void prepareToCheckMatches(View view) {
        int temp_card = view.getId();
        if (temp_card == all_cards[0] || temp_card == all_cards[1]) {
            animateAndCheck(view, pictures[0]);
        } else if (temp_card == all_cards[2] || temp_card == all_cards[3]) {
            animateAndCheck(view, pictures[1]);
        } else if (temp_card == all_cards[4] || temp_card == all_cards[5]) {
            animateAndCheck(view, pictures[2]);
        } else if (temp_card == all_cards[6] || temp_card == all_cards[7]) {
            animateAndCheck(view, pictures[3]);
        } else if (temp_card == all_cards[8] || temp_card == all_cards[9]) {
            animateAndCheck(view, pictures[4]);
        } else if (temp_card == all_cards[10] || temp_card == all_cards[11]) {
            animateAndCheck(view, pictures[5]);
        } else if (temp_card == all_cards[12] || temp_card == all_cards[13]) {
            animateAndCheck(view, pictures[6]);
        } else if (temp_card == all_cards[14] || temp_card == all_cards[15]) {
            animateAndCheck(view, pictures[7]);
        } else if (temp_card == all_cards[16] || temp_card == all_cards[17]) {
            animateAndCheck(view, pictures[8]);
        } else if (temp_card == all_cards[18] || temp_card == all_cards[19]) {
            animateAndCheck(view, pictures[9]);
        }
    }

    private void animateAndCheck(View view, int image_resource) {
        Root.setInteger(CURRENT_CONTAINER, view.getId());
        Root.setInteger(CURRENT_CARD, image_resource);
        animate(view.getId());
        timeToCheck();
        Root.setInteger(OPENED_CARD, image_resource);
        Root.setInteger(LAST_CONTAINER, view.getId());
    }

    private void animate(int container) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.card_flip_right_enter, R.animator.card_flip_right_exit,
                        R.animator.card_flip_left_enter, R.animator.card_flip_left_exit)
                .replace(container, new CardBackFragment())
                .addToBackStack(null)
                .commit();
    }

    private void timeToCheck() {
        if (counter == 2) {
            stopScreen();
            checkMatches();
            startScreen(600);
        }
    }

    private void stopScreen() {
        for (int all_card : all_cards) {
            FrameLayout tempLayout = findViewById(all_card);
            if (tempLayout != null) {
                tempLayout.setOnClickListener(null);
            }
        }
    }

    private void checkMatches() {
        if (Root.getInteger(CURRENT_CARD, 0) == Root.getInteger(OPENED_CARD, 1)) {
            setDropping(10, 500, 1000, 500);
            mContainer.startDropping();
            counter = 0;

            if (Root.getInteger(CURRENT_CARD, 0) == R.layout.cards_olenishka
                    && Root.getInteger(OPENED_CARD, 1) == R.layout.cards_olenishka) {
                Animation show_deer = AnimationUtils.loadAnimation(this, R.anim.show_deer);
                deer.startAnimation(show_deer);
            }

            explodeCards(Root.getInteger(CURRENT_CONTAINER, 0), Root.getInteger(LAST_CONTAINER, 0));
            finish_level--;
            current_scores += 5;
            scores_number.setText(String.valueOf(current_scores));
        }

        if (Root.getInteger(CURRENT_CARD, 0) != Root.getInteger(OPENED_CARD, 1)) {
            animateBack(Root.getInteger(CURRENT_CONTAINER, 0));
            animateBack(Root.getInteger(LAST_CONTAINER, 0));
            counter = 0;
            enableCards(Root.getInteger(CURRENT_CONTAINER, 0), Root.getInteger(LAST_CONTAINER, 0));
        }
    }

    private void startScreen(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int all_card : all_cards) {
                    FrameLayout tempLayout = findViewById(all_card);
                    if (tempLayout != null) {
                        tempLayout.setOnClickListener(oclBtn);
                    }
                }
            }
        }, delay);
    }

    private void showExitDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.DialogText);
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
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        current_scores = current_scores + Root.getInteger(SCORES, 0);
        level.setText(String.valueOf(Root.getInteger(LEVEL_NUMBER, 1)));
        scores_number.setText(String.valueOf(Root.getInteger(SCORES, 0)));
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }
}

