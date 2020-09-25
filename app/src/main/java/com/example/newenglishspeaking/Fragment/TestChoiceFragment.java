package com.example.newenglishspeaking.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.newenglishspeaking.R;

import java.util.ArrayList;

public class TestChoiceFragment extends Fragment {
    private View rootView;
    private TextView testpos, testname;
    private Button test_btn, answer_btn;
    private int testPos;
    private String testName, testNumber;
    private TestFragment testFragment = new TestFragment();
    private AppCompatActivity activity;
    private ArrayList<String> testDatas = new ArrayList<>();

    public TestChoiceFragment(){ }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("영어 회화 테스트");    //타이틀 설정
        rootView = inflater.inflate(R.layout.fragment_testchoice, container, false); //테스트 선택 레이아웃

        testpos = rootView.findViewById(R.id.testpos);  //문제 번호
        testname = rootView.findViewById(R.id.testname);    //문제 이름
        test_btn = rootView.findViewById(R.id.test_btn);        //테스트하기 버튼
        answer_btn = rootView.findViewById(R.id.answer_btn);    //모범답안보기 버튼


        //넘겨받은 테스트 번호 정보
        Bundle bundle = this.getArguments();
        if(bundle != null){
            testDatas = bundle.getStringArrayList("testDatas");
        }
        testpos.setText("문제 " + testDatas.get(0));   //문제 번호 설정
        testname.setText(testDatas.get(2)); //문제 이름 설정

        //테스트하기 버튼 클릭시
        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("testDatas", testDatas);
                bundle.putInt("isTest", 1);
                //테스트 화면으로 이동
                testFragment.setArguments(bundle);
                activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, testFragment).commit();
            }
        });

        //모범답안 보기 클릭시
        answer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("testDatas", testDatas);
                bundle.putInt("isTest", 0);
                //테스트 화면으로 이동
                testFragment.setArguments(bundle);
                activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, testFragment).commit();
            }
        });

        return rootView;
    }
}
