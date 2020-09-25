package com.example.newenglishspeaking.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newenglishspeaking.Fragment.CommunityFragment;
import com.example.newenglishspeaking.Fragment.HomeFragment;
import com.example.newenglishspeaking.Fragment.LoginFragment;
import com.example.newenglishspeaking.MainActivity;
import com.example.newenglishspeaking.Model.PostItem;
import com.example.newenglishspeaking.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.CustomViewHolder>{
    private ArrayList<PostItem> postItems;
    private Context mContext;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("posts");

    public PostAdapter(Context context, ArrayList<PostItem> postItems) {
        this.mContext = context;
        this.postItems = postItems;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView postNumber, postTestName, postTitle, postID, postDate;
        public ImageView delete;

        public CustomViewHolder(View view) {
            super(view);
            postNumber = (TextView) view.findViewById(R.id.postNumber);
            postTestName = (TextView) view.findViewById(R.id.postTestName);
            postTitle = (TextView) view.findViewById(R.id.postTitle);
            postID = (TextView) view.findViewById(R.id.postID);
            postDate = (TextView) view.findViewById(R.id.postDate);
            delete = (ImageView) view.findViewById(R.id.delete);
        }
    }

    @Override
    public PostAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_postlist, parent, false);
        return new PostAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PostAdapter.CustomViewHolder holder, final int position) {

        final PostItem post = postItems.get(position);

        holder.postNumber.setText(getItemCount() - position + "");    //게시글 번호 설정
        String tmp[] = post.getPostTestName().split(" ");
        holder.postTestName.setText(tmp[0]);    //테스트 이름 설정
        holder.postTitle.setText(post.getPostTitle());    //게시글 제목 설정
        holder.postID.setText(post.getPostID());    //게시자 아이디 설정
        holder.postDate.setText(post.getPostDate());    //날짜 설정

        if(MainActivity._userId.equals(holder.postID.getText().toString()) || MainActivity._userId.equals("admin")){
            holder.delete.setVisibility(View.VISIBLE);
        }else{
            holder.delete.setVisibility(View.INVISIBLE);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(view.getRootView().getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("삭제")
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabase.child(post.getPostNumber()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //삭제되었습니다.
                                        HomeFragment homeFragment = new HomeFragment();
                                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        //목록 클릭시 해당 게시글로 이동
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(MainActivity.userName.getText().toString().equals("")){
                    Toast.makeText(mContext, "로그인 후 이용하세요", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    LoginFragment loginFragment = new LoginFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, loginFragment).commit();
                }else{
                    ArrayList<String> postDatas = new ArrayList<>();
                    postDatas.add(post.postID);
                    postDatas.add(post.postDate);
                    postDatas.add(post.postTitle);
                    postDatas.add(post.postTestName);
                    postDatas.add(post.postText);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("postDatas",postDatas);
                    CommunityFragment communityFragment = new CommunityFragment();
                    communityFragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, communityFragment).commit();
                }
            }
        });
    }

    //목록의 개수 반환
    @Override
    public int getItemCount() {
        return postItems.size();
    }
}