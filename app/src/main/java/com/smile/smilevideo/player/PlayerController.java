package com.smile.smilevideo.player;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;
import com.smile.smilevideo.widget.media.IjkVideoView;

/**
 * Created by yaojiulong on 2018/3/16.
 */

public class PlayerController {
    public static final int SIZE_DEFAULT=0;
    public static final int SIZE_4_3=1;
    public static final  int SIZE_16_9 =2;
    public static final int SIZE_18_9=3;
    public Activity mActivity;
    private int mScreenHeight, mScreenWidth;

    public PlayerController(Activity activity) {
        this.mActivity = activity;
        Resources resources = mActivity.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Logger.e("width  "+width);
        Logger.e("height "+height);
        mScreenHeight =height;
        mScreenWidth = width;
    }

    public void setScreenRate(int rate , IjkVideoView video) {
        int width = 0;
        int height = 0;
        if (mActivity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {// 横屏
            if (rate == SIZE_DEFAULT) {
                width = video.getmVideoWidth();
                height = video.getmVideoHeight();
            } else if (rate == SIZE_4_3) {
                width = mScreenHeight / 3 * 4;
                height = mScreenHeight;
            } else if (rate == SIZE_16_9) {
                width = mScreenHeight / 9 * 16;
                height = mScreenHeight;
            } else if (rate == SIZE_18_9){
                width = mScreenHeight / 9 * 18;
                height = mScreenHeight;
            }
        } else { //竖屏
            if (rate == SIZE_DEFAULT) {
                width = video.getmVideoWidth();
                height = video.getmVideoHeight();
            } else if (rate == SIZE_4_3) {
                width = mScreenWidth;
                height = mScreenWidth * 3 / 4;
            } else if (rate == SIZE_16_9) {
                width = mScreenWidth;
                height = mScreenWidth * 9 / 16;
            }else if (rate == SIZE_18_9){
                width = mScreenWidth * 9 / 18;
                height = mScreenWidth ;
            }
        }
        if (width > 0 && height > 0) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) video.getmRenderView().getView().getLayoutParams();
            lp.width = mScreenHeight;
            lp.height = mScreenWidth;
            Logger.e("lp width "+width+"  he  "+height);
            video.getmRenderView().getView().setLayoutParams(lp);
        }
    }

}
