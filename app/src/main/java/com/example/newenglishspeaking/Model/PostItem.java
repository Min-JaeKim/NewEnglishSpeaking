package com.example.newenglishspeaking.Model;

public class PostItem {
    public String postNumber; //게시글 번호
    public String postTestName; //테스트 이름
    public String postTitle; //게시글 제목
    public String postText; //게시글 내용
    public String postID; //게시자 아이디
    public String postDate ; //날짜

    public PostItem(String postNumber, String postTestName, String postTitle, String postText, String postID, String postDate){
        this.postNumber = postNumber;
        this.postTestName = postTestName;
        this.postTitle = postTitle;
        this.postText = postText;
        this.postID = postID;
        this.postDate = postDate;
    }
    public PostItem(String postTestName, String postTitle, String postText,  String postID, String postDate){
        this.postTestName = postTestName;
        this.postTitle = postTitle;
        this.postText = postText;
        this.postID = postID;
        this.postDate = postDate;
    }

    public String getPostText() {
        return postText;
    }

    public String getPostNumber(){
        return postNumber;
    }

    public String getPostTestName(){
        return postTestName;
    }

    public String getPostTitle(){
        return postTitle;
    }

    public String getPostID(){
        return postID;
    }

    public String getPostDate(){
        return postDate;
    }
}
