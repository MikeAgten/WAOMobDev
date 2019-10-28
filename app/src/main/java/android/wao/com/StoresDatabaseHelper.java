package android.wao.com;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.wao.com.data.StoresContract;

import androidx.annotation.Nullable;

public class StoresDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Stores.db";
    public static final String TABLE_NAME = "store_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "ADDRESS";
    public static final String COL_4 = "PHONE_NUMBER";
    public static final String COL_5 = "WEBSITE";
    public static final String COL_6 = "OPEN";


    public StoresDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_STORES_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                StoresContract.StoreEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                StoresContract.StoreEntry.COLUMN_NAME_NAME + "TEXT NOT NULL," +
                StoresContract.StoreEntry.COLUMN_NAME_ADDRESS + "TEXT NOT NULL," +
                StoresContract.StoreEntry.COLUMN_NAME_PHONENUMBER + "TEXT NOT NULL," +
                StoresContract.StoreEntry.COLUMN_NAME_WEBSITE + "TEXT NOT NULL," +
                StoresContract.StoreEntry.COLUMN_NAME_OPEN + "BOOLEAN NOT NULL" +");";

                sqLiteDatabase.execSQL(SQL_CREATE_STORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
