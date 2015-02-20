package com.sagarFitnessApp.test1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.sagarFitnessApp.test1.R;

public class MainView extends SherlockFragment{
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.logs, container, false);
	}

}
