package com.example.newenglishspeaking.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newenglishspeaking.Adapter.PostAdapter;
import com.example.newenglishspeaking.Model.PostItem;
import com.example.newenglishspeaking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class CommunityListFragment extends Fragment {
    private Button register_btn;
    private RecyclerView postRecyclerView;
    private RecyclerView.LayoutManager postLayoutManager;
    private DatabaseReference mDatabase;
    private PostAdapter postAdapter;
    public static ArrayList<PostItem> post = new ArrayList<>();
    private View rootView;

    public CommunityListFragment(){ }
    public static Fragment newInstance() { return new CommunityListFragment(); }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getActivity().setTitle("커뮤니티"); //타이틀 설정
        rootView = inflater.inflate(R.layout.fragment_communitylist, container, false); //레이아웃 설

        //recyclerview 설정
        mDatabase = FirebaseDatabase.getInstance().getReference("posts");
        mDatabase.addListenerForSingleValueEvent(setPostList);

        //'게시물 등록' 버튼을 누르면 게시물 작성 페이지로 이동
        Button write_btn = (Button) rootView.findViewById(R.id.write_btn);
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommunityWriteFragment communityWriteFragment = new CommunityWriteFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, communityWriteFragment).addToBackStack(null).commit();
            }
        });
        return rootView;
    }

    //게시물 리스트 가져오기
    private ValueEventListener setPostList = new ValueEventListener(){
        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            post.clear();
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            while(child.hasNext()){
                DataSnapshot next = child.next();
                String num = next.getKey();
                String t_name = next.child("postTestName").getValue().toString();
                String title = next.child("postTitle").getValue().toString();
                String text = next.child("postText").getValue().toString();
                String id = next.child("postID").getValue().toString();
                String date = next.child("postDate").getValue().toString();
                post.add(new PostItem(num, t_name, title, text, id, date));
            }
            Collections.reverse(post);

            //post recyclerview 설정
            postRecyclerView = rootView.findViewById(R.id.recycler_view2);
            postAdapter = new PostAdapter(getContext(), post);
            postLayoutManager = new LinearLayoutManager(getActivity());
            postRecyclerView.setLayoutManager(postLayoutManager);
            postRecyclerView.setAdapter(postAdapter);
            postAdapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
        }
    };
}
