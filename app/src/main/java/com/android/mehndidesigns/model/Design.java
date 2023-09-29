package com.android.mehndidesigns.model;

public class Design {

    private final String title;
    private final int image;

    public Design(String title, int image) {
        this.title = title;
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

}
