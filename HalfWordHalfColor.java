package com.stormdzh.androidtest.libaray;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/5/31.
 *
 * @author Administrator.
 */

public class HalfWordHalfColor extends View {

    private volatile Paint mTextPaint; //文字画笔
    private float mAboveTextSize;      //“你好世界”文字的字体大小
    private CharSequence mCurrentText; //"你好世界" 将要会话的文字

    private int mTextColor;           //默认文字的颜色
    private int mTextCoverColor;      //上色后的文字颜色
    private LinearGradient mProgressTextGradient; //着色器

    public HalfWordHalfColor(Context context) {
        this(context, null);
    }

    public HalfWordHalfColor(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HalfWordHalfColor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTextColor = Color.parseColor("#6699ff");
        mTextCoverColor = Color.parseColor("#ff0000");
        mCurrentText = "你好世界";
        mAboveTextSize = 50.0F;
        this.mTextPaint = new Paint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextSize(this.mAboveTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#ffffbb33"));  //绘制背景颜色

        float y = (float) (canvas.getHeight() / 2) - (this.mTextPaint.descent() / 2.0F + this.mTextPaint.ascent() / 2.0F);
        if (this.mCurrentText == null) {
            this.mCurrentText = "";
        }
        float textWidth = this.mTextPaint.measureText(this.mCurrentText.toString());

        float coverlength = (float) this.getMeasuredWidth() * 0.44f;   //着色的长度
        float indicator1 = (float) (this.getMeasuredWidth() / 2) - textWidth / 2.0F;  //文字开始的位置
        float indicator2 = (float) (this.getMeasuredWidth() / 2) + textWidth / 2.0F;  //文字结束的位置
        float coverTextLength = textWidth / 2.0F - (float) (this.getMeasuredWidth() / 2) + coverlength; //着色的文字长度
        float textProgress = coverTextLength / textWidth; //需要着色的文字长度占总文字长度的百分比
        if (coverlength <= indicator1) {
            this.mTextPaint.setShader(null);
            this.mTextPaint.setColor(this.mTextColor);
        } else if (indicator1 < coverlength && coverlength <= indicator2) {
            this.mProgressTextGradient = new LinearGradient(((float) this.getMeasuredWidth() - textWidth) / 2.0F, 0.0F, ((float) this.getMeasuredWidth() + textWidth) / 2.0F, 0.0F, new int[]{this.mTextCoverColor, this.mTextColor}, new float[]{textProgress, textProgress + 0.001F}, Shader.TileMode.CLAMP);
            this.mTextPaint.setColor(this.mTextColor);
            this.mTextPaint.setShader(this.mProgressTextGradient);
        } else {
            this.mTextPaint.setShader(null);
            this.mTextPaint.setColor(this.mTextCoverColor);
        }
        canvas.drawText(this.mCurrentText.toString(), ((float) this.getMeasuredWidth() - textWidth) / 2.0F, y, this.mTextPaint);
    }

}
