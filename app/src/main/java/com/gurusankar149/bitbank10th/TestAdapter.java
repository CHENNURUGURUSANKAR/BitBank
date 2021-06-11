package com.gurusankar149.bitbank10th;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private List<TestModel> testModelList;

    public TestAdapter() {
    }

    public TestAdapter(List<TestModel> testModelList) {
        this.testModelList = testModelList;
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int position) {
        int progess = testModelList.get(position).getTopscore();
        holder.topscore.setText(String.valueOf("Test" + " " + testModelList.get(position).getTestID()));
        holder.percent.setText(String.valueOf(Database.qmodel.size()) + "Marks");

        holder.progressBar.setProgress(progess);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.selectedTestIndex = position;
                Intent intent = new Intent(v.getContext(), StartTestActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView topscore, percent;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topscore = itemView.findViewById(R.id.topscore);
            percent = itemView.findViewById(R.id.percent);
            progressBar = itemView.findViewById(R.id.test_progressbar);

        }
    }
}
