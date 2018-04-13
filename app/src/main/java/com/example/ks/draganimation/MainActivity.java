package com.example.ks.draganimation;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    RelativeLayout rvMain;
    private float dX;
    private float x;
    private ObjectAnimator valueAnimator = new ObjectAnimator();
    public static final int ANIMATION_SPEED = 500;

    private ColorPicker colorPicker;
    private int widthMainView;

    private int width, height, marginLeft, marginTop;
    private ColorPicker colorPickerRight;
    private ColorPicker colorPickerLeft;
    private int center;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMain = findViewById(R.id.rv_main);
        colorPicker = new ColorPicker(this);
        colorPicker.setBackgroundColor(Color.YELLOW);
        width = 550;
        height = 300;

        rvMain.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rvMain.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                widthMainView = rvMain.getMeasuredWidth();

                marginTop = (rvMain.getMeasuredHeight() - height) / 2;
                marginLeft = (rvMain.getMeasuredWidth() - width) / 2;

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
                layoutParams.topMargin = marginTop;
                layoutParams.leftMargin = marginLeft;
                colorPicker.setLayoutParams(layoutParams);
                rvMain.addView(colorPicker);
            }
        });


        rvMain.setOnTouchListener(this);


    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x = event.getRawX();
                dX = colorPicker.getX() - x;
                break;

            case MotionEvent.ACTION_MOVE:

                center = (widthMainView - width) / 2;
                colorPicker.animate().x(event.getRawX() + dX)
                        .setDuration(0)
                        .start();

                if (event.getRawX() + dX < 0) {
                    if (colorPickerRight == null) {
                        colorPickerRight = colorPickerCreator(width, height, widthMainView + marginLeft, widthMainView + marginLeft + width, marginTop, Color.RED, "right");
                        rvMain.addView(colorPickerRight);
                        rvMain.removeView(colorPickerLeft);
                        colorPickerLeft = null;
                    }
                } else if (event.getRawX() + dX > widthMainView - width) {
                    if (colorPickerLeft == null) {
                        colorPickerLeft = colorPickerCreator(width, height, -marginLeft - width, -marginLeft, marginTop, Color.GREEN, "left");
                        rvMain.addView(colorPickerLeft);
                        rvMain.removeView(colorPickerRight);
                        colorPickerRight = null;
                    }
                }
                Log.d(TAG, "x  = " + (event.getRawX() + dX));
                if (colorPickerLeft != null) {
                    Log.d(TAG, "colorPickerLeftAnimate = " + (-width - widthMainView / 2 + (event.getRawX() + dX)));
                    colorPickerLeft.animate().translationX(-width - center * 2 + (event.getRawX() + dX)).setDuration(0).start();
                }
                if (colorPickerRight != null) {
                    Log.d(TAG, "colorPickerRightAnimate = " + (widthMainView + (event.getRawX() + dX)));
                    colorPickerRight.animate().translationX(widthMainView + (event.getRawX() + dX)).setDuration(0).start();
                }


                break;
            case MotionEvent.ACTION_UP:
                checker(colorPickerLeft);
                checker(colorPickerRight);
                break;
            default:
                return false;
        }
        return true;
    }

    private void checker(ColorPicker colorPicker) {
        if (colorPicker != null) {
            if (colorPicker.getTag().equals("left")) {
                if (Math.abs(colorPicker.getX()) > widthMainView / 10 && Math.abs(colorPicker.getX()) < center) {
                    colorPicker.animate().translationX(center).setDuration(ANIMATION_SPEED).start();
                    this.colorPicker.animate().translationX(center + widthMainView).setDuration(ANIMATION_SPEED).start();
                } else {
                    colorPicker.animate().translationX(center - widthMainView).setDuration(ANIMATION_SPEED).start();
                    this.colorPicker.animate().x(center).setDuration(ANIMATION_SPEED).start();

                }
            } else if (colorPicker.getTag().equals("right")) {
                if (Math.abs(colorPicker.getX()) < widthMainView - (widthMainView / 10) && Math.abs(colorPicker.getX()) > center) {
                    colorPicker.animate().translationX(center).setDuration(ANIMATION_SPEED).start();
                    this.colorPicker.animate().translationX(center - widthMainView).setDuration(ANIMATION_SPEED).start();
                } else {
                    colorPicker.animate().translationX(center + widthMainView).setDuration(ANIMATION_SPEED).start();
                    this.colorPicker.animate().x(center).setDuration(ANIMATION_SPEED).start();

                }
            }
        }

    }

    private ColorPicker colorPickerCreator(int width, int height, int marginLeft, int marginRight, int marginTop, int color, String tag) {

        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.setBackgroundColor(color);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        layoutParams.topMargin = marginTop;
        layoutParams.rightMargin = marginRight;

        colorPicker.setLayoutParams(layoutParams);
        colorPicker.setTag(tag);
        return colorPicker;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}


