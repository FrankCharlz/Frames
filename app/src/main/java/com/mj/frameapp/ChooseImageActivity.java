package com.mj.frameapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class ChooseImageActivity extends AppCompatActivity {

    private static final int ACTION_REQUEST_GALLERY = 9;
    private static final String USER_PIC_URI_AS_STRING = "pic_uri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);
    }

    public void chooseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        Intent chooser = Intent.createChooser(intent, "Choose a Picture");
        startActivityForResult(chooser, ACTION_REQUEST_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Image selected successfully", Toast.LENGTH_SHORT).show();
            MyApp.log(data.getDataString());

            final Uri imageuri = data.getData();
            startEditActivity(imageuri);

        } else if (resultCode == RESULT_CANCELED) {
            // User cancelled the image capture
            Toast.makeText(this, "Image selction was cancelled", Toast.LENGTH_SHORT).show();
        } else {
            // Image capture failed, advise user
            Toast.makeText(this, "Image selection failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void startEditActivity(Uri uri) {
        /***
        String name = edName.getText().toString().trim().toLowerCase();
        String price = edPrice.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();

        if (!name.startsWith("@")) name = "@" + name;

        if (!price.isEmpty()) price = numberWithCommas(price)+"/=";

        File f = new File(MyApp.getTemplatesFolder(), selected_template);
        if (!f.exists()) {
            Toast.makeText(this, "Failed to load selected template", Toast.LENGTH_SHORT).show();
            return;
        }

        selected_template = f.getAbsolutePath();

        String info[] = new String[] {price, phone, name};
         */

        CurrentItemInfo.uri = uri.toString();

        Intent intent = new Intent(this, BeiActivity.class);
        startActivity(intent);
    }
}
