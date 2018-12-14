package com.example.amosh.todotobe;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.Data.UsersContract;
import com.squareup.picasso.Picasso;


public class ProfileActivity extends AppCompatActivity {
    public static final int PICK_IMAGE_REQUEST = 0;
    String username;
    MyUsersDbHelper usersDbHelper;
    TextView name;
    EditText password;
    EditText email;
    TextView birthday;
    ImageView profilePic;
    TextView apply;
    DatePickerDialog.OnDateSetListener mPicker;
    Uri imageUri;
    String mImageUriString = "";
    DrawerLayout mDrawerLayout;
    ImageView close;
    View header;
    NavigationView navigationView;
    ImageView menu_icon;
    ImageView exit_icon;
    String imageUriString;
    ImageView eye;
    int eyeVisibility = 1;

    public static boolean isValidEmail(EditText text, String description) {
        if (!TextUtils.isEmpty(text.getText()) &&
                Patterns.EMAIL_ADDRESS.matcher(text.getText()).matches()) {
            text.setError(null);
            return true;
        } else {
            text.setError("Please Enter " + description);
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        username = getIntent().getStringExtra("name");

        usersDbHelper = new MyUsersDbHelper(this);

        profilePic = (ImageView) findViewById(R.id.profile_pro_pic);
        name = (TextView) findViewById(R.id.prfile_username);
        password = (EditText) findViewById(R.id.prfile_password);
        email = (EditText) findViewById(R.id.prfile_email);
        birthday = (TextView) findViewById(R.id.prfile_birhday);

        SQLiteDatabase db = usersDbHelper.getReadableDatabase();
        Cursor cursor = usersDbHelper.readUser(username);
        if (cursor.moveToFirst()) {
            imageUriString = cursor.getString(cursor.getColumnIndex(UsersContract.UsersEntry.COLUMN_IMAGE));
            String emailString = cursor.getString(cursor.getColumnIndex(UsersContract.UsersEntry.COLUMN_EMAIL));
            String passwordString = cursor.getString(cursor.getColumnIndex(UsersContract.UsersEntry.COLUMN_PASSWORD));
            String birthDayString = cursor.getString(cursor.getColumnIndex(UsersContract.UsersEntry.COLUMN_BIRTHDAY));
            if (imageUriString.equals("")) {
                profilePic.setImageDrawable(getResources().getDrawable(R.drawable.ic_8c5c19d99c));
            } else {
                Uri imageUri = Uri.parse(imageUriString);
                Picasso.with(ProfileActivity.this).load(imageUri).into(profilePic);
            }
            name.setText(username);
            email.setText(emailString);
            password.setText(passwordString);
            birthday.setText(birthDayString);
        } else {
            Toast.makeText(ProfileActivity.this, "NO DATA AT THE CURSOR", Toast.LENGTH_SHORT).show();
        }

        // Changing profile picture
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        eye = (ImageView) findViewById(R.id.profile_eye);

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (eyeVisibility == 1) {
                    eyeVisibility = 0;
                    password.setTransformationMethod(null);
                    eye.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_button_colored));
                } else {
                    eyeVisibility = 1;
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    eye.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_button_gray));
                }
            }
        });

        // Navigation Drawer image and items
        menu_icon = (ImageView) findViewById(R.id.profile_menu_icon);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.profile_drawer_layout);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
        navigationView = (NavigationView) findViewById(R.id.profile_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent home = new Intent(ProfileActivity.this, MainScreenActivity.class);
                        home.putExtra("name", username);
                        startActivity(home);
                        break;
                    case R.id.nav_overview:
                        Intent overviewActivity = new Intent(ProfileActivity.this, OverviewActivity.class);
                        overviewActivity.putExtra("name", username);
                        startActivity(overviewActivity);
                        break;
                    case R.id.nav_groups:
                        Intent groupsActivity = new Intent(ProfileActivity.this, MyGroupsActivity.class);
                        groupsActivity.putExtra("name", username);
                        startActivity(groupsActivity);
                        break;
                    case R.id.nav_lists:
                        break;
                    case R.id.nav_profile:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_timeline:
                        Intent profileActivity = new Intent(ProfileActivity.this, TimelineActivity.class);
                        profileActivity.putExtra("name", username);
                        startActivity(profileActivity);
                        break;
                    case R.id.nav_settings:
                        Intent settingsActivity = new Intent(ProfileActivity.this, SettingsActivity.class);
                        settingsActivity.putExtra("name", username);
                        startActivity(settingsActivity);
                        break;
                    case R.id.nav_logout:
                        Intent signInActivity = new Intent(ProfileActivity.this, SignInActivity.class);
                        startActivity(signInActivity);
                        break;
                }
                return true;
            }
        });

        header = navigationView.getHeaderView(0);
        close = (ImageView) header.findViewById(R.id.nav_head_close);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDateOfBirth();

            }
        });

        exit_icon = (ImageView) findViewById(R.id.profile_logout_icon);
        exit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });

        apply = (TextView) findViewById(R.id.profile_apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyChanges();
            }
        });
    }

    private void applyChanges() {
        String newMail = email.getText().toString().trim();
        String newPass = password.getText().toString().trim();
        String newBirthDay = birthday.getText().toString().trim();
        String newImage;

        if (mImageUriString.equals("")) {
            newImage = imageUriString;
        } else {
            newImage = mImageUriString;
        }

        if (isValidEmail(email, "valid mail")) {
            Toast.makeText(ProfileActivity.this, "Changes applied", Toast.LENGTH_SHORT).show();
            usersDbHelper.updateUserDetails(username, newMail, newPass, newImage, newBirthDay);
            Intent home = new Intent(ProfileActivity.this, MainScreenActivity.class);
            home.putExtra("name", username);
            startActivity(home);
        } else {
            Toast.makeText(ProfileActivity.this, "check details", Toast.LENGTH_SHORT).show();
        }

    }

    // open gallery to get image
    private void openGallery() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                imageUri = resultData.getData();
                profilePic.setImageURI(imageUri);
                profilePic.invalidate();
                mImageUriString = imageUri.toString();
            }

        }
    }

    private void getDateOfBirth() {

        // picking the new date and set it to the text view
        mPicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String monthName = getMonthName(month);
                String date = monthName + " " + day + "," + year;
                birthday.setText(date);
            }
        };
        // start picker with pre-chosen date
        DatePickerDialog dialog = new DatePickerDialog(ProfileActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mPicker,
                2000, 0, 1);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }

    private String getMonthName(int month) {

        String monthName = "";

        if (month == 1) {
            monthName = "January";
        }
        if (month == 2) {
            monthName = "February";
        }
        if (month == 3) {
            monthName = "March";
        }
        if (month == 4) {
            monthName = "April";
        }
        if (month == 5) {
            monthName = "May";
        }
        if (month == 6) {
            monthName = "June";
        }
        if (month == 7) {
            monthName = "July";
        }
        if (month == 8) {
            monthName = "August";
        }
        if (month == 9) {
            monthName = "September";
        }
        if (month == 10) {
            monthName = "October";
        }
        if (month == 11) {
            monthName = "November";
        }
        if (month == 12) {
            monthName = "December";
        }

        return monthName;
    }

    private void exit() {
        Intent exit = new Intent(ProfileActivity.this, SignInActivity.class);
        startActivity(exit);
    }

}
