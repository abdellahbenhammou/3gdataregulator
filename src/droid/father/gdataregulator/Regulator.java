package droid.father.gdataregulator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Regulator extends Service {

	SharedPreferences sharedPref;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		//---------------------------------
		if(sharedPref.getBoolean("on_off", false)){
			work w = new work();
			Log.i("data", "before ACTIVE...");
			w.execute("");
		}
		//---------------------------------
		else{
			Log.i("data", "NOT ACTIVE...");
		}
		Log.i("data", "STOPPED...");
		stopSelf();

		return Service.START_NOT_STICKY;
	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	public class work extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.i("data", "ACTIVE...");
			int hours = sharedPref.getInt("disable_hours", 0);
			int minutes = sharedPref.getInt("disable_minutes", 0);
			int duration = (hours * 3600 + minutes * 60)*1000;
			Log.i("data", "disable hours: " + hours + " - disable minutes: " + minutes);
			final ConnectivityManager conman = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			Class conmanClass = null;
			try {
				conmanClass = Class.forName(conman.getClass().getName());
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Field iConnectivityManagerField = null;
			try {
				iConnectivityManagerField = conmanClass.getDeclaredField("mService");
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			iConnectivityManagerField.setAccessible(true);
			Object iConnectivityManager = null;
			try {
				iConnectivityManager = iConnectivityManagerField.get(conman);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Class iConnectivityManagerClass = null;
			try {
				iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Method setMobileDataEnabledMethod = null;
			try {
				setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setMobileDataEnabledMethod.setAccessible(true);

			try {
				Log.i("data", "before enabled");
				setMobileDataEnabledMethod.invoke(iConnectivityManager, true);
				Log.i("data", "enabled");
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Toast.makeText(getApplicationContext(), "enabled", Toast.LENGTH_SHORT).show();
			try {
				Log.i("data", "this is the sleeping duration: "+duration);
				Thread.sleep(duration);
				setMobileDataEnabledMethod.setAccessible(true);

				try {
					Log.i("data", "before disabled");
					setMobileDataEnabledMethod.invoke(iConnectivityManager, false);
					Log.i("data", "disabled");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Toast.makeText(getApplicationContext(), "disabled", Toast.LENGTH_SHORT).show();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}}


}
