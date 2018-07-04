package com.example.quidditchnotebook;

import java.util.ArrayList;
import java.util.Arrays;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Build;
import android.widget.TextView;

public class ViewAllGamesActivity extends ActionBarActivity {

	ArrayList<Game> games;
	ArrayList<Team> teams;
	ArrayList<Tournament> tournaments;
	Game focus;
	  ArrayList<Game> modified = new ArrayList<Game>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getIntent().getExtras();
		games = (ArrayList<Game>)b.get("games");
		teams = (ArrayList<Team>)b.get("teams");
		tournaments = (ArrayList<Tournament>)b.get("tournaments");
		setContentView(R.layout.activity_view_all_games);
		((TextView)findViewById(R.id.textView1)).setText("View Games");
		ListView listView1 = (ListView) findViewById(R.id.listView1);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		focus =(Game)b.get("game");
		for(Game g: games)
		{
			if(g.hasSameValues(focus))
			{
				
				modified.add(g);
			}
		}
		  GameAdapter ga = new GameAdapter(ViewAllGamesActivity.this,R.layout.game_list_item,modified,teams,tournaments,false);

	    listView1.setAdapter(ga);
	    
	   listView1.setOnItemLongClickListener(new OnItemLongClickListener() {

	    	 public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
	    		 Game game = (Game)arg0.getAdapter().getItem(position);
	    	    	String tournamentName = tournaments.get(game.getTournamentID()-1).getName();
	    	    	String t1Name = teams.get(game.getFirstTeam()-1).getName();
	    	    	String t2Name = teams.get(game.getSecondTeam()-1).getName();
	    	    	Intent i = new Intent (ViewAllGamesActivity.this,ViewGameStatsActivity.class);
	    			i.putExtra("game", game ); 
	    			i.putExtra("t1Name",t1Name);
	    			i.putExtra("t2Name", t2Name);
	    			i.putExtra("tournName",tournamentName);
	    			startActivity(i);
	    			return true;

	    	        }
	    	 });
	}
 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_all_games, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_view_all_games,
					container, false);
			return rootView;
		}
	}

}
