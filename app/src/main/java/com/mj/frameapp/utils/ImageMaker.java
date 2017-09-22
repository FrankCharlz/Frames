package com.mj.frameapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.mj.frameapp.MyApp;

import java.io.File;
import java.util.Random;
import java.util.WeakHashMap;

/**
 * Created by Frank on 22-Sep-17.
 */

public class ImageMaker {

    private static final int IMAGE_SIZE = 1080;
    private static WeakHashMap<String, Bitmap> bitmapCache = new WeakHashMap<>(8);

    public static Bitmap make(String templateName, Bitmap image) {

        if (bitmapCache.containsKey(templateName)) {
            MyApp.log("got bitmap from cache");
            return  bitmapCache.get(templateName);
        }

        Bitmap template = BitmapFactory.decodeFile(new File(MyApp.getTemplatesFolder(),
                templateName).getAbsolutePath());

        Bitmap bitmap = doMake(image, template);

        bitmapCache.put(templateName, bitmap);
        return bitmap;
    }

    private static Bitmap doMake(Bitmap image, Bitmap template) {

        int width = image.getWidth();
        int height = image.getHeight();

        int new_height, new_width;
        int longest_side = (height > width) ? height : width;

        new_height = (int)(height * 1.0 * IMAGE_SIZE / longest_side);
        new_width =  (int)(width * 1.0 * IMAGE_SIZE / longest_side);

        //rectf dimensions
        int rectf_left = (IMAGE_SIZE - new_width)/2;
        int rectf_right = rectf_left + new_width;
        int rectf_top = (IMAGE_SIZE - new_height)/2;
        int rectf_bottom = rectf_top + new_height;
        RectF rectf = new RectF(rectf_left, rectf_top, rectf_right , rectf_bottom);

        MyApp.log(width + " -- " + height);
        MyApp.log(new_width + " -- " + new_height);

        Bitmap bitmap = Bitmap.createBitmap(IMAGE_SIZE, IMAGE_SIZE, null); //create new empty bitmap;

        Canvas canvas = new Canvas(bitmap); //init canvas with bitmap for height
        canvas.drawBitmap(image, null, rectf, null);


        canvas.drawBitmap(template, null, new RectF(0,0,1080,1080), null); //// TODO: 21-Jan-17 might be CPU intensive

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(4);
        float text_size = 46;
        float text_start_y = 880;
        float text_padding = 6;

        paint.setTextSize(text_size);
        //paint.setTypeface(typeface);

        String info[] = new String[] {"Name: ", "Price: ", "Phone: "};
        for (int i = 0; i < 3; i++) {
            if (i == 2) {paint.setUnderlineText(true);}
            //canvas.drawText(info[i]+data.get(i+1), 150, text_start_y, paint);
            canvas.drawText(info[i]+(new Random().nextLong()), 150, text_start_y, paint);
            text_start_y += (text_size + text_padding);
        }

        return bitmap;
    }


    public static void clearCache() {
        MyApp.log("Clearing bitmap cache");
        bitmapCache.clear();
    }
}
