package com.vanderveldt.rens.mad_libs;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.*;

public class Story_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_activity);

        Bundle extras = getIntent().getExtras();
        String storytext = extras.getString("story");

        TextView storyTV = (TextView) findViewById(R.id.storytext);
        storyTV.setText(storytext);
    }

}