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

public class ResultFragment extends Fragment {
    private View rootView;
    private TextView q1, q2, q3, kq1, kq2, kq3, a1, a2, a3, ma1, ma2, ma3, ka1, ka2, ka3;
    private TextView sc1, sc2, sc3, testName, date;
    private Button list;
    private ArrayList<String> resultDatas = new ArrayList<>();
    public ResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("테스트 결과");    //타이틀 설정
        rootView = inflater.inflate(R.layout.fragment_result, container, false); //테스트 레이아웃

        date = rootView.findViewById(R.id.date);
        testName = rootView.findViewById(R.id.testName);    //문제 이름
        //질문 1, 2, 3
        q1 = rootView.findViewById(R.id.question1);
        q2 = rootView.findViewById(R.id.question2);
        q3 = rootView.findViewById(R.id.question3);
        //질문 해석 1, 2, 3
        kq1 = rootView.findViewById(R.id.k_question1);
        kq2 = rootView.findViewById(R.id.k_question2);
        kq3 = rootView.findViewById(R.id.k_question3);
        //답변 1, 2, 3
        a1 = rootView.findViewById(R.id.answer1);
        a2 = rootView.findViewById(R.id.answer2);
        a3 = rootView.findViewById(R.id.answer3);
        //모범답안 1, 2, 3
        ma1 = rootView.findViewById(R.id.modelAnswer1);
        ma2 = rootView.findViewById(R.id.modelAnswer2);
        ma3 = rootView.findViewById(R.id.modelAnswer3);
        //답안 해석 1, 2, 3
        ka1 = rootView.findViewById(R.id.k_answer1);
        ka2 = rootView.findViewById(R.id.k_answer2);
        ka3 = rootView.findViewById(R.id.k_answer3);
        //점수 1, 2, 3
        //점수 1, 2, 3
        sc1 = rootView.findViewById(R.id.score1);
        sc2 = rootView.findViewById(R.id.score2);
        sc3 = rootView.findViewById(R.id.score3);

        //목록보기
        list = rootView.findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultListFragment resultListFragment = new ResultListFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, resultListFragment).addToBackStack(null).commit();
            }
        });
        //넘겨받은 테스트 번호 정보
        Bundle bundle = this.getArguments();
        if(bundle != null){
            resultDatas = bundle.getStringArrayList("resultDatas");
        }

        a1.setText(resultDatas.get(0));
        ma1.setText(resultDatas.get(3));
        q1.setText(resultDatas.get(6));
        sc1.setText(resultDatas.get(9));
        ka1.setText(resultDatas.get(12));
        kq1.setText(resultDatas.get(15));
        date.setText(resultDatas.get(18));
        testName.setText(resultDatas.get(19));
        a2.setText(resultDatas.get(1));
        a3.setText(resultDatas.get(2));
        ma2.setText(resultDatas.get(4));
        ma3.setText(resultDatas.get(5));
        q2.setText(resultDatas.get(7));
        q3.setText(resultDatas.get(8));
        sc2.setText(resultDatas.get(10));
        sc3.setText(resultDatas.get(11));
        ka2.setText(resultDatas.get(13));
        ka3.setText(resultDatas.get(14));
        kq2.setText(resultDatas.get(16));
        kq3.setText(resultDatas.get(17));

        if(q3.getText().toString().equals("")){
            a3.setVisibility(View.GONE);
            ma3.setVisibility(View.GONE);
            q3.setVisibility(View.GONE);
            sc3.setVisibility(View.GONE);
            ka3.setVisibility(View.GONE);
            kq3.setVisibility(View.GONE);
        }else if (q2.getText().toString().equals("")){
            a2.setVisibility(View.GONE);
            ma2.setVisibility(View.GONE);
            q2.setVisibility(View.GONE);
            sc2.setVisibility(View.GONE);
            ka2.setVisibility(View.GONE);
            kq2.setVisibility(View.GONE);
        }

        return rootView;
    }
}