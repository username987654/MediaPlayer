package page;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import fragment.Fragment;

/**
 * Created by HaoMeng on 2017/5/21.
 */

public class LoadVideoPage extends Fragment {
    private TextView tv;
    @Override
    public View initView() {
        tv = new TextView(context);
        tv.setTextSize(30);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.RED);
        return tv;
    }

    @Override
    protected void initData() {
        super.initData();
        tv.setText("本地视频");
    }
}
