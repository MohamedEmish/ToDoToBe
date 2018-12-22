package com.example.amosh.todotobe;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.Data.Users;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

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
    String userImage = "";
    ImageView eye;
    int eyeVisibility = 1;

    DatePickerDialog.OnDateSetListener mPicker;

    MyUsersDbHelper usersDbHelper;

    private int RC_SIGN_IN = 6;

    private int STORAGE_READ_PERMISSION = 1;
    private int STORAGE_WRITE_PERMISSION = 2;
    private int STORAGE_INTERNET_ACCESS_PERMISSION = 3;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FaceBook SDK NEEDs .. next 2 lines must be here
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

        eye = (ImageView) findViewById(R.id.sign_up_eye);

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


        userBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDateOfBirth();

            }
        });

        usersDbHelper = new MyUsersDbHelper(this);

        //Facebook code
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
                                    String email = object.getString("email");
                                    userEmail.setText(email);
                                    userName.setText(name);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                    userImage = image.trim();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignUpActivity.this, "FaceBook Login cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignUpActivity.this, "ops !! .. some error happened", Toast.LENGTH_SHORT).show();
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
                if (hasReadPermission() && hasWritePermission()) {
                    if (!addNewUser()) {
                        Toast.makeText(SignUpActivity.this, "Please .. check errors", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(SignUpActivity.this, "welcome", Toast.LENGTH_SHORT).show();
                        Intent signInActivity = new Intent(SignUpActivity.this, SignInActivity.class);
                        signInActivity.putExtra("name", userName.getText().toString());
                        signInActivity.putExtra("password", userPassword.getText().toString());

                        // Start the new activity
                        startActivity(signInActivity);
                    }
                }
            }
        });

    }

    private boolean hasInternetPermission() {
        if (ContextCompat.checkSelfPermission(SignUpActivity.this,
                Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestInternetPermission();

        }
        return false;
    }

    private void requestInternetPermission() {
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

    private boolean checkIfValueSet(EditText text, String description) {
        if (TextUtils.isEmpty(text.getText())) {
            text.setError("Missing user " + description);
            return false;
        } else {
            text.setError(null);
            return true;
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
        if (!isValidEmail(userEmail, "valid email")) {
            isAllOk = false;
        }
        if (!isAllOk) {
            return false;
        }
        if (isOldUser(userName.getText().toString().trim())) {
            userName.setError("This name exists");
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

    private void backClick() {
        Intent signInActivity = new Intent(SignUpActivity.this, SignInActivity.class);

        // Start the new activity
        startActivity(signInActivity);
    }

    private void startSignIn() {
        Intent signInActivity = new Intent(SignUpActivity.this, SignInActivity.class);
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
        startActivityForResult(signInIntent, RC_SIGN_IN);
        //update ui
        updateUI(account);

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

            GLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signOut();
                }
            });

        } else {
            //user is not logged in
            userName.setText("");
            userEmail.setText("");
            userImage = "";
            GLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });
        }

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updateUI(null);
                        }
                    }
                });
    }

    private boolean isOldUser(String name) {
        SQLiteDatabase db = usersDbHelper.getReadableDatabase();
        Cursor names = usersDbHelper.checkNames();

        if (names.getCount() == 0) {
            return false;
        }
        if (names != null) {
            names.moveToFirst();
            do {
                if (name.equals(names.getString(0))) {
                    return true;
                }

            } while (names.moveToNext());
            return false;
        }
        return true;
    }

    private void getDateOfBirth() {

        // picking the new date and set it to the text view
        mPicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String monthName = getMonthName(month);
                String date = monthName + " " + day + "," + year;
                userBirthDay.setText(date);
            }
        };
        // start picker with pre-chosen date
        DatePickerDialog dialog = new DatePickerDialog(SignUpActivity.this,
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

}