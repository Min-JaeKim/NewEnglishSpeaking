package com.example.newenglishspeaking.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newenglishspeaking.Adapter.PostAdapter;
import com.example.newenglishspeaking.Adapter.TestAdapter;
import com.example.newenglishspeaking.Model.PostItem;
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

public class HomeFragment extends Fragment {
    private DatabaseReference mDatabase;
    private RecyclerView testRecyclerView;
    private RecyclerView.LayoutManager testLayoutManager;
    private TestAdapter testAdapter;
    private ArrayList<TestItem> test = new ArrayList<>();
    private ArrayList<TestItem> recent_test = new ArrayList<>();
    private View rootView;

    public HomeFragment(){ }
    public static Fragment newInstance() {
        return new HomeFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getActivity().setTitle("Home"); //타이틀 설정
        rootView = inflater.inflate(R.layout.fragment_home, container, false);  //레이아웃 설정

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
                String t_num = next.getKey();
                String t_name = next.child("testName").getValue().toString();
                String level = next.child("level").getValue().toString();
                String isTested = next.child("isTested").getValue().toString();
                test.add(new TestItem(t_num, t_name, level, isTested));
            }
            Collections.reverse(test);

            //test recyclerview 설정
            if(test.size() > 5){    //최대 5개 까지만 나오게
                recent_test = new ArrayList<>(test.subList(0,5));
            }else{
                recent_test = new ArrayList<>(test);
            }
            testRecyclerView = rootView.findViewById(R.id.recycler_view1);
            testAdapter = new TestAdapter(getContext(), recent_test);
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
