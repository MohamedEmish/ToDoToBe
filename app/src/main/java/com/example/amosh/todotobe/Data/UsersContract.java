package com.example.amosh.todotobe.Data;

import android.provider.BaseColumns;

public class UsersContract {

    public UsersContract() {
    }

    public static final class UsersEntry implements BaseColumns {

        public static final String TABLE_USERS = "USERS";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PASSWORD = "Password";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_BIRTHDAY = "birthday";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_EVENT_ID = "event";


        public static final String CREATE_TABLE_USERS = "CREATE TABLE "
                + UsersContract.UsersEntry.TABLE_USERS + "("
                + UsersContract.UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UsersContract.UsersEntry.COLUMN_NAME + " TEXT NOT NULL,"
                + UsersEntry.COLUMN_PASSWORD + " TEXT NOT NULL,"
                + UsersEntry.COLUMN_EMAIL + " TEXT NOT NULL,"
                + UsersEntry.COLUMN_BIRTHDAY + " TEXT NOT NULL,"
                + UsersEntry.COLUMN_IMAGE + " TEXT NOT NULL,"
                + UsersEntry.COLUMN_EVENT_ID + " INTEGER,"
                + "FOREIGN KEY (" + UsersEntry.COLUMN_EVENT_ID + ") REFERENCES "
                + EventsContract.EventsEntry.TABLE_EVENTS + "(" + EventsContract.EventsEntry._ID + "));";
    }
}
