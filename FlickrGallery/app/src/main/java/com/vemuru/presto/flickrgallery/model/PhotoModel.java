package com.vemuru.presto.flickrgallery.model;

/**
 * Created by Manoj Vemuru on 2018-09-18.
 */
public class PhotoModel {

    private static final String IMAGE_URL = "https://farm%s.staticflickr.com/%s/%s_%s.jpg";

    public long id;
    public String secret;
    public String server;
    public int farm;
    public String title;

    public String getImageUrl() {
        return String.format(IMAGE_URL, farm, server, id, secret);
    }
}
