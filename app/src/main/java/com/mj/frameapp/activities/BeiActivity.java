package com.mj.frameapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mj.frameapp.R;
import com.squareup.picasso.Picasso;
import com.tumblr.remember.Remember;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BeiActivity extends AppCompatActivity {

    public static final String CURRENT_IMAGE_URI = "image-uri";
    private EditText edBei, edJina;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bei);

        edBei = (EditText) findViewById(R.id.ed_bei);
        edJina = (EditText) findViewById(R.id.ed_jina_la_bidhaa);



        uri = getIntent().getStringExtra(CURRENT_IMAGE_URI);
        ImageView imageView = (ImageView) findViewById(R.id.image);

        Picasso.with(this).load(Uri.parse(uri)).into(imageView);

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void doneIngizaBei(View v) {
        String jina = edJina.getText().toString();
        String bei = edBei.getText().toString();

        if ( jina.isEmpty() || bei.isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "You must fill both fields to proceed",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        try {
            bei = NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(bei))+"/=";
        } catch (NumberFormatException e) {
            Toast.makeText(
                    getApplicationContext(),
                    "The price enterd is incorrect",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        ArrayList<String> data = new ArrayList<>(3);
        data.add(uri); //image uri
        data.add(jina);
        data.add(bei);
        data.add(Remember.getString("user-phone", "[phone no]"));
        data.add(Remember.getString("insta-name", "[insta handle]"));

        Intent intent = new Intent(this, EditorActivity.class);
        intent.putExtra(EditorActivity.CURRENT_DATA_MAP, data);

        startActivity(intent);
        finish();
    }
}
