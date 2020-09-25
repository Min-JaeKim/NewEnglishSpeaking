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
import com.example.newenglishspeaking.Model.UserData;
import com.example.newenglishspeaking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class SingupFragment extends Fragment {
    private EditText nameText, idText, passwordText;
    private Button signupButton;
    private TextView loginLink;
    private DatabaseReference mDatabase;
    private boolean signed = false;
    private String name, id, password;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getActivity().setTitle("회원가입"); //타이틀 설정
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);   //레이아웃 설정

        nameText = (EditText) rootView.findViewById(R.id.input_name);   //입력받은 이름
        idText = (EditText) rootView.findViewById(R.id.input_id);   //입력받은 아이디
        passwordText = (EditText) rootView.findViewById(R.id.input_password);   //입력받은 비밀번호
        signupButton = (Button) rootView.findViewById(R.id.signup); //회원가입 버튼
        loginLink = (TextView) rootView.findViewById(R.id.link_login);  //로그인 링크

        //빈 칸으로 초기 설정
        nameText.setText("");
        idText.setText("");
        passwordText.setText("");

        //회원가입 버튼 클릭시
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = nameText.getText().toString().trim();    //입력받은 이름 (공백 제거)
                id = idText.getText().toString().trim();    //입력받은 아이디 (공백 제거)
                password = passwordText.getText().toString().trim();    //입력받은 비밀번호 (공백 제거)

                //유효한 이름, 아이디, 패스워드인지 확인
                if(name.isEmpty()){
                    Toast.makeText(getContext(), "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(id.isEmpty()){
                    Toast.makeText(getContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if (password.isEmpty()){
                    Toast.makeText(getContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{  //모두 입력되어있으면 회원가입 진행
                    mDatabase  = FirebaseDatabase.getInstance().getReference("users");
                    mDatabase.addListenerForSingleValueEvent(createUser);
                }
                //회원가입 되어있는 경우 메인으로 이동
                if(signed == true){
                    startActivity(new Intent(getContext(), MainActivity.class));
                }
            }
        });

        //로그인 링크 클릭시
        loginLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 로그인페이지로 이동
                LoginFragment loginFragment = new LoginFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, loginFragment).addToBackStack(null).commit();
            }
        });
        return rootView;
    }

    //firebase에 아이디가 등록되어 있는지 확인
    private ValueEventListener createUser = new ValueEventListener(){
        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            while(child.hasNext()){
                //아이디가 존재할 경우
                if(id.equals(child.next().getKey())){
                    Toast.makeText(getContext(), "존재하는 아이디입니다", Toast.LENGTH_SHORT).show();
                    mDatabase.removeEventListener(this);
                    signed = false;
                    return;
                }
            }
            //존재하지 않을 경우
            registerNewUser();
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
        }
    };

    //firebase DB에 회원 정보 올리기
    private void registerNewUser(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        UserData user = new UserData(name, password);
        mDatabase.child("users").child(id).setValue(user);
        Toast.makeText(getContext(),"회원가입에 성공하였습니다", Toast.LENGTH_SHORT).show();
        Fragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("password", password);
        fragment.setArguments(bundle);
        ((MainActivity)getActivity()).replaceFragment(LoginFragment.newInstance());
    }
}
