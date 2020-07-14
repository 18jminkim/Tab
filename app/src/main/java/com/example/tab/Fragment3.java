package com.example.tab;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.Random;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

public class Fragment3 extends Fragment {

    public static final Random RANDOM = new Random();
    private ImageView coinImageView;
    private Button flipButton;
    private TextView coinTextView;
    private int coinSideCount;
    private ImageView image1;
    private ImageView image2;
    private boolean isFirstImage = true;
    private static final String TAG = "CoinFlip";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3_layout, container, false);
        /*
        coinImageView = (ImageView) view.findViewById(R.id.coinImageView);
        flipButton = (Button) view.findViewById(R.id.flipButton);
        coinTextView = (TextView) view.findViewById(R.id.coinTextView);


        flipButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                flipTheCoin();
            }


        });

         */



        // new implementation starts here

        image1 = (ImageView) view.findViewById(R.id.ImageView01);
        image2 = (ImageView) view.findViewById(R.id.ImageView02);
        image2.setVisibility(View.GONE);

        image1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                image1.setClickable(false);
                //image2.setClickable(false);
                if (RANDOM.nextFloat() >= 0.5f) {
                    applyRotation(0, 450, true, isFirstImage);
                    isFirstImage = true;

                } else {
                    applyRotation(0, 450, false, isFirstImage);
                    isFirstImage = false;
                }


                image1.setClickable(true);
                //image2.setClickable(true);


            }


        });

        return view;




    }


    private void applyRotation(float start, float end, boolean heads, boolean isFirstImage) {
// Find the center of image
        final float centerX = image1.getWidth() / 2.0f;
        final float centerY = image1.getHeight() / 2.0f;

// Create a new 3D rotation with the supplied parameter
// The animation listener is used to trigger the next animation
        final Flip3dAnimation rotation =
                new Flip3dAnimation(start, end, centerX, centerY);
        rotation.setDuration(100);
        rotation.setFillAfter(true);
        //rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(isFirstImage, image1, image2, heads));
        if (isFirstImage)
        {
            Log.d(TAG, "applyRotation: currently first image.");
            image1.startAnimation(rotation);
        } else {
            Log.d(TAG, "applyRotation: currently second image.");

            image2.startAnimation(rotation);
        }
        if (heads){
            Toast.makeText(getActivity(),"Heads!",Toast.LENGTH_SHORT).show();
            //image2.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(),"Tails!",Toast.LENGTH_SHORT).show();


        }









    }



    private void flipTheCoin(){
        coinSideCount = RANDOM.nextInt(2);

        RotateAnimation rotateAnimation = new RotateAnimation(0,36000,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);

        if(coinSideCount==0){
            coinImageView.setImageResource(R.drawable.heads);
            Toast.makeText(getActivity(),"Heads",Toast.LENGTH_SHORT).show();
        }else if(coinSideCount==1){
            coinImageView.setImageResource(R.drawable.tails);
            Toast.makeText(getActivity(),"Tails",Toast.LENGTH_SHORT).show();
        }



        rotateAnimation.setDuration(1000);
        coinImageView.startAnimation(rotateAnimation);

    }


    private void flipCoin(){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //coinImageView.setImageResource(RANDOM.nextFloat() > 0.5f ? R.drawable.tails : R.drawable.heads);

                if (RANDOM.nextFloat() > 0.5f) {
                    coinImageView.setImageResource(R.drawable.tails);
                    coinTextView.setText("Tails!");
                } else {
                    coinImageView.setImageResource(R.drawable.heads);
                    coinTextView.setText("Heads!");
                }


                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator());
                fadeIn.setDuration(1000);
                fadeIn.setFillAfter(true);

                coinImageView.startAnimation(fadeIn);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        coinImageView.startAnimation(fadeOut);
    }
}
