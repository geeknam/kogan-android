package com.kogan.android.core;


public class Meta {

    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total_count;

    public int getLimit(){
        return limit;
    }

    public int getTotalCount(){
        return total_count;
    }

    public boolean hasMore(){
        if(limit + offset > total_count){
            return false;
        }
        return true;
    }
    
}