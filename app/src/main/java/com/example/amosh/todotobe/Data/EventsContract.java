package com.example.amosh.todotobe.Data;

import android.provider.BaseColumns;

public class EventsContract {

    public EventsContract() {
    }

    public static final class EventsEntry implements BaseColumns {

        public static final String TABLE_EVENTS = "EVENTS";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DATE_FROM_DAY = "dateFromDay";
        public static final String COLUMN_DATE_FROM_MONTH = "dateFromMonth";
        public static final String COLUMN_DATE_FROM_YEAR = "dateFromYear";
        public static final String COLUMN_DATE_TO_DAY = "dateToDay";
        public static final String COLUMN_DATE_TO_MONTH = "dateToMonth";
        public static final String COLUMN_DATE_TO_YEAR = "dateToYear";
        public static final String COLUMN_TIME_FROM_HOUR = "timeFromHour";
        public static final String COLUMN_TIME_FROM_MINUTE = "timeFromMinute";
        public static final String COLUMN_TIME_TO_HOUR = "timeToHour";
        public static final String COLUMN_TIME_TO_MINUTE = "timeToMinute";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_NOTIFICATION = "notification";
        public static final String COLUMN_PEOPLE = "people";
        public static final String COLUMN_REPEAT = "repeat";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_STATE = "state";

        public static final int STATE_COMPLETED = 1;
        public static final int STATE_SNOOZED = 2;
        public static final int STATE_OVERDUE = 3;


        public static final String CREATE_TABLE_EVENTS = "CREATE TABLE "
                + EventsEntry.TABLE_EVENTS + "("
                + EventsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EventsEntry.COLUMN_TITLE + " TEXT NOT NULL,"
                + EventsEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL,"
                + EventsEntry.COLUMN_DATE_FROM_DAY + " INTEGER NOT NULL,"
                + EventsEntry.COLUMN_DATE_FROM_MONTH + " INTEGER NOT NULL,"
                + EventsEntry.COLUMN_DATE_FROM_YEAR + " INTEGER NOT NULL,"
                + EventsEntry.COLUMN_DATE_TO_DAY + " INTEGER,"
                + EventsEntry.COLUMN_DATE_TO_MONTH + " INTEGER,"
                + EventsEntry.COLUMN_DATE_TO_YEAR + " INTEGER,"
                + EventsEntry.COLUMN_TIME_FROM_HOUR + " INTEGER NOT NULL,"
                + EventsEntry.COLUMN_TIME_FROM_MINUTE + " INTEGER NOT NULL,"
                + EventsEntry.COLUMN_TIME_TO_HOUR + " INTEGER,"
                + EventsEntry.COLUMN_TIME_TO_MINUTE + " INTEGER,"
                + EventsEntry.COLUMN_LOCATION + " TEXT NOT NULL,"
                + EventsEntry.COLUMN_NOTIFICATION + " TEXT NOT NULL,"
                + EventsEntry.COLUMN_PEOPLE + " TEXT,"
                + EventsEntry.COLUMN_REPEAT + " TEXT NOT NULL,"
                + EventsEntry.COLUMN_STATE + " INTEGER ,"
                + EventsEntry.COLUMN_IMAGE + " TEXT);";

    }
}
