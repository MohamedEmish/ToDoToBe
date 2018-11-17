package com.example.amosh.todotobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        TextView signIn = (TextView) findViewById(R.id.sign_in_text_view);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signInActivity = new Intent(SignUpActivity.this, SignInActivity.class);

                // Start the new activity
                startActivity(signInActivity);
            }
        });

        ImageView backArrow = (ImageView) findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signInActivity = new Intent(SignUpActivity.this, SignInActivity.class);

                // Start the new activity
                startActivity(signInActivity);
            }
        });


    }
}

