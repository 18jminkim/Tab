package com.example.tab;

import android.view.animation.Animation;
import android.widget.ImageView;

public final class DisplayNextView implements Animation.AnimationListener {
    private boolean mCurrentView;
    private boolean heads;
    ImageView image1;
    ImageView image2;


    public DisplayNextView(boolean currentView, ImageView image1, ImageView image2, boolean heads) {
        mCurrentView = currentView;
        this.image1 = image1;
        this.image2 = image2;
        this.heads = heads;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        image1.post(new SwapViews(mCurrentView, image1, image2, heads));
    }

    public void onAnimationRepeat(Animation animation) {
    }
}