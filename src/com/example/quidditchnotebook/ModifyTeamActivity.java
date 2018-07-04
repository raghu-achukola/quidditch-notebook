package com.example.quidditchnotebook;

import java.io.FileOutputStream;
import java.util.ArrayList;

import com.example.quidditchnotebook.ViewTeamActivity.PlaceholderFragment;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ModifyTeamActivity extends ActionBarActivity implements EditDeleteDialogFragment.EditDeleteListener {
	 ArrayList<Team> teams;
	 ArrayList<Game> games;
	  ArrayList<Tournament> tournaments;
	ListView listView1;
	Spinner spinner1;
	ArrayList<Team> modified;
	Team selected;
	TeamAdapter ta;
	AdapterView<?> ad;
	String editName;
	int editRegion;
	String editCity;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_team);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		((TextView)findViewById(R.id.textView1)).setText("Modify Teams");
		Bundle b = getIntent().getExtras();
		 games = (ArrayList<Game>)b.get("games");
		 teams = (ArrayList<Team>)b.get("teams");
		tournaments = (ArrayList<Tournament>)b.get("tournaments");
		listView1 = (ListView) findViewById(R.id.listView1);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapterRS = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.regions_plus_all));
		spinner1.setAdapter(adapterRS);
		 modified= teams;
		ta = new TeamAdapter(ModifyTeamActivity.this,R.layout.team_list_item,modified);
		listView1.setAdapter(ta);
		
		
		 listView1.setOnItemClickListener(new OnItemClickListener() {

	    	 public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
	    		 selected = (Team)arg0.getAdapter().getItem(position);
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
			 boolean hasGames=false;
			 for(Game g:games)
			 {
				 if(g.getFirstTeam()==selected.getId()||g.getSecondTeam()==selected.getId())
				 {
					 hasGames = true;
				 }
				
			 }
			 if(hasGames)
			 {AlertDialog.Builder builder = new AlertDialog.Builder(this);
			 builder.setMessage("This team has attached games. Please delete the games first, and then delete the team. ")
			        .setCancelable(false)
			        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int id) {
			                 //do things
			            }
			        });
			 AlertDialog alert = builder.create();
			 alert.show();
			 }
			 else
			 {
				 AlertDialog.Builder builder = new AlertDialog.Builder(this);
				 builder.setMessage("Are you sure?")
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
		 }
		 else
		 {
			 
			
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
				LayoutInflater inflater = getLayoutInflater();
				View modifyLayout = inflater.inflate(R.layout.edit_team, null);
				
				ArrayAdapter<String> whyMe = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
						getResources().getStringArray(R.array.regions));
				((Spinner)(modifyLayout.findViewById(R.id.spinner1))).setAdapter(whyMe);
				((EditText)(modifyLayout.findViewById(R.id.editText1))).setText(selected.getName());
				((EditText)(modifyLayout.findViewById(R.id.editText2))).setText(selected.getCity());
				((Spinner)(modifyLayout.findViewById(R.id.spinner1))).setSelection(selected.getRegion()-1);
				Button b = (Button)(modifyLayout.findViewById(R.id.button1));
				builder.setView(modifyLayout);
				final AlertDialog alert = builder.create();
				b.setOnClickListener(new OnClickListener(){
					
					public void onClick(View v)
					{
						editName = ((EditText)(alert.findViewById(R.id.editText1))).getEditableText().toString();
						editCity = ((EditText)(alert.findViewById(R.id.editText2))).getEditableText().toString();
						editRegion = ((Spinner)(alert.findViewById(R.id.spinner1))).getSelectedItemPosition()+1;
					
						if(editName.contains(";")||editCity.contains(";"))
						{
							Toast t = Toast.makeText(getApplicationContext(),"No semicolons allowed in name or city", Toast.LENGTH_SHORT);
							t.setGravity(Gravity.BOTTOM, 0, 0);
							t.show();
						}
						else if (((Spinner)(alert.findViewById(R.id.spinner1))).getSelectedItemPosition()==Spinner.INVALID_POSITION)
						{
							Toast t = Toast.makeText(getApplicationContext(),"Select a Region", Toast.LENGTH_SHORT);
							t.setGravity(Gravity.BOTTOM, 0, 0);
							t.show();
						}
						else if(editName.equals("")||editCity.equals(""))
						{
							Toast.makeText(getApplicationContext(), "All fields must be filled in", Toast.LENGTH_SHORT).show();
						}
						else{
						
							
						        
						            	int index = teams.indexOf(selected);
										teams.get(index).setName(editName);
										teams.get(index).setCity(editCity);
										teams.get(index).setRegion(editRegion);
										reWriteTeams(teams);
										finish();

						
						}
					}
				});
				alert.show();
					
				
		 }
	}
	
	
	public void deleteData(Team team)
	{
		int id = team.getId();
		teams.remove(team);
		for(Team t : teams)
		{
			if(t.getId()>id)
			{
				t.setId(t.getId()-1);
			}
		}
		for(Game g: games)
		{
			if(g.getFirstTeam()>id)
			{
				g.setFirstTeam(g.getFirstTeam()-1);
			}
			if(g.getSecondTeam()>id)
			{
				g.setSecondTeam(g.getSecondTeam()-1);
			}
		}
		reWriteGames(games);
		reWriteTeams(teams);
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
	public  void reWriteTeams(ArrayList<Team> teamList)
	{
		try
		{
			FileOutputStream fos = openFileOutput("teams.txt",Context.MODE_PRIVATE);
			for(Team t: teamList)
			{
				String write=String.valueOf(t.getId())+";"+t.getName()+";"+String.valueOf(t.getRegion())+";"+t.getCity()+"\n";
				fos.write(write.getBytes());
			}
			
			fos.close();
		}catch(Exception e){}
	}
		
		

	public static ArrayList<Team> sortTeams(int region, ArrayList<Team> teamList)
	{
		ArrayList<Team> newTeam = teamList;
		ArrayList<Team> regionTeams = new ArrayList<Team>();
		for(Team t:newTeam)
		{
			if(t.getRegion()==region)
				{regionTeams.add(t);}
			
		}
		
		for(int i=0;i<regionTeams.size();i++)
		{Team first = regionTeams.get(i);
		Team holder = regionTeams.get(i);
			for(int j=i+1;j<regionTeams.size();j++)
			{
				if(first.getName().compareToIgnoreCase(regionTeams.get(j).getName())>0)
				{
					first = regionTeams.get(j);
				}
			}
		regionTeams.set(regionTeams.indexOf(first), holder);
		regionTeams.set(i,first);
		}
		return regionTeams;
	}
	
	public void refresh(View v)
	{
		
		int position = spinner1.getSelectedItemPosition();
		
		if(position!=Spinner.INVALID_POSITION)
		{
			if(position==0)
			{
				modified=teams;
				 ta = new TeamAdapter(ModifyTeamActivity.this,R.layout.team_list_item,modified);
					listView1.setAdapter(ta);
			}
			else
			{
				
				modified = ViewTeamActivity.sortTeams(position, teams);
				 ta = new TeamAdapter(ModifyTeamActivity.this,R.layout.team_list_item,modified);
					listView1.setAdapter(ta);
				
				
			}
			
		}
	}


}
