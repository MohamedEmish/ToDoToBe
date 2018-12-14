package com.example.amosh.todotobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ListsActivity extends AppCompatActivity {
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists_layout);

        username = getIntent().getStringExtra("name");

        ImageView backIcon = (ImageView) findViewById(R.id.lists_back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myGroupsIntent = new Intent(ListsActivity.this, MainScreenActivity.class);
                myGroupsIntent.putExtra("name", username);
                startActivity(myGroupsIntent);
            }
        });
    }
}
