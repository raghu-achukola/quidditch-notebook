package com.example.quidditchnotebook;

import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.Build;

public class ViewTeamActivity extends ActionBarActivity {

	Spinner spinner1;
	ListView listView1;
	
	ArrayList<Team> teams;
	ArrayList<Game> games;
	
	ArrayList<Tournament> tournaments;
	ArrayList<Team> modified;
	
	TeamAdapter ta;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_team);
		Bundle b = getIntent().getExtras();
		 games = (ArrayList<Game>)b.get("games");
		 teams = (ArrayList<Team>)b.get("teams");
		tournaments = (ArrayList<Tournament>)b.get("tournaments");
		((TextView)findViewById(R.id.textView1)).setText("View Team");
		listView1 = (ListView) findViewById(R.id.listView1);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapterRS = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.regions_plus_all));
		spinner1.setAdapter(adapterRS);
		 modified= teams;
		ta = new TeamAdapter(ViewTeamActivity.this,R.layout.team_list_item,modified);
		listView1.setAdapter(ta);
	/*	spinner1.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
			{
				
				if(position!=Spinner.INVALID_POSITION)
				{
					if(position==0)
					{
						modified.clear();
						modified.addAll(teams);
						ta.notifyDataSetChanged();
					}
					else
					{
						
						ArrayList<Team> modifiedList = ViewTeamActivity.sortTeams(position, teams);
						modified.clear();
						modified.addAll(modifiedList);
						ta.notifyDataSetChanged();
						
					}
					
				}
			}
			public void onNothingSelected(AdapterView<?> parentView){}
		});*/
		
		
		 listView1.setOnItemClickListener(new OnItemClickListener() {

	    	 public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
	    		 Team t = (Team)arg0.getAdapter().getItem(position);
	    	  
	    	    	Intent i = new Intent (ViewTeamActivity.this,ViewSpecificTeamActivity.class);
	    			i.putExtra("team", t ); 
	    			i.putExtra("teams",teams);
	    			i.putExtra("games", games);
	    			i.putExtra("tournaments",tournaments);
	    			startActivity(i);

	    	        }
	    	 });
		
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
				 ta = new TeamAdapter(ViewTeamActivity.this,R.layout.team_list_item,modified);
					listView1.setAdapter(ta);
			}
			else
			{
				
				modified = ViewTeamActivity.sortTeams(position, teams);
				 ta = new TeamAdapter(ViewTeamActivity.this,R.layout.team_list_item,modified);
					listView1.setAdapter(ta);
				
				
			}
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_team, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_view_team,
					container, false);
			return rootView;
		}
	}

}
