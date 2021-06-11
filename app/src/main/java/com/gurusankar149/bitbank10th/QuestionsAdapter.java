package com.gurusankar149.bitbank10th;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.gurusankar149.bitbank10th.Database.ANSWERED;
import static com.gurusankar149.bitbank10th.Database.REVIEW;
import static com.gurusankar149.bitbank10th.Database.UNANSWERED;
import static com.gurusankar149.bitbank10th.Database.qmodel;
import static com.gurusankar149.bitbank10th.Database.selectedCatIndex;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<QModel> questionsmodel;

    public QuestionsAdapter() {
    }

    public QuestionsAdapter(List<QModel> questionsmodel) {
        this.questionsmodel = questionsmodel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.question.setText(questionsmodel.get(position).getQuestion());
        holder.optionA.setText(questionsmodel.get(position).getOptionA());
        holder.optionB.setText(questionsmodel.get(position).getOptionB());
        holder.optionC.setText(questionsmodel.get(position).getOptionC());
        holder.optionD.setText(questionsmodel.get(position).getOptionD());
        //options selected
        selectedCatIndex=selectedCatIndex;
        holder.setdefault(holder.optionA, 1, position);
        holder.setdefault(holder.optionB, 2, position);
        holder.setdefault(holder.optionC, 3, position);
        holder.setdefault(holder.optionD, 4, position);
        holder.optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.selectedoption=1;
                holder.selectOption(holder.optionA, 1, position);
            }
        });
        holder.optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database.selectedoption = 2;
                holder.selectOption(holder.optionB, 2, position);
            }
        });
        holder.optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.selectedoption = 3;
                holder.selectOption(holder.optionC, 3, position);

            }
        });
        holder.optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.selectedoption = 4;
                holder.selectOption(holder.optionD, 4, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return questionsmodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private Button optionA, optionB, optionC, optionD, previousselectedBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question_question_list_item);
            optionA = itemView.findViewById(R.id.option_A);
            optionB = itemView.findViewById(R.id.option_B);
            optionC = itemView.findViewById(R.id.option_C);
            optionD = itemView.findViewById(R.id.option_D);
            previousselectedBtn = null;
        }

        private void selectOption(Button btn, int optionNo, int position) {
            if (previousselectedBtn == null) {
                btn.setBackgroundResource(R.drawable.selected_btn);
                qmodel.get(position).setSeleteAns(optionNo);
                checkState(position, ANSWERED);
                previousselectedBtn = btn;
            } else {
                if (previousselectedBtn.getId() == btn.getId()) {
                    btn.setBackgroundResource(R.drawable.unselected_btn);
                    qmodel.get(position).setSeleteAns(-1);
                    checkState(position, UNANSWERED);
                    previousselectedBtn = null;

                } else {
                    previousselectedBtn.setBackgroundResource(R.drawable.unselected_btn);
                    btn.setBackgroundResource(R.drawable.selected_btn);
                    qmodel.get(position).setSeleteAns(optionNo);
                    checkState(position, ANSWERED);
                    previousselectedBtn = btn;

                }
            }

        }

        private void checkState(int position, int state) {
            if (qmodel.get(position).getStatus() != REVIEW) {
                qmodel.get(position).setStatus(state);
            }


        }

        public void setdefault(Button btn, int i, int position) {
            if (qmodel.get(position).getSeleteAns() == i) {
                btn.setBackgroundResource(R.drawable.selected_btn);
            } else {
                btn.setBackgroundResource(R.drawable.unselected_btn);
            }
        }
    }

}
