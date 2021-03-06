package com.mj.frameapp;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.tumblr.remember.Remember;

import java.io.File;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Frank on 19-Jan-17.
 */
public class MyApp extends Application {

    public static void log(String s) {
        Log.i("frames-app", s);
    }

    public static File getAppFolder() {
        File folder = new File(Environment.getExternalStorageDirectory(), "framesapp/");

        // Create the storage directory if it does not exist
        if (! folder.exists()){
            if (! folder.mkdirs()){
                MyApp.log("failed to create app directory");
                return null;
            }
        }

        return folder;
    }


    public static File getTemplatesFolder() {
        File folder = new File(Environment.getExternalStorageDirectory(), "framesapp/templates");

        // Create the storage directory if it does not exist
        if (! folder.exists()){
            if (! folder.mkdirs()){
                MyApp.log("failed to create templates directory");
                return null;
            }
        }

        return folder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Remember.init(getApplicationContext(), "lilla");

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("opensans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
