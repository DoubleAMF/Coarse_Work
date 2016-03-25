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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class assList extends AppCompatActivity {

    public List<String> actualAssList = new ArrayList<String>();
    public int NumOfAss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ass_list);

        //Instantiates the prefix to look up assignment data called subjectIdKey
        String subjectIdKey = getSubjectIdKey();
        Log.d("subjectIdKey", subjectIdKey);

        //Instantiates the database to enable reading from it
        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);

        //Getting total number of assignments from database
        NumOfAss = Database.getInt(subjectIdKey+"NumOfAss", 0);
        Log.d("NumOfAss", String.valueOf(NumOfAss));

        ListView subsList = (ListView) findViewById(R.id.assList);

        //ArrayList which holds names of the assignments assigned by searching the database for matching prefix key and incremental assignment
        actualAssList = new ArrayList<>(Arrays.asList("   ")); //Dummy Subject
        for (int i = 1; i <= NumOfAss; i++) {

            String assName = Database.getString(subjectIdKey+"A"+i+"Name", "Not found");
            actualAssList.add(assName);
            Log.d("AssName", assName);
        }

        //Sets an arrayadapter to accept the name array and display it as a list
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, actualAssList);
        subsList.setAdapter(ArrayAdapter);
        subsList.setTextFilterEnabled(true);

        //updates list
        ArrayAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();


        // The onResume() method is exactly the same as the onCreate() method
        String subjectIdKey = getSubjectIdKey();
        Log.d("subjectIdKey", subjectIdKey);

        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);
        NumOfAss = Database.getInt(subjectIdKey+"NumOfAss", 0);
        Log.d("NumOfAss", String.valueOf(NumOfAss));

        ListView subsList = (ListView) findViewById(R.id.assList);


        actualAssList = new ArrayList<>(Arrays.asList("   ")); //Dummy Subject
        for (int i = 1; i <= NumOfAss; i++) {

            String assName = Database.getString(subjectIdKey+"A"+i+"Name", "Not found");
            actualAssList.add(assName);
            Log.d("AssName", assName);
        }

        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, actualAssList);
        subsList.setAdapter(ArrayAdapter);
        subsList.setTextFilterEnabled(true);
        ArrayAdapter.notifyDataSetChanged();
    }

    public String getSubjectIdKey() {

        Intent intent = getIntent();
        String whichSub = intent.getStringExtra("WhichSub");
        Log.d("WhichSub", whichSub);
        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);
        int NumOfSubs = Database.getInt("NumOfSubs", 0);

        String subjectIdKey = null;

        for(int i=1; i<=NumOfSubs; i++)
        {
            String eachSub = Database.getString("S"+i+"Name", "Meh");
            if (whichSub.compareTo(eachSub)==0)
            {
                subjectIdKey = (String)("S"+i);
            }
            else{
                subjectIdKey = "No id found";
            }
        }
        return subjectIdKey;
    }

    //Event handler method for add assignment button pressed. Creates an intent which bring up the form Activity called assignmentAdd.class
    public void addAssPressed(View view) {
        Intent addAss = new Intent(assList.this, assignmentAdd.class);
        String subjectIdKeyExtra = getSubjectIdKey();
        addAss.putExtra("subjectIdKeyExtra", subjectIdKeyExtra);
        startActivity(addAss);
    }

    //Event Handler which sets the target to passing the module
    public void getPass(View view) {
        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);
        SharedPreferences.Editor DbEditor = Database.edit();

        String subjectIdKey = getSubjectIdKey();
        DbEditor.putBoolean(subjectIdKey+"First", false);
        DbEditor.commit();
        finish();
    }

    //Event handler which sets the target to getting a first in the module
    public void getFirst(View view) {
        SharedPreferences Database = this.getSharedPreferences("Database.akmalariff", 0);
        SharedPreferences.Editor DbEditor = Database.edit();

        String subjectIdKey = getSubjectIdKey();
        DbEditor.putBoolean(subjectIdKey+"First", true);
        DbEditor.commit();
        finish();
    }
}
