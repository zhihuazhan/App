package com.app.frame.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.app.R;

/**
 * 可以自定义宽高比的View
 */

public class RatioView extends RelativeLayout {

    /**
     * 固定的属性,width或者height
     */
    public String fixed;

    /**
     * 宽高比
     */
    public float width_height_ration;

    public RatioView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RatioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatioView);
        fixed = array.getString(R.styleable.RatioView_fixed);
        width_height_ration = array.getFloat(R.styleable.RatioView_width_height_ratio, 1);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽度的模式和尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取高度的模式和尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //宽确定，高不确定
        if ("width".equals(fixed)) {
            heightSize = (int) (widthSize * width_height_ration + 0.5f);//根据宽度和比例计算高度
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        } else if ("height".equals(fixed)) {
            widthSize = (int) (heightSize / width_height_ration + 0.5f);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        } else {
            throw new RuntimeException("无法设定宽高比");
        }
        //VLog.i("RatioView", "widthMeasureSpec:" + widthMeasureSpec + " , heightMeasureSpec:" + heightMeasureSpec);
        //必须调用下面的两个方法之一完成onMeasure方法的重写，否则会报错
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

}
