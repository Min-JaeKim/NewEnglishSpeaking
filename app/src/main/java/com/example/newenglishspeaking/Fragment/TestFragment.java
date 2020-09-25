package com.example.newenglishspeaking.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.newenglishspeaking.JaccardSimilarity;
import com.example.newenglishspeaking.MainActivity;
import com.example.newenglishspeaking.Model.ResultItem;
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
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.speech.tts.TextToSpeech.ERROR;
import static com.example.newenglishspeaking.MainActivity._userId;

public class TestFragment extends Fragment {
    private View rootView;
    private TextView q1, q2, q3, kq1, kq2, kq3, a1, a2, a3, ma1, ma2, ma3, ka1, ka2, ka3;
    private TextView sc1, sc2, sc3, testpos,testname;
    private Button test_btn, list_btn, score_btn, retry_btn, save_btn, show_list;
    private RelativeLayout btn_l1, btn_l2, btn_l3;
    private String testNumber, testName, resultKey = "";
    public static String date;
    private int testPos, isTest;
    private DatabaseReference mDatabase;
    private TextToSpeech tts;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private int current_speaking;
    private ResultItem resultItem;
    private ArrayList<String> testDatas = new ArrayList<>();

    public TestFragment(){ }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        getActivity().setTitle("영어 회화 테스트");    //타이틀 설정
        rootView = inflater.inflate(R.layout.fragment_test, container, false); //테스트 레이아웃

        testpos = rootView.findViewById(R.id.testpos);  //문제 번호
        testname = rootView.findViewById(R.id.testname);    //문제 이름
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

        show_list = rootView.findViewById(R.id.show_list); //목록보기 버튼
        score_btn = rootView.findViewById(R.id.score);  //채점하기 버튼
        retry_btn = rootView.findViewById(R.id.re_test);   //다시풀기 버튼
        save_btn = rootView.findViewById(R.id.save);    //저장하기 버튼
        test_btn = rootView.findViewById(R.id.test);    //문제풀기 버튼
        list_btn = rootView.findViewById(R.id.list);    //목록보기 버튼
        //버튼 레이아웃 1, 2, 3
        btn_l1 = rootView.findViewById(R.id.btn_layout1);
        btn_l2 = rootView.findViewById(R.id.btn_layout2);
        btn_l3 = rootView.findViewById(R.id.btn_layout3);
        //점수 1, 2, 3
        sc1 = rootView.findViewById(R.id.score1);
        sc2 = rootView.findViewById(R.id.score2);
        sc3 = rootView.findViewById(R.id.score3);

        //넘겨받은 테스트 번호 정보
        Bundle bundle = this.getArguments();
        if(bundle != null){
            testDatas = bundle.getStringArrayList("testDatas");
            isTest = bundle.getInt("isTest");
        }
        testpos.setText("문제 " + testDatas.get(0));
        testNumber = testDatas.get(1);
        testname.setText(testDatas.get(2));
        testName = testDatas.get(2);
        q1.setText(testDatas.get(3));
        q2.setText(testDatas.get(4));
        q3.setText(testDatas.get(5));
        ma1.setText(testDatas.get(6));
        ma2.setText(testDatas.get(7));
        ma3.setText(testDatas.get(8));
        kq1.setText(testDatas.get(9));
        kq2.setText(testDatas.get(10));
        kq3.setText(testDatas.get(11));
        ka1.setText(testDatas.get(12));
        ka2.setText(testDatas.get(13));
        ka3.setText(testDatas.get(14));
        sc1.setVisibility(View.GONE);   //점수 숨기기
        sc2.setVisibility(View.GONE);
        sc3.setVisibility(View.GONE);

        //모범답안보기일 경우
        if(isTest == 0){
            a1.setVisibility(View.GONE);    //답 입력칸 숨기기
            a2.setVisibility(View.GONE);
            a3.setVisibility(View.GONE);
            kq1.setVisibility(View.VISIBLE);    //문제 해석 보이기
            kq2.setVisibility(View.VISIBLE);
            kq3.setVisibility(View.VISIBLE);
            ma1.setVisibility(View.VISIBLE);   //모범답안 보이기
            ma2.setVisibility(View.VISIBLE);
            ma3.setVisibility(View.VISIBLE);
            ka1.setVisibility(View.VISIBLE);    //답안 해석 보이기
            ka2.setVisibility(View.VISIBLE);
            ka3.setVisibility(View.VISIBLE);
            btn_l1.setVisibility(View.GONE);
            btn_l2.setVisibility(View.GONE);
            btn_l3.setVisibility(View.VISIBLE);
            test_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("testDatas", testDatas);
                    bundle.putInt("isTest", 1);
                    TestFragment testFragment = new TestFragment();
                    testFragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, testFragment).commit();
                }
            });
            list_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TestListFragment testListFragment = new TestListFragment();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, testListFragment).addToBackStack(null).commit();
                }
            });
        }
        //테스트하기일 경우
        else{
            a1.setVisibility(View.VISIBLE); //답 입력칸 보이기
            a2.setVisibility(View.VISIBLE);
            a3.setVisibility(View.VISIBLE);
            kq1.setVisibility(View.GONE);    //문제 해석 숨기기
            kq2.setVisibility(View.GONE);
            kq3.setVisibility(View.GONE);
            ka1.setVisibility(View.GONE);    //답안 해석 숨기기
            ka2.setVisibility(View.GONE);
            ka3.setVisibility(View.GONE);
            ma1.setVisibility(View.GONE);   //모범답안 숨기기
            ma2.setVisibility(View.GONE);
            ma3.setVisibility(View.GONE);
            btn_l1.setVisibility(View.VISIBLE);
            btn_l2.setVisibility(View.GONE);
            btn_l3.setVisibility(View.GONE);
        }

        //문제 수에 따라 칸 수 조절
        if(q2.getText().equals("")){
            q2.setVisibility(View.GONE);
            a2.setVisibility(View.GONE);
            ma2.setVisibility(View.GONE);
        }
        if(q3.getText().equals("")){
            q3.setVisibility(View.GONE);
            a3.setVisibility(View.GONE);
            ma3.setVisibility(View.GONE);
        }

        //TTS 소리 설정
        tts = null;
        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.ENGLISH);
                    tts.setSpeechRate(0.8f);
                }
            }
        });

        //문제 클릭시 TTS
        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = q1.getText().toString();
                tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = q2.getText().toString();
                tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = q3.getText().toString();
                tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        //답안 클릭시 STT
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.stop();
                current_speaking = 1;
                speechInput();
            }
        });
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.stop();
                current_speaking = 2;
                speechInput();
            }
        });
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_speaking = 3;
                speechInput();
            }
        });

        //모범답안 클릭시 TTS
        ma1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = ma1.getText().toString();
                tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        ma2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = ma2.getText().toString();
                tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        ma3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = ma3.getText().toString();
                tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        //목록보기로 이동
        show_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestListFragment testListFragment = new TestListFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, testListFragment).addToBackStack(null).commit();
            }
        });

        //채점하기 버튼 클릭시 채점화면으로 이동
        score_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ma1.setVisibility(View.VISIBLE);
                sc1.setVisibility(View.VISIBLE);
                kq1.setVisibility(View.VISIBLE);
                ka1.setVisibility(View.VISIBLE);

                //문장 유사도 분석후 점수 입력
                sc1.setText(JaccardSimilarity.getJarkard(a1.getText().toString(), ma1.getText().toString()) + "% 일치");
                if(q2.getVisibility() == View.VISIBLE){
                    ma2.setVisibility(View.VISIBLE);
                    ka2.setVisibility(View.VISIBLE);
                    kq2.setVisibility(View.VISIBLE);
                    sc2.setVisibility(View.VISIBLE);
                    sc2.setText(JaccardSimilarity.getJarkard(a2.getText().toString(), ma2.getText().toString()) + "% 일치");
                }
                if(q3.getVisibility() == View.VISIBLE){
                    ma3.setVisibility(View.VISIBLE);
                    ka3.setVisibility(View.VISIBLE);
                    kq3.setVisibility(View.VISIBLE);
                    sc3.setVisibility(View.VISIBLE);
                    sc3.setText(JaccardSimilarity.getJarkard(a3.getText().toString(), ma3.getText().toString()) + "% 일치");
                }
                btn_l1.setVisibility(View.GONE);
                btn_l2.setVisibility(View.VISIBLE);
                btn_l3.setVisibility(View.GONE);
                //다시 풀기
                retry_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("testDatas", testDatas);
                        bundle.putInt("isTest", 1);
                        TestFragment testFragment = new TestFragment();
                        testFragment.setArguments(bundle);
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, testFragment).commit();
                    }
                });

                //저장
                save_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mDatabase = FirebaseDatabase.getInstance().getReference("results");
                        mDatabase.addListenerForSingleValueEvent(createResult);

                        //목록으로 돌아가기
                        TestListFragment testListFragment = new TestListFragment();
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, testListFragment).addToBackStack(null).commit();
                    }
                });
            }
        });

        return rootView;
    }

    private void speechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "음성인식 중 ...");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
        }
    }


    /**
     * Receiving speech input
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(current_speaking == 1){
                        a1.setText(result.get(0));
                        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
                    }else if(current_speaking == 2){
                        a2.setText(result.get(0));
                    }else if(current_speaking == 3){
                        a3.setText(result.get(0));
                    }
                }
                break;
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ValueEventListener createResult = new ValueEventListener(){
        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            int new_id = 0;
            while(child.hasNext()){
                DataSnapshot next = child.next();
                if(next.getKey().split("_")[0].equals(_userId)){
                    if(new_id < Integer.parseInt(next.getKey().split("_")[1])){
                        new_id = Integer.parseInt(next.getKey().split("_")[1]);
                    }
                }
            }
            resultKey = _userId +"_" + ++new_id;
            registerResult(resultKey);
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
        }
    };
    private void registerResult(String resultKey){
        //날짜 설정
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일 h시 m분");
        date = df.format(d);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        resultItem = new ResultItem(a1.getText().toString(), a2.getText().toString(), a3.getText().toString(),
                ma1.getText().toString(), ma2.getText().toString(), ma3.getText().toString(),
                q1.getText().toString(), q2.getText().toString(), q3.getText().toString(),
                sc1.getText().toString(), sc2.getText().toString(), sc3.getText().toString(),
                kq1.getText().toString(), kq2.getText().toString(), kq3.getText().toString(),
                ka1.getText().toString(), ka2.getText().toString(), ka3.getText().toString(),
                testName, _userId, date);
        mDatabase.child("results").child(resultKey).setValue(resultItem);
        Toast.makeText(getContext(),"저장되었습니다", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).replaceFragment(TestListFragment.newInstance());
    }
}
