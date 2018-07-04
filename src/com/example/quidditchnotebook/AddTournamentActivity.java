package com.example.quidditchnotebook;

import java.io.FileOutputStream;
import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class AddTournamentActivity extends ActionBarActivity {


	private ArrayList<Tournament> tournaments;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tournament);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		Bundle b = getIntent().getExtras();
		tournaments = (ArrayList<Tournament>)(b.get("tournaments"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_tournament, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_add_tournament,
					container, false);
			return rootView;
		}
	}
	public void create(View v)
	{
		String name = ((EditText)findViewById(R.id.editText1)).getText().toString();
		
		String date = ((EditText)findViewById(R.id.editText3)).getText().toString();
		boolean day2 = ((CheckBox)findViewById(R.id.checkBox1)).isChecked();
		String write="";
		if(!Tournament.isDateValid(date))
		{
			Toast.makeText(getApplicationContext(), "Invalid Date", Toast.LENGTH_SHORT).show();
		}
		else if(name.contains(";"))
		{
			Toast.makeText(getApplicationContext(),"No semicolons allowed in name ", Toast.LENGTH_SHORT).show();
			
		}
		else if(date.equals("")||(((EditText)findViewById(R.id.editText2)).getText().toString()).equals("")||name.equals(""))
		{
			Toast.makeText(getApplicationContext(), "Name and season must be filled in", Toast.LENGTH_SHORT).show();
		}
		else
		{
			int season = Integer.parseInt(((EditText)findViewById(R.id.editText2)).getText().toString());
		try{
			
			FileOutputStream fos = openFileOutput("tournaments.txt",Context.MODE_APPEND);
			if(tournaments.size()==0)
			{
				write =(day2)? ("1;"+ String.valueOf(season)+";"+name+";"+date+";"+"1"+"\n")
						:(("1;"+ String.valueOf(season)+";"+name+";"+date+";"+"0")+"\n");
				
			}
			else
				{
				write =(day2)? (String.valueOf(Tournament.getLargestID(tournaments)+1)+";"+String.valueOf(season)+";"+name+";"+date+";"+"1"+"\n")
							:(String.valueOf(tournaments.get(tournaments.size()-1).getId()+1)+";"+ String.valueOf(season)+";"+name+";"+date+";"+"0"+"\n");
				}
			Log.w("tourn",write);
				fos.write(write.getBytes());
				
			fos.close();

			
		}catch(Exception e)
		{
			
		}
		finish();
		}
	}

}
