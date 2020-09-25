package com.example.newenglishspeaking.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.newenglishspeaking.MainActivity;
import com.example.newenglishspeaking.Model.PostItem;
import com.example.newenglishspeaking.Model.TestItem;
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

import static com.example.newenglishspeaking.MainActivity._userId;

public class CommunityWriteFragment extends Fragment {
    private EditText input_title, input_content;
    private Button select_test, register_btn, close_btn;
    private TextView testName;
    private DatabaseReference mDatabase;
    private String date;
    private View rootView;
    private ArrayList<TestItem> test = new ArrayList<>();
    public CommunityWriteFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getActivity().setTitle("게시물 등록"); //타이틀 설정
        rootView = inflater.inflate(R.layout.fragment_communitywrite, container, false); //레이아웃 설정

        input_title = rootView.findViewById(R.id.input_title);  //제목 입력
        input_content = rootView.findViewById(R.id.input_content);  //내용 입력
        select_test = rootView.findViewById(R.id.select_test);  //테스트 선택창
        testName = rootView.findViewById(R.id.testName);    //테스트 이름
        register_btn = rootView.findViewById(R.id.register_btn);    //등록 버튼
        close_btn = rootView.findViewById(R.id.close_btn);  //닫기 버튼

        //날짜 설정
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
        date = df.format(d);

        //관련 테스트 이름 설정
        testName.setText("");
        mDatabase = FirebaseDatabase.getInstance().getReference("tests");
        mDatabase.addListenerForSingleValueEvent(setTestList);

        select_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = new String[test.size()];
                for(int i = 0; i < test.size(); i++){
                    items[i] = "문제"+ (i + 1) + ". " + test.get(i).testName;
                }
                final int[] selectedIndex = {0};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("테스트를 선택하세요")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                selectedIndex[0] = which;
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                testName.setText(items[selectedIndex[0]] + "");
                            }
                        })
                        .create()
                        .show();
            }
        });

        //등록
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input_title.getText().toString().equals("")){
                    Toast.makeText(getContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(testName.getText().toString().equals("")){
                    Toast.makeText(getContext(), "관련 문제를 선택해주세요", Toast.LENGTH_SHORT).show();
                }else if(input_content.getText().toString().equals("")){
                    Toast.makeText(getContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    mDatabase  = FirebaseDatabase.getInstance().getReference("posts");
                    mDatabase.addListenerForSingleValueEvent(createPost);
                }
            }
        });

        //취소 버튼
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(CommunityListFragment.newInstance());
            }
        });
        return rootView;
    }

    private ValueEventListener createPost = new ValueEventListener(){
        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            int new_id = 0;
            while(child.hasNext()){
                DataSnapshot next = child.next();
                int id = Integer.parseInt(next.getKey());
                if(id > new_id){
                    new_id = id;
                }
            }
            registerPost(new_id + 1);
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
        }
    };

    private void registerPost(int id){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        PostItem postItem = new PostItem(testName.getText().toString(), input_title.getText().toString(),
                input_content.getText().toString(), _userId, date);
        mDatabase.child("posts").child(id+"").setValue(postItem);
        Toast.makeText(getContext(),"등록되었습니다", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putInt("do",1);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        ((MainActivity)getActivity()).replaceFragment(CommunityListFragment.newInstance());
    }

    //테스트 리스트 가져오기
    private ValueEventListener setTestList = new ValueEventListener(){
        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            test.clear();
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            while(child.hasNext()){
                DataSnapshot next = child.next();
                String t_name = next.child("testName").getValue().toString();
                test.add(new TestItem(t_name));
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError){ }
    };
}
