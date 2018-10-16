package com.hci.project;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

/**
 * Created by Gerrys on 05/12/2017.
 */

public class SplashIntro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashIntro.this).withFullScreen()
                .withTargetActivity(LoginActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(R.color.colorBackground)
                .withLogo(R.mipmap.work)
                .withAfterLogoText("WELCOME TO WORKOUT HELPER");

        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        View v = config.create();

        setContentView(v);
    }
}
