package com.kogan.android.core;


public class Department {
    
    public String title;
    
    public String slug;
    
    public String image;

    public String getTitle(){
        return this.title;
    }

    @Override
    public String toString(){
        return title;
    }
    
}
