package com.example.amosh.todotobe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);


        TextView signUp = (TextView) findViewById(R.id.sign_up_text_view);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signUpActivity = new Intent(SignInActivity.this, SignUpActivity.class);

                // Start the new activity
                startActivity(signUpActivity);
            }
        });

        TextView signIn = (TextView) findViewById(R.id.sign_in_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent splashScreen = new Intent(SignInActivity.this, splash_screen_activity.class);

                // Start the new activity
                startActivity(splashScreen);
            }
        });


    }
}
