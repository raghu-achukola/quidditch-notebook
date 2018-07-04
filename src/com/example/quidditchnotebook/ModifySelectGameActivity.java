package com.example.quidditchnotebook;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import com.example.quidditchnotebook.ViewTournamentActivity.PlaceholderFragment;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.LayoutInflater;

public class ModifySelectGameActivity extends ActionBarActivity implements EditDeleteDialogFragment.EditDeleteListener {

	ArrayList<Team> teams;
	 ArrayList<Game> games;
	  ArrayList<Tournament> tournaments;
	  Game focus;
	  Game selected;
	  ListView listView;
	  GameAdapter ga;
	  ArrayList<Game> modified = new ArrayList<Game>();
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_all_games);
		if(savedInstanceState==null)
		{
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		((TextView)findViewById(R.id.textView1)).setText("Modify Games");
		Bundle b = getIntent().getExtras();
		 games = (ArrayList<Game>)b.get("games");
		 teams = (ArrayList<Team>)b.get("teams");
		tournaments = (ArrayList<Tournament>)b.get("tournaments");
		focus =(Game)b.get("game");
		listView = (ListView)findViewById(R.id.listView1);
		for(Game g: games)
		{
			if(g.hasSameValues(focus))
			{
				Log.w("msga","true");
				modified.add(g);
			}
		}
		ga = new GameAdapter(ModifySelectGameActivity.this, R.layout.game_list_item, modified, teams, tournaments,false);
		listView.setAdapter(ga);
		listView.setOnItemClickListener(new OnItemClickListener() {

	    	 public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
	    		 Game g = (Game)arg0.getAdapter().getItem(position);
	    		 selected = g;
	    		 showEditDeleteDialog();
	    	    

	    	        }
	    	 });
		
	}
	public void showEditDeleteDialog()
	{
		DialogFragment dialog = new EditDeleteDialogFragment();
		dialog.show(getFragmentManager(), "EditDeleteDialogFragment");
	}
	public void onEditDeleteDialogSelect(DialogFragment dialog, int which)
	{
		
		 if(which==1)
		 {
			 
				 AlertDialog.Builder builder = new AlertDialog.Builder(this);
				 builder.setMessage("Are you sure? This action will delete the game and all data and cannot be undone!")
				        .setCancelable(false)
				        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int id) {
				                 //do things
				            	deleteData(selected);
				            }
				        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int id) {
				                 //do things
				            }
				        });
				 AlertDialog alert = builder.create();
				 alert.show();
		 }
		 else
		 {
			 LayoutInflater inflater = getLayoutInflater();
			 View v = inflater.inflate(R.layout.edit_game,null);
			 AlertDialog.Builder hataiShmi = new AlertDialog.Builder(this);
			 hataiShmi.setView(v);
			final Spinner region1 = (Spinner)v.findViewById(R.id.spinner1);
			final Spinner region2 = (Spinner)v.findViewById(R.id.spinner3);
			final Spinner teams1 = (Spinner)v.findViewById(R.id.spinner2);
			final Spinner teams2 = (Spinner)v.findViewById(R.id.spinner4);
			final Spinner seasonSpinner = (Spinner)v.findViewById(R.id.spinnerSeason);
			final Spinner tournamentSpinner = (Spinner)v.findViewById(R.id.spinner5);
			final Spinner gameLevelSpinner = (Spinner)v.findViewById(R.id.spinner6);
			final EditText gameData = (EditText)v.findViewById(R.id.editText1);
			
			ArrayAdapter<String> adapterRS1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
					getResources().getStringArray(R.array.regions));
			region1.setAdapter(adapterRS1);
			ArrayAdapter<String> adapterRS2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
					getResources().getStringArray(R.array.regions));
			region2.setAdapter(adapterRS2);
			ArrayAdapter<String> adapterSeason = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,findSeasons(tournaments));
			ArrayAdapter<String> adapterGT= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
					getResources().getStringArray(R.array.game_types));
			gameLevelSpinner.setAdapter(adapterGT);
			seasonSpinner.setAdapter(adapterSeason);
			seasonSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
				
				public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
				{
					if(position!=Spinner.INVALID_POSITION)
					{
						int season = Integer.parseInt((String)parentView.getAdapter().getItem(position));
						ArrayAdapter<String> adapterTOURN = new ArrayAdapter<String>(ModifySelectGameActivity.this,android.R.layout.simple_spinner_item,
								getTourneyArray(tournaments,season));
						tournamentSpinner.setAdapter(adapterTOURN);
					}
				}
				public void onNothingSelected(AdapterView<?> parentView){}
				
			});
			
			

			
			
			region1.setOnItemSelectedListener(new OnItemSelectedListener()
			{
				@Override
				public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
				{
					if(position!=Spinner.INVALID_POSITION)
					{
						int region = position+1;
						ArrayAdapter<String> adapterT1 = new ArrayAdapter<String>(ModifySelectGameActivity.this,android.R.layout.simple_spinner_item,
								getTeamsArray(teams,region));
						teams1.setAdapter(adapterT1);
					}
				}
				public void onNothingSelected(AdapterView<?> parentView){}
			});
			region2.setOnItemSelectedListener(new OnItemSelectedListener()
			{
				@Override
				public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
				{
					if(position!=Spinner.INVALID_POSITION)
					{
						int region = position+1;
						ArrayAdapter<String> adapterT2 = new ArrayAdapter<String>(ModifySelectGameActivity.this,android.R.layout.simple_spinner_item,
								getTeamsArray(teams,region));
						teams2.setAdapter(adapterT2);
					}
				}
				public void onNothingSelected(AdapterView<?> parentView){}
			});
			
			region1.setSelection(teams.get(selected.getFirstTeam()-1).getRegion()-1);
			teams1.setSelection(getRegionTeams(teams,region1.getSelectedItemPosition()+1).indexOf(teams.get(selected.getFirstTeam()-1)));
			region2.setSelection(teams.get(selected.getSecondTeam()-1).getRegion()-1);
			teams2.setSelection(getRegionTeams(teams,region2.getSelectedItemPosition()+1).indexOf(teams.get(selected.getSecondTeam()-1)));
			int position = Spinner.INVALID_POSITION;
			for(int i = 0; i<seasonSpinner.getAdapter().getCount();i++)
			{
				if(String.valueOf(tournaments.get(selected.getTournamentID()-1).getSeason()).equals((String)seasonSpinner.getAdapter().getItem(i)))
						{
								position = i;
								break;
						}
			}
			seasonSpinner.setSelection(position);
			tournamentSpinner.setSelection(sort(tournaments,Integer.parseInt((String)seasonSpinner.getSelectedItem())).indexOf(tournaments.get(selected.getTournamentID()-1)));
			gameLevelSpinner.setSelection(selected.getGameLevel()-1);
			gameData.setText(selected.getData());
			
			 Button set = (Button)v.findViewById(R.id.button1);
			 final AlertDialog whyMe = hataiShmi.create();
			 set.setOnClickListener(new OnClickListener(){
				 
				 public void onClick(View view)
				 {
					
					 /*
					  * 
					  * Data Testing
					  *  
					  */
					 
					 if(teams1.getSelectedItemPosition()==Spinner.INVALID_POSITION||teams2.getSelectedItemPosition()==Spinner.INVALID_POSITION)
					 {
						 Toast.makeText(getApplicationContext(), "Please select a team", Toast.LENGTH_SHORT).show();
						 Log.w("mod","1");
					 }
					 else if (tournamentSpinner.getSelectedItemPosition()==Spinner.INVALID_POSITION)
					 {
						 Toast.makeText(getApplicationContext(), "Please select a tournament", Toast.LENGTH_SHORT).show();
						 Log.w("mod","2");
					 }
					 else if (gameLevelSpinner.getSelectedItemPosition()==Spinner.INVALID_POSITION)
					 {
						 Toast.makeText(getApplicationContext(), "Please select a tournament", Toast.LENGTH_SHORT).show();
						 Log.w("mod","3");
					 }
					 else if(!Statistics.isDataProcessable(gameData.getEditableText().toString()))
					 {
						 Toast.makeText(getApplicationContext(), "That data is not valid", Toast.LENGTH_SHORT).show();
						 Log.w("mod","4");
					 }
					 else
					 {
					 Team t1 = getRegionTeams(teams,region1.getSelectedItemPosition()+1).get(teams1.getSelectedItemPosition());
					 Team t2 = getRegionTeams(teams,region2.getSelectedItemPosition()+1).get(teams2.getSelectedItemPosition());
					 Tournament t = sort(tournaments,Integer.parseInt((String)seasonSpinner.getSelectedItem())).get(tournamentSpinner.getSelectedItemPosition());
					 String data = gameData.getEditableText().toString();
					 int gameType = gameLevelSpinner.getSelectedItemPosition()+1;			
					 
					 /*
					  * 
					  * Rewriting data
					  * 
					  */
					 int index = games.indexOf(selected);
					 games.get(index).setData(data);
					 games.get(index).setFirstTeam(t1.getId());
					 games.get(index).setSecondTeam(t2.getId());
					 games.get(index).setGameLevel(gameType);
					 games.get(index).setTournamentID(t.getId());
					 reWriteGames(games);
					 finish();
					 }
					 
				 }
			 });
			 whyMe.show();
			 
			
		 }
	}

	public void deleteData(Game fGame)
	{
		int id = fGame.getID();
		games.remove(fGame);
		for(Game g : games)
		{
			if(g.getID()>id)
			{
				g.setID(g.getID()-1);
			}
		}
	
		reWriteGames(games);
		finish();
	}
	public  void reWriteGames(ArrayList<Game> gameList)
	{
		try{
			
			FileOutputStream fos = openFileOutput("games.txt",Context.MODE_PRIVATE);
			for(Game game : gameList)
			{
				String write = String.valueOf(game.getID())+";"+String.valueOf(game.getFirstTeam())+";"+String.valueOf(game.getSecondTeam())+";"
			
					+String.valueOf(game.getTournamentID())+";"+String.valueOf(game.getGameLevel())+";"+game.getData()+"\n" ;
				fos.write(write.getBytes());
				
			}
			fos.close();
		}catch(Exception e)
		{}
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

	
}
