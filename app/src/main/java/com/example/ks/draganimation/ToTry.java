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

public class ToTry extends AppCompatActivity implements View.OnTouchListener {

    RelativeLayout relativeLayout;
    private int widthMainView, heightMainView;
    private int p;

    public static final int VIEW_WIDTH = 200;
    public static final int VIEW_HEIGHT = 100;
    public static final int COUNT_OF_VIEWS = 3;

    private int leftMargin;
    private int topMargin;
    public static final String TAG = ToTry.class.getSimpleName();
    private float x;
    private ArrayList<Integer> dX = new ArrayList<>();
    private ArrayList<String> tags = new ArrayList<>();
    private boolean r, l;
    private int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.totry);
        relativeLayout = findViewById(R.id.cl_main);

        relativeLayout.setOnTouchListener(this);

        tags.clear();
        for (int i = 0; i < getCurrentCountOfElementsOnScreen(); i++) {
            colorPickerAddToView(colorPickerCreator(), i, null);
        }
        l = false;
        r = false;

    }

    private ColorPicker colorPickerCreator() {
        ColorPicker colorPicker = new ColorPicker(this);
        return colorPicker;
    }

    private void colorPickerAddToView(ColorPicker colorPicker, int position, String tag) {

        switch (position) {
            case 0:
                setLayoutParams(colorPicker, position);
                colorPicker.setBackgroundColor(Color.RED);
                colorPicker.setTag("0");
                tags.add((String) colorPicker.getTag());
                break;
            case 1:
                setLayoutParams(colorPicker, position);
                colorPicker.setBackgroundColor(Color.YELLOW);
                colorPicker.setTag("1");
                tags.add((String) colorPicker.getTag());
                break;
            case 2:
                setLayoutParams(colorPicker, position);
                colorPicker.setBackgroundColor(Color.GREEN);
                colorPicker.setTag("2");
                tags.add((String) colorPicker.getTag());
                break;
            case 3:
                setLayoutParams(colorPicker, position);
                colorPicker.setTag(tag);
                switch (tag) {
                    case "0":
                        colorPicker.setBackgroundColor(Color.RED);
                        break;
                    case "1":
                        colorPicker.setBackgroundColor(Color.YELLOW);
                        break;
                    case "2":
                        colorPicker.setBackgroundColor(Color.GREEN);
                        break;
                    case "00":
                        colorPicker.setBackgroundColor(Color.RED);
                        break;
                    case "10":
                        colorPicker.setBackgroundColor(Color.YELLOW);
                        break;
                    case "20":
                        colorPicker.setBackgroundColor(Color.GREEN);
                        break;

                }
                tags.add((String) colorPicker.getTag());
                break;

            case -1:
                setLayoutParams(colorPicker, position);
                colorPicker.setTag(tag);
                switch (tag) {
                    case "0":
                        colorPicker.setBackgroundColor(Color.RED);
                        break;
                    case "1":
                        colorPicker.setBackgroundColor(Color.YELLOW);
                        break;
                    case "2":
                        colorPicker.setBackgroundColor(Color.GREEN);
                        break;
                    case "01":
                        colorPicker.setBackgroundColor(Color.RED);
                        break;
                    case "11":
                        colorPicker.setBackgroundColor(Color.YELLOW);
                        break;
                    case "21":
                        colorPicker.setBackgroundColor(Color.GREEN);
                        break;

                }
                tags.add(0, (String) colorPicker.getTag());
                break;
        }
        colorPicker.invalidate();
        relativeLayout.addView(colorPicker);
    }


    private int getPosition(final int coordinates) {
        p = 0;
        getScreenPosition();
        p = widthMainView / coordinates;
        return p;
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

    private int getCurrentCountOfElementsOnScreen() {
        return COUNT_OF_VIEWS;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getRawX();
                dX.clear();
                for (i = 0; i < tags.size(); i++) {
                    dX.add((int) (relativeLayout.findViewWithTag(tags.get(i)).getX() - x));

                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (i = 0; i < tags.size(); i++) {

                    relativeLayout.findViewWithTag(tags.get(i)).animate().x(event.getRawX() + dX.get(i))
                            .setDuration(0)
                            .start();
                    Log.d(TAG, "tags.get(i) = " + tags.get(i));
                    if (relativeLayout.findViewWithTag(tags.get(i)).getX() < -VIEW_WIDTH && r) {
                        relativeLayout.removeView(relativeLayout.findViewWithTag(tags.get(i)));
                        dX.remove(i);
                        relativeLayout.findViewWithTag(tags.get(i + COUNT_OF_VIEWS)).setTag(tags.get(i));
                        tags.remove(i + COUNT_OF_VIEWS);
                        tags.add(tags.get(i));
                        tags.remove(i);
                        r = false;
                        i = -1;
                        break;
                    } else if (relativeLayout.findViewWithTag(tags.get(i)).getX() < 0 && !r) {
                        colorPickerAddToView(colorPickerCreator(), 3, tags.get(i) + String.valueOf(0));
                        dX.add(dX.get(i) + widthMainView);
                        r = true;
                    }

                    if (relativeLayout.findViewWithTag(tags.get(i)).getX() > widthMainView + VIEW_WIDTH && l) {

                        relativeLayout.removeView(relativeLayout.findViewWithTag(tags.get(i)));
                        dX.remove(i);
                        relativeLayout.findViewWithTag(tags.get(COUNT_OF_VIEWS - i)).setTag(tags.get(i));
                        String tag = tags.get(i);
                        tags.remove(COUNT_OF_VIEWS - i);
                        tags.add(COUNT_OF_VIEWS - i, tag);
                        tags.remove(i);
                        l = false;
                        i = -1;
                        break;
                    } else if (relativeLayout.findViewWithTag(tags.get(i)).getX() > widthMainView && !l
                            && (tags.get(i).contains("0") || tags.get(i).contains("1") || tags.get(i).contains("2"))) {
                        Log.d(TAG, "left go");
                        colorPickerAddToView(colorPickerCreator(), -1, tags.get(i) + String.valueOf(1));
                        dX.add(COUNT_OF_VIEWS - i, dX.get(i) - widthMainView);
                        l = true;
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
