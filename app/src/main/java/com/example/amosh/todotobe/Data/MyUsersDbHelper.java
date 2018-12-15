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
        db.execSQL(ItemsContract.ItemsEntry.CREATE_TABLE_ITEMS);

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
        long id = db.insert(UsersContract.UsersEntry.TABLE_USERS, null, values);
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


    public void updateImage(String name, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsersContract.UsersEntry.COLUMN_IMAGE, image);
        db.update(UsersContract.UsersEntry.TABLE_USERS,
                values,
                UsersContract.UsersEntry.COLUMN_NAME + " =?", new String[]{name});
    }

    public void updateUserDetails(String name, String mail, String pass, String image, String bDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsersContract.UsersEntry.COLUMN_EMAIL, mail);
        values.put(UsersContract.UsersEntry.COLUMN_PASSWORD, pass);
        values.put(UsersContract.UsersEntry.COLUMN_BIRTHDAY, bDay);
        values.put(UsersContract.UsersEntry.COLUMN_IMAGE, image);
        db.update(UsersContract.UsersEntry.TABLE_USERS,
                values,
                UsersContract.UsersEntry.COLUMN_NAME + " =?", new String[]{name});
    }

    // Event Table Functions ..


    public void insertEvent(Events event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EventsContract.EventsEntry.COLUMN_USER_NAME, event.getUserName());
        values.put(EventsContract.EventsEntry.COLUMN_TITLE, event.getTitle());
        values.put(EventsContract.EventsEntry.COLUMN_DESCRIPTION, event.getDescription());

        values.put(EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY, event.getDateFromDay());
        values.put(EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH, event.getDateFromMonth());
        values.put(EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR, event.getDateFromYear());
        values.put(EventsContract.EventsEntry.COLUMN_DATE_TO_DAY, event.getDateToDay());
        values.put(EventsContract.EventsEntry.COLUMN_DATE_TO_MONTH, event.getDateToMonth());
        values.put(EventsContract.EventsEntry.COLUMN_DATE_TO_YEAR, event.getDateToYear());

        values.put(EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR, event.getTimeFromHour());
        values.put(EventsContract.EventsEntry.COLUMN_TIME_FROM_MINUTE, event.getTimeFromMinutes());
        values.put(EventsContract.EventsEntry.COLUMN_TIME_TO_HOUR, event.getTimeToHour());
        values.put(EventsContract.EventsEntry.COLUMN_TIME_TO_MINUTE, event.getTimeToMinutes());

        values.put(EventsContract.EventsEntry.COLUMN_LOCATION, event.getLocation());
        values.put(EventsContract.EventsEntry.COLUMN_NOTIFICATION, event.getNotification());
        values.put(EventsContract.EventsEntry.COLUMN_REPEAT, event.getRepeat());
        values.put(EventsContract.EventsEntry.COLUMN_PEOPLE, event.getPeople());
        values.put(EventsContract.EventsEntry.COLUMN_IMAGE, event.getImage());


        long id = db.insert(EventsContract.EventsEntry.TABLE_EVENTS, null, values);
    }

    public Cursor readEvent(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                EventsContract.EventsEntry._ID,
                EventsContract.EventsEntry.COLUMN_USER_NAME,
                EventsContract.EventsEntry.COLUMN_TITLE,
                EventsContract.EventsEntry.COLUMN_DESCRIPTION,

                EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY,
                EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH,
                EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR,
                EventsContract.EventsEntry.COLUMN_DATE_TO_DAY,
                EventsContract.EventsEntry.COLUMN_DATE_TO_MONTH,
                EventsContract.EventsEntry.COLUMN_DATE_TO_YEAR,

                EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR,
                EventsContract.EventsEntry.COLUMN_TIME_FROM_MINUTE,
                EventsContract.EventsEntry.COLUMN_TIME_TO_HOUR,
                EventsContract.EventsEntry.COLUMN_TIME_TO_MINUTE,

                EventsContract.EventsEntry.COLUMN_LOCATION,
                EventsContract.EventsEntry.COLUMN_NOTIFICATION,
                EventsContract.EventsEntry.COLUMN_REPEAT,
                EventsContract.EventsEntry.COLUMN_PEOPLE,
                EventsContract.EventsEntry.COLUMN_IMAGE
        };
        String selection = EventsContract.EventsEntry.COLUMN_USER_NAME + "=?";
        String[] selectionArgs = new String[]{name};

        String order = EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR + " ASC , "
                + EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH + " ASC , "
                + EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY + " ASC , "
                + EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR + " ASC,"
                + EventsContract.EventsEntry.COLUMN_TIME_FROM_MINUTE + " ASC";

        Cursor cursor = db.query(
                EventsContract.EventsEntry.TABLE_EVENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                order
        );
        return cursor;
    }

    public Cursor readEvent(String name, String dayFrom) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                EventsContract.EventsEntry._ID,
                EventsContract.EventsEntry.COLUMN_USER_NAME,
                EventsContract.EventsEntry.COLUMN_TITLE,
                EventsContract.EventsEntry.COLUMN_DESCRIPTION,

                EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY,
                EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH,
                EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR,
                EventsContract.EventsEntry.COLUMN_DATE_TO_DAY,
                EventsContract.EventsEntry.COLUMN_DATE_TO_MONTH,
                EventsContract.EventsEntry.COLUMN_DATE_TO_YEAR,

                EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR,
                EventsContract.EventsEntry.COLUMN_TIME_FROM_MINUTE,
                EventsContract.EventsEntry.COLUMN_TIME_TO_HOUR,
                EventsContract.EventsEntry.COLUMN_TIME_TO_MINUTE,

                EventsContract.EventsEntry.COLUMN_LOCATION,
                EventsContract.EventsEntry.COLUMN_NOTIFICATION,
                EventsContract.EventsEntry.COLUMN_REPEAT,
                EventsContract.EventsEntry.COLUMN_PEOPLE,
                EventsContract.EventsEntry.COLUMN_IMAGE
        };
        String selection = EventsContract.EventsEntry.COLUMN_USER_NAME + "=?"
                + " AND " + EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY + "=?";
        String[] selectionArgs = new String[]{name, dayFrom};

        String order = EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR + " ASC , "
                + EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH + " ASC , "
                + EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY + " ASC , "
                + EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR + " ASC,"
                + EventsContract.EventsEntry.COLUMN_TIME_FROM_MINUTE + " ASC";

        Cursor cursor = db.query(
                EventsContract.EventsEntry.TABLE_EVENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                order
        );
        return cursor;
    }

    public Cursor readEvent(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                EventsContract.EventsEntry.COLUMN_USER_NAME,
                EventsContract.EventsEntry.COLUMN_TITLE,
                EventsContract.EventsEntry.COLUMN_DESCRIPTION,

                EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY,
                EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH,
                EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR,
                EventsContract.EventsEntry.COLUMN_DATE_TO_DAY,
                EventsContract.EventsEntry.COLUMN_DATE_TO_MONTH,
                EventsContract.EventsEntry.COLUMN_DATE_TO_YEAR,

                EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR,
                EventsContract.EventsEntry.COLUMN_TIME_FROM_MINUTE,
                EventsContract.EventsEntry.COLUMN_TIME_TO_HOUR,
                EventsContract.EventsEntry.COLUMN_TIME_TO_MINUTE,

                EventsContract.EventsEntry.COLUMN_LOCATION,
                EventsContract.EventsEntry.COLUMN_NOTIFICATION,
                EventsContract.EventsEntry.COLUMN_REPEAT,
                EventsContract.EventsEntry.COLUMN_PEOPLE,
                EventsContract.EventsEntry.COLUMN_IMAGE
        };
        String selection = EventsContract.EventsEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        String order = EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR + " ASC , "
                + EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH + " ASC , "
                + EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY + " ASC , "
                + EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR + " ASC,"
                + EventsContract.EventsEntry.COLUMN_TIME_FROM_MINUTE + " ASC";

        Cursor cursor = db.query(
                EventsContract.EventsEntry.TABLE_EVENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                order
        );
        return cursor;
    }

    public Cursor readEvent(String name, String dayFrom, String monthFrom, String yearFrom) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                EventsContract.EventsEntry._ID,
                EventsContract.EventsEntry.COLUMN_USER_NAME,
                EventsContract.EventsEntry.COLUMN_TITLE,
                EventsContract.EventsEntry.COLUMN_DESCRIPTION,

                EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY,
                EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH,
                EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR,
                EventsContract.EventsEntry.COLUMN_DATE_TO_DAY,
                EventsContract.EventsEntry.COLUMN_DATE_TO_MONTH,
                EventsContract.EventsEntry.COLUMN_DATE_TO_YEAR,

                EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR,
                EventsContract.EventsEntry.COLUMN_TIME_FROM_MINUTE,
                EventsContract.EventsEntry.COLUMN_TIME_TO_HOUR,
                EventsContract.EventsEntry.COLUMN_TIME_TO_MINUTE,

                EventsContract.EventsEntry.COLUMN_LOCATION,
                EventsContract.EventsEntry.COLUMN_NOTIFICATION,
                EventsContract.EventsEntry.COLUMN_REPEAT,
                EventsContract.EventsEntry.COLUMN_PEOPLE,
                EventsContract.EventsEntry.COLUMN_IMAGE
        };
        String selection = EventsContract.EventsEntry.COLUMN_USER_NAME + "=?"
                + " AND " + EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY + "=?"
                + " AND " + EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH + "=?"
                + " AND " + EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR + "=?";
        String[] selectionArgs = new String[]{name, dayFrom, monthFrom, yearFrom};

        String order = EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR + " ASC , "
                + EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH + " ASC , "
                + EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY + " ASC , "
                + EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR + " ASC,"
                + EventsContract.EventsEntry.COLUMN_TIME_FROM_MINUTE + " ASC";

        Cursor cursor = db.query(
                EventsContract.EventsEntry.TABLE_EVENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                order
        );
        return cursor;
    }

    public Cursor readEventSection(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                EventsContract.EventsEntry._ID,
                EventsContract.EventsEntry.COLUMN_USER_NAME,
                EventsContract.EventsEntry.COLUMN_TITLE,
                EventsContract.EventsEntry.COLUMN_DESCRIPTION,

                EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY,
                EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH,
                EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR,
                EventsContract.EventsEntry.COLUMN_DATE_TO_DAY,
                EventsContract.EventsEntry.COLUMN_DATE_TO_MONTH,
                EventsContract.EventsEntry.COLUMN_DATE_TO_YEAR,

                EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR,
                EventsContract.EventsEntry.COLUMN_TIME_FROM_MINUTE,
                EventsContract.EventsEntry.COLUMN_TIME_TO_HOUR,
                EventsContract.EventsEntry.COLUMN_TIME_TO_MINUTE,

                EventsContract.EventsEntry.COLUMN_LOCATION,
                EventsContract.EventsEntry.COLUMN_NOTIFICATION,
                EventsContract.EventsEntry.COLUMN_REPEAT,
                EventsContract.EventsEntry.COLUMN_PEOPLE,
                EventsContract.EventsEntry.COLUMN_IMAGE
        };
        String selection = EventsContract.EventsEntry.COLUMN_USER_NAME + "=?";
        String[] selectionArgs = new String[]{name};

        String order = EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR + " ASC , "
                + EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH + " ASC , "
                + EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY + " ASC , "
                + EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR + " ASC,"
                + EventsContract.EventsEntry.COLUMN_TIME_FROM_MINUTE + " ASC";

        Cursor cursor = db.query(true,
                EventsContract.EventsEntry.TABLE_EVENTS,
                projection,
                selection,
                selectionArgs,
                EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY,
                null,
                null,
                null);
        return cursor;
    }

    // Items Functions

    public void insertItem(Items item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemsContract.ItemsEntry.COLUMN_USERNAME, item.getUsername());
        values.put(ItemsContract.ItemsEntry.COLUMN_CATEGORY, item.getCategory());
        values.put(ItemsContract.ItemsEntry.COLUMN_STATE, item.getState());
        values.put(ItemsContract.ItemsEntry.COLUMN_ITEM, item.getName());
        long id = db.insert(ItemsContract.ItemsEntry.TABLE_ITEMS, null, values);
    }

    public Cursor readItems(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                ItemsContract.ItemsEntry._ID,
                ItemsContract.ItemsEntry.COLUMN_USERNAME,
                ItemsContract.ItemsEntry.COLUMN_ITEM,
                ItemsContract.ItemsEntry.COLUMN_CATEGORY,
                ItemsContract.ItemsEntry.COLUMN_STATE
        };
        String selection = ItemsContract.ItemsEntry.COLUMN_USERNAME + "=?";
        String[] selectionArgs = new String[]{username};

        Cursor cursor = db.query(
                ItemsContract.ItemsEntry.TABLE_ITEMS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }

    public Cursor readItems(String username, String category) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                ItemsContract.ItemsEntry._ID,
                ItemsContract.ItemsEntry.COLUMN_USERNAME,
                ItemsContract.ItemsEntry.COLUMN_ITEM,
                ItemsContract.ItemsEntry.COLUMN_CATEGORY,
                ItemsContract.ItemsEntry.COLUMN_STATE
        };
        String selection = ItemsContract.ItemsEntry.COLUMN_USERNAME + "=?"
                + " AND " + ItemsContract.ItemsEntry.COLUMN_CATEGORY + "=?";

        String[] selectionArgs = new String[]{username, category};

        Cursor cursor = db.query(
                ItemsContract.ItemsEntry.TABLE_ITEMS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }

    public Cursor checkItems() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {ItemsContract.ItemsEntry.COLUMN_ITEM,
                ItemsContract.ItemsEntry.COLUMN_CATEGORY};
        Cursor cursor = db.query(ItemsContract.ItemsEntry.TABLE_ITEMS,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    public void updateItemState(String username, String itemName, int state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemsContract.ItemsEntry.COLUMN_STATE, state);
        db.update(ItemsContract.ItemsEntry.TABLE_ITEMS,
                values,
                ItemsContract.ItemsEntry.COLUMN_USERNAME + " =?"
                        + " AND " + ItemsContract.ItemsEntry.COLUMN_ITEM + "=?", new String[]{username, itemName});
    }

}
