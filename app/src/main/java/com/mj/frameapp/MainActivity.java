package com.mj.frameapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final int ACTION_REQUEST_GALLERY = 0xb;
    public static final String USER_PIC_URI_AS_STRING = "user-pic-uri";
    public static final String USER_INFO_ARRAY = "user info array";
    public static final String TEMPLATE_PATH_AS_STRING = "template path";

    private EditText edName, edPhone, edPrice;
    private String selected_template = "none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edPhone = (EditText) findViewById(R.id.ed_phone);
        edName = (EditText) findViewById(R.id.ed_name);
        edPrice = (EditText) findViewById(R.id.ed_price);

    }


    private String numberWithCommas(String number) {
        double amount = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount);
    }

    public void chooseTemplate(View view) {
        File templates_folder = MyApp.getTemplatesFolder();

        if (templates_folder == null) {
            Toast.makeText(this, "Failed to load templates", Toast.LENGTH_SHORT).show();
        }

        final String[] templates = templates_folder.list();

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(R.string.pick_template)
                .setItems(templates, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MyApp.log("selected : "+which);
                        selected_template = templates[which];
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
