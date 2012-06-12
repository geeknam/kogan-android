package com.kogan.android.core;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcel;

public class Category implements Parcelable{
    
    private String title;
    private String slug;

    public static final Parcelable.Creator<Category> CREATOR
        = new Parcelable.Creator<Category>() {
    
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
    }

    public int describeContents() {
        return 0;
    }

    private Category(Parcel in) {
        title = in.readString();
    }

    public String getTitle(){
        return this.title;
    }

    public String getSlug(){
        return this.slug;
    }

    @Override
    public String toString(){
        return title;
    }
    
}
