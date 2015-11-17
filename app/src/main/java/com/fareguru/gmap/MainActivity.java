package com.fareguru.gmap;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gmap.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
/*import com.google.android.maps.GeoPoint;*/

import android.R.color;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements ConnectionCallbacks,GooglePlayServicesClient.OnConnectionFailedListener,LocationListener, com.google.android.gms.location.LocationListener {
    
    private GoogleMap mMap;
LocationClient mLocationClient,lm,mm;

Button button,bdis,bloc,nav;
TextView text;
double curlat,curlng;
double golat,golng,b;
String senderTel,stri;

JSONObject json;
JSONArray contacts = null;
AutoCompleteTextView ed,src;
String search_text;
ArrayList<String> names;
ArrayAdapter<String> adp;
ProgressDialog pDialog;
ImageView iv,iv1;
private static String url_create_product = "http://www.engyin.com/fareguru/data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
    
        ed=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);

        src=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        
        ed.setBackgroundColor(color.white);
       		ed.setThreshold(1);
       		
       		src.setThreshold(1);
		names=new ArrayList<String>();
		
		ed.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				try {
					geoLocate(view);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		
		});
	
		src.setOnTouchListener(new View.OnTouchListener(){
       	 

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				src.showDropDown();
				
				
				return false;
			}
        	}); 
	 
		src.addTextChangedListener(new TextWatcher()
	  {

	   public void afterTextChanged(Editable s)
	   {

	   }

	   public void beforeTextChanged(CharSequence s, int start,
	    int count, int after)
	   {

	   }

	   public void onTextChanged(CharSequence s, int start,int before, int count)
	   {
		   
		   search_text=src.getText().toString();
		  
		   GetsrcPlaces ta = new GetsrcPlaces();
		   if(search_text.length()>=3)
		   {
		    //now pass the argument in the textview to the task
		    ta.execute(src.getText().toString());
		   }                                
	
		 
	   
	   }
	  });
		
		
		 ed.setOnTouchListener(new View.OnTouchListener(){
        	 

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					ed.showDropDown();
					return false;
				}
	        	}); 
		 
		 
			ed.addTextChangedListener(new TextWatcher()
		  {

		   public void afterTextChanged(Editable s)
		   {
			  
		   }

		   public void beforeTextChanged(CharSequence s, int start,
		    int count, int after)
		   {

		   }

		   public void onTextChanged(CharSequence s, int start,int before, int count)
		   {
			   search_text=ed.getText().toString();
			   
			   GetPlaces task = new GetPlaces();
			   if(search_text.length()>=3)
			   {
			   
			    task.execute(ed.getText().toString());
			   
			    
			   }
		   }
		  });
			
	
        button=(Button) findViewById(R.id.button1);
        bdis=(Button) findViewById(R.id.dis);
         iv=(ImageView) findViewById(R.id.cross);
         iv1=(ImageView) findViewById(R.id.cross1);
        setUpMapIfNeeded();
        
        mLocationClient=new LocationClient(this,this,this);
        mLocationClient.connect();
        
        
        iv1.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    		src.setText("");	
    		}
    	});
       iv.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		ed.setText("");	
		}
	});
    ed.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
		
		}
	
    });
    
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	
            	try
            	{
            		
            		 if(ed.getText()==null)
 				    {
 				    	Toast.makeText(MainActivity.this, "Select Destination",Toast.LENGTH_LONG).show();
 				    }
            		 else
            		 {
            			 
            			 
            		geoLocate(v);
            		geoLocatesrc(v);
            		
            		 }
				
			       
			       
            	}
            catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
            	
            	
				     
            }
            
            
            
        });
        bdis.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					
				
				    if((golat==0.0)&&(golng==0.0))
				    {
				    	Toast.makeText(MainActivity.this, "Select Destination",Toast.LENGTH_LONG).show();
				    }
				    else
				    {
					new ReadDistanceJSONFeedTask().execute("http://maps.googleapis.com/maps/api/distancematrix/json?origins="+curlat+","+curlng+"&destinations="+golat+","+golng);
				    }
			}
		});
        
    
        
    }
    
   

    @Override
    protected void onResume() {
        super.onResume();
       
        setUpMapIfNeeded();
        
    }

   
    private void setUpMapIfNeeded() {
    	
    	
    	
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            
            
          
		
        }
            
            
            if (mMap != null) {
                setUpMap();
            }
       
           
    }

    
   
    private void setUpMap() {
    	
     	
        mMap.setMyLocationEnabled(true);
       
    	
        
    
    
    }

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
		LocationRequest req=LocationRequest.create();
		req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		req.setInterval(60000);
		req.setFastestInterval(60000);
		
		


        	mLocationClient.requestLocationUpdates(req,this);     
		 	
       
        	
		gotoCurrentLocation();
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	private void gotoLocation(double lat,double lng,float zoom)
	{
	
	LatLng ll=new LatLng(lat,lng);
	CameraUpdate update=CameraUpdateFactory.newLatLngZoom(ll,zoom);
	mMap.moveCamera(update);
	
	}
	
	public void geoLocatesrc(View v) throws Exception
	{
		hideSoftKeyboard(v);
		
		String loc=src.getText().toString();
		
		Geocoder gc=new Geocoder(this);
		List<Address> list=gc.getFromLocationName(loc,1);
		
		Address add=list.get(0);
		String locality=add.getLocality();
		
		
		Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
		
		 curlat=add.getLatitude();
		 curlng=add.getLongitude();
		gotoLocation(curlat,curlng,10);
		
		System.out.println("cur"+curlat);
		System.out.println("cur"+curlng);
		
		MarkerOptions options=new MarkerOptions()
		  .title("Source Location")
		  .position(new LatLng(curlat,curlng));
		
		mMap.addMarker(options);
		
		
	}
	
	public void geoLocate(View v) throws Exception
	{
		hideSoftKeyboard(v);
		
		String loc=ed.getText().toString();
		
		Geocoder gc=new Geocoder(this);
		List<Address> list=gc.getFromLocationName(loc,1);
		
		Address add=list.get(0);
		String locality=add.getLocality();
		
		
		Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
		
		 golat=add.getLatitude();
		 golng=add.getLongitude();
		gotoLocation(golat,golng,10);
		
		System.out.println("go"+golat);
		System.out.println("go"+golng);
		
		MarkerOptions options=new MarkerOptions()
		  .title("Destination Location")
		  .position(new LatLng(golat,golng));
		
		mMap.addMarker(options);
		
		
	}
	private String getResponse(String URL) throws Exception {
        InputStream stream = new URL(URL).openStream();
        byte[] array = new byte[stream.available()];
        stream.read(array);
        return new String(array);
}
	private void hideSoftKeyboard(View v)
	{
		InputMethodManager imm=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		
	}
	protected void gotoCurrentLocation()
	{
		Location currentLocation=mLocationClient.getLastLocation();
		
		
		
		
		if(currentLocation==null)
		{
			Toast.makeText(this, "ENABLE YOUR LOCATION SERVICES",Toast.LENGTH_SHORT).show();
			
        
		}
		
		
			
		else
		{
			 curlat=currentLocation.getLatitude();
			curlng=currentLocation.getLongitude();
			LatLng l=new LatLng(curlat,curlng);
			
			System.out.println(curlat);
			System.out.println(curlng);
			
			CameraUpdate upd=CameraUpdateFactory.newLatLngZoom(l, 10);
			mMap.animateCamera(upd);
			mMap.addMarker(new MarkerOptions().position(l).title("Current Location"));
		       
			
	       
		}
		
		
	}
	
	
	

	public static JSONObject getJSONfromURL(String url){

		//initialize
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;

		//http post
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
		}

		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
		}catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}

		//try parse the string to a JSON object
		try{
	        	jArray = new JSONObject(result);
		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
		}

		return jArray;
	} 



private class ReadDistanceJSONFeedTask extends AsyncTask<String, Void, String>
{       
        @Override
        protected String doInBackground(String... urls) {
            // TODO Auto-generated method stub
            return readJSONFeed(urls[0]);
        }

        @Override
        protected void onPostExecute(String res)
        {
            try
            {   //Accessing the JSON Results
                JSONObject jsonObject = new JSONObject(res);

                JSONArray rows = jsonObject.getJSONArray("rows");
                Log.i("json", rows.toString());

                JSONObject obj = rows.getJSONObject(0);

                JSONArray elements = obj.getJSONArray("elements");

                JSONObject ini_dis = elements.getJSONObject(0);

                JSONObject distance = ini_dis.getJSONObject("distance");
                Log.i("json", distance.toString());

                //Distance to send
                String actual_distance = distance.getString("text");

                //Calculation part
                String solve = distance.getString("value");
               
               
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                addresses = geocoder.getFromLocation(curlat, curlng, 1);

                String curloc = addresses.get(0).getAddressLine(0);
                
                Geocoder geocode;
                List<Address> addresse;
                geocode = new Geocoder(MainActivity.this, Locale.getDefault());
                addresse = geocoder.getFromLocation(golat, golng, 1);
                String destloc = addresse.get(0).getAddressLine(0);
            	
                
                
                String digits = actual_distance.replaceAll("[^0-9.]", "");
            	
            	Intent n=new Intent("android.intent.action.SECOND");
            	n.putExtra("roaddistance", digits);
            	Bundle b = new Bundle();
            	
            	b.putDouble("curlat",curlat);
            	b.putDouble("curlng",curlng);
            	b.putDouble("golat",golat);
            	b.putDouble("golng",golng);
            	n.putExtras(b);
            	
            	
            	
            	/*LatLng curloc=new LatLng(curlat,curlng);
            	LatLng destloc=new LatLng(golat,golng);
            	*/
            	
            	n.putExtra("curloc",curloc);
            	n.putExtra("destloc",destloc);
            	startActivity(n);
              

            }
            catch(Exception e)
            {
                Log.d("ReadDistanceJSONFeedTask", e.getLocalizedMessage());
            }
        
          
        
        }

            public String readJSONFeed(String URL){
            //Initial steps to getting the JSON Result
            StringBuilder stringBuilder = new StringBuilder();
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(URL);
            try
            {
                HttpResponse response = httpClient.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                //statusCodes at 200 are OK_Statuses
                if(statusCode==200)
                {
                    HttpEntity entity = response.getEntity();
                    InputStream inputStream = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while((line=reader.readLine())!=null)
                    {
                        stringBuilder.append(line);

                    }
                    inputStream.close();
                }
                else
                {
                    Log.d("readJSONFeed","Failed to download file");
                }
            }
            catch(Exception e)
            {
                Log.d("readJSONFeed", e.getLocalizedMessage());
            }
                return stringBuilder.toString();
        }



}






class GetPlaces extends AsyncTask<String, Void, ArrayList<String>>
{
	
	
	@Override
	
	protected void onPreExecute() {
	    super.onPreExecute();
	
	    pDialog = new ProgressDialog(MainActivity.this);
	    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pDialog.setMessage("Places Are Loading...");
	    pDialog.setIndeterminate(false);
	    pDialog.setCancelable(false);
	    pDialog.show();
	}
	
@Override
               // three dots is java for an array of strings
protected ArrayList<String> doInBackground(String... args)
{

Log.d("gottaGo", "doInBackground");




ArrayList<String> predictionsArr = new ArrayList<String>();

try
{
	URL googlePlaces=new URL("https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+search_text+"&location=28.63404,77.282992&radius=10000&offset=3&key=AIzaSyDaH1pWTBQxkEZs-6R4EpjAkYTCTex-0cA");
		
	System.out.println(googlePlaces);
	URLConnection tc = googlePlaces.openConnection();
	BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));

            String line;
            StringBuffer sb = new StringBuffer();
                            //take Google's legible JSON and turn it into one big string.
            while ((line = in.readLine()) != null) {
            sb.append(line);
            System.out.println(sb);
            }
                            //turn that string into a JSON object
            JSONObject predictions = new JSONObject(sb.toString());	
                           //now get the JSON array that's inside that object     
            
            JSONArray ja = new JSONArray(predictions.getString("predictions"));

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                                    //add each entry to our array
                    predictionsArr.add(jo.getString("description"));

                }
} catch (IOException e)
{

Log.e("YourApp", "GetPlaces : doInBackground", e);

} catch (JSONException e)
{

Log.e("YourApp", "GetPlaces : doInBackground", e);

}

return predictionsArr;

}

//then our post

@Override
protected void onPostExecute(ArrayList<String> result)
{

	pDialog.dismiss();
Log.d("YourApp", "onPostExecute : " + result.size());
//update the adapter

ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_dropdown_item);
adapter.setNotifyOnChange(true);


ed.showDropDown();

//attach the adapter to textview
ed.setAdapter(adapter);

for (String string : result)
{

Log.d("YourApp", "onPostExecute : result = " + string);
adapter.add(string);
adapter.notifyDataSetChanged();


}

Log.d("YourApp", "onPostExecute : autoCompleteAdapter" + adapter.getCount());
if (adapter.getCount()==0)
{
    ed.dismissDropDown();
}

}

}


class GetsrcPlaces extends AsyncTask<String, Void, ArrayList<String>>
{
	
	
	@Override
	protected void onPreExecute() {
	    super.onPreExecute();
	    pDialog = new ProgressDialog(MainActivity.this);
	    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pDialog.setMessage("Places Are Loading...");
	    pDialog.setIndeterminate(false);
	    pDialog.setCancelable(false);
	    pDialog.show();
	}
	
@Override
               // three dots is java for an array of strings
protected ArrayList<String> doInBackground(String... args)
{

Log.d("gottaGo", "doInBackground");




ArrayList<String> predictionsArr = new ArrayList<String>();

try
{
	URL googlePlaces=new URL("https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+search_text+"&location=28.63404,77.282992&radius=10000&offset=3&key=AIzaSyDaH1pWTBQxkEZs-6R4EpjAkYTCTex-0cA");
		
	System.out.println(googlePlaces);
	URLConnection tc = googlePlaces.openConnection();
	BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));

            String line;
            StringBuffer sb = new StringBuffer();
                            //take Google's legible JSON and turn it into one big string.
            while ((line = in.readLine()) != null) {
            sb.append(line);
            System.out.println(sb);
            }
                            //turn that string into a JSON object
            JSONObject predictions = new JSONObject(sb.toString());	
                           //now get the JSON array that's inside that object     
            
            JSONArray ja = new JSONArray(predictions.getString("predictions"));

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                                    //add each entry to our array
                    predictionsArr.add(jo.getString("description"));

                }
} catch (IOException e)
{

Log.e("YourApp", "GetPlaces : doInBackground", e);

} catch (JSONException e)
{

Log.e("YourApp", "GetPlaces : doInBackground", e);

}

return predictionsArr;

}

//then our post

@Override
protected void onPostExecute(ArrayList<String> result)
{

	pDialog.dismiss();
Log.d("YourApp", "onPostExecute : " + result.size());
//update the adapter

ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_dropdown_item);
adapter.setNotifyOnChange(true);
src.showDropDown();



//attach the adapter to textview
src.setAdapter(adapter);

for (String string : result)
{

Log.d("YourApp", "onPostExecute : result = " + string);

adapter.add(string);
adapter.notifyDataSetChanged();



}




Log.d("YourApp", "onPostExecute : autoCompleteAdapter" + adapter.getCount());
    if (adapter.getCount()==0)
    {
        src.dismissDropDown();
    }

}

}
}
