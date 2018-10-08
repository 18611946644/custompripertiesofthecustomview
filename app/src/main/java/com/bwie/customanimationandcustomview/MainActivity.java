package com.bwie.customanimationandcustomview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private WaveView wv;
    private ImageView imgCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 找布局控件
        wv = findViewById(R.id.wv);
        imgCursor = findViewById(R.id.img_cursor);

        //自定义View  实现接口回调方法
        WaveView.OnWaveChangeListener listener = new WaveView.OnWaveChangeListener() {
            @Override
            public void onChanged(float y) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imgCursor.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, (int) y);
                imgCursor.setLayoutParams(layoutParams);
            }
        };

        //将 接口回调的对象传过去
        wv.setOnWaveChangeListener(listener);

    }
}
