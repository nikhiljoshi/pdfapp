/*
 * Created by nikhil joshi
 */

package com.niks.pdf.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.niks.pdf.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View easySplashScreenView = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(8000)
                .withBackgroundResource(android.R.color.black)
                .withLogo(R.drawable.back_splash)
                .withAfterLogoText("")
                .create();

        setContentView(easySplashScreenView);

    }
}
