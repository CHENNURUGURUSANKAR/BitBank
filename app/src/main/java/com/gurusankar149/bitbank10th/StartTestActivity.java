package com.gurusankar149.bitbank10th;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.gurusankar149.bitbank10th.Database.home_models;

public class StartTestActivity extends AppCompatActivity {
    TextView catName, testNo, noQuestion, noMarks, timeCount;
    ImageView goBack;
    Button startNow;
    private Dialog progess_dailog;
    private TextView dailog_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);
        catName = findViewById(R.id.cat_name);
        testNo = findViewById(R.id.test_no);
        noQuestion = findViewById(R.id.questions_text);
        noMarks = findViewById(R.id.total_marks);
        timeCount = findViewById(R.id.time_id);
        //back and next or completed
        goBack = findViewById(R.id.go_back);
        startNow = findViewById(R.id.start_now);
        progess_dailog = new Dialog(this);
        progess_dailog.setCancelable(false);
        progess_dailog.setContentView(R.layout.progress_dailog);
        progess_dailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dailog_text = progess_dailog.findViewById(R.id.progess_text);
        dailog_text.setText("Please wait Loading Data ...");
        progess_dailog.show();


        Database.LoadQuestions(new MycompleteListener() {
            @Override
            public void OnSuccess() {
                progess_dailog.dismiss();
                catName.setText(home_models.get(Database.selectedCatIndex).getName());
                testNo.setText(String.valueOf("Test NO:" + Database.selectedTestIndex + 1));
                noQuestion.setText(String.valueOf(Database.qmodel.size()+"\n"+"Questions"));
                noMarks.setText(String.valueOf(Database.qmodel.size()+"\n"+"Marks"));
                timeCount.setText(String.valueOf(Database.testModelList.get(Database.selectedTestIndex).getTime()+"\n"+"Mins"));
            }

            @Override
            public void OnFailure() {
                progess_dailog.dismiss();
                Toast.makeText(StartTestActivity.this, "Fail ", Toast.LENGTH_SHORT).show();

            }

        });
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTestActivity.this.finish();
            }
        });
        startNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QuestionsActivity.class));
                finish();
            }
        });
    }


}