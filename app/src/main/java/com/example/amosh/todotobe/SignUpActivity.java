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
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.Data.Users;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    //FaceBook variables
    CallbackManager callbackManager;
    Profile profile;
    LoginButton fblogin;
    ImageView FBlogin;

    //G+ variables
    GoogleSignInClient mGoogleSignInClient;
    SignInButton glogin;
    ImageView GLogin;
    GoogleSignInOptions gso;
    GoogleSignInAccount account;


    //UI component
    EditText userName;
    EditText userEmail;
    EditText userBirthDay;
    EditText userPassword;
    String userImage;

    MyUsersDbHelper usersDbHelper;

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

        glogin = (SignInButton) findViewById(R.id.google_plus_login);
        GLogin = (ImageView) findViewById(R.id.sign_up_Google);

        userName = (EditText) findViewById(R.id.sign_up_name_text);
        userEmail = (EditText) findViewById(R.id.sign_up_email_text);
        userBirthDay = (EditText) findViewById(R.id.sign_up_birthday_text);
        userPassword = (EditText) findViewById(R.id.sign_up_password_text);

        usersDbHelper = new MyUsersDbHelper(this);

        //Facebook code
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
                                    String image = object.getString("picture");
                                    String email = object.getString("email");
                                    userEmail.setText(email);
                                    userName.setText(name);
                                    userImage = image.trim();
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

        //Google+ code
        //Build Google Sign in options
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //get Sign in client
        mGoogleSignInClient = GoogleSignIn.getClient(SignUpActivity.this, gso);

        //get currently signed in user returns null if there is no logged in user
        account = GoogleSignIn.getLastSignedInAccount(SignUpActivity.this);
        //update ui
        updateUI(account);

        GLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        // Intents changers
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


        // Adding new user
        TextView join = (TextView) findViewById(R.id.sign_up_join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasReadPermission();
                hasWritePermission();
                if (hasReadPermission() == true && hasWritePermission() == true) {
                    addNewUser();
                    if (!addNewUser() == false) {
                        Toast.makeText(SignUpActivity.this, "welcome", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "complete missed data ", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "NOT YET", Toast.LENGTH_SHORT).show();
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


    private boolean addNewUser() {

        boolean isAllOk = true;
        if (!checkIfValueSet(userName, "Name")) {
            isAllOk = false;
        }
        if (!checkIfValueSet(userPassword, "password")) {
            isAllOk = false;
        }
        if (!checkIfValueSet(userBirthDay, "birthday")) {
            isAllOk = false;
        }
        if (!checkIfValueSet(userEmail, "email")) {
            isAllOk = false;
        }
        if (!isAllOk) {
            return false;
        }

        Users users = new Users(
                userName.getText().toString().trim(),
                userPassword.getText().toString().trim(),
                userEmail.getText().toString().trim(),
                userBirthDay.getText().toString().trim(),
                userImage);
        usersDbHelper.insertUser(users);
        return true;
    }

    private boolean checkIfValueSet(EditText text, String description) {
        if (TextUtils.isEmpty(text.getText())) {
            text.setError("Missing user " + description);
            return false;
        } else {
            text.setError(null);
            return true;
        }
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

        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        handleSignInResult(task);
    }

    public void FBLogin(View v) {
        if (v == FBlogin) {
            fblogin.performClick();
        }
    }

    //Method to G+ signIn
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 6);
    }

    //Handle G+ sign in results
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        //Account is not null then user is logged in
        if (account != null) {

            userName.setText(account.getDisplayName());
            userEmail.setText(account.getEmail());
            userImage = account.getPhotoUrl().toString().trim();

        } else {
            //user is not logged in
            // Set the dimensions of the sign-in button.
            glogin.setSize(SignInButton.SIZE_WIDE);
            glogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });
        }

    }


}