package com.example.amosh.todotobe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amosh.todotobe.Adapters.ItemAdapter;
import com.example.amosh.todotobe.Data.Items;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;

import java.util.ArrayList;
import java.util.List;


public class MyGroupsActivity extends AppCompatActivity {
    String username;
    MyUsersDbHelper usersDbHelper;
    ImageView search;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_groups_layout);

        username = getIntent().getStringExtra("name");
        usersDbHelper = new MyUsersDbHelper(this);

        search = (ImageView) findViewById(R.id.my_groups_search_icon);
        final List<Items> itemsList = usersDbHelper.readItemsList(username);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomSearchDialog(MyGroupsActivity.this, itemsList);
            }
        });

        ImageView menu_icon = (ImageView) findViewById(R.id.my_groups_menu_icon);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.my_groups_drawer_layout);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.my_groups_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent homeActivity = new Intent(MyGroupsActivity.this, MainScreenActivity.class);
                        homeActivity.putExtra("name", username);
                        startActivity(homeActivity);
                        break;
                    case R.id.nav_overview:
                        Intent overviewActivity = new Intent(MyGroupsActivity.this, OverviewActivity.class);
                        overviewActivity.putExtra("name", username);
                        startActivity(overviewActivity);
                        break;
                    case R.id.nav_groups:
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;
                    case R.id.nav_lists:
                        Intent listsActivity = new Intent(MyGroupsActivity.this, ListsActivity.class);
                        String category = "";
                        listsActivity.putExtra("category", category);
                        listsActivity.putExtra("name", username);
                        startActivity(listsActivity);
                        break;
                    case R.id.nav_profile:
                        Intent profileActivity = new Intent(MyGroupsActivity.this, ProfileActivity.class);
                        profileActivity.putExtra("name", username);
                        startActivity(profileActivity);
                        break;
                    case R.id.nav_timeline:
                        Intent timelineActivity = new Intent(MyGroupsActivity.this, TimelineActivity.class);
                        timelineActivity.putExtra("name", username);
                        startActivity(timelineActivity);
                        break;
                    case R.id.nav_settings:
                        Intent settingsActivity = new Intent(MyGroupsActivity.this, SettingsActivity.class);
                        settingsActivity.putExtra("name", username);
                        startActivity(settingsActivity);
                        break;
                    case R.id.nav_logout:
                        Intent signInActivity = new Intent(MyGroupsActivity.this, SignInActivity.class);
                        SaveSharedPreference.clearUserName(MyGroupsActivity.this);
                        startActivity(signInActivity);
                        break;
                }
                return true;
            }
        });

        Cursor autoCursor = usersDbHelper.readItems(username, "Auto");
        Cursor billsCursor = usersDbHelper.readItems(username, "Bills");
        Cursor healthCursor = usersDbHelper.readItems(username, "Health");
        Cursor shopCursor = usersDbHelper.readItems(username, "Shop");
        Cursor travelCursor = usersDbHelper.readItems(username, "Travel");
        Cursor workCursor = usersDbHelper.readItems(username, "Work");


        CardView shopCardView = (CardView) findViewById(R.id.my_group_shop_card);
        TextView shopItemsNum = (TextView) findViewById(R.id.my_groups_shop_items);
        shopItemsNum.setText(String.valueOf(shopCursor.getCount()));
        shopCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listsActivity = new Intent(MyGroupsActivity.this, ListsActivity.class);
                String category = "Shop";
                listsActivity.putExtra("category", category);
                listsActivity.putExtra("name", username);
                startActivity(listsActivity);
            }
        });

        CardView workCardView = (CardView) findViewById(R.id.my_group_work_card);
        TextView workItemsNum = (TextView) findViewById(R.id.my_groups_work_items);
        workItemsNum.setText(String.valueOf(workCursor.getCount()));
        workCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listsActivity = new Intent(MyGroupsActivity.this, ListsActivity.class);
                String category = "Work";
                listsActivity.putExtra("category", category);
                listsActivity.putExtra("name", username);
                startActivity(listsActivity);
            }
        });

        CardView autoCardView = (CardView) findViewById(R.id.my_group_auto_card);
        TextView autoItemsNum = (TextView) findViewById(R.id.my_groups_auto_items);
        autoItemsNum.setText(String.valueOf(autoCursor.getCount()));
        autoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listsActivity = new Intent(MyGroupsActivity.this, ListsActivity.class);
                String category = "Auto";
                listsActivity.putExtra("category", category);
                listsActivity.putExtra("name", username);
                startActivity(listsActivity);
            }
        });

        CardView billsCardView = (CardView) findViewById(R.id.my_groups_bills_card);
        TextView billsItemsNum = (TextView) findViewById(R.id.my_groups_bills_items);
        billsItemsNum.setText(String.valueOf(billsCursor.getCount()));
        billsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listsActivity = new Intent(MyGroupsActivity.this, ListsActivity.class);
                String category = "Bills";
                listsActivity.putExtra("category", category);
                listsActivity.putExtra("name", username);
                startActivity(listsActivity);
            }
        });

        CardView healthCardView = (CardView) findViewById(R.id.my_group_health_card);
        TextView healthItemsNum = (TextView) findViewById(R.id.my_groups_health_items);
        healthItemsNum.setText(String.valueOf(healthCursor.getCount()));
        healthCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listsActivity = new Intent(MyGroupsActivity.this, ListsActivity.class);
                String category = "Health";
                listsActivity.putExtra("category", category);
                listsActivity.putExtra("name", username);
                startActivity(listsActivity);
            }
        });

        CardView travelCardView = (CardView) findViewById(R.id.my_group_travel_card);
        TextView travelItemsNum = (TextView) findViewById(R.id.my_groups_travel_items);
        travelItemsNum.setText(String.valueOf(travelCursor.getCount()));
        travelCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listsActivity = new Intent(MyGroupsActivity.this, ListsActivity.class);
                String category = "Travel";
                listsActivity.putExtra("category", category);
                listsActivity.putExtra("name", username);
                startActivity(listsActivity);
            }
        });

    }

    private void showCustomSearchDialog(Context context, final List<Items> list) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_search_dialoge, null, false);

        /*HERE YOU CAN FIND YOU IDS AND SET TEXTS OR BUTTONS*/
        final TextView searchText = view.findViewById(R.id.item_search_text);

        Button searchButton = view.findViewById(R.id.item_search);
        Button close = view.findViewById(R.id.item_search_close);

        final TextView emptyResults = (TextView) view.findViewById(R.id.search_empty);
        final RecyclerView result = (RecyclerView) view.findViewById(R.id.search_result);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = searchText.getText().toString().trim();
                if (text.equals("")) {
                    result.setVisibility(View.GONE);
                    emptyResults.setVisibility(View.GONE);
                }
                List<Items> searchList = new ArrayList<>();
                for (Items items : list) {
                    if (items.getName().equals(text)) {
                        searchList.add(items);
                    }
                }
                if (searchList.size() == 0) {
                    emptyResults.setVisibility(View.VISIBLE);
                    result.setVisibility(View.GONE);
                } else {
                    ItemAdapter searchAdapter = new ItemAdapter(MyGroupsActivity.this, searchList);
                    result.setAdapter(searchAdapter);
                    result.addItemDecoration(new DividerItemDecoration(result.getContext(), DividerItemDecoration.VERTICAL));
                    result.setLayoutManager(new LinearLayoutManager(MyGroupsActivity.this));
                    result.setVisibility(View.VISIBLE);
                    emptyResults.setVisibility(View.GONE);

                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.dialoge_box);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }

}