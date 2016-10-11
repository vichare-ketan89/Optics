package com.keto.optics.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ketanvichare on 10/11/16.
 */
public class OpticsSQLiteOpenHelper extends SQLiteOpenHelper{

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Optics.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //Create Table Query
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "+ FeedEntry.TABLE_NAME + " ("
            + FeedEntry._ID + " INTEGER PRIMARY KEY"+ COMMA_SEP
            + FeedEntry.COLUMN_NAME_MODEL + TEXT_TYPE + COMMA_SEP
            + FeedEntry.COLUMN_NAME_BRAND + TEXT_TYPE + COMMA_SEP
            + FeedEntry.COLUMN_NAME_IMAGE_DATA + TEXT_TYPE + COMMA_SEP
            + FeedEntry.COLUMN_NAME_PRICE + TEXT_TYPE +" )";

    //Delete Table
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public OpticsSQLiteOpenHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
