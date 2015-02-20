package com.sagarFitnessApp.test1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sagarFitnessApp.test1.R;

@SuppressLint("ValidFragment")
public class BodyGroupList extends ListFragment {
	
	String[] list_contents;	
	
		
	@SuppressLint("ValidFragment")
	public BodyGroupList(String[] excerciseList) {
		list_contents = excerciseList;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);

		return inflater.inflate(R.layout.list, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, list_contents));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String muscleGroup = list_contents[position];

		Fragment newContent = new GraphFragment(muscleGroup);
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof Logs) {
			Logs ra = (Logs) getActivity();
			ra.switchContent(fragment);
		}
	}

}
