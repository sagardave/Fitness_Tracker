package com.sagarFitnessApp.test1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sagarFitnessApp.test1.R;

@SuppressLint("ValidFragment")
public class GraphFragment extends Fragment {
	
	private int mPos = -1;
	View view;
	
    GraphicalView chartView;
    String Excercise;
    ArrayList<Integer> weightL = new ArrayList<Integer>();
   //double[] weight = new double[100];
    ArrayList<String> dateL = new ArrayList<String>();

    //String[] dates = new String[100];

    private DBAdapter db;
    
    
    
	public GraphFragment(String mGroup) {
		Excercise = mGroup;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		 LinearLayout chartLayout = (LinearLayout) inflater.inflate(R.layout.dashboard_chart,null);
			setRetainInstance(true);

			return chartLayout;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mPos", mPos);
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
    	    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            db = new DBAdapter(getActivity());
            db.open();
            getRecordsByExcercise(db);
          
    }	
	
	@Override
    public void onResume() {
            super.onResume();            
                       
            if (chartView == null) 
            {
            	LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.dashboard_chart_layout);            
                chartView = ChartFactory.getLineChartView(getActivity(), DataSet(),
                		mRenderer());
                layout.addView(chartView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
               		 	LinearLayout.LayoutParams.MATCH_PARENT));
            } 
            else 
            {
            	  chartView.repaint();
            }
            
    }		
	
	private XYMultipleSeriesDataset DataSet()
	{
		//String[] x = dates;
		//double[] y = weight;
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		
		TimeSeries series = new TimeSeries("Progress");
		//TimeSeries Goal = new TimeSeries("Goal");
		
		for(int i = 0; i < dateL.size(); i++)//x
		{
			if(weightL.get(i) != 0)//y
			{				
				series.add(i, weightL.get(i));
			}
			
		}
		
//		for(int i = 0; i < x.length; i++)
//		{
//			if(y[i] != 0)
//			{
//				Goal.add(i, 8);
//			}
//		}
		
		dataset.addSeries(series);
		//dataset.addSeries(Goal);
		
		return dataset;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

	}

	@SuppressWarnings("deprecation")
	private XYMultipleSeriesRenderer mRenderer()
	{		
		String currentDate = "";
		String prevDate = "";
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setLineWidth(3f);
		renderer.setFillPoints(true);
		renderer.setChartValuesSpacing(10f);
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesTextSize(15);
		renderer.setColor(Color.WHITE);

//		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
//		
//		renderer2.setPointStyle(PointStyle.CIRCLE);
//		renderer2.setLineWidth(3f);
//		renderer2.setFillPoints(true);
//		renderer2.setChartValuesSpacing(10f);
//		renderer2.setDisplayChartValues(false);
//		renderer2.setColor(Color.GREEN);
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		
		mRenderer.setYTitle("Weight (lb)");
		mRenderer.setXTitle("Date");
		mRenderer.setMarginsColor(Color.argb(0, 255, 255, 255));
		mRenderer.setAxesColor(Color.GRAY);
        mRenderer.setLabelsColor(Color.GRAY);
        mRenderer.setXLabelsColor(Color.GRAY);
        mRenderer.setYLabelsColor(0, Color.GRAY);
        mRenderer.setAxisTitleTextSize(40);
        mRenderer.setLabelsTextSize(30); 
        mRenderer.setSelectableBuffer(20);       
        mRenderer.setMargins(new int[]{ 80, 80, 80, 80 });
        mRenderer.setPanEnabled(false, false);
        mRenderer.setLegendTextSize(20);
        mRenderer.addSeriesRenderer(renderer);
        if(dateL.size() > 1){
	        for(int i = 0; i < dateL.size() - 1; i++){
	        	currentDate = dateL.get(i);
	        	if(!currentDate.equals(prevDate)){
	                mRenderer.addTextLabel(i, dateL.get(i));
	        	}
	        	else{
	        		mRenderer.addTextLabel(i, " ");
	        	}
	        	prevDate = currentDate;
	            mRenderer.setXLabels(0);
	            
	        }
	        mRenderer.addTextLabel(dateL.size()-1, dateL.get(dateL.size()-1));
	        mRenderer.setXLabels(0);
        }
        


		//mRenderer.addSeriesRenderer(renderer2);
		
		mRenderer.setYLabelsAlign(Align.RIGHT);
		mRenderer.setInScroll(true);
		
		mRenderer.setChartTitle(Excercise);
		mRenderer.setChartTitleTextSize(50);
		return mRenderer;
		
	}
	
	private void getRecordsByExcercise(DBAdapter myDB) {
				
		//LinearLayout myLinearLayout = (LinearLayout)findViewById(R.id.logsLayout);
		Cursor cursor = myDB.getAllRows();
		Date sdf = null;
		int i = 0;
		//reset cursor to start to check if there is data
		if(cursor.moveToFirst()){
			//process data 
			do{
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				int numOfReps = cursor.getInt(2);
				int numOfWeight = cursor.getInt(3);
				String date = cursor.getString(4);
				String ExcerciseDB = cursor.getString(1);
				if(ExcerciseDB.equalsIgnoreCase(Excercise)){
					 
					 //weight[i] = numOfWeight;
					 weightL.add(numOfWeight);
						//dates[i] = date;
					 	try 
						{
							sdf = new SimpleDateFormat("yyyy-MM-dd").parse(date);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						String mon = new SimpleDateFormat("MMM").format(sdf);
					 dateL.add(mon + " " + sdf.getDate());
					 i++;
				}				
				//myLinearLayout.addView(message);
				
			}while(cursor.moveToNext());  
			i = i;
		}
	}

}
