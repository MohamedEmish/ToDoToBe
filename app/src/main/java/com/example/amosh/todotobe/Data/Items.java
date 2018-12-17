package com.example.amosh.todotobe.Data;

public class Items {
    private String mUsername;
    private String mName;
    private int mState;
    private String mCategory;

//
//    public Items(String username, String name, int state, String category) {
//        mUsername = username;
//        this.mName = name;
//        this.mState = state;
//        this.mCategory = category;
//    }

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

    public void setmName(String name) {
        this.mName = name;
    }

    public void setmCategory(String Category) {
        this.mCategory = Category;
    }

    public void setmUsername(String username) {
        this.mUsername = username;
    }

    public void setmState(int state) {
        this.mState = state;
    }
}
