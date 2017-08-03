package com.mj.frameapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mj.frameapp.MyApp;
import com.tumblr.remember.Remember;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LauncherActivity extends AppCompatActivity {


    private static final String SAMPLE_TEMPLATE_FILENAME = "sample_template.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepareTemplates();

        boolean hasDoneRegistration = Remember.getBoolean("done_registration", false);
        MyApp.log("has done regis : "+hasDoneRegistration);

        if (hasDoneRegistration) {
            startActivity(new Intent(this, ChooseImageActivity.class));
            finish();
        } else {
            //startActivity(new Intent(this, RegWebActivity.class));
            startActivity(new Intent(this, RegistrationActivity.class));
            finish();
        }

    }

    private void prepareTemplates() {
        File appFolder = MyApp.getTemplatesFolder();
        if (appFolder == null) {
            return;
        }

        //copy sample_template.png to app folder
        boolean sample_template_in_app_folder = false;
        for (String file_name : appFolder.list()) {
            MyApp.log(file_name);
            if (file_name.equalsIgnoreCase(SAMPLE_TEMPLATE_FILENAME)) {
                sample_template_in_app_folder = true;
                break;
            }
        }

        if (!sample_template_in_app_folder) {
            try {
                // TODO: 03-Aug-17 use better methods
                InputStream in = getAssets().open(SAMPLE_TEMPLATE_FILENAME);
                FileOutputStream out = new FileOutputStream(new File(appFolder, SAMPLE_TEMPLATE_FILENAME));
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
