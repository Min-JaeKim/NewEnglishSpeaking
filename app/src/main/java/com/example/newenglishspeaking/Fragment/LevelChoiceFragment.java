package com.example.newenglishspeaking.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.newenglishspeaking.MainActivity;
import com.example.newenglishspeaking.R;

public class LevelChoiceFragment extends Fragment {

    private View rootView;
    private Button level1, level2, level3, level4, level5, register_btn;
    private int level;
    private Bundle bundle = new Bundle();
    public LevelChoiceFragment(){ }
    TestListFragment testListFragment = new TestListFragment();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("레벨 선택");    //타이틀 설정
        rootView = inflater.inflate(R.layout.fragment_levelchoice, container, false); //테스트 선택 레이아웃

        level1 = rootView.findViewById(R.id.level1);
        level2 = rootView.findViewById(R.id.level2);
        level3 = rootView.findViewById(R.id.level3);
        level4 = rootView.findViewById(R.id.level4);
        level5 = rootView.findViewById(R.id.level5);
        //테스트 등록 버튼
        register_btn = rootView.findViewById(R.id.register_btn);
        //관리자의 경우만 테스트 등록 버튼이 보이게
        if(MainActivity._userId.equals("admin")){
            register_btn.setVisibility(View.VISIBLE);
        }else{
            register_btn.setVisibility(View.GONE);
        }

        //등록 버튼 클릭시
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //등록 페이지로 이동
                ((MainActivity)getActivity()).replaceFragment(TestRegisterFragment.newInstance());
            }
        });
        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("level", "1");
                testListFragment.setArguments(bundle);
                ((MainActivity)getActivity()).replaceFragment(testListFragment);

            }
        });

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("level", "2");
                testListFragment.setArguments(bundle);
                ((MainActivity)getActivity()).replaceFragment(testListFragment);
            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = 3;
                bundle.putString("level", "3");
                testListFragment.setArguments(bundle);
                ((MainActivity)getActivity()).replaceFragment(testListFragment);

            }
        });

        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = 4;
                bundle.putString("level", "4");
                testListFragment.setArguments(bundle);
                ((MainActivity)getActivity()).replaceFragment(testListFragment);
            }
        });

        level5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                level = 5;
                bundle.putString("level", "5");
                testListFragment.setArguments(bundle);
                ((MainActivity)getActivity()).replaceFragment(testListFragment);
            }
        });

        return rootView;
    }
}