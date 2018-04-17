package com.example.ks.draganimation;

import android.animation.Animator;
import android.os.Bundle;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.FloatPropertyCompat;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class SpringAnim extends AppCompatActivity implements View.OnClickListener {

    ImageView v, imageView, yes, no, star;

    FloatPropertyCompat<View> scale;
    SpringAnimation animationScale;
    ConstraintLayout constraintLayoutAccel;

    public static final float START_VELOCITY_05 = 0.5f;
    public static final float START_VELOCITY_09 = 0.9f;
    public static final float START_VELOCITY_15 = 1.5f;
    public static final float STIFFNESS_LOW = 50;
    public static final float STIFFNESS_MEDIUM = 300;
    public static final long DURATION_500 = 500;
    public static final long DURATION_750 = 750;
    public static final long DELAY = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprind_anim);
        v = findViewById(R.id.btn);
        imageView = findViewById(R.id.iv);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        star = findViewById(R.id.star);
        constraintLayoutAccel = findViewById(R.id.cl_accel);

        yes.setOnClickListener(this);

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
        animationScale.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(STIFFNESS_LOW);
        animationScale.setStartVelocity(START_VELOCITY_05);
        animationScale.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.animate().translationX(-250f).translationY(-550f).scaleX(5f).scaleY(5f).setDuration(DURATION_750).setListener(new Animator.AnimatorListener() {
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
                                animationScale.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(STIFFNESS_MEDIUM);
                                animationScale.setStartVelocity(START_VELOCITY_09);
                                animationScale.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                                    @Override
                                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                                        imageView.animate().translationX(-250f).translationY(150f).scaleX(10f).scaleY(10f).setDuration(DURATION_500).setListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {
                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                animationScale = new SpringAnimation(no, scale);
                                                no.setVisibility(View.VISIBLE);
                                                animationScale.setStartValue(0);
                                                animationScale.setSpring(new SpringForce().setFinalPosition(75f));
                                                animationScale.setMinimumVisibleChange(
                                                        DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE);
                                                animationScale.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(STIFFNESS_MEDIUM);
                                                animationScale.setStartVelocity(START_VELOCITY_15);
                                                animationScale.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                                                    @Override
                                                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                                                        animationScale = new SpringAnimation(yes, scale);
                                                        yes.setVisibility(View.VISIBLE);
                                                        animationScale.setStartValue(0);
                                                        animationScale.setSpring(new SpringForce().setFinalPosition(135f));
                                                        animationScale.setMinimumVisibleChange(
                                                                DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE);
                                                        animationScale.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(STIFFNESS_MEDIUM);
                                                        animationScale.setStartVelocity(START_VELOCITY_15);
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
                }, DELAY);

            }
        });
        animationScale.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes:
                goToTheAccel();
                break;
        }
    }

    private void goToTheAccel() {
        star.setVisibility(View.VISIBLE);
        star.animate().scaleX(3000f).scaleY(3000f).setDuration(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                constraintLayoutAccel.animate().alpha(1).setDuration(100).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

    }
}
