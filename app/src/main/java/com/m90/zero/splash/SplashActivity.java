package com.m90.zero.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.m90.zero.R;
import com.m90.zero.home.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    Activity activity ;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        activity = SplashActivity.this;

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                        activity.startActivity(mainIntent);
                        activity.finish();
                    }
                }, 3000);
    }
}