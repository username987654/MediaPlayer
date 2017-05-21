package adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atguigu.mediaplayer.R;

import java.util.List;

import bean.LocalVideoInfo;
import utils.Utils;

/**
 * Created by HaoMeng on 2017/5/21.
 */

public class BaseAdapter extends android.widget.BaseAdapter {
    private Context context;
    private List<LocalVideoInfo> videoInfos;
    private Utils utils;

    public BaseAdapter(Context context, List<LocalVideoInfo> videoInfos) {
        this.context = context;
        this.videoInfos = videoInfos;
        utils = new Utils();
    }

    @Override
    public int getCount() {
        return videoInfos == null ? 0 : videoInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.local_video_item,null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.duration = (TextView) convertView.findViewById(R.id.tv_duration);
            viewHolder.size = (TextView) convertView.findViewById(R.id.tv_size);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LocalVideoInfo videoInfo = videoInfos.get(position);
        viewHolder.name.setText(videoInfo.getName());
        viewHolder.size.setText(Formatter.formatFileSize(context,videoInfo.getSize()));
        viewHolder.duration.setText(utils.stringForTime((int) videoInfo.getDuration()));
        return convertView;
    }
    static class ViewHolder{
        TextView name;
        TextView duration;
        TextView size;
    }
}
