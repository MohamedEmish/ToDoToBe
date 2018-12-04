package com.example.amosh.todotobe.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyUsersDbHelper extends SQLiteOpenHelper {

    public final static String USERS_DB = "users.db";
    public final static int DB_VERSION = 1;

    public MyUsersDbHelper(Context context) {
        super(context, USERS_DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsersContract.UsersEntry.CREATE_TABLE_USERS);
        db.execSQL(EventsContract.EventsEntry.CREATE_TABLE_EVENTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsersContract.UsersEntry.COLUMN_NAME, user.getUserName());
        values.put(UsersContract.UsersEntry.COLUMN_PASSWORD, user.getUserPassword());
        values.put(UsersContract.UsersEntry.COLUMN_EMAIL, user.getUserEmail());
        values.put(UsersContract.UsersEntry.COLUMN_BIRTHDAY, user.getUserBirthday());
        values.put(UsersContract.UsersEntry.COLUMN_IMAGE, user.getUserImage());
        values.put(UsersContract.UsersEntry.COLUMN_EVENT_ID, user.getUserEventID());
        long id = db.insert(UsersContract.UsersEntry.TABLE_USERS, null, values);
    }

    public Cursor readUser() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                UsersContract.UsersEntry._ID,
                UsersContract.UsersEntry.COLUMN_NAME,
                UsersContract.UsersEntry.COLUMN_PASSWORD,
                UsersContract.UsersEntry.COLUMN_EMAIL,
                UsersContract.UsersEntry.COLUMN_BIRTHDAY,
                UsersContract.UsersEntry.COLUMN_IMAGE,
                UsersContract.UsersEntry.COLUMN_EVENT_ID
        };
        Cursor cursor = db.query(
                UsersContract.UsersEntry.TABLE_USERS,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public Cursor readUser(long itemId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                UsersContract.UsersEntry._ID,
                UsersContract.UsersEntry.COLUMN_NAME,
                UsersContract.UsersEntry.COLUMN_PASSWORD,
                UsersContract.UsersEntry.COLUMN_EMAIL,
                UsersContract.UsersEntry.COLUMN_BIRTHDAY,
                UsersContract.UsersEntry.COLUMN_IMAGE,
                UsersContract.UsersEntry.COLUMN_EVENT_ID
        };
        String selection = UsersContract.UsersEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(itemId)};

        Cursor cursor = db.query(
                UsersContract.UsersEntry.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }

    public Cursor readUser(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                UsersContract.UsersEntry._ID,
                UsersContract.UsersEntry.COLUMN_NAME,
                UsersContract.UsersEntry.COLUMN_PASSWORD,
                UsersContract.UsersEntry.COLUMN_EMAIL,
                UsersContract.UsersEntry.COLUMN_BIRTHDAY,
                UsersContract.UsersEntry.COLUMN_IMAGE,
                UsersContract.UsersEntry.COLUMN_EVENT_ID
        };
        String selection = UsersContract.UsersEntry.COLUMN_NAME + "=?";
        String[] selectionArgs = new String[]{name};

        Cursor cursor = db.query(
                UsersContract.UsersEntry.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }

    public Cursor checkNames() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {UsersContract.UsersEntry.COLUMN_NAME,
                UsersContract.UsersEntry.COLUMN_PASSWORD};
        Cursor cursor = db.query(UsersContract.UsersEntry.TABLE_USERS,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }


    public void updatePassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsersContract.UsersEntry.COLUMN_PASSWORD, password);
        db.update(UsersContract.UsersEntry.TABLE_USERS,
                values,
                UsersContract.UsersEntry.COLUMN_EMAIL + " =?", new String[]{email});
    }

}