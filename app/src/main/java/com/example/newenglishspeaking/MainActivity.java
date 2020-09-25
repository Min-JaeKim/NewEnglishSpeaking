package com.example.newenglishspeaking;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.newenglishspeaking.Fragment.CommunityListFragment;
import com.example.newenglishspeaking.Fragment.HomeFragment;
import com.example.newenglishspeaking.Fragment.LevelChoiceFragment;
import com.example.newenglishspeaking.Fragment.LoginFragment;
import com.example.newenglishspeaking.Fragment.ResultListFragment;
import com.example.newenglishspeaking.Fragment.SingupFragment;
import com.example.newenglishspeaking.Fragment.TestListFragment;
import android.view.View;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment homeFragment, communitylistFragment, loginFragment, singupFragment, resultListFragment;
    private Fragment levelChoiceFragment;
    private Button login_btn, logout_btn, register_btn;
    public static LinearLayout login_register_ll, logout_ll;
    public static TextView userName;
    public static String _userId = new String();
    public static String _userName = new String();
    final int PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //기본 설정
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if ( Build.VERSION.SDK_INT >= 23 ){
            // 퍼미션 체크
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);
        }

        View headerview = navigationView.getHeaderView(0);

        //회원 이름
        userName = headerview.findViewById(R.id.userName);
        userName.setText("");

        //Fragment 선언
        homeFragment = new HomeFragment();
        communitylistFragment = new CommunityListFragment();
        resultListFragment = new ResultListFragment();
        loginFragment = new LoginFragment();
        singupFragment = new SingupFragment();
        levelChoiceFragment = new LevelChoiceFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragmentManager.popBackStack();

        transaction.add(R.id.container, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        //로그인-회원가입 레이아웃
        login_register_ll = headerview.findViewById(R.id.login_register_ll);
        //회원정보, 로그아웃 레이아웃
        logout_ll = headerview.findViewById(R.id.logout_ll);

        if(_userName == null || _userName.equals("")){
            login_register_ll.setVisibility(View.VISIBLE);
            logout_ll.setVisibility(View.GONE);
        }else{
            login_register_ll.setVisibility(View.GONE);
            logout_ll.setVisibility(View.VISIBLE);
            userName.setText(_userName);
        }

        //로그인 버튼
        login_btn = headerview.findViewById(R.id.login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(loginFragment);
            }
        });
        //로그아웃 버튼
        logout_btn = headerview.findViewById(R.id.logout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _userName = "";
                finish();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                Toast.makeText(getBaseContext(), "로그아웃되었습니다", Toast.LENGTH_LONG).show();
            }
        });
        //회원가입 버튼
        register_btn = headerview.findViewById(R.id.register);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(singupFragment);
            }
        });
    }

    //뒤로가기 버튼 눌렀을 때
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Fragment now_fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(now_fragment != homeFragment){
            replaceFragment(homeFragment);
        } else{
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("종료")
                    .setMessage("종료하시겠습니까?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    //좌측 메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //좌측 메뉴 선택시
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

    //좌측 메뉴 선택시 화면 전환
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            replaceFragment(homeFragment);
        } else if (id == R.id.nav_test) {
            replaceFragment(levelChoiceFragment);
        } else if (id == R.id.nav_community) {
            replaceFragment(communitylistFragment);
        } else if (id == R.id.nav_record){
            replaceFragment(resultListFragment);
        }
        return true;
    }

    //화면 전환 함수
    public void replaceFragment(Fragment fragment){
        Fragment now_fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if(now_fragment != fragment){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
