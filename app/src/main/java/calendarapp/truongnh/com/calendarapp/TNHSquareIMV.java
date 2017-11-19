package calendarapp.truongnh.com.calendarapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 217REC10 on 10/30/17.
 */

public class TNHSquareIMV extends AppCompatImageView {

    public TNHSquareIMV(Context context) {
        super(context);
    }

    public TNHSquareIMV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TNHSquareIMV(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()/10 * 8);
    }
}
