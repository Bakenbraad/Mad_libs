package com.vanderveldt.rens.mad_libs;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import java.io.InputStream;
import static android.view.View.VISIBLE;
import static java.lang.String.format;

public class Submit_activity extends AppCompatActivity {

    public static final String MyPREFERENCES = "myprefs";
    SharedPreferences prefs;
    String word;
    String name;
    boolean Init = false;
    TextView welcomeTV;
    TextView wordsToGoTV;
    EditText wordSet;
    InputStream stream;
    Story story;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_submit_activity);

        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        name = prefs.getString("name", "User");

        welcomeTV = (TextView) findViewById(R.id.welcomeText);
        welcomeTV.setText(format("Welcome %s !", name));

        TextView wordsToGoTV = (TextView) findViewById(R.id.wordsToGo);
        wordsToGoTV.setText("Click the button to start:");
    }

    public void onBackPressed() {
        finish();
    }

    public void submitWord(View view) {

        // Runs once to set the texts and story.
        if (!Init){
            // Create random story
            int random = (int )(Math.random() * 5 + 1);
            switch(random){
                case 1: stream = this.getResources().openRawResource(R.raw.madlib0_simple);
                        break;
                case 2: stream = this.getResources().openRawResource(R.raw.madlib1_tarzan);
                        break;
                case 3: stream = this.getResources().openRawResource(R.raw.madlib2_university);
                        break;
                case 4: stream = this.getResources().openRawResource(R.raw.madlib3_clothes);
                        break;
                case 5: stream = this.getResources().openRawResource(R.raw.madlib4_dance);
                        break;
            }
            story = new Story(stream);

            // Set the submit field to the right hint and make it visible
            wordSet = (EditText) findViewById(R.id.submitField);
            wordSet.setHint(story.getNextPlaceholder());
            wordSet.setVisibility(VISIBLE);

             // Set amount of words to go
            wordsToGoTV = (TextView) findViewById(R.id.wordsToGo);
            wordsToGoTV.setText(format("%s word(s) to go!", Integer.toString(story.getPlaceholderRemainingCount())));

            // Make sure this only runs once.
            Init = true;
        }
        else {
            wordsToGoTV.setText(format("%s word(s) to go!", Integer.toString(story.getPlaceholderRemainingCount())));
            // Check if all words are filled in
            if (!story.isFilledIn()) {
                // Get words from fill-in
                wordSet.setHint(story.getNextPlaceholder());
                wordSet = (EditText) findViewById(R.id.submitField);
                word = wordSet.getText().toString();

                // Reset filled in word and set hint to next placeholder
                wordSet.setText("");

                if (word.length() == 0) {
                    Toast toast = Toast.makeText(this, "Please enter a word", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    story.fillInPlaceholder(word);
                    wordsToGoTV.setText(format("%s word(s) to go!", Integer.toString(story.getPlaceholderRemainingCount())));
                    wordSet.setHint(story.getNextPlaceholder());
                }
            }

            // Submit all words as a story to the next activity and clean the story
            if (story.isFilledIn()) {
                Intent goToStoryActivity = new Intent(this, Story_activity.class);
                goToStoryActivity.putExtra("story", story.toString());
                startActivity(goToStoryActivity);
                finish();
                story.clear();
            }
        }
    }

    public void onLogoutPressed(View view) {

        // Allow user to log out and change their name, reset story and close previous windows.
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", "");
        editor.apply();
        editor.putBoolean("login", false);
        editor.apply();
        Intent goToRegister = new Intent(this, Register_activity.class);
        startActivity(goToRegister);
        finish();
        if (story != null){
            story.clear();
        }

    }
}
