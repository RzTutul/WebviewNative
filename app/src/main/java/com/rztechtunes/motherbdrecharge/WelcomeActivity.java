package com.rztechtunes.motherbdrecharge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.rztechtunes.motherbdrecharge.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;

    Animation animation;
    private int progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        this.animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blur);
//        binding.arrow.setAnimation(this.animation);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blur);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blur);
        binding.nightteer.setAnimation(this.animation);
        new Thread(new Runnable() {
            public void run() {
                doWork();
                gotoMain();
            }
        }).start();

    }

    private void gotoMain() {

        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        WelcomeActivity.this.finish();


    }





    public void doWork() {
        this.progress = 1;
        while (this.progress <= 1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.progress += 1;
        }
    }
}