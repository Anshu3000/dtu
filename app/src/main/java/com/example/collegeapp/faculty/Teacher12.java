package com.example.collegeapp.faculty;

public class Teacher12 {
     private String name,email,category,key1,imageurl;

    public Teacher12() {
    }

    public Teacher12(String name, String email, String category, String key1, String imageurl) {
        this.name = name;
        this.email = email;
        this.category = category;
        this.key1 = key1;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCategory() {
        return category;
    }

    public String getKey1() {
        return key1;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
