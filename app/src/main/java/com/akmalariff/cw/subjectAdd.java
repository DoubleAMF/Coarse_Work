package com.akmalariff.cw;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class subjectAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_add);
    }

    public void addSubject(String subject, Float weight) {
        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);
        SharedPreferences.Editor DbEditor = Database.edit();
        int NumOfSubs = Database.getInt("NumOfSubs", 0);
        String addSubjectKey = "S" + String.valueOf(NumOfSubs + 1) + "Name";
        String addWeightKey = "S" + String.valueOf(NumOfSubs + 1) + "Weight";
        DbEditor.putString(addSubjectKey, subject);
        DbEditor.putFloat(addWeightKey, weight);
        DbEditor.putInt("NumOfSubs", NumOfSubs + 1);
        DbEditor.commit();
    }

    public void addSubjectActivityConfirmButtonPressed(View view) {
        EditText subjectName = (EditText) findViewById(R.id.subjectAddName);
        EditText subjectWeight = (EditText) findViewById(R.id.subjectAddWeight);
        addSubject(subjectName.getText().toString(), (float)(Double.parseDouble(subjectWeight.getText().toString())/100));
        finish();
    }
}
