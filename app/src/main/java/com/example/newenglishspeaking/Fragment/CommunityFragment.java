package com.example.newenglishspeaking.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newenglishspeaking.Adapter.CommentAdapter;
import com.example.newenglishspeaking.MainActivity;
import com.example.newenglishspeaking.Model.CommentItem;
import com.example.newenglishspeaking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static com.example.newenglishspeaking.Fragment.CommunityListFragment.post;
import static com.example.newenglishspeaking.MainActivity._userId;

public class CommunityFragment extends Fragment {
    private DatabaseReference mDatabase;
    private TextView postID, postDate, postTitle, postTestName, postContent;
    private EditText input_comment;
    private ImageView send;
    private String postNumber = "", date = "";
    private int new_id;
    private RecyclerView recyclerView;    //게시글 목록 Recycler View
    private RecyclerView.LayoutManager layoutManager;  //게시글 목록 레이아웃 매니저
    private CommentAdapter commAdapter;   //게시글 목록 어댑터
    private InputMethodManager imm;
    private ArrayList<CommentItem> commentItems = new ArrayList<>();
    private ArrayList<String> postDatas = new ArrayList<>();

    public CommunityFragment(){ }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getActivity().setTitle("커뮤니티"); //타이틀 설정
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);    //레이아웃 설정


        postID = rootView.findViewById(R.id.postID);    //게시자 아이디
        postDate = rootView.findViewById(R.id.postDate);    //게시한 날짜
        postTitle = rootView.findViewById(R.id.postTitle);  //제목
        postTestName = rootView.findViewById(R.id.postTestName);    //관련 문제이름
        postContent = rootView.findViewById(R.id.postContent);  //내용
        input_comment = rootView.findViewById(R.id.input_comment);  //댓글
        send = rootView.findViewById(R.id.send);    //댓글 입력버튼
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);    //키보드 제어

        //넘겨받은 게시글 번호 정보
        Bundle bundle = this.getArguments();
        if(bundle != null){
            postDatas = bundle.getStringArrayList("postDatas");
        }
        //게시글 정보 설정
        postID.setText(postDatas.get(0));
        postDate.setText(postDatas.get(1));
        postTitle.setText(postDatas.get(2));
        postTestName.setText(postDatas.get(3));
        postContent.setText(postDatas.get(4));

        //댓글 정보 설정
        mDatabase = FirebaseDatabase.getInstance().getReference("comments");
        mDatabase.addListenerForSingleValueEvent(setCommentList);



        if(MainActivity.userName.equals("")){
            Toast.makeText(getContext(), "로그인 후 이용하세요", Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).replaceFragment(TestListFragment.newInstance());
        }


        //recyclerview 설정
        recyclerView = rootView.findViewById(R.id.recycler_view3);
        commAdapter = new CommentAdapter(commentItems);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(commAdapter);


        //날짜 설정
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
        date = df.format(d);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input_comment.getText().toString().equals("")){
                    Toast.makeText(getContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    CommentItem commentItem = new CommentItem(postNumber, _userId,
                            input_comment.getText().toString(), date);
                    mDatabase.child("comments").child(new_id + 1 + "").setValue(commentItem);
                    commentItems.add(new CommentItem(new_id+1+"", _userId,
                            input_comment.getText().toString(), date));
                    commAdapter.notifyDataSetChanged();
                    input_comment.setText("");
                    hideKeyboard();
                }
            }
        });
        return rootView;
    }

    private void hideKeyboard(){
        imm.hideSoftInputFromWindow(input_comment.getWindowToken(), 0);
    }

    private ValueEventListener setCommentList = new ValueEventListener(){
        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            commentItems.clear();
            new_id = 0;
            while(child.hasNext()){
                DataSnapshot next = child.next();
                if(next.child("postNumber").getValue().equals(postNumber)){
                    int id = Integer.parseInt(next.getKey());
                    if(id > new_id) new_id = id;
                    String user = next.child("commentID").getValue().toString();
                    String comment = next.child("comment").getValue().toString();
                    String date = next.child("commentDate").getValue().toString();
                    commentItems.add(new CommentItem(id+"", user, comment, date));
                }
            }
            commAdapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
        }
    };
}
