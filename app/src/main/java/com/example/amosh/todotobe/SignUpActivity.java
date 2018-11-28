package com.example.amosh.todotobe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    ProfileTracker profileTracker;
    Profile profile;

    LoginButton fblogin;
    ImageView FBlogin;

    EditText userName;
    EditText userEmail;
    EditText userBirthDay;
    EditText userPassword;

    private int STORAGE_READ_PERMISSION = 1;
    private int STORAGE_WRITE_PERMISSION = 2;
    private int STORAGE_INTERNET_ACCESS_PERMISSION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FaceBook SDK NEEDs .. next 2 lines must be here
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        // Then continue your code
        setContentView(R.layout.sign_up_layout);

        FBlogin = (ImageView) findViewById(R.id.sign_up_FB);
        fblogin = (LoginButton) findViewById(R.id.fb_login_button);
        userName = (EditText) findViewById(R.id.sign_up_name_text);
        userEmail = (EditText) findViewById(R.id.sign_up_email_text);
        userBirthDay = (EditText) findViewById(R.id.sign_up_birthday_text);
        userPassword = (EditText) findViewById(R.id.sign_up_password_text);

        List<String> permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken", "picture");

        fblogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                try {
                                    String name = object.getString("name");
                                    String profilePic = object.getString("picture");
                                    String email = object.getString("email");
                                    userEmail.setText(email);
                                    userName.setText(name);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        TextView signIn = (TextView) findViewById(R.id.sign_up_go_sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();

            }
        });

        ImageView backArrow = (ImageView) findViewById(R.id.sign_up_back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backClick();

            }
        });

        TextView join = (TextView) findViewById(R.id.sign_up_join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasReadPermission();
                hasWritePermission();
                if (hasReadPermission() == true && hasWritePermission() == true) {
                    Toast.makeText(SignUpActivity.this, "Got it", Toast.LENGTH_SHORT).show();
                    addNewUser();
                } else {
                    Toast.makeText(SignUpActivity.this, "NOT YET", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ImageView Googlelogin = (ImageView) findViewById(R.id.sign_up_Google);
        Googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasInternetPermission();
                if (hasInternetPermission() == true) {
                    Toast.makeText(SignUpActivity.this, "Internet connection approved", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(SignUpActivity.this, "No internet connection approved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean hasReadPermission() {
        if (ContextCompat.checkSelfPermission(SignUpActivity.this,
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
                            ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_READ_PERMISSION);

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
        if (ContextCompat.checkSelfPermission(SignUpActivity.this,
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
                            ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_WRITE_PERMISSION);

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

    private boolean hasInternetPermission() {
        if (ContextCompat.checkSelfPermission(SignUpActivity.this,
                Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestInternetStoragePermission();

        }
        return false;
    }

    private void requestInternetStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.INTERNET)) {

            new AlertDialog.Builder(this)
                    .setTitle("Internet permission needed")
                    .setMessage("Needed to get your online")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.INTERNET}, STORAGE_INTERNET_ACCESS_PERMISSION);

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, STORAGE_INTERNET_ACCESS_PERMISSION);
        }
    }


    private void addNewUser() {

        //TODO : DataBase for New User
    }

    private void backClick() {
        Intent signInActivity = new Intent(SignUpActivity.this, SignInActivity.class);

        // Start the new activity
        startActivity(signInActivity);
    }

    private void startSignIn() {
        Intent signInActivity = new Intent(SignUpActivity.this, SignInActivity.class);

        // Start the new activity
        startActivity(signInActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }

    public void FBLogin(View v) {
        if (v == FBlogin) {
            fblogin.performClick();
        }
    }
}