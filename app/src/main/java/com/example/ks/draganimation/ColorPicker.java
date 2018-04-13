package com.example.ks.draganimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ColorPicker extends View {
    Paint paint = new Paint();
    private float xPosition, topMargin, constrHeight, constrWidth;
    public static final String TAG = ColorPicker.class.getSimpleName();
    private Paint p = new Paint();

    public ColorPicker(Context context) {
        super(context);
    }

    public ColorPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ColorPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void onDraw(Canvas canvas) {
        if (canvas != null) {

   /*    Log.d(TAG, "getWidth = " + getWidth());
            if (xPosition < 0) {
                if (Math.abs(xPosition) < constrWidth) {
                    paint.setColor(Color.GREEN);
                    canvas.drawRect(getWidth() + xPosition, topMargin, getWidth(), topMargin + constrHeight, paint);
                } else {
                    paint.setColor(Color.RED);
                    canvas.drawRect(getWidth() + xPosition, topMargin, getWidth() + xPosition + constrWidth, topMargin + constrHeight, paint);
                }
            } else if (xPosition > getWidth() / 2) {

                if (Math.abs(xPosition) < constrWidth + getWidth() / 2) {
                    paint.setColor(Color.GREEN);
                    canvas.drawRect(0, topMargin, xPosition - getWidth() / 2, topMargin + constrHeight, paint);
                } else {
                    paint.setColor(Color.RED);
                    canvas.drawRect(xPosition - getWidth() / 2 - constrWidth, topMargin, xPosition - getWidth() / 2, topMargin + constrHeight, paint);
                }
            }
*/
        }

    }

    public void init(float rawX, float topMargin, float constrHeight, float constrWidth) {
        this.xPosition = rawX;
        this.topMargin = topMargin;
        this.constrHeight = constrHeight;
        this.constrWidth = constrWidth;
        invalidate();
      /*  if (Math.abs(xPosition) < constrWidth) {
            invalidate();
        }*/

    }
}