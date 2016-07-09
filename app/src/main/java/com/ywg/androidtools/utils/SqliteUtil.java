package com.ywg.androidtools.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SqliteUtil {

    private static volatile SqliteUtil instance;

    private DbHelper                    dbHelper;
    private SQLiteDatabase db;

    private SqliteUtil(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static SqliteUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (SqliteUtil.class) {
                if (instance == null) {
                    instance = new SqliteUtil(context);
                }
            }
        }
        return instance;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}