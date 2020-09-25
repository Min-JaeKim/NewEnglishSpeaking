package com.example.newenglishspeaking.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.newenglishspeaking.Model.CommentItem;
import com.example.newenglishspeaking.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CustomViewHolder> {
    private ArrayList<CommentItem> commItem;

    public CommentAdapter(ArrayList<CommentItem> commItem) {
        this.commItem = commItem;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView commentID, comment, commentDate;
        public CustomViewHolder(View view) {
            super(view);
            commentID = (TextView) view.findViewById(R.id.commentID);
            commentDate = (TextView) view.findViewById(R.id.commentDate);
            comment = (TextView) view.findViewById(R.id.comment);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_commentlist, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.commentID.setText(commItem.get(position).commentID);    //댓글 게시자 아이디 설정
        holder.comment.setText(commItem.get(position).comment);    //댓글 내용 설정
        holder.commentDate.setText(commItem.get(position).commentDate);    //날짜 설정
    }

    //목록의 개수 반환
    @Override
    public int getItemCount() {
        return commItem.size();
    }
}
