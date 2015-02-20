package com.sagarFitnessApp.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.sagarFitnessApp.test1.R;

public class MainActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
        
		//creates a button to clear the values
        ImageButton newLogButton = (ImageButton) findViewById (R.id.newLogsButton);
        newLogButton.setOnClickListener (new View.OnClickListener ()
        {
            public void onClick (View v)
            {
            	Intent createNewLog = new Intent("com.sagarFitnessApp.test1.NEWLOG");
            	startActivity(createNewLog);
                
            }
        });
        
        ImageButton logs = (ImageButton) findViewById (R.id.createdLogsButton);
        logs.setOnClickListener (new View.OnClickListener ()
        {
            public void onClick (View v)
            {
            	Intent savedLog = new Intent("com.sagarFitnessApp.test1.SAVEDLOG");
            	startActivity(savedLog);
                
            }
        });
        
        ImageButton progress = (ImageButton) findViewById (R.id.progressButton);
        progress.setOnClickListener (new View.OnClickListener ()
        {
            public void onClick (View v)
            {
            	Intent history = new Intent("com.sagarFitnessApp.test1.HISTORY");
            	startActivity(history);
                
            }
        });
        
        ImageButton aboutMe = (ImageButton) findViewById (R.id.aboutButton);
        aboutMe.setOnClickListener (new View.OnClickListener ()
        {
            public void onClick (View v)
            {           	
            	Intent about = new Intent("com.sagarFitnessApp.test1.ABOUT");
            	startActivity(about);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
