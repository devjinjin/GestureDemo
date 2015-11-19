package com.devjinjin.bignotesketchpad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends Activity {
    private static final String TAG = SplashActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle pSavedInstanceState) {

        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onResume() {
        super.onResume();
        createThreadAndDialog(); 
    }

    private void createThreadAndDialog() {
        mWaitHandler.postDelayed(mWaitRunnable, 2500);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    final Handler mWaitHandler = new Handler();

    final Runnable mWaitRunnable = new Runnable() {
        @Override
        public void run() {
            mWaitHandler.removeCallbacks(mWaitRunnable);
            Intent newIntent = new Intent(SplashActivity.this,
                    MainActivity.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newIntent);
            finish();
        }
    };
}
