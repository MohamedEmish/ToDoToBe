package com.example.amosh.todotobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);


        TextView signUp = (TextView) findViewById(R.id.sign_up_text_view);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSignUpIntent();
            }
        });

        TextView signIn = (TextView) findViewById(R.id.sign_in_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSplashScreen();
            }
        });


    }

    private void startSplashScreen() {
        Intent splashScreen = new Intent(SignInActivity.this, splash_screen_activity.class);

        // Start the new activity
        startActivity(splashScreen);
    }

    private void startSignUpIntent() {
        Intent signUpActivity = new Intent(SignInActivity.this, SignUpActivity.class);
        // Start the new activity
        startActivity(signUpActivity);
    }


}
