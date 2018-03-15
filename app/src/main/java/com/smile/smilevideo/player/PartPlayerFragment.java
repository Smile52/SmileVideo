package com.smile.smilevideo.player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smile.smilevideo.R;
import com.smile.smilevideo.base.BaseFragment;
import com.smile.smilevideo.widget.media.AndroidMediaController;
import com.smile.smilevideo.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**半屏播放器
 * Created by yaojiulong on 2018/3/14.
 */

public class PartPlayerFragment extends BaseFragment {
    private IjkVideoView videoView;
    private AndroidMediaController mediaController;
    private boolean backPressed;
    private ImageView mChange;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_part_player , container , false);
    }

    @Override
    protected void initFindViewById(View view) {
        videoView = (IjkVideoView) view.findViewById(R.id.part_player_video);
        mChange = (ImageView) view.findViewById(R.id.part_change_img);
    }

    @Override
    public void initData() {
        super.initData();
        initSomething();
        play();
    }

    private void initSomething(){
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

    }

    @Override
    public void setListener() {
        super.setListener();
        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void play(){
        videoView.setVideoPath("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=56758&editionType=high&source=ucloud");
        videoView.start();
    }


    @Override
    public void onPause() {
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




}
