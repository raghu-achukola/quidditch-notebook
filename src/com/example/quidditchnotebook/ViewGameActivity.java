package com.example.quidditchnotebook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.Build;

public class ViewGameActivity extends ActionBarActivity {
	Spinner regionSpinner1;	//
	Spinner regionSpinner2;	//
	Spinner teamSpinner1;
	Spinner teamSpinner2;
	Spinner seasonSpinner;
	Spinner tournamentSpinner;
	Spinner gameTypeSpinner;	//
	ArrayList<Team> teams;
	 ArrayList<Game> games;
	  ArrayList<Tournament> tournaments;
	  int season;
	  boolean isTeam1=false;
	  boolean isTeam2=false;
	  boolean isTournament=false;
	  boolean isGameLevel=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_game);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		Bundle b = getIntent().getExtras();
		 games = (ArrayList<Game>)b.get("games");
		 teams = (ArrayList<Team>)b.get("teams");
		tournaments = (ArrayList<Tournament>)b.get("tournaments");
		
		((TextView)findViewById(R.id.textView1)).setText("Enter any search criteria");
	
	
		Button button = (Button)findViewById(R.id.button1);
		button.setText("Search");
		regionSpinner1=(Spinner)findViewById(R.id.spinner1);
		ArrayAdapter<String> adapterRS1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.regions_plus_blank));
		regionSpinner1.setAdapter(adapterRS1);
		regionSpinner2=(Spinner)findViewById(R.id.spinner3);
		ArrayAdapter<String> adapterRS2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.regions_plus_blank));
		regionSpinner2.setAdapter(adapterRS2);
		gameTypeSpinner=(Spinner)findViewById(R.id.spinner6);
		ArrayAdapter<String> adapterGT= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.game_types_plus_blank));
		gameTypeSpinner.setAdapter(adapterGT);
		
		gameTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
			{
				if(position!=Spinner.INVALID_POSITION&&position!=0)
				{ isGameLevel=true;}
				else
				{isGameLevel=false;}
			}
			public void onNothingSelected(AdapterView<?> parentView)
			{
				isGameLevel=false;
			}
		});
		seasonSpinner = (Spinner) findViewById(R.id.spinnerSeason);
		tournamentSpinner=(Spinner)findViewById(R.id.spinner5);
		ArrayAdapter<String> adapterSeason = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,findSeasons(tournaments));
		seasonSpinner.setAdapter(adapterSeason);
		seasonSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
			{String[] blankArray = {""};
				if(position!=Spinner.INVALID_POSITION&&position!=0)
				{
					season = Integer.parseInt((String)parentView.getAdapter().getItem(position));
					ArrayAdapter<String> adapterTOURN = new ArrayAdapter<String>(ViewGameActivity.this,android.R.layout.simple_spinner_item,
							getTourneyArray(tournaments,season));
					tournamentSpinner.setAdapter(adapterTOURN);
					isTournament=true;
				}
				else
				{
					tournamentSpinner.setAdapter(new ArrayAdapter<String>(ViewGameActivity.this, android.R.layout.simple_spinner_item,blankArray));
					isTournament=false;
					
				}
			}
			public void onNothingSelected(AdapterView<?> parentView){isTournament=false;}
			
		});

	
	teamSpinner1 = (Spinner)findViewById(R.id.spinner2);
	teamSpinner2 = (Spinner)findViewById(R.id.spinner4);
	
	
	regionSpinner1.setOnItemSelectedListener(new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
		{String[] blankArray = {""};
			if(position!=Spinner.INVALID_POSITION&&position!=0)
			{
				int region = position;
				ArrayAdapter<String> adapterT1 = new ArrayAdapter<String>(ViewGameActivity.this,android.R.layout.simple_spinner_item,
						getTeamsArray(teams,region));
				teamSpinner1.setAdapter(adapterT1);
				isTeam1=true;
			}
			else
			{
				teamSpinner1.setAdapter(new ArrayAdapter<String>(ViewGameActivity.this, android.R.layout.simple_spinner_item,blankArray));
				isTeam1=false;
			}
		}
		public void onNothingSelected(AdapterView<?> parentView){isTeam1=false;}
	});
	regionSpinner2.setOnItemSelectedListener(new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
		{String[] blankArray = {""};
			if(position!=Spinner.INVALID_POSITION&&position!=0)
			{
				int region = position;
				ArrayAdapter<String> adapterT2 = new ArrayAdapter<String>(ViewGameActivity.this,android.R.layout.simple_spinner_item,
						getTeamsArray(teams,region));
				teamSpinner2.setAdapter(adapterT2);
				isTeam2=true;
			}
			else
			{
				teamSpinner2.setAdapter(new ArrayAdapter<String>(ViewGameActivity.this, android.R.layout.simple_spinner_item,blankArray));
				isTeam2=false;
			}
		}
		public void onNothingSelected(AdapterView<?> parentView){isTeam2 = false;}
	});
	
	}
	private String[] getTeamsArray(ArrayList<Team> t, int region)
	{
		Log.w("region",String.valueOf(region));
		ArrayList<Team> teamArrayList = getRegionTeams(t,region);
		
		ArrayList<String> regionTeams = new ArrayList<String>();
		
		for(Team team:teamArrayList)
		{
			regionTeams.add(team.getName());
		}
		
			 String[] teamsArray = Arrays.copyOf(regionTeams.toArray(), regionTeams.toArray().length, String[].class);
			 return teamsArray;
	}
	private ArrayList<Team> getRegionTeams(ArrayList<Team> t, int region)
	{
		ArrayList<Team> regionTeams = new ArrayList<Team>();
		Log.w("size",""+t.size());
		for(int i=0;i<t.size();i++)
		{
			if(t.get(i).getRegion()==region)
			{
				regionTeams.add(t.get(i));
			}
		}
		return regionTeams;
	}
	public String[] getTourneyArray(ArrayList<Tournament> t, int s)
	{
		if(t.size()==0)
		{
			
			Log.w("tourn",""+"gettout");
			String[] lol = {""};
			return lol;
		}
		ArrayList<Tournament> tourny = t;
		tourny=sort(tourny,s);
		ArrayList<String> sTourny = new ArrayList<String>();
		
		for(int i=0;i<tourny.size();i++)
		{
			
					
			sTourny.add(tourny.get(i).getName());
		}
		String[] tourneyArray = Arrays.copyOf(sTourny.toArray(),sTourny.toArray().length, String[].class);
		return tourneyArray;
	}
	private ArrayList<Tournament> sort(ArrayList<Tournament> tourn, int s)
	{
		ArrayList<Tournament>t =tourn;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Tournament tour1;
		Tournament tour2;
		
		try
		{
		for(int i=0;i<t.size();i++)
		{
			tour1 = t.get(i);
			tour2 = tour1;
			cal1.setTime((df.parse(tour1.getDate())));
			Log.w("tourn",cal1.getTime().toString());
			for(int j=i+1;j<t.size();j++)
			{
				cal2.setTime(df.parse(t.get(j).getDate()));
				Log.w("tourn",cal2.getTime().toString());
				if(cal1.after(cal2))
				{
					tour2 = t.get(j);
					cal1 = cal2;
				}
			}
			
			t.set(i, tour2);
			t.set(t.indexOf(tour2), tour1);
			
		}
		}catch(Exception e)
		{}
		
		for(int i=0;i<t.size();i++)
		{
			if(t.get(i).getSeason()!=s)
			{
				t.remove(i);
			}
		}
		return t;
	}
	private String[] findSeasons(ArrayList<Tournament> tournaments2) {
		ArrayList<Integer> seasons = new ArrayList<Integer>();
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("");
		for(Tournament t: tournaments2)
		{
			if(!seasons.contains(Integer.valueOf(t.getSeason())))
			{
				seasons.add(Integer.valueOf(t.getSeason()));
			}
		}
		Integer min;
		Integer holder;
		
		for(int i = 0; i<seasons.size();i++)
		{
			min = seasons.get(i);
			holder = min;
			for(int j=i+1;j<seasons.size();j++)
			{
				if(seasons.get(j).intValue()<min.intValue())
				{
					min = seasons.get(j);
					
				}
			}
			seasons.set(i, min);
			seasons.set(seasons.indexOf(min), holder);
		}
		for(int i = 0 ; i<seasons.size();i++)
		{
			temp.add(String.valueOf(seasons.get(i).intValue()));
		}
		return Arrays.copyOf(temp.toArray(),temp.toArray().length,String[].class);
		
	}
	public void next(View v)
	{
		boolean error = false;
		int tournamentID = -1;
		try
		{tournamentID = (isTournament)? sort(tournaments,season).get(tournamentSpinner.getSelectedItemPosition()).getId():-1;}
		catch(Exception e){Toast.makeText(getApplicationContext(), "No tournaments exist for that season", Toast.LENGTH_SHORT).show();
		error=true;
		}
		int team1ID=-1;int team2ID=-1;
		try
		{ team1ID = (isTeam1)? getRegionTeams(teams,regionSpinner1.getSelectedItemPosition()).get(teamSpinner1.getSelectedItemPosition()).getId():-1;
		 team2ID = (isTeam2)? getRegionTeams(teams,regionSpinner2.getSelectedItemPosition()).get(teamSpinner2.getSelectedItemPosition()).getId():-1;
		 
		if(team1ID==team2ID&&team1ID!=-1){Toast.makeText(getApplicationContext(), "Please do not select a duplicate team. Either select a different team or put only one team", Toast.LENGTH_LONG).show(); error=true;}}
		catch(Exception e){Toast.makeText(getApplicationContext(), "No teams exist for that region", Toast.LENGTH_SHORT).show();
		error = true;}
		
		int gameLevel = (isGameLevel)? gameTypeSpinner.getSelectedItemPosition():-1;
		Game g = new Game(-1,team1ID,team2ID,tournamentID,gameLevel,null);
		Log.w("mga",String.valueOf(tournamentID));
		Log.w("mga",String.valueOf(team1ID));
		Log.w("mga",String.valueOf(team2ID));
		Log.w("mga",String.valueOf(gameLevel));
		if(!error)
		{Intent i = new Intent(ViewGameActivity.this,ViewAllGamesActivity.class);
		i.putExtra("game", g);
		i.putExtra("games", games);
		i.putExtra("teams",teams);
		i.putExtra("tournaments", tournaments);
		startActivity(i);
		finish();}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_game, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_view_game,
					container, false);
			return rootView;
		}
	}

}
