package com.mj.frameapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mj.frameapp.MyApp;
import com.mj.frameapp.activities.ChooseImageActivity;
import com.mj.frameapp.activities.RegWebActivity;
import com.tumblr.remember.Remember;

public class LauncherActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean hasDoneRegistration = Remember.getBoolean("done_registration", false);
        MyApp.log("has done regis : "+hasDoneRegistration);

        if (hasDoneRegistration) {
            startActivity(new Intent(this, ChooseImageActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, RegWebActivity.class));
            //startActivity(new Intent(this, RegistrationActivity.class));
            finish();
        }

    }
}
