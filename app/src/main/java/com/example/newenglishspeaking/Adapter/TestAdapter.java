package com.example.newenglishspeaking.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newenglishspeaking.Fragment.HomeFragment;
import com.example.newenglishspeaking.Fragment.LoginFragment;
import com.example.newenglishspeaking.Fragment.TestChoiceFragment;
import com.example.newenglishspeaking.MainActivity;
import com.example.newenglishspeaking.Model.TestItem;
import com.example.newenglishspeaking.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.CustomViewHolder> {

    private ArrayList<TestItem> testItem;
    private Context mContext;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("tests");

    public TestAdapter(Context context, ArrayList<TestItem> testItem) {
        this.mContext = context;
        this.testItem = testItem;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView number, testName;
        public ImageView ratingImg1, ratingImg2, ratingImg3, ratingImg4, isTested, delete;

        public CustomViewHolder(View view) {
            super(view);
            number = (TextView) view.findViewById(R.id.testNumber);
            testName = (TextView) view.findViewById(R.id.testName);
            ratingImg1 = (ImageView) view.findViewById(R.id.ratingImg1);
            ratingImg2 = (ImageView) view.findViewById(R.id.ratingImg2);
            ratingImg3 = (ImageView) view.findViewById(R.id.ratingImg3);
            ratingImg4 = (ImageView) view.findViewById(R.id.ratingImg4);
            isTested = (ImageView) view.findViewById(R.id.isTested);
            delete = (ImageView) view.findViewById(R.id.delete);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_testlist, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        final TestItem test = testItem.get(position);
        holder.number.setText(getItemCount() - position +"");    //테스트 번호 설정
        holder.testName.setText(test.getTestName());    //테스트 이름 설정

        //테스트 유무를 확인하고 테스트한 경우 체크 표시, 테스트하지 않은 경우 체크 표시가 사라진다.
        if(test.getIsTested().equals("0")){
            holder.isTested.setVisibility(View.INVISIBLE);
        }else{
            holder.isTested.setVisibility(View.VISIBLE);
        }

        //테스트 레벨에 따라 별표가 나타난다.
        switch (Integer.parseInt(test.getLevel())){
            case 5:
                break;
            case 4:
                holder.ratingImg1.setVisibility(View.INVISIBLE);
                break;
            case 3:
                holder.ratingImg1.setVisibility(View.INVISIBLE);
                holder.ratingImg2.setVisibility(View.INVISIBLE);
                break;
            case 2:
                holder.ratingImg1.setVisibility(View.INVISIBLE);
                holder.ratingImg2.setVisibility(View.INVISIBLE);
                holder.ratingImg3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                holder.ratingImg1.setVisibility(View.INVISIBLE);
                holder.ratingImg2.setVisibility(View.INVISIBLE);
                holder.ratingImg3.setVisibility(View.INVISIBLE);
                holder.ratingImg4.setVisibility(View.INVISIBLE);
        }

        if(MainActivity._userId.equals("admin")){
            holder.delete.setVisibility(View.VISIBLE);
        }else{
            holder.delete.setVisibility(View.INVISIBLE);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(view.getRootView().getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("삭제")
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabase.child(test.getTestNumber()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //삭제되었습니다.
                                        HomeFragment homeFragment = new HomeFragment();
                                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        //목록 클릭시 해당 테스트로 이동
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(MainActivity.userName.getText().toString().equals("")){
                    Toast.makeText(mContext, "로그인 후 이용하세요", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    LoginFragment loginFragment = new LoginFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, loginFragment).commit();
                }else{
                    ArrayList<String> testDatas = new ArrayList<>();
                    testDatas.add((getItemCount() - position) + "");
                    testDatas.add(test.getTestNumber());
                    testDatas.add(test.getTestName());
                    testDatas.add(test.getQ1());
                    testDatas.add(test.getQ2());
                    testDatas.add(test.getQ3());
                    testDatas.add(test.getA1());
                    testDatas.add(test.getA2());
                    testDatas.add(test.getA3());
                    testDatas.add(test.getKq1());
                    testDatas.add(test.getKq2());
                    testDatas.add(test.getKq3());
                    testDatas.add(test.getKa1());
                    testDatas.add(test.getKa2());
                    testDatas.add(test.getKa3());
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("testDatas", testDatas); //테스트 데이터 전달
                    TestChoiceFragment testChoiceFragment = new TestChoiceFragment();
                    testChoiceFragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, testChoiceFragment).commit();
                }
            }
        });
    }

    //목록의 개수 반환
    @Override
    public int getItemCount() {
        return testItem.size();
    }
}
