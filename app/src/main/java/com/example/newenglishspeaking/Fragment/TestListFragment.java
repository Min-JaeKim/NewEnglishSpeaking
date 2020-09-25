package com.example.newenglishspeaking.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newenglishspeaking.Adapter.TestAdapter;
import com.example.newenglishspeaking.MainActivity;
import com.example.newenglishspeaking.Model.TestItem;
import com.example.newenglishspeaking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class TestListFragment extends Fragment {
    private DatabaseReference mDatabase;
    private RecyclerView testRecyclerView;
    private RecyclerView.LayoutManager testLayoutManager;
    private TestAdapter testAdapter;
    public static ArrayList<TestItem> test = new ArrayList<>();
    private Button register_btn;
    private View rootView;

    private String level_choice;
    public TestListFragment(){ }
    public static Fragment newInstance() {
        return new TestListFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getActivity().setTitle("영어 회화 테스트 목록"); //타이틀 설정
        rootView = inflater.inflate(R.layout.fragment_testlist, container, false); //레이아웃 설정

        if (getArguments() != null) {
            level_choice = getArguments().getString("level");
        }
        //firebase DB에서 데이터 가져오기
        mDatabase = FirebaseDatabase.getInstance().getReference("tests");
        mDatabase.addListenerForSingleValueEvent(setTestList);

        return rootView;
    }

    //테스트 리스트 가져오기
    private ValueEventListener setTestList = new ValueEventListener(){
        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            test.clear();
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            while(child.hasNext()){
                DataSnapshot next = child.next();
                String level = next.child("level").getValue().toString();
                String t_num = next.getKey();
                String t_name = next.child("testName").getValue().toString();
                String q1 = next.child("q1").getValue().toString();
                String q2 = next.child("q2").getValue().toString();
                String q3 = next.child("q3").getValue().toString();
                String a1 = next.child("a1").getValue().toString();
                String a2 = next.child("a2").getValue().toString();
                String a3 = next.child("a3").getValue().toString();
                String kq1 = next.child("kq1").getValue().toString();
                String kq2 = next.child("kq2").getValue().toString();
                String kq3 = next.child("kq3").getValue().toString();
                String ka1 = next.child("ka1").getValue().toString();
                String ka2 = next.child("ka2").getValue().toString();
                String ka3 = next.child("ka3").getValue().toString();
                String isTested = next.child("isTested").getValue().toString();
                if(level.equals(level_choice)){
                    test.add(new TestItem(t_num, t_name, level, isTested,
                            q1, q2, q3, a1, a2, a3, kq1, kq2, kq3, ka1, ka2, ka3));
                }
            }

            Collections.reverse(test);
            testRecyclerView = rootView.findViewById(R.id.recycler_view1);
            testAdapter = new TestAdapter(getContext(), test);
            testLayoutManager = new LinearLayoutManager(getActivity());
            testRecyclerView.setLayoutManager(testLayoutManager);
            testRecyclerView.setAdapter(testAdapter);
            testAdapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
        }
    };
}
