package com.gurusankar149.bitbank10th;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {
    List<RankHolderModel> rankHolderModels;

    public LeaderBoardAdapter() {
    }

    public LeaderBoardAdapter(List<RankHolderModel> rankHolderModels) {
        this.rankHolderModels = rankHolderModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_board_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rank_hlder_name.setText(rankHolderModels.get(position).getUsername());
    holder.rank_number.setText(String.valueOf(position+1));
    holder.cat_name.setText(rankHolderModels.get(position).getCat_id());
    holder.test_name.setText(rankHolderModels.get(position).getTest_id()+"-TEST");
    holder.marks.setText(rankHolderModels.get(position).getMarks()+""+" / 160"+"MARKS");


    }

    @Override
    public int getItemCount() {
        return rankHolderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank_number, rank_hlder_name, cat_name, test_name, marks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank_number = itemView.findViewById(R.id.rank_numer);
            rank_hlder_name = itemView.findViewById(R.id.rankholder_name);
            cat_name = itemView.findViewById(R.id.rank_cat_name);
            test_name = itemView.findViewById(R.id.rank_test_name);
            marks = itemView.findViewById(R.id.rank_marks);
        }
    }
}
