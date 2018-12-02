package com.example.amosh.todotobe.Data;

public class Users {
    private final String mUserName;
    private final String mUserPassword;
    private final String mUserEmail;
    private final String mUserBirthday;
    private final String mUserImage;


    public Users(String userName, String userPassword, String userEmail, String userBirthday,
                 String userImage) {
        mUserName = userName;
        this.mUserPassword = userPassword;
        this.mUserEmail = userEmail;
        this.mUserBirthday = userBirthday;
        this.mUserImage = userImage;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public String getUserBirthday() {
        return mUserBirthday;
    }

    public String getUserImage() {
        return mUserImage;
    }
}
