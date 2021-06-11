
package com.gurusankar149.bitbank10th;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TestActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private TestAdapter adapter;

    private Dialog progess_dailog;
    private TextView dailog_text;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        toolbar = (Toolbar) findViewById(R.id.toolbar_test);
        recyclerView = findViewById(R.id.test_recycler_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(Database.home_models.get(Database.selectedCatIndex).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        // Loadtestdata();
        progess_dailog = new Dialog(this);
        progess_dailog.setCancelable(false);
        progess_dailog.setContentView(R.layout.progress_dailog);
        progess_dailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dailog_text = progess_dailog.findViewById(R.id.progess_text);
        dailog_text.setText("Please wait Loading Data ...");
        progess_dailog.show();

        Database.LoadTestData(new MycompleteListener() {
            @Override
            public void OnSuccess() {
                progess_dailog.dismiss();
                adapter = new TestAdapter(Database.testModelList);
                recyclerView.setAdapter(adapter);
                Log.d("TAG", String.valueOf(Database.selectedCatIndex));
            }

            @Override
            public void OnFailure() {
                progess_dailog.dismiss();
                Toast.makeText(TestActivity.this, "Fail to to Load Data", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            TestActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TestActivity.this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Database.selectedCatIndex=Database.selectedCatIndex;
    }
}