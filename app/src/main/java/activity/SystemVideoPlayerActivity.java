package activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.atguigu.mediaplayer.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bean.LocalVideoInfo;
import utils.Utils;

public class SystemVideoPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PROGRESS = 0;
    private VideoView video_player;
    private ArrayList<LocalVideoInfo> videoInfos;
    private int position;
    private LinearLayout llTop;
    private TextView tvName;
    private ImageView ivBattery;
    private TextView tvSystemTime;
    private Button btnVoice;
    private SeekBar seekbarVoice;
    private Button btnSwitchPlayer;
    private LinearLayout llBottom;
    private TextView tvCurrentTime;
    private SeekBar seekbarVideo;
    private TextView tvDuration;
    private Button btnExit;
    private Button btnPre;
    private Button btnStartPause;
    private Button btnNext;
    private Button btnSwitchScreen;
    private Utils utils;
    private BroadCastReceiver receiver;
    private Uri uri;
    private GestureDetector detector;
    private static final int DELAYED_HIDECONTROL_MESSAGE = 1;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-05-22 03:56:56 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        llTop = (LinearLayout) findViewById(R.id.ll_top);
        tvName = (TextView) findViewById(R.id.tv_name);
        ivBattery = (ImageView) findViewById(R.id.iv_battery);
        tvSystemTime = (TextView) findViewById(R.id.tv_system_time);
        btnVoice = (Button) findViewById(R.id.btn_voice);
        seekbarVoice = (SeekBar) findViewById(R.id.seekbar_voice);
        btnSwitchPlayer = (Button) findViewById(R.id.btn_switch_player);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        tvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        seekbarVideo = (SeekBar) findViewById(R.id.seekbar_video);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        btnExit = (Button) findViewById(R.id.btn_exit);
        btnPre = (Button) findViewById(R.id.btn_pre);
        btnStartPause = (Button) findViewById(R.id.btn_start_pause);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnSwitchScreen = (Button) findViewById(R.id.btn_switch_screen);
        utils = new Utils();

        btnVoice.setOnClickListener(this);
        btnSwitchPlayer.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnStartPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnSwitchScreen.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-05-22 03:56:56 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == btnVoice) {
            // Handle clicks for btnVoice
        } else if (v == btnSwitchPlayer) {
            // Handle clicks for btnSwitchPlayer
        } else if (v == btnExit) {
            // Handle clicks for btnExit
        } else if (v == btnPre) {
            setPreVideo();
            // Handle clicks for btnPre
        } else if (v == btnStartPause) {
            startPauserplayer();
            // Handle clicks for btnStartPause
        } else if (v == btnNext) {
            setNextVideo();
            // Handle clicks for btnNext
        } else if (v == btnSwitchScreen) {
            finish();
            // Handle clicks for btnSwitchScreen
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_video_player);
        video_player = (VideoView) findViewById(R.id.vv);

        getData();
        initData();
        findViews();
        setListener();
        setData();
        //video_player.setMediaController(new MediaController(this));
        detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                Toast.makeText(SystemVideoPlayerActivity.this, "长按了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(SystemVideoPlayerActivity.this, "双击了", Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                judge();

                Toast.makeText(SystemVideoPlayerActivity.this, "单机了", Toast.LENGTH_SHORT).show();
                return super.onSingleTapConfirmed(e);
            }

        });
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;

    }
    /**
     * 屏幕的高
     */
    private int screenHeight;
    private int screenWidth;
    //视频的原生的宽和高
    private int videoWidth;
    private int videoHeight;

    boolean isShowControlPlayer = true;
    private void judge(){
        if (!isShowControlPlayer){
            showControlPlayer();
        }else {
            hideControlPlayer();
        }
        handler.sendEmptyMessageDelayed(DELAYED_HIDECONTROL_MESSAGE,4000);
    }
    private void showControlPlayer() {
        handler.removeCallbacksAndMessages(DELAYED_HIDECONTROL_MESSAGE);
        llTop.setVisibility(View.VISIBLE);
        llBottom.setVisibility(View.VISIBLE);
        handler.sendEmptyMessageDelayed(DELAYED_HIDECONTROL_MESSAGE,4000);
        isShowControlPlayer = true;
    }

    private void hideControlPlayer() {
        llTop.setVisibility(View.GONE);
        llBottom.setVisibility(View.GONE);
        isShowControlPlayer = false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        detector.onTouchEvent(event);
        return true;
    }

    private void startPauserplayer() {
        if (video_player.isPlaying()) {
            video_player.pause();
            btnStartPause.setBackgroundResource(R.drawable.btn_start_selector);
        } else {
            video_player.start();
            btnStartPause.setBackgroundResource(R.drawable.btn_pause_selector);
        }
    }


    private void setData() {
        if (videoInfos != null && videoInfos.size() > 0) {
            LocalVideoInfo videoInfo = videoInfos.get(position);
            tvName.setText(videoInfo.getName());
            video_player.setVideoPath(videoInfo.getData());

        } else {
            video_player.setVideoURI(uri);
        }

    }

    private void getData() {
        uri = getIntent().getData();

        videoInfos = (ArrayList<LocalVideoInfo>) getIntent().getSerializableExtra("videolist");
        position = getIntent().getIntExtra("position", 0);
    }

    private void initData() {
        receiver = new BroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);
    }

    class BroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            if (level <= 0) {
                ivBattery.setImageResource(R.drawable.ic_battery_0);
            } else if (level <= 10) {
                ivBattery.setImageResource(R.drawable.ic_battery_10);
            } else if (level <= 20) {
                ivBattery.setImageResource(R.drawable.ic_battery_20);
            } else if (level <= 40) {
                ivBattery.setImageResource(R.drawable.ic_battery_40);
            } else if (level <= 60) {
                ivBattery.setImageResource(R.drawable.ic_battery_60);
            } else if (level <= 80) {
                ivBattery.setImageResource(R.drawable.ic_battery_80);
            } else if (level <= 100) {
                ivBattery.setImageResource(R.drawable.ic_battery_100);
            }
        }
    }

    private void setListener() {
        video_player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                int duration = video_player.getDuration();
                seekbarVideo.setMax(duration);
                tvDuration.setText(utils.stringForTime(duration));
                tvCurrentTime.setText(utils.stringForTime(duration));
                video_player.start();
                handler.sendEmptyMessage(PROGRESS);
            }
        });
        video_player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        video_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //finish();
                setNextVideo();
            }
        });
        seekbarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    video_player.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setPreVideo() {
        position--;
        if (position > 0) {
            LocalVideoInfo videoInfo = videoInfos.get(position);
            video_player.setVideoPath(videoInfo.getData());
            tvName.setText(videoInfo.getName());
            setButtonStatus();
        }
    }

    private void setNextVideo() {
        position++;
        if (position < videoInfos.size()) {
            LocalVideoInfo videoInfo = videoInfos.get(position);
            video_player.setVideoPath(videoInfo.getData());
            tvName.setText(videoInfo.getName());
            setButtonStatus();
        } else {
            finish();
        }
    }

    private void setButtonStatus() {
        if (videoInfos != null && videoInfos.size() > 0) {
            setEnable(true);
            if (position == 0) {
                btnPre.setBackgroundResource(R.drawable.btn_pre_gray);
                btnPre.setEnabled(false);
            }
            if (position == videoInfos.size() - 1) {
                btnNext.setBackgroundResource(R.drawable.btn_next_gray);
                btnNext.setEnabled(false);
            }
        } else if (uri != null) {
            setEnable(false);
        }
    }

    private void setEnable(boolean b) {
        if (b) {
            btnNext.setBackgroundResource(R.drawable.btn_next_selector);
            btnPre.setBackgroundResource(R.drawable.btn_pre_selector);
        } else {
            btnNext.setBackgroundResource(R.drawable.btn_next_gray);
            btnPre.setBackgroundResource(R.drawable.btn_pre_gray);
        }
        btnNext.setEnabled(b);
        btnPre.setEnabled(b);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PROGRESS:
                    int currentPosition = video_player.getCurrentPosition();
                    seekbarVideo.setProgress(currentPosition);
                    tvSystemTime.setText(getSystemTime());
                    sendEmptyMessageDelayed(PROGRESS, 1000);
                    break;
                case DELAYED_HIDECONTROL_MESSAGE:
                    hideControlPlayer();
                    break;
            }
        }
    };

    private String getSystemTime() {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());

    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;

        }
        super.onDestroy();
    }
}
