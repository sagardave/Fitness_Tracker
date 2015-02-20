package com.example.test1;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;


import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
        
		//creates a button to clear the values
        Button newLogButton = (Button) findViewById (R.id.newLogsButton);
        newLogButton.setOnClickListener (new View.OnClickListener ()
        {
            public void onClick (View v)
            {
            	Intent createNewLog = new Intent("com.example.test1.NEWLOG");
            	startActivity(createNewLog);
                
            }
        });
        
        Button logs = (Button) findViewById (R.id.createdLogsButton);
        logs.setOnClickListener (new View.OnClickListener ()
        {
            public void onClick (View v)
            {
            	Intent savedLog = new Intent("com.example.test1.SAVEDLOG");
            	startActivity(savedLog);
                
            }
        });
        
        Button progress = (Button) findViewById (R.id.progressButton);
        progress.setOnClickListener (new View.OnClickListener ()
        {
            public void onClick (View v)
            {
            	Intent history = new Intent("com.example.test1.HISTORY");
            	startActivity(history);
                
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
