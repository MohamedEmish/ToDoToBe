package com.example.amosh.todotobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class WorkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_layout);

        ImageView backIcon = (ImageView) findViewById(R.id.work_back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myGroupsIntent = new Intent(WorkActivity.this, MyGroupsActivity.class);
                startActivity(myGroupsIntent);
            }
        });
    }
}
