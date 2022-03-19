package com.example.tellasflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.choice_bush).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.choice_bush).setBackgroundColor(Color.RED);
                findViewById(R.id.choice_obama).setBackgroundColor(Color.GREEN);
            }
        });

        findViewById(R.id.choice_obama).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.choice_obama).setBackgroundColor(Color.GREEN);
            }
        });

        findViewById(R.id.choice_trump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.choice_trump).setBackgroundColor(Color.RED);
                findViewById(R.id.choice_obama).setBackgroundColor(Color.GREEN);
            }
        });

        findViewById(R.id.reset_but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.choice_bush).setBackgroundColor(Color.parseColor("#f4cccc"));
                findViewById(R.id.choice_obama).setBackgroundColor(Color.parseColor("#f4cccc"));
                findViewById(R.id.choice_trump).setBackgroundColor(Color.parseColor("#f4cccc"));
            }
        });

        findViewById(R.id.button_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 1000);
            }
        });

        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                MainActivity.this.startActivityForResult(intent, 79);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && data != null) {
            String string1 = data.getExtras().getString("quest");
            String string2 = data.getExtras().getString("ans");
            // System.out.println(string1 + string2);

            ((TextView) findViewById(R.id.flashcard_question)).setText(string1);
            ((TextView) findViewById(R.id.choice_obama)).setText(string2);

            ((TextView) findViewById(R.id.flashcard_answer)).setText(string2);
        }

        if (requestCode == 79 && data != null){

        }
    }

}