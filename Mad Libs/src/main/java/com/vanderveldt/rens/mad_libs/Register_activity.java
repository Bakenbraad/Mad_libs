package com.vanderveldt.rens.mad_libs;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import android.widget.EditText;
import android.content.Intent;
import android.content.Context;

public class Register_activity extends AppCompatActivity {

    public static final String MyPREFERENCES = "myprefs";
    SharedPreferences sharedpreferences;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences.getBoolean("login", false)){
            forward();
            finish();
        }
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
    }

    public void saveName(View view) {
        EditText nameET = (EditText) findViewById(R.id.nameText);
        name = nameET.getText().toString();
        if (!(name.length() == 0)) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("name", name);
            editor.apply();
            editor.putBoolean("login", true);
            editor.apply();
            forward();
            finish();
        } else {
            Toast toast = Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onBackPressed() {
        finish();
    }
    public void forward() {
        name = sharedpreferences.getString("name", "");
        if (!(name.length() == 0)) {
            Intent goToSubmitActivity = new Intent(this, Submit_activity.class);
            startActivity(goToSubmitActivity);
        }
    }

}
