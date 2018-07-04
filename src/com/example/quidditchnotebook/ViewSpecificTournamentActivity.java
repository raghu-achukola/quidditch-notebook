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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class ViewSpecificTournamentActivity extends ActionBarActivity {

	ArrayList<Game> games;
	ArrayList<Team> teams;
	ArrayList<Tournament> tournaments;
	Tournament focus;
	ArrayList<Game> importantGames;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_specific_tournament);

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
		focus = (Tournament)b.get("tournament");
		name.setText(focus.getName().toUpperCase());
		
		info.setText(focus.getDate());
			importantGames= new ArrayList<Game>();
			for(Game g: games)
			{
				if(g.getTournamentID()==focus.getId())
				{
					importantGames.add(g);
				}
				else{}
			}
			
			ListView listView1 = (ListView) findViewById(R.id.listView1);
			 GameAdapter ga = new GameAdapter(ViewSpecificTournamentActivity.this,R.layout.game_list_item,importantGames,teams,tournaments,false);
			 listView1.setAdapter(ga);
			 listView1.setOnItemClickListener(new OnItemClickListener() {

		    	 public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		    		 Game g = (Game) arg0.getAdapter().getItem(position);
		    	    	String tournamentName = focus.getName();
		    	    	String t1Name = teams.get(g.getFirstTeam()-1).getName();
		    	    	String t2Name = teams.get(g.getSecondTeam()-1).getName();
		    	    	Intent i = new Intent (ViewSpecificTournamentActivity.this,ViewGameStatsActivity.class);
		    		i.putExtra("game", g ); 
		    			i.putExtra("t1Name",t1Name);
		    			i.putExtra("t2Name", t2Name);
		    			i.putExtra("tournName",tournamentName);
		    			startActivity(i);

		    	        }
		    	 });

		
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_specific_tournament, menu);
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
					R.layout.fragment_view_specific_tournament, container,
					false);
			return rootView;
		}
	}

}
