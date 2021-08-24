package com.example.booksstore.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booksstore.MainActivity;
import com.example.booksstore.R;
import com.example.booksstore.common.Constants;
import com.example.booksstore.db.FavoriteDBHelper;
import com.example.booksstore.interfaces.BookClickInterface;
import com.example.booksstore.model.Book;
import com.example.booksstore.model.BookVolumeInfo;

import java.util.ArrayList;

public class FavoriteBookAdapter extends RecyclerView.Adapter<FavoriteBookAdapter.FavoriteBookViewHolder> {
private ArrayList<BookVolumeInfo> bookVolumeInfos;
private Context context;
private LayoutInflater layoutInflater;
private BookClickInterface bookClickInterface;
    public FavoriteBookAdapter(Context context, ArrayList<BookVolumeInfo> books, BookClickInterface bookClickInterface) {
        this.bookVolumeInfos = books;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.bookClickInterface = bookClickInterface;
    }
    @NonNull
    @Override
    public FavoriteBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  layoutInflater.inflate(R.layout.favorite_book_details,parent,false);
        return new FavoriteBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteBookViewHolder holder, int position) {
        BookVolumeInfo bookVolumeInfo = bookVolumeInfos.get(position);
        String imgUrl = bookVolumeInfo.getImageLinks().getThumbnail();
        Glide.with(context).load(imgUrl).fitCenter().into(holder.imageViewBookImage);
        holder.imageViewSaveBook.setBackgroundResource(R.drawable.ic_baseline_bookmark_24);
        holder.textViewBookAuthors.setText(String.valueOf(bookVolumeInfo.getAuthors()));
        holder.textViewBookAuthors.setVisibility(bookVolumeInfo.getAuthors() != null ? View.VISIBLE : View.GONE);
        holder.textViewBookRating.setText(String.valueOf(bookVolumeInfo.getAverageRating()));
        holder.textViewBookDescription.setText(bookVolumeInfo.getDescription());
        holder.textViewBookYear.setText(bookVolumeInfo.getPublishedDate());


    }

    @Override
    public int getItemCount() {
       int size = 0;
       if (bookVolumeInfos != null && !bookVolumeInfos.isEmpty()){
           size = bookVolumeInfos.size();
       }
       return size;
    }

    public class FavoriteBookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewBookImage, imageViewSaveBook, imageViewClose;
        TextView textViewBookTitle, textViewBookAuthors, textViewBookYear, textViewBookRating, textViewBookDescription;

        public FavoriteBookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBookImage = itemView.findViewById(R.id.bookImage);
            textViewBookTitle = itemView.findViewById(R.id.bookTitle);
            textViewBookAuthors = itemView.findViewById(R.id.textAuthors);
            textViewBookYear = itemView.findViewById(R.id.publishDate);
            textViewBookRating = itemView.findViewById(R.id.rating);
            textViewBookDescription = itemView.findViewById(R.id.bookDescription);
            imageViewSaveBook = itemView.findViewById(R.id.saveButton);
            imageViewClose = itemView.findViewById(R.id.closeButon);
            imageViewClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookClickInterface.onCloseClick();
                }
            });
        }
    }
}
