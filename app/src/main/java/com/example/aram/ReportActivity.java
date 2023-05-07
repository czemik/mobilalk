package com.example.aram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import com.example.aram.models.Report;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Calendar;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {
    private static final int SECRET_KEY = 99;
    private final String LOG_TAG = ReportActivity.class.getName();

    private static TextView dateView;
    private EditText amountET;
    private static Report report;
    private static ReportActivity ins = null;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private NotificationHandler mNotificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Bundle bundle = getIntent().getExtras();
        String reportId = bundle.getString("id");
        int secret_key = bundle.getInt("SECRET_KEY");

        report = new Report();
        report.setUid(user.getUid());

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Reports");
        if(secret_key != SECRET_KEY || user == null){
            finish();
        }
        Log.d(LOG_TAG, "Logged in user:" + user);

        findViewById(R.id.datePickerButton).setOnClickListener(showDatePickerDialog());
        findViewById(R.id.submitButton).setOnClickListener(submitReport());
        findViewById(R.id.cancelButton).setOnClickListener(e->finish());

        mNotificationHandler = new NotificationHandler(this);

        amountET = findViewById(R.id.amountEditText);
        dateView = findViewById(R.id.dateTextView);
        ins = this;
        if(reportId != null){
            getReportById(reportId);
        }
    }

    public View.OnClickListener showDatePickerDialog() {
        return view -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        };
    }

    public View.OnClickListener submitReport(){
        return view -> {

            if(report.getYear() == null  || report.getMonth() == null || amountET.getText().toString().isEmpty()){
                return;
            }
            Integer amount;
            try{
                amount = Integer.parseInt(amountET.getText().toString());
            }
            catch (NumberFormatException exception){
                Log.e(LOG_TAG, exception.getMessage());
                return;
            }

            report.setAmount(amount);
            Log.d(LOG_TAG, report.toString());
            sendToFirebase();
        };
    }

    public void getReportById(String id){
        new Thread(()->{
            mItems
                    .document(id)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshot -> {
                        report = queryDocumentSnapshot.toObject(Report.class);
                        System.out.println(report);
                        setFields();
                    }).addOnFailureListener(e->Log.e(LOG_TAG, e.getMessage()));
        }).start();
    }

    public void setFields(){
        amountET.setText(report.getAmount().toString());
        setDateView();
    }
    public void sendToFirebase(){
        Thread thread;
        System.out.println(report.getId());
        if(report.getId() == null){
            thread = new Thread(() -> mItems.add(report).addOnSuccessListener(documentReference -> {
                report.setId(documentReference.getId());
                documentReference.set(report);
                mNotificationHandler.send(getString(R.string.success_report));
                finish();
            }).addOnFailureListener(e -> Log.e(LOG_TAG, "Couldn't add report!")));
        }
        else{
            thread = new Thread(() -> mItems.document(report.getId()).set(report).addOnSuccessListener(documentReference -> {
                mNotificationHandler.send(getString(R.string.success_edit));
                finish();
            }).addOnFailureListener(e -> Log.e(LOG_TAG, "Couldn't edit the report!")));
        }
        thread.start();


    }

    protected static void setDateView(){
        dateView.setText(DateConverter.convertDate(ins, report));
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            ReportActivity.report.setYear(year);
            ReportActivity.report.setMonth(month + 1);
            ReportActivity.setDateView();
        }
    }


}