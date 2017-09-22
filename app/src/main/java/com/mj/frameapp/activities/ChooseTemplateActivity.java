package com.mj.frameapp.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;

import com.mj.frameapp.MyApp;
import com.mj.frameapp.R;
import com.mj.frameapp.adapters.TemplatesAdapter;
import com.mj.frameapp.utils.ImageMaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChooseTemplateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_template);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        File folder = MyApp.getTemplatesFolder();

        if (folder == null) {
            MyApp.log("app folder does not exist");
            return;
        }

        String[] ft = folder.list();
        if (ft == null) {
            MyApp.log("app folder dsnt have templates files");
            return;
        }

        ArrayList<String> templates = new ArrayList<>(ft.length);
        for (int i = 0; i < ft.length; i++) {
            MyApp.log("got template: "+ft[i]);
            templates.add(ft[i]);
        }

        Bitmap selectedImage = null;
        try {
            Uri uri = Uri.parse(getIntent().getStringExtra(ChooseImageActivity.CURRENT_IMAGE_URI));
            selectedImage = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            MyApp.log("selected image is bullshit");
            return;
        }

        TemplatesAdapter adapter = new TemplatesAdapter(this, selectedImage, templates);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageMaker.clearCache();
    }

    @Override
    protected void attachBaseContext(android.content.Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
