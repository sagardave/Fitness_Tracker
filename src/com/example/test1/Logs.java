package com.example.test1;

import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Logs extends SlidingFragmentActivity {

	private Fragment mContent;	
	DBAdapter myDB;			

	@Override
	public void onCreate(Bundle savedInstanceState) {	
		
		super.onCreate(savedInstanceState);			

		setTitle(R.string.Progress);		
		setContentView(R.layout.logs);		
		
		// check if the content frame contains the menu frame
		if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			// show home as up so we can toggle
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		} else {
			// add a dummy view
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
		
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new GraphFragment(null);
		
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new BodyGroupList())
		.commit();
			

		// customize the SlidingMenu

		SlidingMenu sm = getSlidingMenu();
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);	
		setSlidingActionBarEnabled(false);		
		
		myDB = new DBAdapter(this);
		myDB.open();	
		
//		Button displayButton = (Button) findViewById (R.id.displayButton);
//		displayButton.setOnClickListener (new View.OnClickListener (){
//        	public void onClick (View v)
//            {         		
//        		displayRecordSet();
//            }
//        });
		
//		Button clearButton = (Button) findViewById (R.id.clearButton);
//		clearButton.setOnClickListener (new View.OnClickListener (){
//        	public void onClick (View v)
//            { 	
//        		LinearLayout myLinearLayout = (LinearLayout)findViewById(R.id.logsLayout);
//
//        		myDB.deleteAll();
//        		displayRecordSet();
//        		
//        		myLinearLayout.removeAllViews();
//            }
//        });
	}
	
	private void displayRecordSet() {
		String text = "Nothing!";		
		//LinearLayout myLinearLayout = (LinearLayout)findViewById(R.id.logsLayout);
		Cursor cursor = myDB.getAllRows();
		//reset cursor to start to check if there is data
		if(cursor.moveToFirst()){
			//process data 
			do{
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				int numOfReps = cursor.getInt(2);
				int numOfWeight = cursor.getInt(3);
				String date = cursor.getString(4);
				String MuscleGroup = cursor.getString(5);
				
				text = "Date: " + date + " Set " + id + ": " + name + 
						" Target:" + MuscleGroup + " " + numOfReps + " " 
						+ " Reps @ " + numOfWeight + " lb";	
				
				TextView message = new TextView(Logs.this);
				message.setText(text);
				//myLinearLayout.addView(message);
				
			}while(cursor.moveToNext());        	
		}		
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		myDB.close();

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void switchContent(final Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}	
	
}
