package com.vanderveldt.rens.mad_libs;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import android.preference.PreferenceManager;
import static android.preference.PreferenceManager.*;
import static android.view.View.VISIBLE;
import static java.lang.String.format;

public class Submit_activity extends AppCompatActivity {

    String word;
    String name;
    boolean Init = false;
    TextView welcomeTV;
    TextView wordsToGoTV;
    EditText wordSet;
    InputStream stream;
    Story story;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_activity);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        name = prefs.getString("name", "Rens");

        welcomeTV = (TextView) findViewById(R.id.welcomeText);
        welcomeTV.setText(format("Welcome %s !", name));

        TextView wordsToGoTV = (TextView) findViewById(R.id.wordsToGo);
        wordsToGoTV.setText("Click the button to start:");
    }
    public void submitWord(View view) {

        // Runs once to set the texts and story.
        if (!Init){

            // Create story
            stream = this.getResources().openRawResource(R.raw.madlib1_tarzan);
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
                wordSet = (EditText) findViewById(R.id.submitField);
                word = wordSet.getText().toString();

                // Reset filled in word and set hint to next placeholder
                wordSet.setText("");
                wordSet.setHint(story.getNextPlaceholder());

                if (word.length() == 0) {
                    Toast toast = Toast.makeText(this, "Please enter a word", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    story.fillInPlaceholder(word);
                    wordsToGoTV.setText(format("%s word(s) to go!", Integer.toString(story.getPlaceholderRemainingCount())));
                }
            }

            // Submit all words as a story to the next activity and clean the story
            if (story.isFilledIn()) {
                Intent goToStoryActivity = new Intent(this, Story_activity.class);
                goToStoryActivity.putExtra("story", story.toString());
                finish();
                startActivity(goToStoryActivity);
                story.clear();
            }
        }
    }
}
