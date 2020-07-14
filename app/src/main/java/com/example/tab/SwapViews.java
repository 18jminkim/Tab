package com.example.tab;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public final class SwapViews implements Runnable {
    private boolean mIsFirstView;
    private boolean heads;
    ImageView image1;
    ImageView image2;

    public SwapViews(boolean isFirstView, ImageView image1, ImageView image2, boolean heads) {
        mIsFirstView = isFirstView;
        this.image1 = image1;
        this.image2 = image2;
        this.heads = heads;
    }

    public void run() {
        final float centerX = image1.getWidth() / 2.0f;
        final float centerY = image1.getHeight() / 2.0f;
        Flip3dAnimation rotation;

        if (!heads) {
            image1.setVisibility(View.GONE);
            image2.setVisibility(View.VISIBLE);
            //image2.requestFocus();

        } else {
            image2.setVisibility(View.GONE);
            image1.setVisibility(View.VISIBLE);
            //image1.requestFocus();

        }
        rotation = new Flip3dAnimation(-900, 0, centerX, centerY);

        rotation.setDuration(200);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new DecelerateInterpolator());

        if (!heads) {
            image2.startAnimation(rotation);
        } else {
            image1.startAnimation(rotation);
        }
    }
}