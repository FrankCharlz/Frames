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

    public void choosePhoto(View view) {

        if (selected_template.contentEquals("none")) {
            Toast.makeText(this, "Choose template kwanza", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        Intent chooser = Intent.createChooser(intent, "Choose a Picture");
        startActivityForResult(chooser, ACTION_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Image success", Toast.LENGTH_SHORT).show();
            MyApp.log(data.getDataString());

            final Uri imageuri = data.getData();
            startEditActivity(imageuri);

        } else if (resultCode == RESULT_CANCELED) {
            // User cancelled the image capture
            Toast.makeText(this, "Image cancelled", Toast.LENGTH_SHORT).show();
        } else {
            // Image capture failed, advise user
            Toast.makeText(this, "Image failed", Toast.LENGTH_SHORT).show();
        }
    }

    private String numberWithCommas(String number) {
        double amount = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount);
    }

    private void startEditActivity(Uri uri) {
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

        Intent intent = new Intent(this, EditorActivity.class);
        intent.putExtra(USER_PIC_URI_AS_STRING, uri.toString());
        intent.putExtra(TEMPLATE_PATH_AS_STRING, selected_template);
        intent.putExtra(USER_INFO_ARRAY, info);
        startActivity(intent);
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
