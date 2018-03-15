package com.smile.smilevideo.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by yaojiulong on 2018/3/15.
 */

public class PlayerFrameLayout extends FrameLayout {

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    private Context mContext;
    private int mScreenWidth, mScreenHeight;
    private OnLayoutMoveListener mMoveListener;

    public PlayerFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public PlayerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        this.mContext= context;
       // Log.e("dandy","eeeeee1");

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        Logger.e("width  "+mScreenWidth);
        Logger.e("height "+mScreenHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // Log.e("dandy","eeeeee");
        switch (event.getAction()){
            //按下

            case MotionEvent.ACTION_DOWN:
                Log.e("dandy","111");
                //当手指按下的时候
                x1 = event.getX();
                y1 = event.getY();

                break;
            //移动
            case MotionEvent.ACTION_MOVE:
               // Log.e("dandy","www");
                break;

            //抬起
            case MotionEvent.ACTION_UP:
                Log.e("dandy","dandy,,");
                //当手指离开的时候
                x2 = event.getX();
                y2 = event.getY();
                if(y1 - y2 > 50 && isLandscape()) {
                    Log.e("dandy","向上滑");
                    //Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                    if (x2 < mScreenHeight/2){
                        Logger.e("左边向上滑动");
                        mMoveListener.onLeftMoveUp(y1-y2);
                    }else {
                        mMoveListener.onRightMoveUp(y1-y2);
                        Logger.e("右边向上滑动");

                    }
                } else if(y2 - y1 > 50 && isLandscape()) {
                    Log.e("dandy","向下滑");

                    if (x2 < mScreenHeight/2){
                        mMoveListener.onLeftMoveDown(y2-y1);
                        Logger.e("左边向下滑动");
                    }else {
                        mMoveListener.onRightMoveDown(y2-y1);
                        Logger.e("右边向下滑动");

                    }
                   // Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                } else if(x1 - x2 > 50 && isLandscape()) {
                    Log.e("dandy","向左滑");
                   // Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
                } else if(x2 - x1 > 50 && isLandscape()) {
                    Log.e("dandy","向右滑");
                  //  Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
                }
                break;

        }

        return true;

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断是不是横屏
     * @return
     */
    private boolean isLandscape(){
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            return false;
        } else {
            //横屏
            return true;
        }
    }

    public void setMoveListener(OnLayoutMoveListener listener){
        this.mMoveListener = listener;
    }


   public   interface OnLayoutMoveListener {
        void onLeftMoveUp(float distance);
        void onLeftMoveDown( float distance);
        void onRightMoveUp( float distance);
        void onRightMoveDown( float distance);
    }
}
