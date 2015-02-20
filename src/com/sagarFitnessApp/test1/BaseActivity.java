package com.sagarFitnessApp.test1;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.sagarFitnessApp.test1.R;


public class BaseActivity extends SlidingFragmentActivity {
	
	private int mTitleRes;
	protected ListFragment mFrag;
	
	public BaseActivity(int titleRes){
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setTitle(mTitleRes);
		
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
				
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		mFrag = new BodyGroupList(null);
		ft.replace(R.id.menu_frame, mFrag);
		ft.commit();
		
		// customize the SlidingMenu
				SlidingMenu sm = getSlidingMenu();
				sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
				sm.setShadowWidthRes(R.dimen.shadow_width);
				sm.setShadowDrawable(R.drawable.shadow);
				sm.setBehindScrollScale(0.25f);
				sm.setFadeDegree(0.25f);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case android.R.id.home:
			toggle();
			return true;
		}
		return onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public class BasePagerAdapter extends FragmentPagerAdapter{
		private List<Fragment> mFragments = new ArrayList<Fragment>();
		private ViewPager mPager;
		
		public BasePagerAdapter(FragmentManager fm, ViewPager vp){
			super(fm);
			mPager = vp;
			mPager.setAdapter(this);
			for (int i = 0; i < 3; i++){
				addTab(new BodyGroupList(null));
			}
		}
		
		public void addTab(Fragment frag){
			mFragments.add(frag);
		}
		
		@Override
		public Fragment getItem(int position){
			return mFragments.get(position);
		}
		
		@Override
		public int getCount(){
			return mFragments.size();
		}
	}

}
