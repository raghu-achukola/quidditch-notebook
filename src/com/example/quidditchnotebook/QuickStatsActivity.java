package com.example.quidditchnotebook;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class QuickStatsActivity extends ActionBarActivity {

	EditText team1;
	EditText team2;
	EditText data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quick_stats);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		team1 = (EditText)findViewById(R.id.editText1);
		team2 = (EditText)findViewById(R.id.editText2);
		data = (EditText)findViewById(R.id.data);
		team1.setText("Team 1");
		team2.setText("Team 2");
		
		
	}
	public void viewStats(View v){
		
		try{
			
			double[] lol = Statistics.process(data.getEditableText().toString());
			
		Intent i = new Intent (QuickStatsActivity.this,ViewGameStatsActivity.class);
		i.putExtra("game", new Game(-1,-1,-1,-1,-1, data.getEditableText().toString()));
		i.putExtra("t1Name", team1.getEditableText().toString());
		i.putExtra("t2Name",team2.getEditableText().toString());
		i.putExtra("tournName", "");
		startActivity(i);}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "Please enter valid processable data", Toast.LENGTH_LONG).show();
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quick_stats, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_quick_stats,
					container, false);
			return rootView;
		}
	}

}
