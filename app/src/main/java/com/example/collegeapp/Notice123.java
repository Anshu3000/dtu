package com.example.collegeapp;

public class Notice123 {
    private String  date,time,keyd1,title,imageurl;

    public Notice123() {
    }

    public Notice123(String date, String time, String keyd1, String title, String imageurl) {
        this.date = date;
        this.time = time;
        this.keyd1 = keyd1;
        this.title = title;
        this.imageurl = imageurl;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getKeyd1() {
        return keyd1;
    }

    public String getTitle() {
        return title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setKeyd1(String keyd1) {
        this.keyd1 = keyd1;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
