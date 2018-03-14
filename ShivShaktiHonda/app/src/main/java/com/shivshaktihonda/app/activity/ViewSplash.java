package com.shivshaktihonda.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;

/**
 * Created by abc on 12/8/2017.
 */

public class ViewSplash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CM.getSp(ViewSplash.this, CV.USER_ID,"").equals("")) {
                    Intent intent = new Intent(ViewSplash.this, ViewLogin.class);
                    CM.startActivity(intent, ViewSplash.this);
                    ViewSplash.this.finish();
                }else{
                    Intent intent = new Intent(ViewSplash.this, ViewDashboard.class);
                    CM.startActivity(intent, ViewSplash.this);
                    ViewSplash.this.finish();
                }
            }
        },5000);

    }
}
