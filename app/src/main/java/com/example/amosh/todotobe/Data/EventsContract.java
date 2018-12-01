package com.example.amosh.todotobe.Data;

import android.provider.BaseColumns;

import com.example.amosh.todotobe.SignInActivity;

public class EventsContract {

    SignInActivity signInActivity;

    public EventsContract() {
    }

    public void name() {
        String userName = signInActivity.uName();
        EventsEntry.TABLE_NAME = userName + "Events";
        EventsEntry.name = userName;
    }

    public static final class EventsEntry implements BaseColumns {

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_DATE_FROM = "datefrom";
        public static final String COLUMN_DATE_TO = "dateto";
        public static final String COLUMN_TIME_FROM = "timefrom";
        public static final String COLUMN_TIME_TO = "timeto";
        public static final String COLUMN_NOTIFICATION = "notification";
        public static final String COLUMN_PEOPLE = "people";
        public static final String COLUMN_REPEAT = "repeat";
        public static final String COLUMN_IMAGE = "image";
        public static String TABLE_NAME;
        public static String name;
        public static final String CREATE_TABLE_EVENTS = "CREATE TABLE " +
                EventsContract.EventsEntry.TABLE_NAME + "(" +
                EventsContract.EventsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EventsEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                EventsEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                EventsEntry.COLUMN_LOCATION + " TEXT NOT NULL," +
                EventsEntry.COLUMN_DATE_FROM + " TEXT NOT NULL," +
                EventsEntry.COLUMN_DATE_TO + " TEXT," +
                EventsEntry.COLUMN_TIME_FROM + " TEXT NOT NULL," +
                EventsEntry.COLUMN_TIME_TO + " TEXT," +
                EventsEntry.COLUMN_NOTIFICATION + " TEXT NOT NULL," +
                EventsEntry.COLUMN_PEOPLE + " TEXT," +
                EventsEntry.COLUMN_REPEAT + " TEXT NOT NULL," +
                EventsEntry.COLUMN_IMAGE +
                EventsContract.EventsEntry.COLUMN_NAME + " TEXT DEFAULT " + name +
                " , FOREIGN KEY (" + EventsEntry.COLUMN_NAME + ") REFERENCES" +
                UsersContract.UsersEntry.TABLE_NAME + "(" + UsersContract.UsersEntry.COLUMN_NAME + ");";
    }
}
