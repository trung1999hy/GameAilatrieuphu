package com.example.myapplication.dhbatchu.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class AnimationUtils {

    public static AnimatorSet zoomButton(View view) {
        final float from = 1.0f;
        final float to = .9f;

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, from, to);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, from, to);

        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(view, View.SCALE_X, to, from);
        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(view, View.SCALE_Y, to, from);

        AnimatorSet set1 = new AnimatorSet();
        set1.play(scaleX).before(scaleX1);
        set1.play(scaleY).before(scaleY1);
        set1.setDuration(20);
        set1.setInterpolator(new AccelerateInterpolator());
        set1.start();
        return set1;
    }

    public static AnimatorSet zoomInButton(View view, float from, float to) {
//        final float from = 1.0f;
//        final float to = .9f;

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, from, to);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, from, to);

        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(scaleX,scaleY);
        set1.setDuration(20);
        set1.setInterpolator(new AccelerateInterpolator());
        set1.start();
        return set1;
    }

    public static AnimatorSet zoomOutButton(View view, float from, float to) {
//        final float from = 1.0f;
//        final float to = .9f;

        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(view, View.SCALE_X, to, from);
        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(view, View.SCALE_Y, to, from);

        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(scaleX1,scaleY1);
        set1.setDuration(20);
        set1.setInterpolator(new AccelerateInterpolator());
        set1.start();
        return set1;
    }
}
