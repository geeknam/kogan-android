package com.kogan.android.core;

import java.io.Serializable;

public class Department implements Serializable{
    
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
