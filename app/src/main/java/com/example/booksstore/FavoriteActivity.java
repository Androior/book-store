package com.example.booksstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.booksstore.adapter.FavoriteBookAdapter;
import com.example.booksstore.common.Constants;
import com.example.booksstore.db.FavoriteDBHelper;
import com.example.booksstore.fragment.BookFragment;
import com.example.booksstore.interfaces.BookClickInterface;
import com.example.booksstore.model.Book;
import com.example.booksstore.model.BookVolumeInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;

public class FavoriteActivity extends AppCompatActivity implements BookClickInterface {
    ArrayList<BookVolumeInfo> bookVolumeInfos;
    FavoriteDBHelper favoriteDBHelper;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        bookVolumeInfos = new ArrayList<>();
        favoriteDBHelper = new FavoriteDBHelper(this);
        recyclerView = findViewById(R.id.recycler_view_favorite_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        loadData();
    }


    private void loadData() {
        if (bookVolumeInfos != null || !bookVolumeInfos.isEmpty()) {
            bookVolumeInfos.clear();
        }

        BookVolumeInfo bookVolumeInfo = null;
        ArrayList<String> booksAuthorsArr = new ArrayList<>();

        Cursor cursor = favoriteDBHelper.selectAllFavouriteList();
        try {
            while (cursor.moveToNext()) {
                String bookTitle = cursor.getString(cursor.getColumnIndex(Constants.FAVORITE_TABLE.COLUMN_BOOK_TITLE));
                String bookAuthors = cursor.getString(cursor.getColumnIndex(Constants.FAVORITE_TABLE.COLUMN_BOOK_AUTHORS));
                int bookRating = cursor.getInt(cursor.getColumnIndex(Constants.FAVORITE_TABLE.COLUMN_BOOK_RATING));
                String bookDescription = cursor.getString(cursor.getColumnIndex(Constants.FAVORITE_TABLE.COLUMN_BOOK_DESCRIPTION));
                String bookYear = cursor.getString(cursor.getColumnIndex(Constants.FAVORITE_TABLE.COLUMN_BOOK_YEAR));
                String bookImage = cursor.getString(cursor.getColumnIndex(Constants.FAVORITE_TABLE.COLUMN_BOOK_IMAGE));
                String favoriteStatus = cursor.getString(cursor.getColumnIndex(Constants.FAVORITE_TABLE.COLUMN_FAV_STATUS));
                String id = cursor.getString(cursor.getColumnIndex(Constants.FAVORITE_TABLE.COLUMN_ID));

                booksAuthorsArr.add(bookAuthors);
                bookVolumeInfo = new BookVolumeInfo(bookTitle, booksAuthorsArr, bookRating, bookDescription, bookYear, bookImage, favoriteStatus, id);
                bookVolumeInfos.add(bookVolumeInfo);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((cursor != null) && (cursor.isClosed())) {
                cursor.close();
                favoriteDBHelper.close();
            }

            Collections.sort(bookVolumeInfos);
            FavoriteBookAdapter favoriteBookAdapter = new FavoriteBookAdapter(this, bookVolumeInfos,this);
            recyclerView.setAdapter(favoriteBookAdapter);
        }
    }

    @Override
    public void onBookClick(Book book) {

    }

    @Override
    public void onSeeAllClick(int sectionType, int bookType) {

    }

    @Override
    public void onCloseClick() {
        finish();
    }
}