package com.android.mehndidesigns.model;

public class Mehndi {

    private final String id;
    private final String image;

    public Mehndi(String id, String image) {
        this.id = id;
        this.image = image;
    }


    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

}
