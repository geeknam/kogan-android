package com.kogan.android.core;

import java.io.Serializable;

public class Category implements Serializable{
    
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
