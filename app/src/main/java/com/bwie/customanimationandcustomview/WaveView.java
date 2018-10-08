package com.bwie.customanimationandcustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * //波动动画
 * Created by DELL on 2018/10/7.
 */

public class WaveView extends View{
    //全局变量
    private static final String TAG = "WaveView";
    private float fai = 0;
    private Paint paint1;
    private Paint paint2;
    private Path path1;
    private Path path2;
    private DrawFilter drawFilter;


    //1 重写三个方法
    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //定义一个初始化方法
        init();
    }

    //使用接口回调 进行传值
    //3 波形浮动的监听
    public interface OnWaveChangeListener{
        void onChanged(float y);
    }
    private OnWaveChangeListener listener;

    public void setOnWaveChangeListener(OnWaveChangeListener listener) {
        this.listener = listener;
    }


    //定义一个初始化方法
    private void init() {
        //定义并设置两支画笔  分别绘画两种交叉的相似流动图
        paint1 = new Paint();
        paint1.setColor(Color.WHITE);
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setStrokeWidth(5);

        paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setAntiAlias(true);
        paint2.setStrokeWidth(5);
        paint2.setAlpha(60);

        //定义两个要绘制的路径
        path1 = new Path();
        path2 = new Path();

        //Filter(过滤器)

        drawFilter = new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);

    }

    //2 重写onDraw（）方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //定义公式中的参数变量 PI代表π
        double Ω = 2 * Math.PI / getMeasuredWidth();
        //画一个  Filter(过滤器)
        canvas.setDrawFilter(drawFilter);
        fai -= 0.1f;

        int A = getMeasuredHeight() / 2;
        //每次绘画  都将前一步的清除重置
        path1.reset();
        path2.reset();

        //设置绘画起始点 从左下角开始
        path1.moveTo(getLeft(),getBottom());
        path2.moveTo(getLeft(), getBottom());

        // 从最左侧开始，画到最右侧，每20px画一条线
        for (int x = 0; x <=getMeasuredWidth() ; x+=20) {
            //正弦公式
            //float y = Asin(Ωx+φ)+k
            //分别设置两条流动的正弦图在y轴上的值
            float y1 = A * (float) Math.sin(Ω * x + fai)+A;
            float y2 = -A * (float) Math.sin(Ω * x + fai)+A;

            if (x > getMeasuredWidth() / 2 - 10 && x < getMeasuredWidth() / 2 + 10) {
                //判断图片需要改变时的值 接口回调的的传值
                listener.onChanged(y2);
            }

//            Log.i(TAG, "onDraw: (" + x + ", " + y + ")");
            path1.lineTo(x, y1);
            path2.lineTo(x, y2);

        }


        //设置每条流动的正弦图的终止点  右下角
        path1.lineTo(getWidth(), getBottom());
        path2.lineTo(getWidth(), getBottom());

        //在画布上使用路径画图
        canvas.drawPath(path1, paint1);
        canvas.drawPath(path2, paint2);

        //设置间隔发送时间
        postInvalidateDelayed(50);

    }
}
