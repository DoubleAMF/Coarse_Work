package com.akmalariff.cw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public int NumOfSubs;
    public List<String> actualSubsList = new ArrayList<String>();
    public double Target = 0.70000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiating decimal formatter for one decimal place
        DecimalFormat onedec = new DecimalFormat("0.0");

        /*Instantiates "Database" created using SharedPreferences.
        * The name of each Key is formatted to simulate a branched tree mapping
        * Important Keys:
        * NumOfSubs is the total number of subjects
        * S#Name = Name of subject number #
        * S#A#Name = Name of assignment # in subject #
        * S#A#Item = same form where Item can be "Weight", "Grade"
        * S#NumOfAss = Number of assignments in Subject number #*/
        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);

        //Retrieve total number of Subjects
        NumOfSubs = Database.getInt("NumOfSubs", 0);

        //Instantiation
        ListView subsList = (ListView) findViewById(R.id.subsList);

        //This bulk of code instantiates elements of an ArrayList by searching and adding each subject entry that does not exceed the maximum number of subjects
        actualSubsList = new ArrayList<>(Arrays.asList("Refresh")); //Dummy Subject
        for (int i = 1; i <= NumOfSubs; i++) {

            String subjectName = Database.getString("S" + i + "Name", "Not found");
            actualSubsList.add(subjectName);
            Log.d("Subject", subjectName);
        }

        //Instantiates TargetMessage with default message
        String TargetMessage = "Default";

        //Instantiates an new ArrayList with required additional info about the required grade message using if clause
        final ArrayList<String> subsListWithReq =  new ArrayList<>(Arrays.asList("Subject\nRequired Average Future Grade")); //Dummy Again
        for (int i = 1; i <= NumOfSubs; i++) {

            if(requiredGrade(i)<0)
            {
                TargetMessage = "Target Already Achieved";
            }
            else if (requiredGrade(i)>100) {
                TargetMessage = "Sorry Get Real";
            }
            else{
                TargetMessage = onedec.format(requiredGrade(i))+"%";
            }

            String subjectName = Database.getString("S" + i + "Name", "Not found")+"\n"+TargetMessage;

            subsListWithReq.add(subjectName);
            Log.d("Subject", subjectName);
        }

        //Setting up the Array adapter which receives the ArrayList with required grade info
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subsListWithReq);
        subsList.setAdapter(ArrayAdapter);
        subsList.setTextFilterEnabled(true);

        //Refreshes List
        ArrayAdapter.notifyDataSetChanged();

        //Set item click action which include intent
        subsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent addAssignment = new Intent(MainActivity.this, assList.class);
                Log.d("SubjectClickPosition", String.valueOf(position));
                String whichSub = actualSubsList.get(position).toString();
                addAssignment.putExtra("WhichSub", whichSub);
                Log.d("WhichSub: ", whichSub);
                startActivity(addAssignment);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //The whole onResume() method is exactly the same as the onCreate() method to ensure consistency
        //Instantiating decimal formatter for one decimal place
        DecimalFormat onedec = new DecimalFormat("0.0");

        /*Instantiates "Database" created using SharedPreferences.
        * The name of each Key is formatted to simulate a branched tree mapping
        * Important Keys:
        * NumOfSubs is the total number of subjects
        * S#Name = Name of subject number #
        * S#A#Name = Name of assignment # in subject #
        * S#A#Item = same form where Item can be "Weight", "Grade"
        * S#NumOfAss = Number of assignments in Subject number #*/
        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);

        //Retrieve total number of Subjects
        NumOfSubs = Database.getInt("NumOfSubs", 0);

        //Instantiation
        ListView subsList = (ListView) findViewById(R.id.subsList);

        //This bulk of code instantiates elements of an ArrayList by searching and adding each subject entry that does not exceed the maximum number of subjects
        actualSubsList = new ArrayList<>(Arrays.asList("Refresh")); //Dummy Subject
        for (int i = 1; i <= NumOfSubs; i++) {

            String subjectName = Database.getString("S" + i + "Name", "Not found");
            actualSubsList.add(subjectName);
            Log.d("Subject", subjectName);
        }

        //Instantiates TargetMessage with default message
        String TargetMessage = "Default";

        //Instantiates an new ArrayList with required additional info about the required grade message using if clause
        final ArrayList<String> subsListWithReq =  new ArrayList<>(Arrays.asList("Subject\nRequired Average Future Grade")); //Dummy Again
        for (int i = 1; i <= NumOfSubs; i++) {

            if(requiredGrade(i)<0)
            {
                TargetMessage = "Target Already Achieved";
            }
            else if (requiredGrade(i)>100) {
                TargetMessage = "Sorry Get Real";
            }
            else{
                TargetMessage = onedec.format(requiredGrade(i))+"%";
            }

            String subjectName = Database.getString("S" + i + "Name", "Not found")+"\n"+TargetMessage;

            subsListWithReq.add(subjectName);
            Log.d("Subject", subjectName);
        }

        //Setting up the Array adapter which receives the ArrayList with required grade info
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subsListWithReq);
        subsList.setAdapter(ArrayAdapter);
        subsList.setTextFilterEnabled(true);

        //Refreshes List
        ArrayAdapter.notifyDataSetChanged();

        //Set item click action which include intent
        subsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent addAssignment = new Intent(MainActivity.this, assList.class);
                Log.d("SubjectClickPosition", String.valueOf(position));
                String whichSub = actualSubsList.get(position).toString();
                addAssignment.putExtra("WhichSub", whichSub);
                Log.d("WhichSub: ", whichSub);
                startActivity(addAssignment);

            }
        });

    }


    //Method which handles the entry of a new Subject. Note required in final app, however useful for debugging
    public void addSubject(String subject, Float weight) {
        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);
        SharedPreferences.Editor DbEditor = Database.edit();
        NumOfSubs = Database.getInt("NumOfSubs", 0);
        String addSubjectKey = "S" + String.valueOf(NumOfSubs + 1) + "Name";
        String addWeightKey = "S" + String.valueOf(NumOfSubs + 1) + "Weight";
        DbEditor.putString(addSubjectKey, subject);
        DbEditor.putFloat(addWeightKey, weight);
        DbEditor.putInt("NumOfSubs", NumOfSubs + 1);
        DbEditor.commit();
    }


    //Event handler method for add subject button
    public void addSubjectPressed(View view) {
        Intent addSubs = new Intent(this, subjectAdd.class);
        startActivity(addSubs);
    }

    //Event handler method for clear data
    public void clearData(View view) {
        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);
        SharedPreferences.Editor DbEditor = Database.edit();
        DbEditor.clear();
        DbEditor.commit();
    }

    //Method which calculates the required grade
    public double requiredGrade(int subjectNum)
    {
        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);
        String subjectIdKey = "S"+String.valueOf(subjectNum);
        double sumOfGradesTimesWeight = 0;
        double sumOfWeight = 0;
        for (int e = 1; e <= Database.getInt(subjectIdKey+"NumOfAss", 0); e++)
        {
            double grade = (double) Database.getFloat(subjectIdKey+"A"+String.valueOf(e)+"Grade", 0);
            double weight = (double) Database.getFloat(subjectIdKey+"A"+String.valueOf(e)+"Weight", 0);
            Log.d("GradeKey:", (subjectIdKey+"A"+String.valueOf(e)+"Grade"));
            Log.d("WeightKey:", (subjectIdKey+"A"+String.valueOf(e)+"Weight"));
            sumOfWeight += weight;
            sumOfGradesTimesWeight += grade*weight;
        }

        //The Targetflag indicates whether user is looking to get a first trying to pass the module
        Boolean Targetflag = Database.getBoolean(subjectIdKey+"First", true);
        Target = (Targetflag==true?0.7:0.4);

        Log.d("sumOfWeight", String.valueOf(sumOfWeight));
        Log.d("sumOftimed", String.valueOf(sumOfGradesTimesWeight));
        double required = 100*(Target-sumOfGradesTimesWeight)/(1.0000-sumOfWeight);
        Log.d("Required", String.valueOf(required));
        return required;
    }

}
