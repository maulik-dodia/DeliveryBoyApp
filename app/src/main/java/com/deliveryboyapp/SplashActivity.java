package com.deliveryboyapp;

import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);

        displayLoading();

        long SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                navigateToOtherScreen(SplashActivity.this, DeliveryListActivity.class,
                        false, null, true);

                displayToastMessage(getString(R.string.str_login_success));
                hideLoading();
            }

        }, SPLASH_TIME_OUT);
    }
}