package com.example.booksstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

public class BookVolumeInfo implements Parcelable,Comparable<BookVolumeInfo> {

    String title, publisher, publishedDate, description, printType, language, previewLink, infoLink, id;
    double averageRating;
    int ratingsCount;
    ArrayList<String> authors,categories;
    BookImageLinks imageLinks;
    private String favStatus;
    public BookVolumeInfo() {
    }

    public BookVolumeInfo(String title, ArrayList<String> authors, double averageRating, String bookDesc, String publishedDate, String thumbnail, String favStatus, String id) {
        this.title = title;
        this.authors = authors;
        this.averageRating = averageRating;
        this.description = bookDesc;
        this.publishedDate = publishedDate;
        imageLinks = new BookImageLinks();
        this.imageLinks.thumbnail = thumbnail;
        this.favStatus = favStatus;
        this.id = id;
    }

    protected BookVolumeInfo(Parcel in) {
        title = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();
        printType = in.readString();
        language = in.readString();
        previewLink = in.readString();
        infoLink = in.readString();
        averageRating = in.readDouble();
        ratingsCount = in.readInt();
        authors = in.createStringArrayList();
        categories = in.createStringArrayList();
        imageLinks = in.readParcelable(BookImageLinks.class.getClassLoader());
        favStatus = in.readString();
    }

    public static final Creator<BookVolumeInfo> CREATOR = new Creator<BookVolumeInfo>() {
        @Override
        public BookVolumeInfo createFromParcel(Parcel in) {
            return new BookVolumeInfo(in);
        }

        @Override
        public BookVolumeInfo[] newArray(int size) {
            return new BookVolumeInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public BookImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(BookImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        dest.writeString(description);
        dest.writeString(printType);
        dest.writeString(language);
        dest.writeString(previewLink);
        dest.writeString(infoLink);
        dest.writeDouble(averageRating);
        dest.writeInt(ratingsCount);
        dest.writeStringList(authors);
        dest.writeStringList(categories);
        dest.writeParcelable(imageLinks, flags);
        dest.writeString(favStatus);

    }


    @Override
    public int compareTo(BookVolumeInfo o) {
        return getTitle().compareTo(o.getTitle());
    }
}
