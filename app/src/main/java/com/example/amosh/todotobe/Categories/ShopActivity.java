package com.example.amosh.todotobe.Categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.amosh.todotobe.MyGroupsActivity;
import com.example.amosh.todotobe.R;

public class ShopActivity extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_layout);

        username = getIntent().getStringExtra("name");

        ImageView backIcon = (ImageView) findViewById(R.id.shop_back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myGroupsIntent = new Intent(ShopActivity.this, MyGroupsActivity.class);
                myGroupsIntent.putExtra("name", username);
                startActivity(myGroupsIntent);
            }
        });
    }
}
