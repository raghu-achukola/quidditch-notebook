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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class AddGameActivity extends ActionBarActivity {

	Spinner regionSpinner1;	//
	Spinner regionSpinner2;	//
	Spinner teamSpinner1;
	Spinner teamSpinner2;
	Spinner seasonSpinner;
	Spinner tournamentSpinner;
	Spinner gameTypeSpinner;	//
	ArrayList<Team> teams;
	ArrayList<Tournament> tournaments;
	ArrayList<Game> games;
	int season;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_game);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		regionSpinner1=(Spinner)findViewById(R.id.spinner1);
		ArrayAdapter<String> adapterRS1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.regions));
		regionSpinner1.setAdapter(adapterRS1);
		regionSpinner2=(Spinner)findViewById(R.id.spinner3);
		ArrayAdapter<String> adapterRS2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.regions));
		regionSpinner2.setAdapter(adapterRS2);
		gameTypeSpinner=(Spinner)findViewById(R.id.spinner6);
		ArrayAdapter<String> adapterGT= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.game_types));
		
		gameTypeSpinner.setAdapter(adapterGT);
		Bundle b = getIntent().getExtras();
		teams = (ArrayList<Team>)(b.get("teams"));
		tournaments = (ArrayList<Tournament>)(b.get("tournaments"));
		games = (ArrayList<Game>)(b.get("games"));

		seasonSpinner = (Spinner) findViewById(R.id.spinnerSeason);
		tournamentSpinner=(Spinner)findViewById(R.id.spinner5);
		ArrayAdapter<String> adapterSeason = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,findSeasons(tournaments));
		seasonSpinner.setAdapter(adapterSeason);
		seasonSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
			{
				if(position!=Spinner.INVALID_POSITION)
				{
					season = Integer.parseInt((String)parentView.getAdapter().getItem(position));
					ArrayAdapter<String> adapterTOURN = new ArrayAdapter<String>(AddGameActivity.this,android.R.layout.simple_spinner_item,
							getTourneyArray(tournaments,season));
					tournamentSpinner.setAdapter(adapterTOURN);
				}
			}
			public void onNothingSelected(AdapterView<?> parentView){}
			
		});
		
		
		teamSpinner1 = (Spinner)findViewById(R.id.spinner2);
		teamSpinner2 = (Spinner)findViewById(R.id.spinner4);
		
		
		regionSpinner1.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
			{
				if(position!=Spinner.INVALID_POSITION)
				{
					int region = position+1;
					ArrayAdapter<String> adapterT1 = new ArrayAdapter<String>(AddGameActivity.this,android.R.layout.simple_spinner_item,
							getTeamsArray(teams,region));
					teamSpinner1.setAdapter(adapterT1);
				}
			}
			public void onNothingSelected(AdapterView<?> parentView){}
		});
		regionSpinner2.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
			{
				if(position!=Spinner.INVALID_POSITION)
				{
					int region = position+1;
					ArrayAdapter<String> adapterT2 = new ArrayAdapter<String>(AddGameActivity.this,android.R.layout.simple_spinner_item,
							getTeamsArray(teams,region));
					teamSpinner2.setAdapter(adapterT2);
				}
			}
			public void onNothingSelected(AdapterView<?> parentView){}
		});
		
	}

	private String[] findSeasons(ArrayList<Tournament> tournaments2) {
		ArrayList<Integer> seasons = new ArrayList<Integer>();
		ArrayList<String> temp = new ArrayList<String>();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_game, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_add_game,
					container, false);
			return rootView;
		}
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

	public void next(View v)
	{
		if( regionSpinner1.getSelectedItemPosition()==Spinner.INVALID_POSITION||
				regionSpinner2.getSelectedItemPosition()==Spinner.INVALID_POSITION||
						teamSpinner1.getSelectedItemPosition()==Spinner.INVALID_POSITION||
								teamSpinner2.getSelectedItemPosition()==Spinner.INVALID_POSITION||
										tournamentSpinner.getSelectedItemPosition()==Spinner.INVALID_POSITION||
												gameTypeSpinner.getSelectedItemPosition()==Spinner.INVALID_POSITION)
		{
			Toast.makeText(getApplicationContext(), "Fields cannot be left blank", Toast.LENGTH_SHORT).show();
			Log.w("game","data");
		}
		else if (teamSpinner1.getSelectedItemPosition()==
								teamSpinner2.getSelectedItemPosition() &&regionSpinner1.getSelectedItemPosition()==regionSpinner2.getSelectedItemPosition())
		{
			Toast.makeText(getApplicationContext(), "Please choose two different teams", Toast.LENGTH_SHORT).show();
		}
		else
		{
			int region1 = regionSpinner1.getSelectedItemPosition()+1;
			Team t1 = getRegionTeams(teams,region1).get(teamSpinner1.getSelectedItemPosition());
			int region2 = regionSpinner2.getSelectedItemPosition()+1;
			Team t2 = getRegionTeams(teams,region2).get(teamSpinner2.getSelectedItemPosition());
			Tournament t = sort(tournaments,season).get(tournamentSpinner.getSelectedItemPosition());
			int gameType = gameTypeSpinner.getSelectedItemPosition()+1;
			int id;
			if(games.size()==0){ id = 1;}
			else{id = Game.getLargestID(games)+1;}
			Game g = new Game(id,t1.getId(),t2.getId(),t.getId(),gameType);
			Intent i = new Intent(AddGameActivity.this,AddDataActivity.class);
		//	i.putExtra("id",id);
		//	i.putExtra("team1Id",t1.getId());
		//	i.putExtra("team2Id", t2.getId());
			i.putExtra("team1Name",t1.getName());
			i.putExtra("team2Name",t2.getName());
			i.putExtra("tournamentName", t.getName());
		//	i.putExtra("gameType", gameType);
			i.putExtra("game", g);
			i.putExtra("games",games);
			startActivity(i);  
			
		}
	}
}
