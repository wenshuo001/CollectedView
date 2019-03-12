package com.example.collectedview.UI;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collectedview.MainActivity;
import com.example.collectedview.R;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Creator :Wen
 * DataTime: 2019/3/12
 * Description:
 */
public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton play, pause, stop, volume_plus, volume_decrease;
    private TextView musicName, musicLength, musicCur;
    private SeekBar seekBar;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    private AudioManager audioManager;

    private Timer timer;

    int maxVolume, currentVolume;

    private boolean isSeekBarChanging;//互斥变量，防止进度条与定时器冲突。
    private int currentPosition;//当前音乐播放的进度

    SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);

        format = new SimpleDateFormat("mm:ss");

        play = (ImageButton) findViewById(R.id.play);
        pause = (ImageButton) findViewById(R.id.pause);
        stop = (ImageButton) findViewById(R.id.stop);
        volume_plus = (ImageButton) findViewById(R.id.volume_plus);
        volume_decrease = (ImageButton) findViewById(R.id.volume_decrease);

        musicName = (TextView) findViewById(R.id.music_name);
        musicLength = (TextView) findViewById(R.id.music_length);
        musicCur = (TextView) findViewById(R.id.music_cur);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new MySeekBar());

        play.setOnClickListener(MusicActivity.this);
        pause.setOnClickListener(MusicActivity.this);
        stop.setOnClickListener(MusicActivity.this);
        volume_plus.setOnClickListener(MusicActivity.this);
        volume_decrease.setOnClickListener(MusicActivity.this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }else {
            initMediaPlayer();//初始化mediaplayer
        }
//        if (ContextCompat.checkSelfPermission(MusicActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
//                PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MusicActivity.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        } else {
//            //initMediaPlayer();//初始化mediaplayer
//        }

    }
    private AssetManager assetManager;
    private void initMediaPlayer() {
        try {
            assetManager = getAssets();
            AssetFileDescriptor fileDescriptor = assetManager.openFd("test1.mp3");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getStartOffset());//指定音频文件的路径
            mediaPlayer.prepare();//让mediaplayer进入准备状态
            mediaPlayer.setLooping(true);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    seekBar.setMax(mediaPlayer.getDuration());
                    musicLength.setText(format.format(mediaPlayer.getDuration()) + "");
                    musicCur.setText("00:00");
                    musicName.setText("music.mp3");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 &&
//                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//                    Toast.makeText(MusicActivity.this, "denied access", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                break;
//            default:
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();//开始播放
                    mediaPlayer.seekTo(currentPosition);

                    //监听播放时回调函数
                    timer = new Timer();
                    timer.schedule(new TimerTask() {

                        Runnable updateUI = new Runnable() {
                            @Override
                            public void run() {
                                musicCur.setText(format.format(mediaPlayer.getCurrentPosition()) + "");
                            }
                        };

                        @Override
                        public void run() {
                            if (!isSeekBarChanging) {
                                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                runOnUiThread(updateUI);
                            }
                        }
                    }, 0, 50);
                }
                break;
            case R.id.pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();//暂停播放
                }
                break;
            case R.id.stop:
                Toast.makeText(MusicActivity.this, "停止播放", Toast.LENGTH_SHORT).show();
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();//停止播放
                    initMediaPlayer();
                }
                break;
            //音量加
            case R.id.volume_plus:
                maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                Toast.makeText(MusicActivity.this, "音量增加,最大音量是：" + maxVolume + "，当前音量" + currentVolume,
                        Toast.LENGTH_SHORT).show();
                break;
            //音量减
            case R.id.volume_decrease:
                maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                Toast.makeText(MusicActivity.this, "音量减小,最大音量是：" + maxVolume + "，当前音量" + currentVolume,
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isSeekBarChanging = true;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /*进度条处理*/
    public class MySeekBar implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
        }

        /*滚动时,应当暂停后台定时器*/
        public void onStartTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = true;
        }

        /*滑动结束后，重新设置值*/
        public void onStopTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = false;
            mediaPlayer.seekTo(seekBar.getProgress());
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("权限已申请");
                initMediaPlayer();
            } else {
                showToast("权限已拒绝");
            }
        }else if (requestCode == 2){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(MusicActivity.this, permissions[i]);
                    if (showRequestPermission) {
                        showToast("权限未申请");
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showToast(String string){
        Toast.makeText(MusicActivity.this,string,Toast.LENGTH_LONG).show();
    }
}

