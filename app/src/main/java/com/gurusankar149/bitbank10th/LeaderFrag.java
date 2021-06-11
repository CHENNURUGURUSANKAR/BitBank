package com.gurusankar149.bitbank10th;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class LeaderFrag extends Fragment {
    RecyclerView recyclerView;
    LeaderBoardAdapter leaderBoardAdapter;
    public static List<RankHolderModel> rankHolderModels = new ArrayList<>();
    FirebaseFirestore firebaseStore = FirebaseFirestore.getInstance();
    private Dialog progess_dailog;
    private TextView dailog_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leader, container, false);
        recyclerView = view.findViewById(R.id.leader_board_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        progess_dailog = new Dialog(view.getContext());
        progess_dailog.setCancelable(false);
        progess_dailog.setContentView(R.layout.progress_dailog);
        progess_dailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dailog_text = progess_dailog.findViewById(R.id.progess_text);
        dailog_text.setText("Please wait Data  Loading...");
        progess_dailog.show();
        Database.LoadRank(new MycompleteListener() {
            @Override
            public void OnSuccess() {
                leaderBoardAdapter=new LeaderBoardAdapter(Database.rankHolderModels);
                recyclerView.setAdapter(leaderBoardAdapter);
                progess_dailog.dismiss();
                Toast.makeText(view.getContext(), "success to retrive", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnFailure() {
                progess_dailog.dismiss();
                Toast.makeText(view.getContext(), "Fail to retrive", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}