package com.example.tellasflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        CountDownTimer countDownTimer;


        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View answerSideView = findViewById(R.id.flashcard_answer);

                int cx = answerSideView.getWidth() / 2;
                int cy = answerSideView.getHeight() / 2;

                float finalRadius = (float) Math.hypot(cx, cy);

                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
            }
        });

        /*
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

         */

        findViewById(R.id.button_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 1000);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                MainActivity.this.startActivityForResult(intent, 79);
            }
        });


        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Animation leftOutAnim = AnimationUtils.loadAnimation
                        (v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation
                        (v.getContext(), R.anim.right_in);



                if (allFlashcards.size() == 0)
                    return;

                currentCardDisplayedIndex += 1;

                if(currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(findViewById(R.id.flashcard_question),
                            "You've reached the end of the cards, going back to start.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardDisplayedIndex = 0;
                }

                allFlashcards = flashcardDatabase.getAllCards();
                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                ((TextView) findViewById(R.id.flashcard_question)).setText(flashcard.getAnswer());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(flashcard.getQuestion());



                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);
                findViewById(R.id.flashcard_question).startAnimation(rightInAnim);

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
            //((TextView) findViewById(R.id.choice_obama)).setText(string2);

            ((TextView) findViewById(R.id.flashcard_answer)).setText(string2);

            flashcardDatabase.insertCard(new Flashcard(string1, string2));

            allFlashcards = flashcardDatabase.getAllCards();
        }

        //if (requestCode == 79 && data != null){
        //}

    }

}