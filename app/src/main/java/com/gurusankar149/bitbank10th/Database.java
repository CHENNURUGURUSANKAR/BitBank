
package com.gurusankar149.bitbank10th;

import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static List<Home_model> home_models = new ArrayList<>();
    public static List<TestModel> testModelList = new ArrayList<>();
    public static List<QModel> qmodel = new ArrayList<>();
    public static List<RankHolderModel> rankHolderModels = new ArrayList<>();
    public static int selectedCatIndex = 0;
    public static int selectedTestIndex = 0;
    public static int selectedQuestionIndex = 0;
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
     String UID=user.getUid();
    public static final int NOTVISITED = 0;
    public static final int UNANSWERED = 1;
    public static final int ANSWERED = 2;
    public static final int REVIEW = 3;
    public static int selectedoption = 0;
    public static int currect_ans = 0;
    public static int worng_ans = 0;

    public static void StoreData(String email, String name, final MycompleteListener mycompleteListener) {
        Map<String, Object> user = new HashMap<>();
        user.put(" EMAIL_ID", email);
        user.put("NAME", name);
        user.put("TOTAL_SCORE", 0);
        DocumentReference userdoc = db.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        WriteBatch batch = db.batch();
        batch.set(userdoc, user);
        DocumentReference doccount = db.collection("USERS").document("TOTAL_USERS");
        batch.update(doccount, "COUNT", FieldValue.increment(1));
        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mycompleteListener.OnSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mycompleteListener.OnFailure();
                    }
                });
    }

    //loading categories
    public static void Loadcat(MycompleteListener mycompleteListener) {
        home_models.clear();
        db.collection("QUIZ").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Map<String, QueryDocumentSnapshot> doclist = new ArrayMap<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    doclist.put(doc.getId(), doc);
                }
                QueryDocumentSnapshot catlistdoc = doclist.get("Categories");
                Long catcount = catlistdoc.getLong("COUNT");
                for (int i = 1; i <= catcount; i++) {
                    String catid = catlistdoc.getString("CAT" + String.valueOf(i) + "_ID");
                    QueryDocumentSnapshot catdoc = doclist.get(catid);
                    int noOftets = (int) catdoc.getLong("NO_OF_TESTS").intValue();
                    String catname = catdoc.getString("NAME");
                    home_models.add(new Home_model(catid, catname, noOftets));
                }
                mycompleteListener.OnSuccess();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mycompleteListener.OnFailure();
            }
        });
    }

    public static void LoadTestData(MycompleteListener mycompleteListener) {
        testModelList.clear();
        db.collection("QUIZ").document(String.valueOf(home_models.get(selectedCatIndex).getDocID())).
                collection("TEST_LIST").document("TEST_INFO")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                int onOfTests = home_models.get(selectedCatIndex).getNumberoftests();
                Log.d("onOfTests", String.valueOf(selectedCatIndex));
                Long j = documentSnapshot.getLong("COUNT");
                Log.d("Count", String.valueOf(j));
                for (int i = 1; i <= j; i++) {
                    testModelList.add(new TestModel(documentSnapshot.getString("TEST" + String.valueOf(i) + "_ID")
                            , qmodel.size(),
                            documentSnapshot.getLong("TEST" + String.valueOf(i) + "_TIME").intValue()
                    ));
                }
                mycompleteListener.OnSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mycompleteListener.OnFailure();
            }
        });
    }

    public static void LoadQuestions(MycompleteListener mycompleteListener) {
        qmodel.clear();
        db.collection("Questions").whereEqualTo("CAT_ID", home_models.get(selectedCatIndex).getDocID()).whereEqualTo("TEST_ID", testModelList.get(selectedTestIndex).getTestID()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    qmodel.add(new QModel(
                            doc.getString("Question"),
                            doc.getString("A"),
                            doc.getString("B"),
                            doc.getString("C"),
                            doc.getString("D"),
                            doc.getLong("Ans").intValue(), -1, NOTVISITED
                    ));
                }
                mycompleteListener.OnSuccess();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mycompleteListener.OnFailure();
            }
        });
    }

    public static void LoadRank(MycompleteListener mycompleteListener) {
        rankHolderModels.clear();
     /*   db.collection("RESULT").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//FROM HER WE NEED TO EDIT THE CODE
                Map<String, QueryDocumentSnapshot> RANKlIST = new ArrayMap<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    RANKlIST.put(doc.getId(), doc);
                }
                QueryDocumentSnapshot RANK_HOLDERS = RANKlIST.get("RANK_HOLDERS");
                Long RankHolderCount = RANK_HOLDERS.getLong("COUNT");
                for (int i = 1; i <= RankHolderCount; i++) {
                    String Rankid = RANK_HOLDERS.getString("RANK" + String.valueOf(i) + "_ID");
                    QueryDocumentSnapshot RankDoc = RANKlIST.get(Rankid);
                    String cat_name=RankDoc.getString("CAT_ID");
                    String text_name=RankDoc.getString("TEST_ID");
                    int marks=RankDoc.getLong("MARKS").intValue();
                    String user_name=RankDoc.getString("USER_NAME");
                    rankHolderModels.add(new RankHolderModel(Rankid, cat_name, text_name,user_name,marks));
                }


                mycompleteListener.OnSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mycompleteListener.OnFailure();
            }
        });*/
        db.collection("RESULT").orderBy("MARKS", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : documentSnapshots) {
                    Log.d("TAG", String.valueOf(snapshot.getData()));
                    String CAT_NAME = snapshot.getString("CAT_ID");
                    String TEST_NAME = snapshot.getString("TEST_ID");
                    String USER_NAME = snapshot.getString("USER_NAME");
                    String DOC_ID = snapshot.getString("DOC_ID");
                    int MARKS = snapshot.getLong("MARKS").intValue();
                    rankHolderModels.add(new RankHolderModel(DOC_ID, CAT_NAME, TEST_NAME, USER_NAME, MARKS));
                }
                mycompleteListener.OnSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mycompleteListener.OnFailure();
            }
        });
    }
    public static void  LoadProfile(MycompleteListener mycompleteListener)
    {
        rankHolderModels.clear();

        FirebaseUser user=mAuth.getCurrentUser();
        String UID=user.getUid();
        db.collection("RESULT").whereEqualTo("UID",UID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : documentSnapshots) {
                    Log.d("TAG", String.valueOf(snapshot.getData()));
                    String CAT_NAME = snapshot.getString("CAT_ID");
                    String TEST_NAME = snapshot.getString("TEST_ID");
                    String USER_NAME = snapshot.getString("USER_NAME");
                    String DOC_ID = snapshot.getString("DOC_ID");
                    int MARKS = snapshot.getLong("MARKS").intValue();
                    rankHolderModels.add(new RankHolderModel(DOC_ID, CAT_NAME, TEST_NAME, USER_NAME, MARKS));
                }

                mycompleteListener.OnSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mycompleteListener.OnFailure();
            }
        });
    }
}
