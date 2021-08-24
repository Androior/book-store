package com.example.booksstore.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.booksstore.common.Constants;
import com.example.booksstore.common.Constants.FAVORITE_TABLE;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class FavoriteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE = "favorite.db";
    public static int dbVersion = 1;
    public FavoriteDBHelper(Context context) {
        super(context, DATABASE, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " +
                FAVORITE_TABLE.TABLE_NAME + " (" +
                FAVORITE_TABLE.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FAVORITE_TABLE.COLUMN_BOOK_TITLE + " TEXT NOT NULL, " +
                FAVORITE_TABLE.COLUMN_BOOK_AUTHORS + " TEXT NOT NULL, " +
                FAVORITE_TABLE.COLUMN_BOOK_YEAR + " TEXT NOT NULL, " +
                FAVORITE_TABLE.COLUMN_BOOK_DESCRIPTION + " TEXT NOT NULL, " +
                FAVORITE_TABLE.COLUMN_FAV_STATUS + " TEXT NOT NULL, " +
                FAVORITE_TABLE.COLUMN_BOOK_IMAGE + " TEXT NOT NULL, " +
                FAVORITE_TABLE.COLUMN_BOOK_RATING + " INTEGER NOT NULL " +
                ");";
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_TABLE.TABLE_NAME);
        onCreate(db);
    }

    // insert data into database
    public void insertIntoTheDatabase(String bookTitle, ArrayList<String>bookAuthors, String bookImage, String bookYear, String bookDescription,
                                      int bookRating, String favStatus) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FAVORITE_TABLE.COLUMN_BOOK_TITLE, bookTitle);
        cv.put(FAVORITE_TABLE.COLUMN_BOOK_AUTHORS, String.valueOf(bookAuthors));
        cv.put(FAVORITE_TABLE.COLUMN_BOOK_YEAR, bookYear);
        cv.put(FAVORITE_TABLE.COLUMN_BOOK_RATING, bookRating);
        cv.put(FAVORITE_TABLE.COLUMN_BOOK_IMAGE, bookImage);
        cv.put(FAVORITE_TABLE.COLUMN_BOOK_DESCRIPTION, bookDescription);
        cv.put(FAVORITE_TABLE.COLUMN_FAV_STATUS, favStatus);

        db.insert(FAVORITE_TABLE.TABLE_NAME,null, cv);
        Log.d("FavDB Status", bookTitle + ", favstatus - "+favStatus+" - . " + cv);
        System.out.println("FavDB Status" +  bookTitle + " " +  favStatus + " "  + cv.get(FAVORITE_TABLE.COLUMN_BOOK_TITLE));
    }

    // read all data
    public Cursor readAllDataPerBook(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + FAVORITE_TABLE.TABLE_NAME + " where " + FAVORITE_TABLE.COLUMN_ID+"="+id+"";
        return db.rawQuery(sql,null,null);
    }
    public Cursor readAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + FAVORITE_TABLE.TABLE_NAME+"";
        return db.rawQuery(sql,null,null);
    }
    // remove line from database
    public void removeFavoriteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + FAVORITE_TABLE.TABLE_NAME + " SET  "+ FAVORITE_TABLE.COLUMN_FAV_STATUS+" ='0' WHERE "+FAVORITE_TABLE.COLUMN_BOOK_TITLE+"="+id+"";
        db.execSQL(sql);
        Log.d("remove", String.valueOf(id));
        System.out.println(" REMOVE ID" +  id);
    }
    public Cursor selectAllFavouriteList(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = " select * from " + Constants.FAVORITE_TABLE.TABLE_NAME + " where " +
                FAVORITE_TABLE.COLUMN_FAV_STATUS + " ='1'";
        return sqLiteDatabase.rawQuery(sql, null, null);
    }
    public void deleteFavoriteBookFromDB(String row) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String table = FAVORITE_TABLE.TABLE_NAME;
        String whereClause = "id=?";
        String[] whereArgs = new String[] { String.valueOf(row) };
        sqLiteDatabase.delete(table, whereClause, whereArgs);
        System.out.println("ID " + row);
    }


}
