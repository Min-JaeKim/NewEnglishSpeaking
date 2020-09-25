package com.example.newenglishspeaking.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.newenglishspeaking.MainActivity;
import com.example.newenglishspeaking.Model.TestItem;
import com.example.newenglishspeaking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class TestRegisterFragment extends Fragment {
    private DatabaseReference mDatabase;
    private Button register_btn, close_btn;
    private EditText input_testname, input_q1, input_q2, input_q3;
    private EditText input_a1, input_a2, input_a3, input_kq1, input_kq2, input_kq3, input_ka1, input_ka2, input_ka3;
    private String name, q1, q2, q3, a1, a2, a3, kq1, kq2, kq3, ka1, ka2, ka3;
    private RatingBar level_bar;
    private int level;

    public TestRegisterFragment(){ }
    public static Fragment newInstance() {
        return new TestRegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getActivity().setTitle("테스트 등록");   //타이틀 설정
        View rootView = inflater.inflate(R.layout.fragment_testregister, container, false); //레이아웃 설정

        input_testname = rootView.findViewById(R.id.input_testname);    //테스트 이름 입력
        input_q1 = rootView.findViewById(R.id.input_q1);    //문제1, 2, 3 입력
        input_q2 = rootView.findViewById(R.id.input_q2);
        input_q3 = rootView.findViewById(R.id.input_q3);
        input_a1 = rootView.findViewById(R.id.input_a1);    //모범답안1, 2, 3 입력
        input_a2 = rootView.findViewById(R.id.input_a2);
        input_a3 = rootView.findViewById(R.id.input_a3);
        input_kq1 = rootView.findViewById(R.id.input_kq1);    //문제 한글 해석1, 2, 3입력
        input_kq2 = rootView.findViewById(R.id.input_kq2);
        input_kq3 = rootView.findViewById(R.id.input_kq3);
        input_ka1 = rootView.findViewById(R.id.input_ka1);    //답안한글 해석1, 2, 3입력
        input_ka2 = rootView.findViewById(R.id.input_ka2);
        input_ka3 = rootView.findViewById(R.id.input_ka3);

        level_bar = rootView.findViewById(R.id.level);

        register_btn = rootView.findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = input_testname.getText().toString().trim();  //문제 이름 (공백 제거)
                q1 = input_q1.getText().toString().trim();  //문제 1, 2, 3
                q2 = input_q2.getText().toString().trim();
                q3 = input_q3.getText().toString().trim();
                a1 = input_a1.getText().toString().trim();  //모범답안 1, 2, 3
                a2 = input_a2.getText().toString().trim();
                a3 = input_a3.getText().toString().trim();
                kq1 = input_kq1.getText().toString().trim();  //문제 해석 1, 2, 3
                kq2 = input_kq2.getText().toString().trim();
                kq3 = input_kq3.getText().toString().trim();
                ka1 = input_ka1.getText().toString().trim();    //답안 해석 1, 2, 3
                ka2 = input_ka2.getText().toString().trim();
                ka3 = input_ka3.getText().toString().trim();
                level = (int) level_bar.getRating();  //레벨

                if (name.equals("")){
                    Toast.makeText(getContext(), "문제 이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(level <1){
                    Toast.makeText(getContext(), "레벨을 선택해주세요", Toast.LENGTH_SHORT).show();
                }else if(q1.equals("")){
                    Toast.makeText(getContext(), "문제1을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if (kq1.equals("")){
                    Toast.makeText(getContext(), "문제 한글 해석 1을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(a1.equals("")){
                    Toast.makeText(getContext(), "모범 답안 1을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(ka1.equals("")) {
                    Toast.makeText(getContext(), "답안 한글 해석 1을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(q2.equals("") && (!a2.equals("") || !kq2.equals("") || !ka2.equals(""))){
                    Toast.makeText(getContext(), "문제2를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(!q2.equals("") && kq2.equals("")){
                    Toast.makeText(getContext(), "문제 한글 해석 2를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(!kq2.equals("") && a2.equals("")){
                    Toast.makeText(getContext(), "모범 답안 2를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(!a2.equals("") && ka2.equals("")){
                    Toast.makeText(getContext(), "답안 한글 해석 2를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(q3.equals("") && (!a3.equals("") || !kq3.equals("") || !ka3.equals(""))){
                    Toast.makeText(getContext(), "문제3을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(!q3.equals("") && kq3.equals("")){
                    Toast.makeText(getContext(), "문제 한글 해석 3을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(!kq3.equals("") && a3.equals("")){
                    Toast.makeText(getContext(), "모범 답안 3을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(!a3.equals("") && ka3.equals("")){
                    Toast.makeText(getContext(), "답안 한글 해석 3을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    mDatabase  = FirebaseDatabase.getInstance().getReference("tests");
                    mDatabase.addListenerForSingleValueEvent(createTest);
                }
            }
        });
        close_btn = rootView.findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TestListFragment.newInstance());
            }
        });
        return rootView;
    }

    private ValueEventListener createTest = new ValueEventListener(){
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
            registerTest(new_id + 1);
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
        }
    };

    private void registerTest(int id){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        TestItem test = new TestItem(id+"", name, level+"", 0+"", q1, q2, q3, a1, a2, a3, kq1, kq2, kq3, ka1, ka2, ka3);
        mDatabase.child("tests").child(id+"").setValue(test);
        Toast.makeText(getContext(),"등록되었습니다", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).replaceFragment(TestListFragment.newInstance());
    }
}