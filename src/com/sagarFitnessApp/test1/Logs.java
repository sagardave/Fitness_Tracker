package com.sagarFitnessApp.test1;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.sagarFitnessApp.test1.R;


public class Logs extends SlidingFragmentActivity {

	private Fragment mContent;	
	DBAdapter myDB;			

	@Override
	public void onCreate(Bundle savedInstanceState) {	
		
		super.onCreate(savedInstanceState);		
		

		setTitle(R.string.Progress);		
		setContentView(R.layout.logs);		
	    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		myDB = new DBAdapter(this);
		myDB.open();
		
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
			mContent = new GraphFragment("");
		
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		
		
		String[] excerciseList = getExcerciseList();
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new BodyGroupList(excerciseList))
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
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

	}

	private String[] getExcerciseList() {
		String[] list = new String[200];
		int count = 0;		
		
		Cursor cursor = myDB.getAllRows();
		//reset cursor to start to check if there is data
		if(cursor.moveToFirst()){
			//process data 
			do{				
				String ExcerciseName = cursor.getString(1);
				
				if((!contains(list, ExcerciseName))){	
					if(!ExcerciseName.equalsIgnoreCase("")){
						list[count] = ExcerciseName;						
						count++;		
					}
				}
				
			}while(cursor.moveToNext());        	
		}
		
		String[] finalList = new String[count];
		
		for(int i = 0; i < count; i++){
			finalList[i] = list[i];
		}
		
		return finalList;
	}


	private boolean contains(String[] list, String excerciseName) {
		int count = 0;
		for(int i = 0; i < list.length; i++){
			if(list[i] != null){
				if(list[i].equalsIgnoreCase(excerciseName)){
					count++;
				}
			}
		}
		if(count == 0){
			return false;
		}
		else{
			return true;
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
