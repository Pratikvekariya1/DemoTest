package com.example.demoapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.example.demoapp.databinding.ActivitySplashBinding;
import com.example.demoapp.ui.list.ListActivity;

public class SplashActivity extends Activity {

    private static final int SPLASH_TIME_SECS = 5;

    private ActivitySplashBinding binding;

    private Handler splashHandler = new Handler();
    private Runnable splashRunnable = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(SplashActivity.this, ListActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPause() {
        splashHandler.removeCallbacks(splashRunnable);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashHandler.postDelayed(splashRunnable, SPLASH_TIME_SECS * 1000);
    }

}
