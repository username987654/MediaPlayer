package page;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.mediaplayer.R;

import java.util.ArrayList;
import java.util.List;

import adapter.BaseAdapter;
import bean.LocalVideoInfo;
import fragment.Fragment;

/**
 * Created by HaoMeng on 2017/5/21.
 */

public class LocalVideoPage extends Fragment {
    private List<LocalVideoInfo> videoInfos;
    private BaseAdapter baseAdapter;
    private TextView tv_unfoundvideo;
    private ListView localvideo_list;

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.local_video_list, null);
        localvideo_list = (ListView) view.findViewById(R.id.localvideo_list);
        tv_unfoundvideo = (TextView) view.findViewById(R.id.tv_unfoundvideo);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        getData();
    }

    public void getData() {
        new Thread() {
            @Override
            public void run() {
                videoInfos = new ArrayList<LocalVideoInfo>();
                ContentResolver cr = context.getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,
                        MediaStore.Video.Media.DURATION,
                        MediaStore.Video.Media.SIZE,
                        MediaStore.Video.Media.DATA
                };
                Cursor cursor = cr.query(uri, objs, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(0);
                        long duration = cursor.getLong(1);
                        long size = cursor.getLong(2);
                        String data = cursor.getString(3);
                        videoInfos.add(new LocalVideoInfo(name, duration, size, data));
                    }
                    cursor.close();
                }
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(videoInfos != null && videoInfos.size()> 0){
                tv_unfoundvideo.setVisibility(View.GONE);
                baseAdapter = new BaseAdapter(context, videoInfos);
                localvideo_list.setAdapter(baseAdapter);
            }else {
                tv_unfoundvideo.setVisibility(View.VISIBLE);
            }
        }
    };
}
