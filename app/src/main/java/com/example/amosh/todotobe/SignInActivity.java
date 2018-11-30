package com.example.amosh.todotobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    EditText userName;
    EditText userPassword;
    ImageView eye;
    int eyeVisibility = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        String name = getIntent().getStringExtra("name");
        String password = getIntent().getStringExtra("password");

        userName = (EditText) findViewById(R.id.sign_in_name);
        userPassword = (EditText) findViewById(R.id.sign_in_password);

        userName.setText(name);
        userPassword.setText(password);

        eye = (ImageView) findViewById(R.id.sign_in_eye);

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (eyeVisibility == 1) {
                    eyeVisibility = 0;
                    userPassword.setTransformationMethod(null);
                    eye.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_button_colored));
                } else {
                    eyeVisibility = 1;
                    userPassword.setTransformationMethod(new PasswordTransformationMethod());
                    eye.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_button_gray));
                }
            }
        });



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
