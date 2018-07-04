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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.os.Build;

public class ViewSpecificTeamActivity extends ActionBarActivity {
	ArrayList<Game> games;
	ArrayList<Team> teams;
	ArrayList<Tournament> tournaments;
	Team focusTeam;
	ArrayList<Game> importantGames;
	ListView listView1;
	GameAdapter ga ;
	boolean[]selected;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_specific_team);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
		
		
		
		Bundle b = getIntent().getExtras();
		TextView name = (TextView)findViewById(R.id.textView1);
		TextView info = (TextView)findViewById(R.id.textView2);
		
		games = (ArrayList<Game>)b.get("games");
		teams = (ArrayList<Team>)b.get("teams");
		tournaments = (ArrayList<Tournament>)b.get("tournaments");
		focusTeam = (Team)b.get("team");
		name.setText(focusTeam.getName().toUpperCase());
		info.setText(focusTeam.getCity()+" ("+Team.getStringRegion(focusTeam.getRegion())+")");
			importantGames= new ArrayList<Game>();
			for(Game g: games)
			{
				if(g.getFirstTeam()==focusTeam.getId())
				{
					importantGames.add(g);
				}
				else if (g.getSecondTeam()==focusTeam.getId())
				{
					importantGames.add(Game.invert(g));
				}
				else{}
			}
			
			 listView1 = (ListView) findViewById(R.id.listView1);
			  ga = new GameAdapter(ViewSpecificTeamActivity.this,R.layout.game_list_item,importantGames,teams,tournaments,true);
		
			
			 selected = new boolean[importantGames.size()];
			 for(int i = 0; i<selected.length;i++)
			 {
				 selected[i] = selected[i];
			 }
			 listView1.setAdapter(ga);
			 listView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			 listView1.setOnItemLongClickListener(new OnItemLongClickListener() {

		    	 public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
		    		 Game g = importantGames.get(position);
		    	    	String tournamentName = tournaments.get(g.getTournamentID()-1).getName();
		    	    	String t1Name = teams.get(g.getFirstTeam()-1).getName();
		    	    	String t2Name = teams.get(g.getSecondTeam()-1).getName();
		    	    	Intent i = new Intent (ViewSpecificTeamActivity.this,ViewGameStatsActivity.class);
		    		i.putExtra("game", g ); 
		    			i.putExtra("t1Name",t1Name);
		    			i.putExtra("t2Name", t2Name);
		    			i.putExtra("tournName",tournamentName);
		    			startActivity(i);
		    			return true;

		    	        }
		    	 });
			 listView1.setOnItemClickListener(new OnItemClickListener(){
				 
				 public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
				 {
					 ((CheckBox)(view.findViewById(R.id.checkBox1))).setChecked(!((CheckBox)(view.findViewById(R.id.checkBox1))).isChecked());
					selected[position]=!selected[position];
				 }
			 });

	}
	

	
	public void teamSelectStats(View v)
	{
		ArrayList<Game> toAnalyze = new ArrayList<Game>();
		for(int j=0; j<ga.getCount();j++)
		{
			if(selected[j])
			{
				toAnalyze.add(ga.getItem(j));
			}
		}
		Intent i = new Intent (ViewSpecificTeamActivity.this,TeamStatsActivity.class);
		i.putExtra("team",focusTeam);
		i.putExtra("games",toAnalyze);
		startActivity(i);
		
	}
	public void teamStats(View v)
	{
		
		Intent i = new Intent (ViewSpecificTeamActivity.this,TeamStatsActivity.class);
		i.putExtra("team",focusTeam);
		i.putExtra("games",importantGames);
		startActivity(i);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_specific_team, menu);
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
			View rootView = inflater.inflate(
					R.layout.fragment_view_specific_team, container, false);
			return rootView;
		}
	}

}
