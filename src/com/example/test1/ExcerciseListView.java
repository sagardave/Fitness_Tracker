package com.example.test1;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class ExcerciseListView extends ListFragment {

	String[] list_contents;	
	String[] Dates;
	DBAdapter myDB;
	
	public ExcerciseListView(String[] list, String[] dates) {
		list_contents = list;
		Dates = dates;
	}	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		myDB = new DBAdapter(getActivity());
		myDB.open();
		setRetainInstance(true);

        return(inflater.inflate(R.layout.list, container, false));

	}

	@Override
	public void onDestroyView() {
		myDB.close();		
		super.onDestroyView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		SampleAdapter adapter = new SampleAdapter(getActivity());
		
		for (int i = 0; i < list_contents.length; i++) {
			adapter.add(new SampleItem(list_contents[i], Dates[i]));
		}
		setListAdapter(adapter);	
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//String muscleGroup = list_contents[position];		
		
		
	}
	
	private class SampleItem {
		public String excercise;
		public String date;
		public SampleItem(String excercise, String Date) {
			this.excercise = excercise; 
			this.date = Date;
		}
	}
	
	public class SampleAdapter extends ArrayAdapter<SampleItem> {
		
		String prevDate = "";
		String currentDate = "";
		String currentExcercise = "";
		String nextExcercise = "";
		String[] repsAndSetsArray;
		String prev = "";
		
		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			String value = "";
			TextView separator = (TextView) convertView.findViewById(R.id.separator);
			TextView repsAndSets = (TextView) convertView.findViewById(R.id.dateOfExcercise);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			
			currentDate = getItem(position).date;
			separator.setText(currentDate);	
			if(currentDate != ""){				
				separator.setVisibility(View.VISIBLE);
			}
			else{
				separator.setVisibility(View.GONE);
			}				
			
			prevDate = separator.getText().toString();
			
			title.setText(getItem(position).excercise);			
			
			repsAndSetsArray = getExcerciseSetsAndReps(getItem(position).excercise.toString());
			
			for(int i = 0; i < repsAndSetsArray.length; i++){
				if(!repsAndSetsArray.equals(prev)){
					
	            	value += ("Set " + (i+1) + ": " + repsAndSetsArray[i] + "\n");   
	            	
	            	prev = repsAndSetsArray[i];
				}
			}
			repsAndSets.setText(value);
				
			

			return convertView;
		}
		
		public String[] getExcerciseSetsAndReps(String Excercise) {	
			
			String[] repsAndSets = new String[50];
			int count = 0;
			Cursor cursor = myDB.getAllRows();
			//reset cursor to start to check if there is data
			if(cursor.moveToFirst()){
				//process data 
				do{				
					String ExcerciseName = cursor.getString(1);
					int id = cursor.getInt(0);
					int numOfReps = cursor.getInt(2);
					int numOfWeight = cursor.getInt(3);
					String MuscleGroup = cursor.getString(5);
					ExcerciseName = ExcerciseName + "-  " + MuscleGroup;
					
					if( (!ExcerciseName.equals("") && !ExcerciseName.equals(null)) && (ExcerciseName.equals(Excercise)) ){					
						repsAndSets[count] = numOfReps + " Reps" + " @ " + numOfWeight + " lb";
						count++;
					}
					
				}while(cursor.moveToNext());        	
			}
			
			String[] finalList = new String[count];
			
			for(int i = 0; i < count; i++){
				finalList[i] = repsAndSets[i];
			}
			
			return finalList;		
			
		}

	}

}
