package com.kogan.android.core;

import java.io.Serializable;

public class Facet implements Serializable{

    private String id;
    private String name;
    private String value;
    private String group;

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }

    public String getGroup(){
        return group;
    }
}