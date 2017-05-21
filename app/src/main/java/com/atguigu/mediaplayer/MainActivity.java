package com.atguigu.mediaplayer;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import fragment.Fragment;
import page.LoadAudioPage;
import page.LoadVideoPage;
import page.NetAudioPage;
import page.NetVideoPage;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup rg_menu;
    private int position;
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg_menu = (RadioGroup) findViewById(R.id.rg_menu);

        rg_menu.setOnCheckedChangeListener(this);
        addFragment();

    }

    private void addFragment() {
        fragments = new ArrayList<>();
        fragments.add(new LoadVideoPage());
        fragments.add(new LoadAudioPage());
        fragments.add(new NetVideoPage());
        fragments.add(new NetAudioPage());
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_local_video:
                position = 0;
                break;
            case R.id.rb_local_audio:
                position = 1;
                break;
            case R.id.rb_net_video:
                position = 2;
                break;
            case R.id.rb_net_audio:
                position = 3;
                break;
        }
        Fragment fragment = fragments.get(position);
        executeFragmentTransaction(fragment);
    }
    private Fragment tempFragment;
    private void executeFragmentTransaction(Fragment fragment){

        if(tempFragment != fragment){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if(!fragment.isAdded()){
                if(tempFragment != null){
                    ft.hide(tempFragment);
                }
                ft.add(R.id.fl_interface,fragment);
            }else {
                if(tempFragment != null){
                    ft.hide(tempFragment);
                }
                ft.show(fragment);
            }

            ft.commit();
            tempFragment = fragment;
        }

    }
}
