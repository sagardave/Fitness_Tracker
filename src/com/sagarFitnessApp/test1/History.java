package com.sagarFitnessApp.test1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.sagarFitnessApp.test1.R;

@SuppressLint("SimpleDateFormat")
public class History extends FragmentActivity {
	
	public History() {
		super();
	}

	DBAdapter myDB;	
	String[] list;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("Created Logs");
		setContentView(R.layout.menu_frame);
		
		myDB = new DBAdapter(this);
		myDB.open();				
	
		list = getExcerciseList();	
		String[] date = getExcerciseDate();
		
				
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new ExcerciseListView(list, date))
		.commit();
	
	}
		
	
	@Override
	protected void onDestroy() {		
		super.onDestroy();
		myDB.close();
	}

	public String[] getExcerciseList() {
		ArrayList<String> listA = new ArrayList<String>();
		ArrayList<String> dateL = new ArrayList<String>();
		//String[] list = new String[150];
		int count = 0;
		//String[] date = new String[150];
		String[] repsAndSets = new String[150];
		String prev = "";		
		
		Cursor cursor = myDB.getAllRows();
		//reset cursor to start to check if there is data
		if(cursor.moveToFirst()){
			//process data 
			do{				
				String ExcerciseName = cursor.getString(1);
				String Date = cursor.getString(4);
				String MuscleGroup = cursor.getString(5);
				int id = cursor.getInt(0);
				int numOfReps = cursor.getInt(2);
				int numOfWeight = cursor.getInt(3);
				
				if((!ExcerciseName.equals("") && !ExcerciseName.equals(null))){
					if(!(ExcerciseName + "-  " + MuscleGroup).equals(prev)){
						//list[count] = ExcerciseName + "-  " + MuscleGroup;
						listA.add(ExcerciseName + "-  " + MuscleGroup);
						dateL.add(Date);
						repsAndSets[count] = numOfReps + " Reps" + " @ " + numOfWeight + " lb";
						prev = listA.get(count);
						count++;
					}
				}
				
			}while(cursor.moveToNext());        	
		}
		
		String[] finalList = new String[count];
		
		for(int i = 0; i < count; i++){
			finalList[i] = listA.get(i).toString();
		}
		
		return finalList;
	}	
	
	@SuppressWarnings("deprecation")
	@SuppressLint("SimpleDateFormat")
	public String[] getExcerciseDate() {
		int count = 0;
		String[] date = new String[list.length];

		Date sdf = null;
		Cursor cursor = myDB.getAllRows();
		Calendar cal=Calendar.getInstance();
		String prev = "";
		String current = "";
		String prevExer = "";
		String curExer = "";
		//reset cursor to start to check if there is data
		if(cursor.moveToFirst()){
			//process data 
			do{				
				String ExcerciseName = cursor.getString(1);
				String Date = cursor.getString(4);
				curExer = cursor.getString(1);

				if(!curExer.equals(prevExer) && !ExcerciseName.equals("") && !ExcerciseName.equals(null)){
					try 
					{
						sdf = new SimpleDateFormat("yyyy-MM-dd").parse(Date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					String mon = new SimpleDateFormat("MMM").format(sdf);
					String day = new SimpleDateFormat("EE").format(sdf);
					
					current = day + " " + mon + " " + sdf.getDate() + ", " + (sdf.getYear()+1900);
					
					if(!prev.equals(current)){						
						date[count] = current;
					}
					else{
						date[count] = "";
					}
					count++;
					prev = day + " " + mon + " " + sdf.getDate() + ", " + (sdf.getYear()+1900);
				}				
				
				prevExer = curExer;
				
			}while(cursor.moveToNext());        	
		}
		
		String[] finalDate = new String[list.length];
		
		for(int i = 0; i < count; i++){
			finalDate[i] = date[i];
		}
		
		return finalDate;
	}	
}
