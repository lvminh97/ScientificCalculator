package com.example.kumail.scientificcalculator.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kumail.scientificcalculator.model.History;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "scientificCalculator";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "history";

    private static final String KEY_ID = "id";
    private static final String KEY_EXPRESSION = "expression";
    private static final String KEY_RESULT = "result";

    public  DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_students_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_EXPRESSION, KEY_RESULT);
        db.execSQL(create_students_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_students_table);

        onCreate(db);
    }

    public void addHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EXPRESSION, history.getExpression());
        values.put(KEY_RESULT, history.getResult());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<History> getAllHistory() {
        ArrayList<History> historyList = new ArrayList<History>();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            History history = new History(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            historyList.add(history);
            cursor.moveToNext();
        }
        return historyList;
    }

    public void deleteAllHistory() {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE 1";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
}
