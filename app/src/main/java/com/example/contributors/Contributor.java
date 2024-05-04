package com.example.contributors;

import android.graphics.Bitmap;

public class Contributor {
    private String login;
    private String contributions;
    private int id;
    private String avatar_url;
    private Bitmap bitmap;

    // И другие поля
    //String html_url;
    public String getName() {
        return login;
    }

    public String getAvatarURL() {
        return avatar_url;
    }

    public String getContributions() { return contributions; }

    @Override
    public String toString() {
        return login;
    }

}
