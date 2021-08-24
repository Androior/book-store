package com.example.booksstore.common;

import android.provider.BaseColumns;
import android.widget.TextView;

import com.example.booksstore.model.BookImageLinks;

import java.util.ArrayList;

public class Constants {

    public class BookType{
        public static final int BOOKS = 0;
        public static final int MAGAZINES = 1;
    }

    public class SectionType{
        public static final int HEALTHY_LIFESTYLE = 0;
        public static final int WELLNESS = 1;
        public static final int HEALTHY_MEALS = 2;
    }
    public class FAVORITE_TABLE {
        public static final String TABLE_NAME = "favoriteTable";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_BOOK_TITLE = "title";
        public static final String COLUMN_BOOK_AUTHORS = "authors";
        public static final String COLUMN_BOOK_YEAR = "year";
        public static final String COLUMN_BOOK_RATING = "rating";
        public static final String COLUMN_BOOK_DESCRIPTION = "description";
        public static final String COLUMN_BOOK_IMAGE = "image";
        public static final String COLUMN_FAV_STATUS = "favorite";

    }

}
