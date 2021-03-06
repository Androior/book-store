package com.example.booksstore;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksstore.adapter.SeeAllAdapter;
import com.example.booksstore.common.Constants;
import com.example.booksstore.interfaces.BookClickInterface;
import com.example.booksstore.model.Book;
import com.example.booksstore.model.BookResponseModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity implements BookClickInterface {

    private static final String TAG = SearchActivity.class.getSimpleName();
    RecyclerView recyclerView;
    EditText editText;
    Gson gson;
    ArrayList<Book> bookList = new ArrayList<>();
    SeeAllAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
        editText = (EditText) findViewById(R.id.search_edit_text);
        gson = new Gson();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Log.d("SEARCH", "START: " + start + " BEFORE: " + before + " COUNT" + count);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 1){
                    loadBooks(editable.toString());
                } else{
                    bookList = new ArrayList<>();
                    adapter = new SeeAllAdapter(bookList, SearchActivity.this, SearchActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                Log.d("SEARCHWORD", editable.toString());
            }
        });

    }

    private void loadBooks(String query){
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://www.googleapis.com/books/v1/volumes").newBuilder();

        urlBuilder.addQueryParameter("q", query);

        String url = urlBuilder.build().toString();

        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonString = response.body().string();
                    BookResponseModel bookResponseModel = gson.fromJson(jsonString, BookResponseModel.class);
                    Log.d(TAG, bookResponseModel.getKind());
                    bookResponseModel.setSectionType(Constants.SectionType.HEALTHY_LIFESTYLE);
                    bookResponseModel.setSectionType(Constants.SectionType.WELLNESS);
                    bookResponseModel.setSectionType(Constants.SectionType.HEALTHY_MEALS);
                    bookList = bookResponseModel.getItems();
                    if (bookList == null){
                        bookList = new ArrayList<>();
                    }

                    SearchActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new SeeAllAdapter(bookList, SearchActivity.this, SearchActivity.this);

                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            //progressBar.setVisibility(View.GONE);
                        }
                    });

                }
            }
        });
    }


    @Override
    public void onBookClick(Book book) {
        Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.BOOK, book);
        startActivity(intent);
    }

    @Override
    public void onSeeAllClick(int sectionType, int bookType) {

    }

    @Override
    public void onCloseClick() {

    }
}
