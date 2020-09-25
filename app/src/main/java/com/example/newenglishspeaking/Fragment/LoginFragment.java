package com.example.newenglishspeaking.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.newenglishspeaking.MainActivity;
import com.example.newenglishspeaking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class LoginFragment extends Fragment {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
    private EditText idText, passwordText;    //아이디
    private Button loginButton; //로그인 버튼
    private TextView signupLink;    //회원가입 링크
    private String id, password;

    public LoginFragment(){ }
    public static Fragment newInstance() {
        return new LoginFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getActivity().setTitle("로그인");  //타이틀 설정
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);    //레이아웃 설정

        idText = (EditText) rootView.findViewById(R.id.input_id);   //입력받은 아이디
        passwordText = (EditText) rootView.findViewById(R.id.input_password);   //입력받은 비밀번호
        loginButton = (Button) rootView.findViewById(R.id.login);   //로그인 버튼
        signupLink = (TextView) rootView.findViewById(R.id.link_signup);    //회원가입 링크

        //빈 칸으로 초기 설정
        idText.setText("");
        passwordText.setText("");

        //로그인 버튼 클릭시
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = idText.getText().toString().trim();    //입력받은 아이디(공백 제거)
                password = passwordText.getText().toString().trim();    //입력받은 비밀번호(공백 제거)
                if(!id.isEmpty() && !password.isEmpty()){   //빈 칸이 아닐 경우
                    mDatabase.addListenerForSingleValueEvent(login);    //로그인 시작
                }else{  //빈 칸일 경우
                    Toast.makeText(getContext(), "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //회원가입 링크 클릭시
        signupLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //회원가입 페이지로 이동
                SingupFragment registerFragment = new SingupFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, registerFragment).addToBackStack(null).commit();
            }
        });
        return rootView;
    }

    //firebase DB에서 회원 정보가져오기
    private ValueEventListener login = new ValueEventListener(){
        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            while(child.hasNext()){
                DataSnapshot next = child.next();
                if(id.equals(next.getKey())){   //입력받은 아이디와 일치하는 경우
                    String name = next.child("name").getValue().toString();
                    String pwd = next.child("password").getValue().toString();
                    if(pwd.equals(password)){   //입력받은 비밀번호와 일치하는 경우
                        Toast.makeText(getContext(), name + "님 안녕하세요", Toast.LENGTH_LONG).show();
                        //로그인된 화면으로 변경
                        MainActivity.login_register_ll.setVisibility(View.GONE);
                        MainActivity.logout_ll.setVisibility(View.VISIBLE);
                        //아이디와 회원 이름 저장
                        MainActivity._userId = id;
                        MainActivity._userName = name;
                        //메인으로 이동
                        startActivity(new Intent(getContext(), MainActivity.class));
                        return;
                    }
                }
            }
            //일치하는 회원이 없을 경우
            Toast.makeText(getContext(), "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
        }
    };
}
