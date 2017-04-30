package com.mj.frameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tumblr.remember.Remember;

public class RegistrationActivity extends AppCompatActivity {

    EditText edName, edPhone;
    TextView btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edName = (EditText) findViewById(R.id.ed_name);
        edPhone = (EditText) findViewById(R.id.ed_phone);
        btnSubmit = (TextView) findViewById(R.id.bth_submit);


    }

    public void completeRegistration(View v) {
        String name = edName.getText().toString();
        String phone = edPhone.getText().toString();

        if ( name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "You must fill both fields to proceed",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        //kajaza zote
        Remember.putString("insta-name", name);
        Remember.putString("user-phone", phone);
        Remember.putBoolean("done_registration", true);

        Toast.makeText(
                getApplicationContext(),
                "Congrats, you are now all set.",
                Toast.LENGTH_SHORT
        ).show();

        v.getContext().startActivity(new Intent(v.getContext(), MainActivity.class));
        finish();
    }
}
