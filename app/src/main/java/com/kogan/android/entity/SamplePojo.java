package com.kogan.android.entity;

import java.io.Serializable;


public class SamplePojo implements Serializable {
    private final String stringOne;
    private final int intOne;

    public SamplePojo(final String stringOne, final int intOne) {
        this.stringOne = stringOne;
        this.intOne = intOne;
    }

    public String getStringOne() {
        return stringOne;
    }

    public int getIntOne() {
        return intOne;
    }
}
