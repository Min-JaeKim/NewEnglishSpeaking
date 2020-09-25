package com.example.newenglishspeaking.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newenglishspeaking.Adapter.ResultAdapter;
import com.example.newenglishspeaking.MainActivity;
import com.example.newenglishspeaking.Model.ResultItem;
import com.example.newenglishspeaking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ResultListFragment extends Fragment {
    private View rootView;
    private DatabaseReference mDatabase;
    private ArrayList<ResultItem> result = new ArrayList<>();
    private RecyclerView resultRecyclerView;
    private RecyclerView.LayoutManager resultLayoutManager;
    private ResultAdapter resultAdapter;

    public ResultListFragment(){ }
    public static Fragment newInstance() { return new ResultListFragment(); }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("테스트 결과"); //타이틀 설정
        rootView = inflater.inflate(R.layout.fragment_resultlist, container, false); //레이아웃 설정

        if(MainActivity.userName.getText().toString().equals("")){
            Toast.makeText(getContext(), "로그인 후 이용하세요", Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).replaceFragment(LoginFragment.newInstance());
        }else{
            //firebase DB에서 데이터 가져오기
            mDatabase = FirebaseDatabase.getInstance().getReference("results");
            mDatabase.addListenerForSingleValueEvent(setResultList);
        }
        return rootView;
    }
    //테스트 리스트 가져오기
    private ValueEventListener setResultList = new ValueEventListener(){
        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            result.clear();
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            while(child.hasNext()){
                DataSnapshot next = child.next();
                String resultKey = next.getKey();
                if(resultKey.split("_")[0].equals(MainActivity._userId)){
                    String a1 = next.child("a1").getValue().toString();
                    String a2 = next.child("a2").getValue().toString();
                    String a3 = next.child("a3").getValue().toString();
                    String ma1 = next.child("ma1").getValue().toString();
                    String ma2 = next.child("ma2").getValue().toString();
                    String ma3 = next.child("ma3").getValue().toString();
                    String q1 = next.child("q1").getValue().toString();
                    String q2 = next.child("q2").getValue().toString();
                    String q3 = next.child("q3").getValue().toString();
                    String sc1 = next.child("sc1").getValue().toString();
                    String sc2 = next.child("sc2").getValue().toString();
                    String sc3 = next.child("sc3").getValue().toString();
                    String kq1 = next.child("kq1").getValue().toString();
                    String kq2 = next.child("kq2").getValue().toString();
                    String kq3 = next.child("kq3").getValue().toString();
                    String ka1 = next.child("ka1").getValue().toString();
                    String ka2 = next.child("ka2").getValue().toString();
                    String ka3 = next.child("ka3").getValue().toString();
                    String testName = next.child("testName").getValue().toString();
                    String userId = next.child("userId").getValue().toString();
                    String date = next.child("date").getValue().toString();
                    result.add(new ResultItem(resultKey, a1, a2, a3, ma1, ma2, ma3, q1, q2, q3, sc1, sc2, sc3,
                            kq1, kq2, kq3, ka1, ka2, ka3, testName, userId, date));
                }
            }

            Collections.reverse(result);

            resultRecyclerView = rootView.findViewById(R.id.recycler_view4);
            resultAdapter = new ResultAdapter(getContext(), result);
            resultLayoutManager = new LinearLayoutManager(getActivity());
            resultRecyclerView.setLayoutManager(resultLayoutManager);
            resultRecyclerView.setAdapter(resultAdapter);
            resultAdapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
        }
    };

}
