package com.example.amosh.todotobe.Data;

public class Items {
    private final String mUsername;
    private final String mName;
    private final int mState;
    private final String mCategory;


    public Items(String username, String name, int state, String category) {
        mUsername = username;
        this.mName = name;
        this.mState = state;
        this.mCategory = category;
    }

    public String getName() {
        return mName;
    }

    public String getCategory() {
        return mCategory;
    }

    public int getState() {
        return mState;
    }

    public String getUsername() {
        return mUsername;
    }
}
