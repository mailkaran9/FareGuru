package com.fareguru.gmap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
/*
import com.example.gmap.Auto.savedata;*/

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gmap.R;

public class Second extends Activity{
	double af=0,tf=0,tnf=0,f=0,a;
	String ab;
	Button navigate,go,tp;
	TextView tfare,afare,tnfare,distance,msgtext;
	String road,curloc,destloc;
	Spinner spinner;
	String vehicle;
	Double curlat,golat,curlng,golng;
	ProgressDialog pd;
	TextView tv1,tv2,tv3;
	Calendar now;
	int hour;
	Dialog dialog,msg;
	ImageButton b1,b2,b3;
	EditText txt;
	
	
	EditText amount;
	
	private static String url_create_product = "http://www.engyin.com/fareguru/data.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		b1=(ImageButton) findViewById(R.id.imageButton1);
		b2=(ImageButton) findViewById(R.id.imageButton2);
		b3=(ImageButton) findViewById(R.id.imageButton3);
		
		navigate=(Button) findViewById(R.id.navigate);
		
		afare=(TextView) findViewById(R.id.afare);
		tv3=(TextView) findViewById(R.id.tv3);
		tfare=(TextView) findViewById(R.id.tfare);
		tnfare=(TextView) findViewById(R.id.tnfare);
		distance=(TextView) findViewById(R.id.distance);
		
		
		road=getIntent().getStringExtra("roaddistance");
		now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY,23);
		hour = now.get(Calendar.HOUR_OF_DAY);
		
		Bundle b = getIntent().getExtras();
		
		
		curlat=b.getDouble("curlat");
		curlng=b.getDouble("curlng");
		golat=b.getDouble("golat");
		golng=b.getDouble("golng");
		
        curloc = getIntent().getStringExtra("curloc");
        destloc = getIntent().getStringExtra("destloc");
		distance.setText(road);
			
		
		a=Double.parseDouble(distance.getText().toString());
		af=autofare(a);
		tf=taxifare(a);
		tnf=taxinonfare(a);
		
		afare.setText("Rs"+af);
		tfare.setText("Rs"+tf);
		tnfare.setText("Rs"+tnf);
		
		
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = new Dialog(Second.this);
				msg=new Dialog(Second.this);
				vehicle="Auto";
				f=af;
				 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.customdialog);	

				
				txt = (EditText) dialog.findViewById(R.id.text);
				
		        txt.setKeyListener(new DigitsKeyListener().getInstance("1234567890."));
				      
				         Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
				         Button submitButton = (Button) dialog.findViewById(R.id.submit);
						 
				         dialogButton.setOnClickListener(new OnClickListener() {
				
				                   
				        	 public void onClick(View v) {
				
				                        dialog.dismiss();
				
				                    }
				
				                });
				         submitButton.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								ab=txt.getText().toString();
								msg();
							new savedata().execute();	
                           
							dialog.dismiss();
						
							
							}
							
						});
				         dialog.show();
				         

			}
			
			
		});
        
b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = new Dialog(Second.this);
				vehicle="TaxiAC";
				f=tf;
				 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.customdialog);		
				final EditText txt;
				txt = (EditText) dialog.findViewById(R.id.text);

		        txt.setKeyListener(new DigitsKeyListener().getInstance("1234567890."));
				         Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
				         Button submitButton = (Button) dialog.findViewById(R.id.submit);
						 
				         dialogButton.setOnClickListener(new OnClickListener() {
				
				                   
				        	 public void onClick(View v) {
				
				                        dialog.dismiss();
				
				                    }
				
				                });
				         submitButton.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									ab=txt.getText().toString();
									msg();
								new savedata().execute();
								dialog.dismiss();
								}
							});
				         dialog.show();
				         

			}
			
			
		});

b3.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dialog = new Dialog(Second.this);
		vehicle="TaxiNonAC";
		f=tnf;
		 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.customdialog);		
		final EditText txt;
		txt = (EditText) dialog.findViewById(R.id.text);

        txt.setKeyListener(new DigitsKeyListener().getInstance("1234567890."));
		      Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		         Button submitButton = (Button) dialog.findViewById(R.id.submit);
				 
		         dialogButton.setOnClickListener(new OnClickListener() {
		
		                   
		        	 public void onClick(View v) {
		
		                        dialog.dismiss();
		
		                    }
		
		                });
		         submitButton.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ab=txt.getText().toString();
							msg();
						new savedata().execute();	
						dialog.dismiss();
						}
					});
		         dialog.show();
		         

	}
	
	
});
        
       
        
navigate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?" + "saddr="+ curlat + "," + curlng + "&daddr=" + golat + "," + golng));
			                       startActivity(intent);
				
			}
		});
		
		
	}
	
	
	
	
public double autofare(double a)
{
	
	double f;
	if(a==0)
    {
 	   af=0;
    }
		else if(a<=2)
    {
     af=25;
    }
    else
    { 
    af=25+(a-2)*8;
    } 
	
if (!(hour>=5 && hour<23)) 
{
	    af=1.25*af;
	}

	af=Math.ceil(af);
	
  return af;
}

public double taxifare(double a)
{
	double tf;
	if(a==0)
    {
 	   tf=0;
    }
    else if(a<=1)
    {
     tf=25;
    }
    else
    { 
    tf=25+(a-1)*16;
    }
	
	if (!(hour>=5 && hour<23)) 
	{
	    tf=1.25*tf;
	}

	tf=Math.ceil(tf);
	
  return tf;
}

public double taxinonfare(double a)
{
	double tnf;
	 if(a==0)
     {
  	   tnf=0;
     }
     
     else if(a<=1)
     {
      tnf=25;
     }
     else
     { 
     tnf=25+(a-1)*14;
     } 
	 
	 if (!(hour>=5 && hour<23)) 
	 {
		    tnf=1.25*tnf;
		}

	 
	 tnf=Math.ceil(tnf);
  return tnf;
}


private void hideSoftKeyboard(View v)
{
	InputMethodManager imm=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
	imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	
}	
public void msg()
{
	if(ab!=null)
	{
		System.out.println("in msg");
		double ent=Double.parseDouble(ab);
	    
		double e;
		
		e=0.10*f;
		e=Math.ceil(e);
		if(ent<(f+e))
		{
			tv3.setTextColor(Color.MAGENTA);
			tv3.setText("That's Great!Driver seems to be good");
		}
		else
		{
			tv3.setTextColor(Color.RED);
			tv3.setText("Bad Luck.Try and Negotiate");
			
			
		}
	}
}


class savedata extends AsyncTask<String, String, String> {
	 
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(Second.this);
        pd.setMessage("Please Wait");
        pd.setIndeterminate(false);
        pd.setCancelable(true);
        pd.show();
    }

    /**
     * Creating product
     * */
    protected String doInBackground(String... args) {
     /*   String d = distance.getText().toString();*/
     

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
 /*       params.add(new BasicNameValuePair("curloc", curloc));
        params.add(new BasicNameValuePair("destloc",destloc ));
*/
        if((hour>=5)&&(hour<23))
        {
    	   params.add(new BasicNameValuePair("time","day"));
        }
        else
        {
        	 params.add(new BasicNameValuePair("time","night"));
        }
        	
    	  
    	   

    	   params.add(new BasicNameValuePair("distance",road));

        params.add(new BasicNameValuePair("amount",ab ));
        params.add(new BasicNameValuePair("vehicle",vehicle));
        
        params.add(new BasicNameValuePair("fare", Double.toString(f) ));
        
        
        params.add(new BasicNameValuePair("city","delhi"));
        params.add(new BasicNameValuePair("curloc",curloc ));

        params.add(new BasicNameValuePair("destloc",destloc ));
       

        
        // getting JSON Object
        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(url_create_product,"POST", params);
        try
        {
        	int success=json.getInt("success");
        	if(success==1)
        	{ 
        		System.out.println("success");
        	}
        	
        }
        catch(JSONException e)
        {
        	e.printStackTrace();
        }
      
        System.out.println("done");
        
        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog once done
        pd.dismiss();
       

     
       

    }

}
}
