package com.name.rolo.util;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "NewsDB.db";
    // версия базы данных
    private static final int DATABASE_VERSION = 1;


    public static final String DATABASE_TABLE = "news_table";
    public static final String TITLE_COLUMN = "titles";
    public static final String TEXT_COLUMN = "texts";
    public static final String URL_COLUMN = "urls";
    public static final String DATES_COLUMN = "dates";
    public static final String HREFS_COLUMN = "hrefs";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + TITLE_COLUMN
            + " text not null, " + TEXT_COLUMN + " text not null, " + URL_COLUMN
            + " text not null, " + DATES_COLUMN + " text not null, " + HREFS_COLUMN + " text not null);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
        onCreate(db);

    }
}