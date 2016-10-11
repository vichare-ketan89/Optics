package com.keto.optics.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ketanvichare on 10/11/16.
 */
public class OpticsSQLiteWrapper {

    SQLiteDatabase sqLiteDatabase;

    public OpticsSQLiteWrapper(Context context){
        OpticsSQLiteOpenHelper mSQLiteOpenHelper = new OpticsSQLiteOpenHelper(context);
        sqLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
    }

    public Long addRecord(){
        Long longId = sqLiteDatabase.insert(FeedEntry.TABLE_NAME,"", new ContentValues());
        return longId;
    }

    public Cursor getRecordsForModel(String modelName){
        Cursor cursor = sqLiteDatabase.query(false, FeedEntry.TABLE_NAME, new String[]{},"selectionString",
                new String[]{}, "grpupBy","having","orderBy","limit");
        return cursor;
    }

    public Cursor getRecordsForProductName(String productName){
        Cursor cursor = sqLiteDatabase.query(false, FeedEntry.TABLE_NAME, new String[]{},"selectionString",
                new String[]{}, "grpupBy","having","orderBy","limit");
        return cursor;
    }

}
