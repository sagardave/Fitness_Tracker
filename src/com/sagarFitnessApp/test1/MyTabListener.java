package com.sagarFitnessApp.test1;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MyTabListener<T extends Fragment> implements TabListener {
	
	 private Fragment mFragment;
	 private final SherlockFragmentActivity mActivity;
	 private final String mTag;
	 private final Class<T> mClass;
	 
	  /** Constructor used each time a new tab is created. */
	    public MyTabListener(SherlockFragmentActivity activity, String tag,
	            Class<T> clz) {
	        mActivity = activity;
	        mTag = tag;
	        mClass = clz;
	    }
    
	    /* The following are each of the ActionBar.TabListener callbacks */
	    
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		FragmentManager fragMgr = ((FragmentActivity) mActivity)
                .getSupportFragmentManager();
        ft = fragMgr.beginTransaction();

        // Check if the fragment is already initialized
        if (mFragment == null) {
            // If not, instantiate and add it to the activity
            mFragment = Fragment.instantiate(mActivity, mClass.getName());

            ft.add(android.R.id.content, mFragment, mTag);
        } else {
            // If it exists, simply attach it in order to show it
            ft.attach(mFragment);
        }
        
        ft.commit();
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

        FragmentManager fragMgr = ((FragmentActivity) mActivity)
                .getSupportFragmentManager();
        ft = fragMgr.beginTransaction();

        // Check if the fragment is already initialized
        if (mFragment == null) {
            // If not, instantiate and add it to the activity
            mFragment = Fragment.instantiate(mActivity, mClass.getName());

            ft.add(android.R.id.content, mFragment, mTag);
        } else {
            // If it exists, simply attach it in order to show it
            ft.detach(mFragment);
        }
        
        ft.commit();
	}


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
		
	}
    
}
