package com.example.quidditchnotebook;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class AddTeamActivity extends ActionBarActivity {

	private ArrayList<Team> teams;
	private Spinner spinner1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_team);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		Bundle b = getIntent().getExtras();
		teams = (ArrayList<Team>)(b.get("teams"));
		spinner1=(Spinner)findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.regions));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_team, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_add_team,
					container, false);
			return rootView;
		}
	}
	public void create(View v)
	{
		Spinner spinner = (Spinner)findViewById(R.id.spinner1);
		String team = ((EditText)findViewById(R.id.editText1)).getText().toString();
		String city = ((EditText)findViewById(R.id.editText2)).getText().toString();
		if(team.contains(";")||city.contains(";"))
		{
			Toast t = Toast.makeText(getApplicationContext(),"No semicolons allowed in name or city", Toast.LENGTH_SHORT);
			t.setGravity(Gravity.BOTTOM, 0, 0);
			t.show();
		}
		else if (spinner.getSelectedItemPosition()==Spinner.INVALID_POSITION)
		{
			Toast t = Toast.makeText(getApplicationContext(),"Select a Region", Toast.LENGTH_SHORT);
			t.setGravity(Gravity.BOTTOM, 0, 0);
			t.show();
		}
		else if(team.equals("")||city.equals(""))
		{
			Toast.makeText(getApplicationContext(), "All fields must be filled in", Toast.LENGTH_SHORT).show();
		}
		else
		{
			String write = "";
			if(teams.size()==0)
			{
				write = "1;"+team+";"+String.valueOf(spinner.getSelectedItemPosition()+1)+";"+city+"\n";
				Log.w("teamsp",write);
			}
			else
			{
				write=String.valueOf(Team.getLargestID(teams)+1)+";"
						+team+";"+String.valueOf(spinner.getSelectedItemPosition()+1)+";"+city+"\n";
				Log.w("teamsp",write);
			}
			try{
				FileOutputStream fos = openFileOutput("teams.txt",Context.MODE_APPEND);
				fos.write(write.getBytes());
				fos.close();
				
					
				}
				catch(Exception e)
			{Log.w("teamsp","exc");}
			finish();
		}
	}
}
