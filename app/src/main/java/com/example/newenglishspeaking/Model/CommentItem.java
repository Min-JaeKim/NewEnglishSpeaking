package com.example.newenglishspeaking.Model;

public class CommentItem {
    public String postNumber;  //게시글 번호
    public String commentID; //댓글 게시자 아이디
    public String comment; //댓글 내용
    public String commentDate ; //댓글 날짜

    public CommentItem(String postNumber, String commentID, String comment, String commentDate){
        this.postNumber = postNumber;
        this.commentID = commentID;
        this.comment = comment;
        this.commentDate = commentDate;

    }
    public String getPostNumber(){
        return postNumber;
    }
    public String getCommentID(){
        return commentID;
    }

    public String getComment(){
        return comment;
    }

    public String getCommentDate(){
        return commentDate;
    }
}
