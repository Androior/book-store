package com.example.booksstore;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.booksstore.common.Constants;
import com.example.booksstore.db.FavoriteDBHelper;
import com.example.booksstore.model.Book;
import com.example.booksstore.model.BookVolumeInfo;

public class BookDetailActivity extends AppCompatActivity {

    public static final String BOOK = "BOOK";//kluc za intentot
    private boolean isFav;
    ImageView saveButton, closeButton, bookImage;
    TextView bookTitle, bookAuthors, bookYear, bookRating, bookDescription;
    String imgUrl, favStatus;
    Book book;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        saveButton = findViewById(R.id.saveButton);
        closeButton = (ImageView) findViewById(R.id.closeButon);
        bookImage = (ImageView) findViewById(R.id.bookImage);
        bookTitle = (TextView) findViewById(R.id.bookTitle);
        bookAuthors = (TextView) findViewById(R.id.textAuthors);
        bookYear = (TextView) findViewById(R.id.publishDate);
        bookRating = (TextView) findViewById(R.id.rating);
        bookDescription = (TextView) findViewById(R.id.bookDescription);


        book = (Book) getIntent().getParcelableExtra(BOOK);
        bookTitle.setText(book.getVolumeInfo().getTitle());
        bookAuthors.setText(String.valueOf(book.getVolumeInfo().getAuthors()));
        bookAuthors.setVisibility(book.getVolumeInfo().getAuthors() != null ? View.VISIBLE : View.GONE);
        bookRating.setText(String.valueOf(book.getVolumeInfo().getAverageRating()));
        bookDescription.setText(book.getVolumeInfo().getDescription());
        bookYear.setText(book.getVolumeInfo().getPublishedDate());
        imgUrl = book.getVolumeInfo().getImageLinks().getThumbnail();
        Glide.with(this).load(imgUrl).into(bookImage);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertDataInDB(v);
//            }
//        });
        FavoriteDBHelper favoriteDBHelper = new FavoriteDBHelper(this);
        checkIfBookIsFavorite(favoriteDBHelper);
    }

    public void moreDetails(View view) {
        if (book != null) {
            String url = book.getVolumeInfo().getPreviewLink();
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.URL_EXTRA, url);
            startActivity(intent);
        }
    }

    public void insertDataInDB(View view) {
        FavoriteDBHelper favoriteDBHelper = new FavoriteDBHelper(getApplicationContext());
        BookVolumeInfo bookVolumeInfo = book.getVolumeInfo();
        bookVolumeInfo.setFavStatus("1");
        favoriteDBHelper.insertIntoTheDatabase(bookVolumeInfo.getTitle(), bookVolumeInfo.getAuthors(),
                bookVolumeInfo.getImageLinks().getThumbnail(), bookVolumeInfo.getPublishedDate(),
                bookVolumeInfo.getDescription(), (int) bookVolumeInfo.getAverageRating(), bookVolumeInfo.getFavStatus());
        saveButton.setImageResource(R.drawable.ic_baseline_bookmark_24);
    }

    public void closeBookDetails(View view) {
        finish();
    }

    private void checkIfBookIsFavorite(FavoriteDBHelper favoriteDBHelper) {
        try {
            Cursor cursor = favoriteDBHelper.readAllData();
            while (cursor.moveToNext()) {
                String bookName = cursor.getString(cursor.getColumnIndex(Constants.FAVORITE_TABLE.COLUMN_BOOK_TITLE));
                String itemFavStatus = cursor.getString(cursor.getColumnIndex(Constants.FAVORITE_TABLE.COLUMN_FAV_STATUS));
                if (itemFavStatus.equals("1") && bookTitle.getText().toString().equals(bookName)) {
                    isFav = true;
                    saveButton.setImageResource(R.drawable.ic_baseline_bookmark_24);
                } else if (itemFavStatus.equals("0") && !bookTitle.getText().toString().equals(bookName)) {
                    isFav = false;
                    saveButton.setImageResource(R.drawable.ic_bookmark_border);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
