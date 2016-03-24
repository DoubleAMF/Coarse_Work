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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public int NumOfSubs;
    public List<String> actualSubsList = new ArrayList<String>();
    public double Target = 0.70000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DecimalFormat onedec = new DecimalFormat("0.#");

        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);
        NumOfSubs = Database.getInt("NumOfSubs", 0);

        ListView subsList = (ListView) findViewById(R.id.subsList);


        actualSubsList = new ArrayList<>(Arrays.asList("Subject...")); //Dummy Subject
        for (int i = 1; i <= NumOfSubs; i++) {

            String subjectName = Database.getString("S" + i + "Name", "Not found");
            actualSubsList.add(subjectName);
            Log.d("Subject", subjectName);
        }

        String TargetMessage = "Default";
        ArrayList<String> subsListWithReq =  new ArrayList<>(Arrays.asList("Subject\nRequired Average Future Grade")); //Dummy Again
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

        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subsListWithReq);
        subsList.setAdapter(ArrayAdapter);
        subsList.setTextFilterEnabled(true);
        ArrayAdapter.notifyDataSetChanged();

        subsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent addAssignment = new Intent(MainActivity.this, assList.class);
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

        DecimalFormat onedec = new DecimalFormat("0.#");

        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);
        NumOfSubs = Database.getInt("NumOfSubs", 0);

        ListView subsList = (ListView) findViewById(R.id.subsList);


        actualSubsList = new ArrayList<>(Arrays.asList("Subject...")); //Dummy Subject
        for (int i = 1; i <= NumOfSubs; i++) {

            String subjectName = Database.getString("S" + i + "Name", "Not found");
            actualSubsList.add(subjectName);
            Log.d("Subject", subjectName);
        }

        String TargetMessage = "Default";
        ArrayList<String> subsListWithReq =  new ArrayList<>(Arrays.asList("Subject\nRequired Average Future Grade")); //Dummy Again
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

        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subsListWithReq);
        subsList.setAdapter(ArrayAdapter);
        subsList.setTextFilterEnabled(true);
        ArrayAdapter.notifyDataSetChanged();

        subsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent addAssignment = new Intent(MainActivity.this, assList.class);
                String whichSub = actualSubsList.get(position).toString();
                addAssignment.putExtra("WhichSub", whichSub);
                Log.d("WhichSub: ", whichSub);
                startActivity(addAssignment);
            }
        });

    }

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


    public void addSubjectPressed(View view) {
        Intent addSubs = new Intent(this, subjectAdd.class);
        startActivity(addSubs);
    }

    public void clearData(View view) {
        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);
        SharedPreferences.Editor DbEditor = Database.edit();
        DbEditor.clear();
        DbEditor.commit();
    }

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

        Boolean Targetflag = Database.getBoolean(subjectIdKey+"First", true);
        Target = (Targetflag==true?0.7:0.4);

        Log.d("sumOfWeight", String.valueOf(sumOfWeight));
        Log.d("sumOftimed", String.valueOf(sumOfGradesTimesWeight));
        double required = 100*(Target-sumOfGradesTimesWeight)/(1.0000-sumOfWeight);
        Log.d("Required", String.valueOf(required));
        return required;
    }



}
