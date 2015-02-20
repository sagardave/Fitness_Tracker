package com.example.test1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

public class NewLog extends SherlockFragmentActivity implements OnItemSelectedListener{
	int setNumberValue = 1;
	
	DBAdapter myDB;
	
	//String[] listviewItems = new String[10];
	//final TextView[] addedSetItems = new TextView[10]; 
	EditText[] addedReps = new EditText[10]; 	
	int[] repsArray = new int[10];
	EditText[] addedWeight = new EditText[10];
	int[] weightArray = new int[10];
    private List<EditText> editTextList = new ArrayList<EditText>();

	
	int reps = 0;
	double weight = 0;
	int listviewCount = 0;
	String muscleGroup = null;
	String workoutName = null;
	Spinner spinner;

	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String currentDateandTime = sdf.format(new Date());
	
	String[] muscleGroups = {"Chest", "Back", "Biceps", "Triceps", "Abs", "Shoulders", "Forearm", "Deltoids", "Legs"};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		openDB();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newlog);
		
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(NewLog.this, android.R.layout.simple_spinner_item, muscleGroups);
		
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setAdapter(spinnerAdapter);				
		
    	TextView setNumber = (TextView)findViewById(R.id.Sets);
    	setNumber.setText("Set 1: ");    	

    	TextView tv = (TextView)findViewById(R.id.Date);
    	tv.setText(currentDateandTime);     	
		
        Button addSetButtom = (Button) findViewById (R.id.addSetButton);
        addSetButtom.setOnClickListener (new View.OnClickListener ()
        {
            public void onClick (View v)
            {
            	EditText numOfReps = (EditText)findViewById(R.id.editText1);
            	EditText numOfWeight = (EditText)findViewById(R.id.editText2);            	

                LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
                			LayoutParams.WRAP_CONTENT, 0.1f);
                param.setMargins(20, 20, 20, 0);                   
                           	
            	TextView setNumber = (TextView)findViewById(R.id.Sets);
            	setNumber.setText("Set " + setNumberValue + ": ");
            	            	
            	TextView set = new TextView(NewLog.this);
            	set.setText("Set " + (setNumberValue) + ": ");
            	
            	EditText editReps = new EditText(NewLog.this);
            	editReps.setText(numOfReps.getText().toString());
            	
            	TextView rep = new TextView(NewLog.this);
            	rep.setText("Reps @: ");            	
            	
            	EditText editWeight = new EditText(NewLog.this);
            	editWeight.setText(numOfWeight.getText().toString());
            	
            	editTextList.add(editWeight);
            	            	
            	TextView unitOfWeight = new TextView(NewLog.this);
            	unitOfWeight.setText("lb");            	
            	
            	//String item = "Set " + setNumberValue + ": " + reps + " Reps @ " + weight + " lb";
            	
            	 // set some properties of rowTextView or something
            	 //rowTextView.setText(item);
            	 
            	 //Dynamically add new EditText views
            	
            	 LinearLayout newLayout = new LinearLayout(NewLog.this);
            	 
            	 newLayout.setLayoutParams(param);
            	 newLayout.addView(set);
            	 newLayout.addView(editReps);            	 
            	 newLayout.addView(rep);            	 
            	 newLayout.addView(editWeight);            	 
            	 newLayout.addView(unitOfWeight);            	
            	 
            	 LinearLayout myLinearLayout = (LinearLayout)findViewById(R.id.linearLayout4);
            	 myLinearLayout.addView(newLayout);
            	    // add the textview to the linearlayout
            	 //myLinearLayout.addView(rowTextView);

            	    // save a reference to the textview for later
            	 addedReps[listviewCount] = numOfReps;
            	 repsArray[listviewCount] = Integer.parseInt(numOfReps.getText().toString());
            	 
            	 addedWeight[listviewCount] = numOfWeight;
            	 weightArray[listviewCount] = Integer.parseInt(numOfWeight.getText().toString());

            	// myDB.insertRow(name, studentNum, favColour)
            	
            	setNumberValue++;
            	listviewCount++;
            }
        }); 
                
        Button saveSetButtom = (Button) findViewById (R.id.saveWorkout);
        saveSetButtom.setOnClickListener (new View.OnClickListener (){
        	@SuppressLint("SimpleDateFormat")
			public void onClick (View v)
            { 
        		
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        		String date = sdf.format(new Date()).toString();
        		
        		TextView nameOfWorkout = (TextView)findViewById(R.id.workoutName);
        		workoutName = nameOfWorkout.getText().toString();
        		
        		for(int i = 0; i < listviewCount; i++)
        		{
                	reps = Integer.parseInt(addedReps[i].getText().toString());
                	weight = Integer.parseInt(addedWeight[i].getText().toString());
                	Log.d(null, addedWeight[i].getText().toString());
                	if(weightArray[i] != 0)
                	{
                		addRecord(workoutName, reps, weightArray[i], date, muscleGroup);  
                	}
        		}
            }
        	
        });        
        spinner.setOnItemSelectedListener(this);       
	}
		
	public void addRecord(String name, int numOfReps, double numOfWeight, String date, String MuscleGroup)
	{
		long newId = myDB.insertRow(name, numOfReps, numOfWeight, date, MuscleGroup);
	}
	
	private void openDB() {
		myDB = new DBAdapter(this);
		myDB.open();		
	}		

	@Override
	protected void onDestroy() {		
		super.onDestroy();		
		closeDB();
	}
	
	private void closeDB() {
		myDB.close();
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		int position = spinner.getSelectedItemPosition();
		muscleGroup = spinner.getItemAtPosition(position).toString();		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
	
}
