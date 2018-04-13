package com.example.ks.draganimation;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class NewTotry extends AppCompatActivity implements View.OnTouchListener {

    RelativeLayout relativeLayoutBig;
    private int widthMainView, heightMainView;
    private int p = 0;

    public static final int VIEW_WIDTH = 200;
    public static final int VIEW_HEIGHT = 100;
    public static final int COUNT_OF_VIEWS = 3;

    private int leftMargin;
    private int topMargin;
    public static final String TAG = NewTotry.class.getSimpleName();
    private float x;
    private ArrayList<Integer> dX = new ArrayList<>();
    private HashMap<Integer, Integer> hashMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtotry);
        relativeLayoutBig = findViewById(R.id.cl_main);

        relativeLayoutBig.setOnTouchListener(this);

        p = 0;


        for (int i = 0; i < getCurrentCountOfElementsOnScreen(); i++) {
            colorPickerAddToView(colorPickerCreator(), i, 0, 0);
        }

    }

    private ColorPicker colorPickerCreator() {
        ColorPicker colorPicker = new ColorPicker(this);
        return colorPicker;
    }

    private void colorPickerAddToView(ColorPicker colorPicker, int position, int p, int k) {

        switch (position) {
            case 0:
                setLayoutParams(colorPicker, position);
                colorPicker.setBackgroundColor(Color.RED);
                colorPicker.setTag(0);
                break;
            case 1:
                setLayoutParams(colorPicker, position);
                colorPicker.setBackgroundColor(Color.YELLOW);
                colorPicker.setTag(1);
                break;
            case 2:
                setLayoutParams(colorPicker, position);
                colorPicker.setBackgroundColor(Color.GREEN);
                colorPicker.setTag(2);
                break;
            case 3:
                setNewLayoutParams(colorPicker, p);
                colorPicker.setTag(k + 3);
                break;
        }

        Log.d(TAG, "w = " + colorPicker.getWidth());
        colorPicker.invalidate();
        relativeLayoutBig.addView(colorPicker);
    }


    private void getScreenPosition() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        widthMainView = size.x;
        heightMainView = size.y;

        leftMargin = (widthMainView - getCurrentCountOfElementsOnScreen() * VIEW_WIDTH) / (getCurrentCountOfElementsOnScreen() + 1);
        topMargin = (heightMainView - VIEW_HEIGHT) / 2;

    }

    private void setLayoutParams(ColorPicker colorPicker, int position) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(VIEW_WIDTH, VIEW_HEIGHT);
        getScreenPosition();
        layoutParams.topMargin = topMargin;
        colorPicker.setLayoutParams(layoutParams);
        colorPicker.setTranslationX((position + 1) * leftMargin + VIEW_WIDTH * position);

    }

    private void setNewLayoutParams(ColorPicker colorPicker, int p) {


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(VIEW_WIDTH, VIEW_HEIGHT);
        getScreenPosition();
        layoutParams.topMargin = topMargin;
        colorPicker.setLayoutParams(layoutParams);
        colorPicker.setTranslationX(widthMainView);
        Log.d(TAG, "getTranaslationX = " + colorPicker.getTranslationX());
        switch (p % 3) {
            case 0:
                colorPicker.setBackgroundColor(Color.RED);
                break;
            case 1:
                colorPicker.setBackgroundColor(Color.YELLOW);
                break;
            case 2:
                colorPicker.setBackgroundColor(Color.GREEN);
                break;
        }

    }

    private int getCurrentCountOfElementsOnScreen() {
        return COUNT_OF_VIEWS;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getRawX();
                dX.clear();
                for (int i = 0; i < relativeLayoutBig.getChildCount(); i++)
                    dX.add((int) (relativeLayoutBig.findViewWithTag(i).getX() - x));
                break;
            case MotionEvent.ACTION_MOVE:
                for (Integer i = 0; i < relativeLayoutBig.getChildCount(); i++) {
                    View view = relativeLayoutBig.findViewWithTag(i);
                    if (view != null)
                        view.animate().x(event.getRawX() + dX.get(i))
                                .setDuration(0)
                                .start();

                    Log.d(TAG, "i = " + relativeLayoutBig.getChildCount());

                    if (event.getRawX() + dX.get(i) < -(leftMargin * (p + 1) + VIEW_WIDTH * p)) {
                        Log.d(TAG, "event.getRawX() + dX = " + (event.getRawX() + dX.get(i))
                                + "    -(leftMargin * (p + 1) + VIEW_WIDTH * p) = " + (-(leftMargin * (p + 1) + VIEW_WIDTH * p)));
                        colorPickerAddToView(colorPickerCreator(), 3, p, p+3);
                        relativeLayoutBig.invalidate();
                        Log.d(TAG, String.valueOf(relativeLayoutBig.getChildCount()));
                        dX.add((int) (widthMainView + x));
                        p++;

                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }

        return true;
    }
}
