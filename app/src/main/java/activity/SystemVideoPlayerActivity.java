package activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.atguigu.mediaplayer.R;

public class SystemVideoPlayerActivity extends AppCompatActivity {
    private VideoView video_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_video_player);
        video_player = (VideoView) findViewById(R.id.video_player);
        Uri uri = getIntent().getData();
        video_player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                video_player.start();
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
                finish();

            }
        });
        video_player.setVideoURI(uri);
        video_player.setMediaController(new MediaController(this));
    }
}
