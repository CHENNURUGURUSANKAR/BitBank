package com.gurusankar149.bitbank10th;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class HomeAdapter extends BaseAdapter {
    private List<Home_model> home_models;

    public HomeAdapter() {
    }

    public HomeAdapter(List<Home_model> home_models) {
        this.home_models = home_models;
    }

    @Override
    public int getCount() {
        return home_models.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items,parent,false);

        } else {
            view=convertView;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.selectedCatIndex=position;
                Intent intent=new Intent(v.getContext(),TestActivity.class);
//                intent.putExtra("ActivPosi",position);
                v.getContext().startActivity(intent);
            }
        });

        TextView year=view.findViewById(R.id.year_of_exam);
        TextView no_questions=view.findViewById(R.id.no_questions);
        year.setText(home_models.get(position).getName());
        no_questions.setText(String.valueOf(home_models.get(position).getNumberoftests()+" "+"paper"));
        return view;
    }
}
