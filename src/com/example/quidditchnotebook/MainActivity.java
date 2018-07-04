package com.example.quidditchnotebook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	private Spinner spinner1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		spinner1=(Spinner)findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.main_menu));
		spinner1.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	public void move(View v)
	{
		ArrayList<Game> games = processGameFile();
		ArrayList<Team> teams = processTeamFile();
		ArrayList<Tournament> tournaments = processTournamentFile();
		Intent i=new Intent();
		Spinner spinner = (Spinner)findViewById(R.id.spinner1);
		switch(spinner.getSelectedItemPosition())
		{
		case 0:	i= new Intent(MainActivity.this,AddGameActivity.class);
				break;
		case 1: i= new Intent(MainActivity.this,AddTeamActivity.class);
				break;
		case 2: i = new Intent(MainActivity.this,AddTournamentActivity.class);
				break;
		case 3: i = new Intent(MainActivity.this,ModifyGameActivity.class);
				break;
		case 4: i = new Intent(MainActivity.this,ModifyTeamActivity.class);
				break;
		case 5: i = new Intent(MainActivity.this,ModifyTournamentActivity.class);
				break;
		case 6:	i= new Intent(MainActivity.this,ViewGameActivity.class);
				break;
		case 7: i= new Intent(MainActivity.this,ViewTeamActivity.class);
				break;
		case 8: i = new Intent(MainActivity.this,ViewTournamentActivity.class);
				break;
		case 9: i = new Intent(MainActivity.this,QuickStatsActivity.class);
				break;
		case 10: i = new Intent(Intent.ACTION_VIEW,Uri.parse("vnd.youtube://"+ "J6X2TcE3gxw"));
		break;
		case Spinner.INVALID_POSITION: break;
		default:	break;
		
		}
		if(spinner.getSelectedItemPosition()>=0&&spinner.getSelectedItemPosition()<=8)
		{
			i.putExtra("games",games);
			i.putExtra("teams",teams);
			i.putExtra("tournaments", tournaments);
			startActivity(i);
			  
		}
		else if (spinner.getSelectedItemPosition()==9||spinner.getSelectedItemPosition()==10)
		{
			startActivity(i);
		}
		
		
	}
	private ArrayList<Team> processTeamFile()
	{
		ArrayList<Team> teams = new ArrayList<Team>();
		try{
			InputStream instream = openFileInput("teams.txt");
			if(instream != null)
			{
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line="";
				String[] splitt;
				while((line=buffreader.readLine())!=null)
				{
					splitt=line.split(";");
					teams.add(new Team(Integer.parseInt(splitt[0]),splitt[1],Integer.parseInt(splitt[2]),splitt[3]));
					Log.w("Team", splitt[1]);//LOGLOGLOG
					Log.w("Team",String.valueOf(Integer.parseInt(splitt[0])));
					Log.w("Team",String.valueOf(Integer.parseInt(splitt[2])));
				}
				
			}
			 
			return teams;
		}
		catch(IOException f)
		{
			return teams;
		}
		
	}
	private ArrayList<Game> processGameFile()
	{
		ArrayList<Game> games = new ArrayList<Game>();
		try{
			InputStream instream = openFileInput("games.txt");
			if(instream != null)
			{
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line="";
				String[] splitt;
				while((line=buffreader.readLine())!=null)
				{
					splitt=line.split(";");
					games.add(new Game(Integer.parseInt(splitt[0]),Integer.parseInt(splitt[1]),Integer.parseInt(splitt[2])
							,Integer.parseInt(splitt[3]),Integer.parseInt(splitt[4]),splitt[5]));
				}
				
			}
			return games;
		}
		catch(IOException f)
		{
			return games;
		}
		
	}
	private ArrayList<Tournament> processTournamentFile()
	{
		ArrayList<Tournament> tournaments = new ArrayList<Tournament>();
		try{
			InputStream instream = openFileInput("tournaments.txt");
			if(instream != null)
			{
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line="";
				String[] splitt;
				while((line=buffreader.readLine())!=null)
				{
					Log.w("lol",line);
					splitt=line.split(";");
					tournaments.add(new Tournament(Integer.parseInt(splitt[0]),Integer.parseInt(splitt[1])
							,splitt[2],splitt[3],(Integer.parseInt(splitt[4])==1)));
				}
				
			}
			return tournaments;
		}
		catch(IOException f)
		{
			return tournaments;
		}
		
	}


}
