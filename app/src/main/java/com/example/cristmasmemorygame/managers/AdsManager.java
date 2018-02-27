package com.example.cristmasmemorygame.managers;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.cristmasmemorygame.enums.AdsType;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AdsManager {

    private AdView banner;
    private InterstitialAd interstitial;
    private RewardedVideoAd rewardedVideo;

    //AD's IDs are changed and will not work properly!
    private String AD_UNIT_ID = "ca-app-pub-3856915593704214";
    private String INTERSTITIAL_ID = "";
    private String BANNER_ID = "ca-app-pub-3856915593704214";
    private String VIDEO_ID = "ca-app-pub-3856915593704214";

    public AdsManager(Context context, AdsType type) {
        MobileAds.initialize(context, AD_UNIT_ID);

        switch (type){
            case Banner:
                banner = new AdView(context);
                banner.setAdSize(AdSize.BANNER);
                banner.setAdUnitId(BANNER_ID);
                requestNewBanner();
                break;
            case Interstitial:
                interstitial = new InterstitialAd(context.getApplicationContext());
                interstitial.setAdUnitId(INTERSTITIAL_ID);
                requestNewInterstitial();
                break;
            case Video:
                rewardedVideo = MobileAds.getRewardedVideoAdInstance(context.getApplicationContext());
                requestNewVideo();
                break;
        }
    }

    public void requestNewBanner() {
        AdRequest adRequest = getAdRequest();
        banner.loadAd(adRequest);
    }

    public void requestNewInterstitial() {
        AdRequest adRequest = getAdRequest();
        interstitial.loadAd(adRequest);
    }

    public void requestNewVideo() {
        AdRequest adRequest = getAdRequest();
        rewardedVideo.loadAd(VIDEO_ID, adRequest);
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder()
               .addTestDevice("B58A1240E267A6BD1BCE84B90B56EDB9") // Lenovo K10
                .build();
    }

    public void setBannerListener(AdListener adListener){
        banner.setAdListener(adListener);
    }

    public void setInterstitialListener(AdListener listener){
        interstitial.setAdListener(listener);
    }

    public void setVideoListener(RewardedVideoAdListener listener){
        rewardedVideo.setRewardedVideoAdListener(listener);
    }

    public AdView getBannerView(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        banner.setLayoutParams(params);
        return banner;
    }

    public boolean showInterstitial() {
        if (interstitial.isLoaded()){
            interstitial.show();
            return true;
        } else {
            return false;
        }
    }

    public boolean showVideo() {
        if (rewardedVideo.isLoaded()){
            rewardedVideo.show();
            return true;
        } else {
            return false;
        }
    }

    public void onResume(Context context) {
        rewardedVideo.resume(context);
    }

    public void onPause(Context context) {
        rewardedVideo.pause(context);
    }

    public void onDestroy(Context context) {
        rewardedVideo.destroy(context);
    }
}