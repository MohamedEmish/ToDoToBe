package com.example.amosh.todotobe.Data;

import android.provider.BaseColumns;

public class ItemsContract {

    public ItemsContract() {
    }

    public static final class ItemsEntry implements BaseColumns {

        public static final String TABLE_ITEMS = "Items";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_USERNAME = "name";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_STATE = "email";
        public static final String COLUMN_ITEM = "item";


        public static final String CREATE_TABLE_ITEMS = "CREATE TABLE "
                + ItemsContract.ItemsEntry.TABLE_ITEMS + "("
                + ItemsContract.ItemsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ItemsContract.ItemsEntry.COLUMN_USERNAME + " TEXT NOT NULL,"
                + ItemsEntry.COLUMN_ITEM + " TEXT NOT NULL,"
                + ItemsEntry.COLUMN_CATEGORY + " TEXT NOT NULL,"
                + ItemsEntry.COLUMN_STATE + " INTEGER NOT NULL,"
                + "FOREIGN KEY (" + ItemsEntry.COLUMN_USERNAME + ") REFERENCES "
                + EventsContract.EventsEntry.TABLE_EVENTS + "(" + EventsContract.EventsEntry.COLUMN_USER_NAME + "));";
    }
}
