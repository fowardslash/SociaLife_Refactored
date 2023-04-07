package com.example.projectmain.Model;

public class  Post {
    public Post() {
    }

//    int avatar;
//    int imgPost;
//    String username;
//    String name;
//    String number_like;
//    String content;
//    String time;


    String avatar;
    String imgPost;
    String username;
    String name;
    String number_like;
    String content;
    String time;


    public Post(String avatar, String imgPost, String username, String name, String number_like, String content, String time) {
        this.avatar = avatar;
        this.imgPost = imgPost;
        this.username = username;
        this.name = name;
        this.number_like = number_like;
        this.content = content;
        this.time = time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getImgPost() {
        return imgPost;
    }

    public void setImgPost(String imgPost) {
        this.imgPost = imgPost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber_like() {
        return number_like;
    }

    public void setNumber_like(String number_like) {
        this.number_like = number_like;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
