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
import com.example.newenglishspeaking.Fragment.ResultFragment;
import com.example.newenglishspeaking.Fragment.TestChoiceFragment;
import com.example.newenglishspeaking.MainActivity;
import com.example.newenglishspeaking.Model.ResultItem;
import com.example.newenglishspeaking.Model.TestItem;
import com.example.newenglishspeaking.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.CustomViewHolder>{

    private ArrayList<ResultItem> resultItem;
    private Context mContext;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("results");

    public ResultAdapter(Context context, ArrayList<ResultItem> resultItem) {
        this.mContext = context;
        this.resultItem = resultItem;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView resultNumber, testName, resultDate;
        public ImageView delete;

        public CustomViewHolder(View view) {
            super(view);
            resultNumber = view.findViewById(R.id.resultNumber);
            testName = (TextView) view.findViewById(R.id.testName);
            resultDate = (TextView) view.findViewById(R.id.resultDate);
            delete = (ImageView) view.findViewById(R.id.delete);
        }
    }

    @Override
    public ResultAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_resultlist, parent, false);
        return new ResultAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ResultAdapter.CustomViewHolder holder, final int position) {
        final ResultItem result = resultItem.get(position);
        holder.resultNumber.setText(getItemCount() - position +"");    //테스트 번호 설정
        holder.testName.setText(result.getTestName());    //테스트 이름 설정
        holder.resultDate.setText(result.getDate());
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
                                mDatabase.child(result.getResultKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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
                ArrayList<String> resultDatas = new ArrayList<String>();
                resultDatas.clear();
                resultDatas.add(result.getA1());
                resultDatas.add(result.getA2());
                resultDatas.add(result.getA3());
                resultDatas.add(result.getMa1());
                resultDatas.add(result.getMa2());
                resultDatas.add(result.getMa3());
                resultDatas.add(result.getQ1());
                resultDatas.add(result.getQ2());
                resultDatas.add(result.getQ3());
                resultDatas.add(result.getSc1());
                resultDatas.add(result.getSc2());
                resultDatas.add(result.getSc3());
                resultDatas.add(result.getKa1());
                resultDatas.add(result.getKa2());
                resultDatas.add(result.getKa3());
                resultDatas.add(result.getKq1());
                resultDatas.add(result.getKq2());
                resultDatas.add(result.getKq3());
                resultDatas.add(result.getDate());
                resultDatas.add(result.getTestName());

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("resultDatas",resultDatas); //결과 데이터 전달
                ResultFragment resultFragment = new ResultFragment();
                resultFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, resultFragment).commit();
            }
        });
    }

    //목록의 개수 반환
    @Override
    public int getItemCount() {
        return resultItem.size();
    }
}
