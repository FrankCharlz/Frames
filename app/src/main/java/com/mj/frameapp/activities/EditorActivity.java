package com.mj.frameapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mj.frameapp.MyApp;
import com.mj.frameapp.R;
import com.tumblr.remember.Remember;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditorActivity extends AppCompatActivity {

    public static final String CURRENT_IMAGE_URI = "image_uri";
    public static final String CURRENT_DATA_MAP = "data_map";
    private Bitmap bitmap;
    private static final int IMAGE_SIZE = 1080;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        MyApp.verifyStoragePermissions(this);

        ImageView imageView = (ImageView) findViewById(R.id.img_user_image);
        imageView.setOnTouchListener(new ImageTouchListener());

        Intent intent = getIntent();
        data = intent.getStringArrayListExtra(CURRENT_DATA_MAP);

        //String template_path = intent.getStringExtra(MainActivity.TEMPLATE_PATH_AS_STRING);
        //MyApp.log("Template path : "+template_path);


        Uri uri = Uri.parse(data.get(0));

        Typeface typeface = Typeface.createFromAsset(getAssets(), "opensans.ttf");

        final InputStream imageStream;
        try {
            imageStream = getContentResolver().openInputStream(uri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

            int width = selectedImage.getWidth();
            int height = selectedImage.getHeight();

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

            bitmap = Bitmap.createBitmap(IMAGE_SIZE, IMAGE_SIZE, null); //create new empty bitmap;

            Canvas canvas = new Canvas(bitmap); //init canvas with bitmap for height
            //canvas.drawARGB(200, 0x56, 0x93, 0x54);
            canvas.drawARGB(0xff, 0xff, 0xff, 0xff);


            canvas.drawBitmap(selectedImage, null, rectf, null);


            //Bitmap template = BitmapFactory.decodeFile(template_path);
            Bitmap template = loadTemplate(this);
            canvas.drawBitmap(template, null, new RectF(0,0,1080,1080), null); //// TODO: 21-Jan-17 might be CPU intensive

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(4);
            float text_size = 46;
            float text_start_y = 880;
            float text_padding = 6;

            paint.setTextSize(text_size);
            paint.setTypeface(typeface);

            String info[] = new String[] {"Price: ", "Phone: ", "Social: "};
            for (int i = 0; i < 3; i++) {
                if (i == 2) {paint.setUnderlineText(true);}
                canvas.drawText(info[i]+data.get(i+1), 150, text_start_y, paint);
                text_start_y += (text_size + text_padding);
            }

            imageView.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private Bitmap loadTemplate(Context context) throws IOException {
        InputStream inputStream = getAssets().open("template1.png");
        return BitmapFactory.decodeStream(inputStream);

    }

    private Bitmap merge(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }

    public Uri save(View view) {
        if (bitmap == null) {
            Toast.makeText(this, "Nothing to save", Toast.LENGTH_SHORT).show();
            return null;
        }

        String imageFileName = "image_" + System.currentTimeMillis();
        File file =  new File(
                MyApp.getAppFolder(),  /* prefix */
                imageFileName + ".png"  /* suffix */
        );
        MyApp.log("out file: "+file.getAbsolutePath());

        OutputStream outputstream = null;
        try {
            outputstream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputstream);
            Toast.makeText(this, "Image saved at : "+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            MediaScannerConnection.scanFile(this, new String[] { file.getPath() }, new String[] { "image/png" }, null);
            return Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Saving image failed", Toast.LENGTH_SHORT).show();
            MyApp.log(e.getMessage());
            MyApp.log(e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void share(View view) {
        // prepare image
        Uri uri = save(view);
        if (uri == null)  return;

        // preparing text
        String text = "";
        String info[] = new String[] {"Price: ", "Phone: ", "Social: "};
        for (int i = 0; i < 3; i++) {
            text += (info[i] + data.get(0) + "\n");
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.setType("image/*");
        startActivity(sendIntent);
    }

    private class ImageTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            MyApp.log("Touched at: " +
                    event.getX() +", "+
                    event.getY()
            );
            return true;
        }
    }
}
