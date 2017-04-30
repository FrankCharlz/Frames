package com.mj.frameapp;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.tumblr.remember.Remember;

import java.io.File;

/**
 * Created by Frank on 19-Jan-17.
 */
public class MyApp extends Application {

    public static void log(String s) {
        Log.e("frame-app", s);
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
                MyApp.log("failed to create tmplates directory");
                return null;
            }
        }

        return folder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Remember.init(getApplicationContext(), "lilla");
    }
}
