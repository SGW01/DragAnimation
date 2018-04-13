package com.example.ks.draganimation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.FloatPropertyCompat;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class SpringAnim extends AppCompatActivity {

    ImageView v, imageView, yes, no;

    FloatPropertyCompat<View> scale;
    SpringAnimation animationScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprind_anim);
        v = findViewById(R.id.btn);
        imageView = findViewById(R.id.iv);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);

        scale = new FloatPropertyCompat<View>("scale") {
            @Override
            public float getValue(View view) {
                // return the value of any one property
                return view.getScaleX();
            }

            @Override
            public void setValue(View view, float value) {
                // Apply the same value to two properties
                view.setScaleX(value);
                view.setScaleY(value);
            }
        };
        animate(50, 0.5f, 10f, -550f, 250f, 5f, 5f, 1000, 3, v);

    }


    private void animate(float stiffness, float startValue, float finalPos,
                         final float translationY, final float translationX,
                         final float scaleX, final float scaleY,
                         final long duration, final int animationNumber, final View view) {
        animationScale = new SpringAnimation(view, scale);
        view.setVisibility(View.VISIBLE);
        view.setTag(animationNumber);
        animationScale.setStartValue(startValue);
        animationScale.setSpring(new SpringForce().setFinalPosition(finalPos));
        animationScale.setMinimumVisibleChange(
                DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE);
        animationScale.getSpring().setDampingRatio(0.5f).setStiffness(stiffness);
        animationScale.setStartVelocity(0.5f);



        animationScale.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                animationScale.removeEndListener(this);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.animate().translationY(translationY).translationX(-translationX).scaleX(scaleX).scaleY(scaleY).setDuration(duration).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                animation.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {

                                        animation.removeAllListeners();
                                        animation.cancel();
                                        switch ((int) view.getTag()) {
                                            case 1:

                                                animate(100, 0.5f, 10f, 200f, 250f, 5f, 9.5f, 1000, 2, imageView);
                                                break;
                                            case 2:
                                                animate(100, 0, 3f, 0, 0, 3f, 3f, 0, 3, no);
                                                break;
                                            case 3:
                                                animate(100, 0, 5f, 0, 0, 5f, 5f, 0, 4, yes);
                                                break;
                                        }

                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });
                            }
                        }).start();
                    }
                }, 500);

            }
        });
        animationScale.start();
    }


}
