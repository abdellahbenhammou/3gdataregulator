package droid.father.gdataregulator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView enable_time, disable_time;
	SharedPreferences sharedPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sharedPref = getApplicationContext().getSharedPreferences(getApplicationContext().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		final SharedPreferences.Editor editor = sharedPref.edit();

		enable_time = (TextView) findViewById(R.id.enableTime);
		enable_time.setText(sharedPref.getInt("enable_hours", 0)+" hours " +sharedPref.getInt("enable_minutes", 0) + " minutes" );
		disable_time = (TextView) findViewById(R.id.disableTime);
		disable_time.setText(sharedPref.getInt("disable_hours", 0)+" hours " +sharedPref.getInt("disable_minutes", 0) + " minutes" );
		Button setenable = (Button) findViewById(R.id.enable);
		setenable.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), SetEnableTime.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(i);

			}
		});

		Button setdisable = (Button) findViewById(R.id.disable);
		setdisable.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), SetDisableTime.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(i);

			}
		});

		Switch on_off = (Switch) findViewById(R.id.switch1);
		on_off.setChecked(sharedPref.getBoolean("on_off", false));
		on_off.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "changed to :" +(isChecked ? "ON" :"OFF"), Toast.LENGTH_SHORT).show();
				Log.i("data", "Changed " +(isChecked ? "ON": "OFF"));
				editor.putBoolean("on_off", isChecked);
				editor.apply();
				
			}
		});
		//on_off.set


		final Button b = (Button) findViewById(R.id.button1);
		final Button save = (Button) findViewById(R.id.button2);

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(b.getText().toString().equals("enable")){
					// TODO Auto-generated method stub
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
					b.setText("disable");
					Toast.makeText(getApplicationContext(), "enabled", Toast.LENGTH_SHORT).show();

				}
				else if(b.getText().toString().equals("disable")){
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
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
					b.setText("enable");
					Toast.makeText(getApplicationContext(), "disabled", Toast.LENGTH_SHORT).show();
				}

			}
		});

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Calendar cal = Calendar.getInstance();
				Intent intent = new Intent(getApplicationContext(), Regulator.class);
				PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);
				AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				// 
				int hours_e = sharedPref.getInt("enable_hours", 0);
				int minutes_e = sharedPref.getInt("enable_minutes", 0);
				
				int hours_d = sharedPref.getInt("disable_hours", 0);
				int minutes_d = sharedPref.getInt("disable_minutes", 0);
				
				int duration = (hours_e * 3600 + minutes_e * 60 + hours_d * 3600 + minutes_d * 60) * 1000;
				Log.i("data", "enable hours: " + hours_e + " - enable minutes: " + minutes_e + "- Duration: " + duration);
				Log.i("data", "disable hours: " + hours_d + " - disable minutes: " + minutes_d + "- Duration: " + duration);
				alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), duration, pintent); 
			
			}
		});
	}


	@Override
	protected void onResume() {
		super.onResume();
		enable_time.setText(sharedPref.getInt("enable_hours", 0)+" hours " +sharedPref.getInt("enable_minutes", 0) + " minutes" );
		disable_time.setText(sharedPref.getInt("disable_hours", 0)+" hours " +sharedPref.getInt("disable_minutes", 0) + " minutes" );
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
