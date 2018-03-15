package com.smile.smilevideo.player;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toolbar;

import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBarWrapper;
import com.orhanobut.logger.Logger;
import com.smile.smilevideo.R;
import com.smile.smilevideo.base.BaseActivity;
import com.smile.smilevideo.home.HomeFragment;
import com.smile.smilevideo.view.PlayerFrameLayout;
import com.smile.smilevideo.widget.media.AndroidMediaController;
import com.smile.smilevideo.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**视频播放器宿主
 * Created by yaojiulong on 2017/10/17.
 */

public class PlayerActivity extends BaseActivity  {
    private IjkVideoView videoView;
    private AndroidMediaController mediaController;
    private boolean backPressed;

    private FragmentManager mManager;
    private FragmentTransaction mTransaction;

    private AllPlayerFragment mAllPlayerFragment;
    private PartPlayerFragment mPartPlayerFragment;
    private View mChange;
    private LinearLayout mContentLayout;
    private android.support.v7.widget.Toolbar mToolbar;
    private SeekBar mVideoSeekBar;
    private PlayerFrameLayout mPlayerLayout;
    private int screenBrightness;//亮度
    private int screenMode;
    private VerticalSeekBarWrapper mLightWrapper, mSoundWrapper;
    private VerticalSeekBar mLightSeekbar, mSoundSeekbar;
    private int soundAudio;
    private AudioManager audioManager;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_player);
        initSomething();
        addToolbar();
        play();

        //showDefaultFragment();
    }

    @Override
    protected void findViewId() {
        super.findViewId();
        mChange =findViewById(R.id.change_img);
        mContentLayout = (LinearLayout) findViewById(R.id.player_content_layout);
        mVideoSeekBar = (SeekBar) findViewById(R.id.play_video_seekbar);
        mPlayerLayout = (PlayerFrameLayout) findViewById(R.id.player_frame_layout);
        mLightWrapper = (VerticalSeekBarWrapper) findViewById(R.id.player_light_wrapper);
        mSoundWrapper = (VerticalSeekBarWrapper) findViewById(R.id.player_sound_wrapper);
        mSoundSeekbar = (VerticalSeekBar) findViewById(R.id.player_sound_seekbar);
        mLightSeekbar = (VerticalSeekBar) findViewById(R.id.player_light_seekbar);

        mToolbar = getToolbar();
    }



    @Override
    protected void initData() {
        super.initData();
        mToolbar.setTitle("Video");
        mUiHandler.sendEmptyMessageDelayed(0, 200);
        try {
            screenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            screenMode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        soundAudio = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
      //  soundMode = audioManager.getMode();

        mLightSeekbar.setMax(255);
        mLightSeekbar.setProgress(screenBrightness);

        mSoundSeekbar.setMax(15);
        mSoundSeekbar.setProgress(soundAudio);
        if (screenMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            setScreenMode(Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }
    }

    Handler mUiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  0:
                    if (videoView.getDuration()>0){
                        mVideoSeekBar.setMax(videoView.getDuration());
                        mVideoSeekBar.setProgress(videoView.getCurrentPosition());
                    }
                    mUiHandler.sendEmptyMessageDelayed(0, 200);
                    break;
            }
        }
    };

    @Override
    protected void setListener() {
        super.setListener();
        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PlayerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //竖屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    //横屏
                    mToolbar.setVisibility(View.VISIBLE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }

            }
        });
        mVideoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekPos = seekBar.getProgress();
                videoView.seekTo(seekPos);
            }
        });

        mPlayerLayout.setMoveListener(new PlayerFrameLayout.OnLayoutMoveListener() {

            @Override
            public void onLeftMoveUp(float distance) {

                mLightWrapper.setVisibility(View.VISIBLE);
                int i = mLightSeekbar.getProgress();
                mLightSeekbar.setProgress(i+(int) distance/b);
                setScreenBrightness(mLightSeekbar.getProgress());
                hideSeekbar(mLightWrapper);
            }

            @Override
            public void onLeftMoveDown(float distance) {
                mLightWrapper.setVisibility(View.VISIBLE);
                int i = mLightSeekbar.getProgress();
                mLightSeekbar.setProgress(i-(int) distance/b);
                setScreenBrightness(mLightSeekbar.getProgress());
                hideSeekbar(mLightWrapper);
            }

            @Override
            public void onRightMoveUp(float distance) {
                mSoundWrapper.setVisibility(View.VISIBLE);
                int i =mSoundSeekbar.getProgress();
                mSoundSeekbar.setProgress(i+ (int) distance/a);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mSoundSeekbar.getProgress(), AudioManager.FLAG_PLAY_SOUND);
                hideSeekbar(mSoundWrapper);
            }

            @Override
            public void onRightMoveDown(float distance) {
                mSoundWrapper.setVisibility(View.VISIBLE);
                int i =mSoundSeekbar.getProgress();
                mSoundSeekbar.setProgress(i- (int) distance/a);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mSoundSeekbar.getProgress(), AudioManager.FLAG_PLAY_SOUND);
                hideSeekbar(mSoundWrapper);
            }
        });
        if (screenMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            setScreenMode(Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }
        mLightSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekPos = seekBar.getProgress();
                Log.d("dandy", "屏幕亮度改变seekPos:" + seekPos);
                setScreenBrightness(seekPos);
            }
        });
        mSoundSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekPos = seekBar.getProgress();
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekPos, AudioManager.FLAG_PLAY_SOUND);
            }
        });

    }

    private void hideSeekbar(final VerticalSeekBarWrapper wrapper){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                wrapper.setVisibility(View.INVISIBLE);
            }
        }, 2000);//3秒后执行Runnable中的run方法
    }


    private void initSomething(){
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        videoView = (IjkVideoView) findViewById(R.id.videoView);


    }

    private void play(){
        videoView.setVideoPath("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=56758&editionType=high&source=ucloud");
        videoView.start();
    }


    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }


    @Override
    protected void permissionGrantedSuccess() {

    }

    @Override
    protected void permissionGrantedFail() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (backPressed || videoView.isBackgroundPlayEnabled()) {
            videoView.stopPlayback();
            videoView.release(true);
            videoView.stopBackgroundPlay();
        } else {
            videoView.stopBackgroundPlay();
        }
        videoView.pause();
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    public void onBackPressed() {
        backPressed = true;

        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//切换为横屏
            Log.e("dandy","切换为横屏");
            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mToolbar.setVisibility(View.GONE);
            videoView.setLayoutParams(lp);
           // mContentLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

            Resources resources = this.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            float density = dm.density;
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            Logger.e("width  "+width);
            Logger.e("height "+height);
            mScreenHeight =height;
            b=height/255;
            a=height/15;
            }else {

        }
    }
    private int b;
    private int a;

    private int mScreenHeight ;


    private void setScreenMode(int value) {
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, value);
    }

    private void setScreenBrightness(int value) {
        Window w = getWindow();
        WindowManager.LayoutParams l = w.getAttributes();
        l.screenBrightness = value;
        w.setAttributes(l);
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
    }
}
