package com.globant.equattrocchio.domain;

/**
 * Created by facundo.scoccia on 7/26/17.
 */

public class Image {
    private Integer id;
    private String url;
    private String largeUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }
}
