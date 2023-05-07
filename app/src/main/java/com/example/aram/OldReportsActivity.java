package com.example.aram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aram.models.Report;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class OldReportsActivity extends AppCompatActivity {
    private static final int SECRET_KEY = 99;
    private final String LOG_TAG = OldReportsActivity.class.getName();
    private LinearLayout container;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private FirebaseUser user;
    private List<Report> reportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_reports);
        user = FirebaseAuth.getInstance().getCurrentUser();
        Bundle bundle = getIntent().getExtras();
        int secret_key = bundle.getInt("SECRET_KEY");

        if(secret_key != SECRET_KEY || user == null){
            finish();
        }
        container = findViewById(R.id.reportContainer);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Reports");
        reportList = new ArrayList<>();
        Log.d(LOG_TAG, "Logged in user:" + user);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getReports();
    }

    private void refresh(){
        LayoutInflater inflater = LayoutInflater.from(this);
        container.removeAllViews();
        for (int i = 0; i < reportList.size(); i++) {
            View itemView = inflater.inflate(R.layout.report_layout, container, false);
            Report report = reportList.get(i);
            TextView textView = itemView.findViewById(R.id.item_text_view);
            textView.setText(getString(R.string.amount) + ": " +report.getAmount() +"\n"+ DateConverter.convertDate(this, report));

            Button editButton = itemView.findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editReport(report);
                }
            });

            Button deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteReport(report);
                }
            });

            container.addView(itemView);
        }
    }

    private void getReports() {
        reportList.clear();
        new Thread(()-> mItems
                .whereEqualTo("uid", user.getUid())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Report item = document.toObject(Report.class);
                        reportList.add(item);
                    }
                    System.out.println(reportList);
                    refresh();
                })).start();

    }
    public void deleteReport(Report report) {

        DocumentReference ref = mItems.document(report.getId());

        new Thread(() -> {
            ref.delete().addOnSuccessListener(success -> {
                Log.d(LOG_TAG, "Deleted: " + report.getId());
            }).addOnFailureListener(failure -> {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Couldn't delete: " + report.getId(), Toast.LENGTH_SHORT).show();
                });
            }).addOnCompleteListener(task -> {
                refresh();
            });
        }).start();
    }

    public void editReport(Report report){
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        intent.putExtra("id", report.getId());
        startActivity(intent);
    }

}