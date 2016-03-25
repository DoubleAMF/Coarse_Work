package com.akmalariff.cw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class assignmentAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_add);
    }

    public String getSubjectIdKey() {
        //Retrieves the SubjectIdKey which was transferred through the intent
        Intent intent = getIntent();
        return (intent.getStringExtra("subjectIdKeyExtra"));
    }

    //This method handles the addition of assignment
    public void addAss(String assName, Float weight, Float grade) {
        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);
        SharedPreferences.Editor DbEditor = Database.edit();

        String subjectIdKey = getSubjectIdKey();
        int NumOfAss = Database.getInt(subjectIdKey + "NumOfAss", 0);
        String completeNewEntryKey = subjectIdKey+"A"+String.valueOf(NumOfAss+1);

        //This block creates the prefix keys required to store the data using the database structure designed
        String addNameKey = completeNewEntryKey+"Name";
        String addGradeKey = completeNewEntryKey+"Grade";
        String addWeightKey = completeNewEntryKey+ "Weight";

        //Inserts key-value pairs into the database
        DbEditor.putString(addNameKey, assName);
        DbEditor.putFloat(addWeightKey, weight);
        DbEditor.putFloat(addGradeKey, grade);
        DbEditor.putInt(subjectIdKey+"NumOfAss", NumOfAss + 1);
        DbEditor.commit();
    }


    //Actually adds the values required for the assignment by retrieving user input then passing it to the add assignment handler method called addAss
    public void addSubjectActivityConfirmButtonPressed(View view) {
        EditText assName = (EditText) findViewById(R.id.AssignmentAddName);
        EditText assGrade = (EditText) findViewById(R.id.AssignmentAddGrade);
        EditText assWeight = (EditText) findViewById(R.id.AssignmentAddWeight);

        addAss(assName.getText().toString(), (float) (Double.parseDouble(assWeight.getText().toString())/100), (float) (Double.parseDouble(assGrade.getText().toString())/100));
        finish();
    }


}
