package com.gurusankar149.bitbank10th;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CheckAnsAdapter extends RecyclerView.Adapter<CheckAnsAdapter.ViewHloder> {
    List<QModel> qModelList;

    public CheckAnsAdapter() {
    }

    public CheckAnsAdapter(List<QModel> qModelList) {
        this.qModelList = qModelList;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHloder holder, int position) {
        holder.question_text.setText(qModelList.get(position).getQuestion());
        holder.setAns(position);
        holder.setCurrectAns(position);
      holder.setimg(position);
      holder.question_no.setText(String.valueOf(position+1));

    }


    @NonNull
    @Override
    public ViewHloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_ans_result_item_model, parent, false);
        return new ViewHloder(view);
    }

    ;

    @Override
    public int getItemCount() {
        return qModelList.size();
    }

    public class ViewHloder extends RecyclerView.ViewHolder {
        TextView question_text, anstext, currect_anstext,question_no;
        ImageView question_status;

        public ViewHloder(@NonNull View itemView) {
            super(itemView);
            question_text = itemView.findViewById(R.id.check_result_question);
            anstext = itemView.findViewById(R.id.check_result_SelectedAns);
            currect_anstext = itemView.findViewById(R.id.check_result_currectAns);
            question_no=itemView.findViewById(R.id.check_ans_question_number);
            question_status=itemView.findViewById(R.id.ans_status_check_ans);


        }

        public void setCurrectAns(int position) {
            if (qModelList.get(position).getAns()==1){
                currect_anstext.setText(qModelList.get(position).getOptionA());
            }
            else  if (qModelList.get(position).getAns()==2){
                currect_anstext.setText(qModelList.get(position).getOptionB());
            }
            else  if (qModelList.get(position).getAns()==3){
                currect_anstext.setText(qModelList.get(position).getOptionC());
            }
            else{
                currect_anstext.setText(qModelList.get(position).getOptionD());
            }

        }

        public void setAns(int position) {

            if (qModelList.get(position).getSeleteAns()==1){
                anstext.setText(qModelList.get(position).getOptionA());
            }
            else  if (qModelList.get(position).getSeleteAns()==2){
                anstext.setText(qModelList.get(position).getOptionB());
            }
            else  if (qModelList.get(position).getSeleteAns()==3){
                anstext.setText(qModelList.get(position).getOptionC());
            }
            else if (qModelList.get(position).getSeleteAns()==4){
                anstext.setText(qModelList.get(position).getOptionD());
            }
            else {
                anstext.setText("NOT Answered");
            }
        }

        public void setimg(int position) {
            if (qModelList.get(position).ans==qModelList.get(position).seleteAns)
            {
                question_status.setBackgroundResource(R.drawable.currect);
                anstext.setTextColor(Color.parseColor("#1CB901"));
            }
            else {
               question_status.setBackgroundResource(R.drawable.worng);
                anstext.setTextColor(Color.parseColor("#FF0006"));
            }
        }
    }
}
