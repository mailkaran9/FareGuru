package com.fareguru.gmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.gmap.R;


public class Start extends Activity {

	@Override
	protected void onCreate(Bundle abc) {
		// TODO Auto-generated method stub
		super.onCreate(abc);

	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.auto);
		Thread timer=new Thread()
		{
			public void run()
			{
				try
				{
					sleep(1000);
				} 
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				finally
				{
					Intent openStartingPoint=new Intent("android.intent.action.MAP");
					startActivity(openStartingPoint);
				
				}
			}
		
		
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
		
	}
	
	
	


}
