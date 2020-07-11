package com.example.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.Random;

public class Fragment3 extends Fragment {

    public static final Random RANDOM = new Random();
    private ImageView coinImageView;
    private Button flipButton;
    private TextView coinTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3_layout, container, false);
        coinImageView = (ImageView) view.findViewById(R.id.coinImageView);
        flipButton = (Button) view.findViewById(R.id.flipButton);
        coinTextView = (TextView) view.findViewById(R.id.coinTextView);


        flipButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                flipCoin();
            }


        });

        return view;




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
