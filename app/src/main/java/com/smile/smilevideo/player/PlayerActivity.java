package com.smile.smilevideo.player;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.smile.smilevideo.R;
import com.smile.smilevideo.base.BaseActivity;
import com.smile.smilevideo.widget.media.AndroidMediaController;
import com.smile.smilevideo.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**视频播放页面
 * Created by yaojiulong on 2017/10/17.
 */

public class PlayerActivity extends BaseActivity {
    private IjkVideoView videoView;
    private AndroidMediaController mediaController;
    private boolean backPressed;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_player);
        initSomething();

        play();
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
}
