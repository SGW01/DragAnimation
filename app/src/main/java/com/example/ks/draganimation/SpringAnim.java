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
                return view.getScaleX();
            }

            @Override
            public void setValue(View view, float value) {
                view.setScaleX(value);
                view.setScaleY(value);
            }
        };
        animate();

    }


    private void animate() {
        animationScale = new SpringAnimation(v, scale);
        v.setVisibility(View.VISIBLE);
        animationScale.setStartValue(0.5f);
        animationScale.setSpring(new SpringForce().setFinalPosition(10f));
        animationScale.setMinimumVisibleChange(
                DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE);
        animationScale.getSpring().setDampingRatio(0.5f).setStiffness(50);
        animationScale.setStartVelocity(0.5f);
        animationScale.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.animate().translationX(-250f).translationY(-550f).scaleX(5f).scaleY(5f).setDuration(1000).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                animationScale = new SpringAnimation(imageView, scale);
                                imageView.setVisibility(View.VISIBLE);
                                animationScale.setStartValue(0.5f);
                                animationScale.setSpring(new SpringForce().setFinalPosition(10f));
                                animationScale.setMinimumVisibleChange(
                                        DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE);
                                animationScale.getSpring().setDampingRatio(0.5f).setStiffness(100);
                                animationScale.setStartVelocity(0.5f);
                                animationScale.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                                    @Override
                                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                                        imageView.animate().translationX(-250f).translationY(150f).scaleX(5f).scaleY(9.5f).setDuration(1000).setListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {
                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                animationScale = new SpringAnimation(no, scale);
                                                no.setVisibility(View.VISIBLE);
                                                animationScale.setStartValue(0);
                                                animationScale.setSpring(new SpringForce().setFinalPosition(45f));
                                                animationScale.setMinimumVisibleChange(
                                                        DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE);
                                                animationScale.getSpring().setDampingRatio(0.5f).setStiffness(100);
                                                animationScale.setStartVelocity(0.5f);
                                                animationScale.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                                                    @Override
                                                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                                                        animationScale = new SpringAnimation(yes, scale);
                                                        yes.setVisibility(View.VISIBLE);
                                                        animationScale.setStartValue(0);
                                                        animationScale.setSpring(new SpringForce().setFinalPosition(75f));
                                                        animationScale.setMinimumVisibleChange(
                                                                DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE);
                                                        animationScale.getSpring().setDampingRatio(0.5f).setStiffness(100);
                                                        animationScale.setStartVelocity(0.5f);
                                                        animationScale.start();
                                                    }
                                                });
                                                animationScale.start();

                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animation) {

                                            }
                                        }).start();
                                    }
                                });
                                animationScale.start();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).start();

                    }
                },500);

            }
        });
        animationScale.start();

    }

}
