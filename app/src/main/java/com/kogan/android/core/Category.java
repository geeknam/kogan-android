package com.kogan.android.core;


public class Category {
    
    private String title;
    private String slug;

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
