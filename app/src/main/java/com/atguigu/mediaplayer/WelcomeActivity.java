package com.atguigu.mediaplayer;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class WelcomeActivity extends AppCompatActivity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startMainActivity();

                }
            }, 2000);


    }

    boolean flag = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if(flag){
            flag = false;
            handler.removeCallbacksAndMessages(null);
            startMainActivity();
        }
        return true;
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }
}
