package com.example.amosh.todotobe.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.amosh.todotobe.SignInActivity;

public class MyEventDbHelper extends SQLiteOpenHelper {

    public final static int DB_VERSION = 1;
    public static String EVENTS_DB;
    SignInActivity signInActivity;

    public MyEventDbHelper(Context context) {
        super(context, EVENTS_DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EventsContract.EventsEntry.CREATE_TABLE_EVENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void name() {
        String userName = signInActivity.uName();
        MyEventDbHelper.EVENTS_DB = userName + "Events.db";
    }

    public void insertEvent(Events events) {
        String userName = signInActivity.uName();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EventsContract.EventsEntry.COLUMN_TITLE, events.getEventTitle());
        values.put(EventsContract.EventsEntry.COLUMN_DESCRIPTION, events.getEventDescription());
        values.put(EventsContract.EventsEntry.COLUMN_LOCATION, events.getEventLocation());
        values.put(EventsContract.EventsEntry.COLUMN_DATE_FROM, events.getEventDateFrom());
        values.put(EventsContract.EventsEntry.COLUMN_DATE_TO, events.getEventDateTo());
        values.put(EventsContract.EventsEntry.COLUMN_TIME_FROM, events.getEventTimeFrom());
        values.put(EventsContract.EventsEntry.COLUMN_TIME_TO, events.getEventTimeTo());
        values.put(EventsContract.EventsEntry.COLUMN_NOTIFICATION, events.getEventNotification());
        values.put(EventsContract.EventsEntry.COLUMN_PEOPLE, events.getEventPeople());
        values.put(EventsContract.EventsEntry.COLUMN_REPEAT, events.getEventRepeat());
        values.put(EventsContract.EventsEntry.COLUMN_IMAGE, events.getEventImage());
        values.put(EventsContract.EventsEntry.COLUMN_NAME, userName);

        long id = db.insert(EventsContract.EventsEntry.TABLE_NAME, null, values);
    }

    public Cursor readEvent() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                EventsContract.EventsEntry._ID,
                EventsContract.EventsEntry.COLUMN_TITLE,
                EventsContract.EventsEntry.COLUMN_DESCRIPTION,
                EventsContract.EventsEntry.COLUMN_LOCATION,
                EventsContract.EventsEntry.COLUMN_DATE_FROM,
                EventsContract.EventsEntry.COLUMN_DATE_TO,
                EventsContract.EventsEntry.COLUMN_TIME_FROM,
                EventsContract.EventsEntry.COLUMN_TIME_TO,
                EventsContract.EventsEntry.COLUMN_NOTIFICATION,
                EventsContract.EventsEntry.COLUMN_PEOPLE,
                EventsContract.EventsEntry.COLUMN_REPEAT,
                EventsContract.EventsEntry.COLUMN_IMAGE,
                EventsContract.EventsEntry.COLUMN_NAME
        };
        Cursor cursor = db.query(
                EventsContract.EventsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public Cursor readEvent(long itemId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                EventsContract.EventsEntry._ID,
                EventsContract.EventsEntry.COLUMN_TITLE,
                EventsContract.EventsEntry.COLUMN_DESCRIPTION,
                EventsContract.EventsEntry.COLUMN_LOCATION,
                EventsContract.EventsEntry.COLUMN_DATE_FROM,
                EventsContract.EventsEntry.COLUMN_DATE_TO,
                EventsContract.EventsEntry.COLUMN_TIME_FROM,
                EventsContract.EventsEntry.COLUMN_TIME_TO,
                EventsContract.EventsEntry.COLUMN_NOTIFICATION,
                EventsContract.EventsEntry.COLUMN_PEOPLE,
                EventsContract.EventsEntry.COLUMN_REPEAT,
                EventsContract.EventsEntry.COLUMN_IMAGE,
                EventsContract.EventsEntry.COLUMN_NAME
        };
        String selection = EventsContract.EventsEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(itemId)};

        Cursor cursor = db.query(
                EventsContract.EventsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }
}
