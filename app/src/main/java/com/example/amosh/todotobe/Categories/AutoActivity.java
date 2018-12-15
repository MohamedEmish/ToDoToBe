package com.example.amosh.todotobe.Categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.amosh.todotobe.MyGroupsActivity;
import com.example.amosh.todotobe.R;

public class AutoActivity extends AppCompatActivity {
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_layout);

        userName = getIntent().getStringExtra("name");

        ImageView backIcon = (ImageView) findViewById(R.id.auto_back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myGroupsIntent = new Intent(AutoActivity.this, MyGroupsActivity.class);
                myGroupsIntent.putExtra("name", userName);
                startActivity(myGroupsIntent);
            }
        });
    }
}