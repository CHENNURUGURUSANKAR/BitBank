package com.gurusankar149.bitbank10th;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class CheckAnswers extends AppCompatActivity {
    RecyclerView recyclerView;
    CheckAnsAdapter chechAnsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_answers);
        recyclerView=findViewById(R.id.check_result_recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        chechAnsAdapter=new CheckAnsAdapter(Database.qmodel);
        recyclerView.setAdapter(chechAnsAdapter);
    }
}