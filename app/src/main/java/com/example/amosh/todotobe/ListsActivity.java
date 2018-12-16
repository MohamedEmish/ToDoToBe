package com.example.amosh.todotobe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.amosh.todotobe.Adapters.ItemAdapter;
import com.example.amosh.todotobe.Data.Items;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;

public class ListsActivity extends AppCompatActivity {
    String username;

    ItemAdapter eItemAdapter;
    RelativeLayout emptyView;
    RecyclerView itemsListView;

    MyUsersDbHelper usersDbHelper;

    Spinner category;
    String eCategory;

    EditText name;
    Button add;
    Button close;
    Dialog dialog;
    String newName;

    ImageView search;
    ImageView next;
    ImageView backIcon;
    TextView title;

    String keyCategory;

    Cursor cursor;

    TextView listNumber;


    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists_layout);

        username = getIntent().getStringExtra("name");
        keyCategory = getIntent().getStringExtra("category");

        usersDbHelper = new MyUsersDbHelper(this);

        title = (TextView) findViewById(R.id.lists_layout_title);

        fab = (FloatingActionButton) findViewById(R.id.lists_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(ListsActivity.this);
            }
        });

        next = (ImageView) findViewById(R.id.lists_next_button);
        search = (ImageView) findViewById(R.id.lists_item_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchClick();
            }
        });

        switch (keyCategory) {
            case "":
                goNext(keyCategory);
                break;
            case "Auto":
                goNext(keyCategory);
                break;
            case "Bills":
                goNext(keyCategory);
                break;
            case "Health":
                goNext(keyCategory);
                break;
            case "Shop":
                goNext(keyCategory);
                break;
            case "Travel":
                goNext(keyCategory);
                break;
            case "Work":
                goNext(keyCategory);
                break;
        }
    }

    public void showCustomDialog(final Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_add_item_dialouge_box, null, false);

        /*HERE YOU CAN FIND YOU IDS AND SET TEXTS OR BUTTONS*/
        name = view.findViewById(R.id.new_item_name);

        category = view.findViewById(R.id.new_item_spinner);
        setupCategorySpinner();
        add = view.findViewById(R.id.new_item_add);
        close = view.findViewById(R.id.new_item_close);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewItem();
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

    private void setupCategorySpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_category_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        category.setAdapter(categorySpinnerAdapter);

        // Set the integer mSelected to the constant values
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("Auto")) {
                        eCategory = "Auto";
                    } else if (selection.equals("Bills")) {
                        eCategory = "Bills";
                    } else if (selection.equals("Health")) {
                        eCategory = "Health";
                    } else if (selection.equals("Shop")) {
                        eCategory = "Shop";
                    } else if (selection.equals("Travel")) {
                        eCategory = "Travel";
                    } else if (selection.equals("Work")) {
                        eCategory = "Work";
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void addNewItem() {

        newName = name.getText().toString().trim();

        if (isOldItem(newName)) {
            name.setError("This item exists");
        } else if (name.getText().toString().trim().equals("")) {
            name.setError("Type item name");
        } else if (!isOldItem(newName) && !newName.equals("")) {
            Items item = new Items(
                    username,
                    newName,
                    0,
                    eCategory);
            usersDbHelper.insertItem(item);
            Cursor newCursor = usersDbHelper.readItems(username);
            ItemAdapter newAdapter = new ItemAdapter(ListsActivity.this, newCursor);
            itemsListView.setAdapter(newAdapter);
            listNumber.setText(String.valueOf(newCursor.getCount()));
            dialog.dismiss();
        }
    }

    private boolean isOldItem(String name) {
        SQLiteDatabase db = usersDbHelper.getReadableDatabase();
        Cursor items = usersDbHelper.checkItems(username);

        if (items.getCount() == 0) {
            return false;
        }
        if (items != null) {
            items.moveToFirst();
            do {
                if (name.equals(items.getString(0))) {
                    return true;
                }

            } while (items.moveToNext());
            return false;
        }
        return true;
    }

    private void searchClick() {
        // TODO : custom Dialog box to search
    }

    private void goNext(final String category) {

        final String guide = category;

        backIcon = (ImageView) findViewById(R.id.lists_back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (guide.equals("")) {
                    Intent maiScreen = new Intent(ListsActivity.this, MainScreenActivity.class);
                    maiScreen.putExtra("name", username);
                    startActivity(maiScreen);
                } else {
                    Intent myGroupsIntent = new Intent(ListsActivity.this, MyGroupsActivity.class);
                    myGroupsIntent.putExtra("name", username);
                    startActivity(myGroupsIntent);
                }
            }
        });

        if (guide.equals("")) {

            title.setText("Lists");
            // Find and set empty view on the recycler View, so that it only shows when the list has 0 items.

            emptyView = (RelativeLayout) findViewById(R.id.lists_empty_view);

            // Find the Recycler View which will be populated with the event data
            itemsListView = (RecyclerView) findViewById(R.id.lists_recycler);
            itemsListView.addItemDecoration(new DividerItemDecoration(itemsListView.getContext(), DividerItemDecoration.VERTICAL));
            itemsListView.setLayoutManager(new LinearLayoutManager(this));

            // Cursor to populate recycler View
            cursor = usersDbHelper.readItems(username);
            if (cursor.getCount() == 0) {
                itemsListView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                itemsListView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
            eItemAdapter = new ItemAdapter(this, cursor);
            eItemAdapter.notifyDataSetChanged();
            itemsListView.setAdapter(eItemAdapter);


            listNumber = (TextView) findViewById(R.id.lists_item_num);
            listNumber.setText(String.valueOf(cursor.getCount()));
        } else {
            title.setText(guide);
            // Find and set empty view on the recycler View, so that it only shows when the list has 0 items.

            emptyView = (RelativeLayout) findViewById(R.id.lists_empty_view);

            // Find the Recycler View which will be populated with the event data
            itemsListView = (RecyclerView) findViewById(R.id.lists_recycler);
            itemsListView.addItemDecoration(new DividerItemDecoration(itemsListView.getContext(), DividerItemDecoration.VERTICAL));
            itemsListView.setLayoutManager(new LinearLayoutManager(this));

            // Cursor to populate recycler View
            cursor = usersDbHelper.readItems(username, guide);
            if (cursor.getCount() == 0) {
                itemsListView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                itemsListView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
            eItemAdapter = new ItemAdapter(this, cursor);
            eItemAdapter.notifyDataSetChanged();
            itemsListView.setAdapter(eItemAdapter);


            listNumber = (TextView) findViewById(R.id.lists_item_num);
            listNumber.setText(String.valueOf(cursor.getCount()));
        }

    }

    public void updateData() {
        eItemAdapter = new ItemAdapter(this, cursor);
        itemsListView.setAdapter(eItemAdapter);
    }


}
