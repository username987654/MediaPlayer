package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.mediaplayer.R;

/**
 * Created by HaoMeng on 2017/5/21.
 */

public class Titled extends LinearLayout implements View.OnClickListener {
    private TextView tv_ssk;
    private RelativeLayout rl_game;
    private ImageView iv_recrod;
    private Context context;
    public Titled(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_ssk = (TextView) getChildAt(1);
        rl_game = (RelativeLayout) getChildAt(2);
        iv_recrod = (ImageView) getChildAt(3);

        tv_ssk.setOnClickListener(this);
        rl_game.setOnClickListener(this);
        iv_recrod.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_ssk:
                Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rl_game:
                Toast.makeText(context, "游戏", Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv_record:
                Toast.makeText(context, "记录", Toast.LENGTH_SHORT).show();
                break;

        }



    }
}
