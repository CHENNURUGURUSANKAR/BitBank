package com.gurusankar149.bitbank10th;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static com.gurusankar149.bitbank10th.Database.ANSWERED;
import static com.gurusankar149.bitbank10th.Database.NOTVISITED;
import static com.gurusankar149.bitbank10th.Database.REVIEW;
import static com.gurusankar149.bitbank10th.Database.UNANSWERED;
import static com.gurusankar149.bitbank10th.Database.qmodel;

public class QuestionGridAdapter extends BaseAdapter {
    private int noOfQuestions;
    private Context context;

    public QuestionGridAdapter() {
    }

    public QuestionGridAdapter(Context context, int noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
        this.context = context;
    }

    @Override
    public int getCount() {
        return noOfQuestions;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_questions_grid_items, parent, false);

        } else {
            view = convertView;
        }
        TextView ansState = view.findViewById(R.id.ans_state);
        ansState.setText(String.valueOf(position + 1));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof QuestionsActivity) {
                    ((QuestionsActivity) context).goToQuestion(position);
                }

            }
        });
        switch (qmodel.get(position).getStatus()) {
            case NOTVISITED:
                ansState.setBackgroundResource(R.drawable.gray);
                //ansState.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.gray)));
                break;
            case UNANSWERED:
                ansState.setBackgroundResource(R.drawable.red);
                // ansState.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.red)));
                break;
            case ANSWERED:
                ansState.setBackgroundResource(R.drawable.green);
                //ansState.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.green)));
                break;
            case REVIEW:
                ansState.setBackgroundResource(R.drawable.blue);
                // ansState.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.blue)));
                break;
            default:
                break;
        }
        return view;

    }
}
