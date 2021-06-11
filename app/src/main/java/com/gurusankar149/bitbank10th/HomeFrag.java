package com.gurusankar149.bitbank10th;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class HomeFrag extends Fragment {
    private GridView home_grid_view;

    private Dialog progess_dailog;
    private TextView dailog_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progess_dailog = new Dialog(getContext());
        progess_dailog.setCancelable(false);
        progess_dailog.setContentView(R.layout.progress_dailog);
        progess_dailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dailog_text = progess_dailog.findViewById(R.id.progess_text);
        dailog_text.setText("Please wait Data Loading ...");
        progess_dailog.show();
        home_grid_view = view.findViewById(R.id.home_grid_view);
        // loadlist();

        Database.Loadcat(new MycompleteListener() {
            @Override
            public void OnSuccess() {
                HomeAdapter adapter = new HomeAdapter(Database.home_models);
                home_grid_view.setAdapter(adapter);
                progess_dailog.dismiss();
            }

            @Override
            public void OnFailure() {
                progess_dailog.dismiss();
                Toast.makeText(getContext(), "disable to load data", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}