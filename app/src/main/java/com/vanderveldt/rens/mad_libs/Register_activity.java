package com.vanderveldt.rens.mad_libs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Register_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
    }

    Intent intentname = new Intent(this, WordActivity.class);
    startActivity(intentname);
    finish();
}
