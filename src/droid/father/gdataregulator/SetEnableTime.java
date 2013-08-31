package droid.father.gdataregulator;



import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;

public class SetEnableTime extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_enable_time);
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		final SharedPreferences.Editor editor = sharedPref.edit();
		final NumberPicker hours = (NumberPicker) findViewById(R.id.hours);
		final NumberPicker minutes = (NumberPicker) findViewById(R.id.minutes);
		hours.setMaxValue(180);
		minutes.setMaxValue(59);
		hours.setValue(sharedPref.getInt("enable_hours", 0));
		minutes.setValue(sharedPref.getInt("enable_minutes", 0));
		
		Button save = (Button) findViewById(R.id.button1);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int h = hours.getValue();
				int m = minutes.getValue();
				
				editor.putInt("enable_hours", h);
				editor.putInt("enable_minutes", m);
				editor.apply();
				finish();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_enable_time, menu);
		return true;
	}

}

