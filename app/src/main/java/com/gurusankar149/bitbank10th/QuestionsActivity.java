package com.gurusankar149.bitbank10th;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.gurusankar149.bitbank10th.Database.ANSWERED;
import static com.gurusankar149.bitbank10th.Database.NOTVISITED;
import static com.gurusankar149.bitbank10th.Database.REVIEW;
import static com.gurusankar149.bitbank10th.Database.UNANSWERED;
import static com.gurusankar149.bitbank10th.Database.currect_ans;
import static com.gurusankar149.bitbank10th.Database.qmodel;
import static com.gurusankar149.bitbank10th.Database.selectedCatIndex;
import static com.gurusankar149.bitbank10th.Database.selectedQuestionIndex;
import static com.gurusankar149.bitbank10th.Database.selectedTestIndex;
import static com.gurusankar149.bitbank10th.Database.testModelList;
import static com.gurusankar149.bitbank10th.Database.worng_ans;

public class QuestionsActivity extends AppCompatActivity {
    private RecyclerView qRecyclerView;
    private TextView questionNo, testId, timeRemaing;
    private Button submit, clearSelection, markForReview;
    private ImageView bookmark, listQuestions, goBack, goNext, close_question_info, addbookmark;
    private QuestionsAdapter questionsAdapter;
    private int questionId = 0;
    private DrawerLayout drawerLayout;
    private GridView questionInfoGridview;
    private QuestionGridAdapter questionGridAdapter;
    private Button No, Yes;
    private Dialog progess_dailog, whilecheckingresult;
    private TextView dailog_text, answeredquestion, unanswredquestions;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String UID = user.getUid();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private List<RankHolderModel> rankHolderModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_info_layout);

        qRecyclerView = findViewById(R.id.qRecyclerVire);
        questionNo = findViewById(R.id.questionNo);
        testId = findViewById(R.id.testCat);
        timeRemaing = findViewById(R.id.testTimeRemaing);
        submit = findViewById(R.id.submitTest);
        clearSelection = findViewById(R.id.clearSelection);
        markForReview = findViewById(R.id.markForReview);
        bookmark = findViewById(R.id.bookmarkQuestion);
        listQuestions = findViewById(R.id.listQuestions);
        goBack = findViewById(R.id.goBack);
        goNext = findViewById(R.id.go_next);
        drawerLayout = findViewById(R.id.drawerLayout);
        close_question_info = findViewById(R.id.close_question_info);
        questionInfoGridview = findViewById(R.id.gridview_question_info);
        addbookmark = findViewById(R.id.addbokmark);
        //gridview implementation
        questionGridAdapter = new QuestionGridAdapter(this,qmodel.size());
        questionInfoGridview.setAdapter(questionGridAdapter);
//comfim layout
        progess_dailog = new Dialog(this);
        progess_dailog.setCancelable(true);
        progess_dailog.setContentView(R.layout.confirmsubmistion);
        progess_dailog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //prograss dailog to while checking result
        whilecheckingresult = new Dialog(this);
        whilecheckingresult.setCancelable(true);
        whilecheckingresult.setContentView(R.layout.progress_dailog);
        whilecheckingresult.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dailog_text = whilecheckingresult.findViewById(R.id.progess_text);
        dailog_text.setText("Please wait cheching result");
        No = progess_dailog.findViewById(R.id.confirm_no);
        Yes = progess_dailog.findViewById(R.id.confirm_yes);
        answeredquestion = progess_dailog.findViewById(R.id.ans_count);
        unanswredquestions = progess_dailog.findViewById(R.id.unans_count);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        qRecyclerView.setLayoutManager(linearLayoutManager);
        questionsAdapter = new QuestionsAdapter(qmodel);
        qRecyclerView.setAdapter(questionsAdapter);
        setSnaphelper();
        questionNo.setText("1/" + String.valueOf(qmodel.size()));
        testId.setText(String.valueOf(Database.home_models.get(Database.selectedCatIndex).getName()));
        BackandNext();
        startTimmer();
        clearSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qmodel.get(questionId).setSeleteAns(-1);
                qmodel.get(questionId).setStatus(UNANSWERED);
                questionsAdapter.notifyDataSetChanged();
            }
        });
        listQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                questionGridAdapter.notifyDataSetChanged();
                if (!(drawerLayout.isDrawerOpen(GravityCompat.END))) {
                    questionGridAdapter.notifyDataSetChanged();
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });
        close_question_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionGridAdapter.notifyDataSetChanged();
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int anscount = 0;
                int unanscount = 0;
                for (int count = 1; count <= qmodel.size(); count++) {
                    if (qmodel.get(count - 1).getStatus() == ANSWERED) {
                        anscount = anscount + 1;
                        answeredquestion.setText(String.valueOf(anscount) + "" + " Answered");
                        answeredquestion.setTextColor(getResources().getColor(R.color.green));
                    } else if (qmodel.get(count - 1).getStatus() == REVIEW) {
                        anscount = anscount + 1;
                        answeredquestion.setText(String.valueOf(anscount) + "" + " Answered");
                        answeredquestion.setTextColor(getResources().getColor(R.color.green));

                    } else {
                        unanscount = unanscount + 1;
                        unanswredquestions.setText(String.valueOf(unanscount) + "" + " Not Answered");
                        unanswredquestions.setTextColor(getResources().getColor(R.color.red));
                    }
                }
                progess_dailog.show();
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progess_dailog.dismiss();
                Toast.makeText(QuestionsActivity.this, "Continoue the exam", Toast.LENGTH_SHORT).show();
            }
        });
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progess_dailog.dismiss();
                whilecheckingresult.show();
                for (int i = 1; i <= qmodel.size(); i++) {
                    if (qmodel.get(i - 1).getStatus() == ANSWERED) {
                        if (qmodel.get(i - 1).ans == qmodel.get(i - 1).getSeleteAns()) {
                            currect_ans = currect_ans + 1;
                            Log.d("Check", String.valueOf(i) + "Currect Ans");

                        } else {
                            worng_ans = worng_ans + 1;
                            Log.d("Check", String.valueOf(i) + "Worng Ans");
                        }

                    } else if (qmodel.get(i - 1).getStatus() == UNANSWERED) {
                        worng_ans = worng_ans + 1;
                        Log.d("Check", String.valueOf(i) + "Un Ans");
                    } else if (qmodel.get(i - 1).getStatus() == REVIEW) {
                        if (qmodel.get(i - 1).ans == qmodel.get(i - 1).getSeleteAns()) {
                            currect_ans = currect_ans + 1;
                            Log.d("Check", String.valueOf(i) + "Currect Ans Reviewed");
                        } else {
                            worng_ans = worng_ans + 1;
                            Log.d("Check", String.valueOf(i) + "Worng Ans Reviewed");
                        }

                    } else {
                        worng_ans = worng_ans + 1;
                        Log.d("Check", String.valueOf(i) + "Not Yet Viwed the Question");
                    }
                }
                storeResultData();
                whilecheckingresult.dismiss();
                startActivity(new Intent(getApplicationContext(), ResultActivity.class));
                finish();
            }
        });
    }

    private void storeResultData() {
        rankHolderModelList.clear();
        String DOC_ID = firebaseFirestore.collection("RESULT").document().getId();
        String CAT_ID = Database.home_models.get(selectedCatIndex).getName();
        String TEST_ID = testModelList.get(selectedTestIndex).getTestID();
        String USER_NAME = user.getDisplayName();
        Map<String, Object> setRankholders = new ArrayMap<>();
        setRankholders.put("DOC_ID", DOC_ID);
        setRankholders.put("CAT_ID", CAT_ID);
        setRankholders.put("TEST_ID", TEST_ID);
        setRankholders.put("USER_NAME", USER_NAME);
        setRankholders.put("MARKS",currect_ans);
        setRankholders.put("UID",UID);

        firebaseFirestore.collection("RESULT").document(DOC_ID).set(setRankholders).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               /* Map<String, Object> setRankHoldersDoc = new ArrayMap<>();
                setRankHoldersDoc.put("COUNT",Database.rankHolderModels.size()+1);
                setRankHoldersDoc.put("RANK"+String.valueOf(Database.rankHolderModels.size()+1)+"_ID",DOC_ID);
                firebaseFirestore.collection("RESULT").document("RANK_HOLDERS").set(setRankHoldersDoc).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(QuestionsActivity.this, "success", Toast.LENGTH_SHORT).show();
                     // rankHolderModelList.add(new RankHolderModel(DOC_ID,CAT_ID,TEST_ID,USER_NAME,currect_ans));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QuestionsActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });*/
                Toast.makeText(QuestionsActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(QuestionsActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void goToQuestion(int position)
    {
        qRecyclerView.smoothScrollToPosition(position);
        if (drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }
    private void startTimmer() {
        Long time = (long) ((testModelList.get(selectedTestIndex).getTime()) * 60 * 1000);
        CountDownTimer timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long remTime) {
                @SuppressLint("DefaultLocale") String Time = String.format("%02d : %02d" + " Mins"
                        , TimeUnit.MILLISECONDS.toMinutes(remTime),
                        TimeUnit.MILLISECONDS.toSeconds(remTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remTime))
                );
                timeRemaing.setText(Time);
            }

            @Override
            public void onFinish() {
                QuestionsActivity.this.finish();

            }
        };
        timer.start();
    }

    private void BackandNext() {
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionId > 0) {
                    qRecyclerView.smoothScrollToPosition(questionId - 1);
                } else {
                    Toast.makeText(QuestionsActivity.this, "this is first question", Toast.LENGTH_SHORT).show();
                }

            }
        });
        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addbookmark.getVisibility() != View.VISIBLE) {
                    qmodel.get(questionId).setStatus(REVIEW);
                    addbookmark.setVisibility(View.VISIBLE);

                } else {
                    addbookmark.setVisibility(View.GONE);
                    if (qmodel.get(questionId).getStatus() != -1) {
                        qmodel.get(questionId).setStatus(ANSWERED);
                    } else {
                        qmodel.get(questionId).setStatus(UNANSWERED);
                    }

                }
            }
        });
        markForReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionGridAdapter.notifyDataSetChanged();
                if (qmodel.get(questionId).getStatus() != REVIEW) {
                    addbookmark.setVisibility(View.VISIBLE);
                    qmodel.get(questionId).setStatus(REVIEW);
                    Log.d("REVIEW", String.valueOf(questionId) + "mark for Review ");

                } else {
                    addbookmark.setVisibility(View.GONE);
                    if (qmodel.get(questionId).getStatus() != -1) {
                        qmodel.get(questionId).setStatus(ANSWERED);
                        Log.d("REVIEW", String.valueOf(questionId) + "mark for Review ANSWERED");
                    } else {
                        qmodel.get(questionId).setStatus(UNANSWERED);
                        Log.d("REVIEW", String.valueOf(questionId) + "mark for Review UNANSWERED");

                    }
                }

            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionGridAdapter.notifyDataSetChanged();
                if (qmodel.get(questionId).getStatus() != REVIEW) {
                    addbookmark.setVisibility(View.VISIBLE);
                    qmodel.get(questionId).setStatus(REVIEW);
                    Log.d("REVIEW", String.valueOf(questionId) + "mark for Review ");

                } else {
                    addbookmark.setVisibility(View.GONE);
                    if (qmodel.get(questionId).getStatus() != -1) {
                        qmodel.get(questionId).setStatus(ANSWERED);
                        Log.d("REVIEW", String.valueOf(questionId) + "mark for Review ANSWERED");
                    } else {
                        qmodel.get(questionId).setStatus(UNANSWERED);
                        Log.d("REVIEW", String.valueOf(questionId) + "mark for Review UNANSWERED");

                    }
                }
            }
        });
    }

    private void setSnaphelper() {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(qRecyclerView);
        qRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                questionGridAdapter.notifyDataSetChanged();
                View view = snapHelper.findSnapView(qRecyclerView.getLayoutManager());
                questionId = qRecyclerView.getLayoutManager().getPosition(view);
                selectedQuestionIndex = questionId;
                if (qmodel.get(questionId).getStatus() == NOTVISITED) {
                    qmodel.get(questionId).setStatus(UNANSWERED);
                }
                if (qmodel.get(questionId).getStatus() == REVIEW) {
                    addbookmark.setVisibility(View.VISIBLE);
                } else {
                    addbookmark.setVisibility(View.GONE);
                }
                questionNo.setText(String.valueOf(questionId + 1 + "/" + qmodel.size()));
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        currect_ans = 0;
    }
}