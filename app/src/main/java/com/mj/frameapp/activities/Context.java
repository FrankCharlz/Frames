package com.mj.frameapp.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mj.frameapp.R;
import com.mj.frameapp.adapters.TemplatesAdapter;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Context extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_template);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        TemplatesAdapter adapter = new TemplatesAdapter(this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void attachBaseContext(android.content.Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
