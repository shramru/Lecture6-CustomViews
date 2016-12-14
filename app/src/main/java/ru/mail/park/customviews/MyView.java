package ru.mail.park.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class MyView extends View {

    private final Paint paint = new Paint();

    Random rnd = new Random();

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (wMode == MeasureSpec.EXACTLY) {
            if (hMode == MeasureSpec.EXACTLY) {
                setMeasuredDimension(width, height);
            } else if (hMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(width, Math.min(width, height));
            } else {
                setMeasuredDimension(width, width);
            }
        } else if (wMode == MeasureSpec.AT_MOST) {
            if (hMode == MeasureSpec.EXACTLY) {
                setMeasuredDimension(Math.min(width, height), height);
            } else if (hMode == MeasureSpec.AT_MOST) {
                int minSize = Math.min(width, height);
                setMeasuredDimension(minSize, minSize);
            } else {
                setMeasuredDimension(width, width);
            }
        } else {
            if (hMode == MeasureSpec.UNSPECIFIED) {
                setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
            } else {
                setMeasuredDimension(height, height);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        paint.setARGB(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        canvas.drawCircle(centerX, centerY, centerX, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        if (action == MotionEvent.ACTION_UP){
            MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
            event.getPointerCoords(0, pointerCoords);

            float x = pointerCoords.x;
            float y = pointerCoords.y;
            float centerX = getWidth() / 2f;
            float centerY = getHeight() / 2f;

            if (Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(centerX, 2)) {
                paint.setARGB(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                invalidate();
            }
        }

        return true;
    }
}
