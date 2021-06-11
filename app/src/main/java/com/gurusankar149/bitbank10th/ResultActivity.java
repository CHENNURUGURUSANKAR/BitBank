
package com.gurusankar149.bitbank10th;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.gurusankar149.bitbank10th.Database.qmodel;

public class ResultActivity extends AppCompatActivity {
    private TextView resultCatname, resultText;
    private Button praticeMore, ResultCheck_ans;/*
    RecyclerView recyclerView;
    CheckAnsAdapter checkAnsAdapter;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultCatname = findViewById(R.id.Result_CatName_text);
        resultText = findViewById(R.id.Result_CurrectAns_count);
        praticeMore = findViewById(R.id.result_practice_more);
        praticeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        ResultCheck_ans = findViewById(R.id.result_checkAns);
        ResultCheck_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CheckAnswers.class));
                finish();
            }
        });
        resultCatname.setText(Database.home_models.get(Database.selectedCatIndex).getName());
        resultText.setText(String.valueOf(Database.currect_ans) + "/" + String.valueOf(qmodel.size()));

       /* checkAnsAdapter = new CheckAnsAdapter(qmodel);
        recyclerView = findViewById(R.id.result_RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(checkAnsAdapter);
*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ResultActivity.this.finish();
    }
}