package com.mj.frameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tumblr.remember.Remember;

public class BeiActivity extends AppCompatActivity {

    private EditText edBei, edJina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bei);

        edBei = (EditText) findViewById(R.id.ed_bei);
        edJina = (EditText) findViewById(R.id.ed_jina_la_bidhaa);

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

        //kajaza zote
        CurrentItemInfo.name = jina;
        CurrentItemInfo.bei = bei;

        v.getContext().startActivity(new Intent(v.getContext(), EditorActivity.class));
        finish();
    }
}
