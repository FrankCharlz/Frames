package com.mj.frameapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mj.frameapp.activities.EditorActivity;

import java.io.InputStream;

/**
 * Created by Frank on 03-Aug-17.
 */

public class ImageUtils {


    public static Bitmap loadTemplate(String path) {
        return BitmapFactory.decodeFile(path);
    }
}
