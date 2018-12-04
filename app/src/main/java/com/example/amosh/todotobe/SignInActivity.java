package com.example.amosh.todotobe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amosh.todotobe.Data.MyUsersDbHelper;

import java.io.File;

public class SignInActivity extends AppCompatActivity {

    EditText userName;
    EditText userPassword;
    ImageView eye;
    int eyeVisibility = 1;
    TextView forgotPassword;

    MyUsersDbHelper usersDbHelper;

    SharedPreferences loginPreference;
    String MY_PREF = "my_pref";

    public String passedUserName;

    private int STORAGE_READ_PERMISSION = 1;
    private int STORAGE_WRITE_PERMISSION = 2;
    private int STORAGE_INTERNET_ACCESS_PERMISSION = 3;

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    private void startSplashScreen(String name) {
        Intent splashScreen = new Intent(SignInActivity.this, splash_screen_activity.class);

        splashScreen.putExtra("name", name);
        startActivity(splashScreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        String name = getIntent().getStringExtra("name");
        String password = getIntent().getStringExtra("password");

        usersDbHelper = new MyUsersDbHelper(this);

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
                hasReadPermission();
                hasWritePermission();
                passedUserName = userName.getText().toString().trim();
                if (hasReadPermission() && hasWritePermission()) {
                    if (!doesDatabaseExist(SignInActivity.this, "users.db")) {
                        Toast.makeText(SignInActivity.this, "NO USERS INFO. DETECTED\nPLEASE.. SIGN UP", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!isUserExist(userName.getText().toString().trim(), userPassword.getText().toString().trim())) {
                            Toast.makeText(SignInActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                        } else {
                            // initialize SharePreference
                            loginPreference = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);

                            // this condition will do the trick.
                            if (loginPreference.getString("tag", "notOk").equals("notOk")) {

                                // add tag in SharedPreference here..
                                SharedPreferences.Editor edit = loginPreference.edit();
                                edit.putString("tag", "ok");
                                edit.commit();
                                startSplashScreen(passedUserName);

                            } else if (loginPreference.getString("tag", null).equals("ok")) {
                                startMainScreenIntent(passedUserName);
                            }
                        }
                    }
                }
            }
        });

        forgotPassword = (TextView) findViewById(R.id.forgot_pass);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });


    }

    private void startSignUpIntent() {
        Intent signUpActivity = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(signUpActivity);
    }

    private void startMainScreenIntent(String name) {
        Intent MainScreenActivity = new Intent(SignInActivity.this, MainScreenActivity.class);

        MainScreenActivity.putExtra("name", name);

        startActivity(MainScreenActivity);
    }

    private boolean isUserExist(String name, String password) {
        SQLiteDatabase db = usersDbHelper.getReadableDatabase();
        Cursor names = usersDbHelper.checkNames();
        if (names.getCount() > 0) {
            names.moveToFirst();
            do {
                if (name.equals(names.getString(0))) {
                    if (password.equals((names.getString(1))))
                        return true;
                }

            } while (names.moveToNext());
            return false;
        }
        return false;
    }

    private boolean hasReadPermission() {
        if (ContextCompat.checkSelfPermission(SignInActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestReadStoragePermission();

        }
        return false;
    }

    private void requestReadStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Read permission needed")
                    .setMessage("Needed to save your data")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(SignInActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_READ_PERMISSION);

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_READ_PERMISSION);
        }
    }

    private boolean hasWritePermission() {
        if (ContextCompat.checkSelfPermission(SignInActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestWriteStoragePermission();

        }
        return false;
    }

    private void requestWriteStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Write permission needed")
                    .setMessage("Needed to save your data")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(SignInActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_WRITE_PERMISSION);

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_WRITE_PERMISSION);
        }
    }

    private void resetPassword() {
        // TODO : forget password method
    }
}
