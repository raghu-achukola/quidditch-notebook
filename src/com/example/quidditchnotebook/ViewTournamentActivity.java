package com.example.quidditchnotebook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;
import android.widget.Button;
import android.widget.LinearLayout;

public class ViewTournamentActivity extends ActionBarActivity {

	ArrayList<Game> games;
	ArrayList<Team> teams;
	ArrayList<Tournament> tournaments;
	ListView listView1;
	ArrayList<Tournament> modified;
	EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_tournament);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
			
			
			
			
			
			
			
			editText = (EditText)findViewById(R.id.editText1);
			editText.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        hideKeyboard();
                    }

                }
            });



			
			((TextView)findViewById(R.id.textView1)).setText("View Tournaments");
			Bundle b = getIntent().getExtras();
			 games = (ArrayList<Game>)b.get("games");
			 teams = (ArrayList<Team>)b.get("teams");
			tournaments = (ArrayList<Tournament>)b.get("tournaments");
			listView1 = (ListView) findViewById(R.id.listView1);
			
			listView1.setOnItemClickListener(new OnItemClickListener() {

		    	 public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		    		 Tournament t = (Tournament)arg0.getAdapter().getItem(position);
		    	  
		    	    	Intent i = new Intent (ViewTournamentActivity.this,ViewSpecificTournamentActivity.class);
		    			i.putExtra("tournament", t ); 
		    			i.putExtra("teams",teams);
		    			i.putExtra("games", games);
		    			i.putExtra("tournaments",tournaments);
		    			startActivity(i);

		    	        }
		    	 });
			
		}
	}
	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		}
	
	
	public void go(View v)
	{
		
		((LinearLayout) findViewById(R.id.dummy_id)).requestFocus();
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		try{
		int season = Integer.parseInt(editText.getEditableText().toString());
		ArrayList<Tournament> modified = sortTournaments(season,tournaments);
		listView1.setAdapter(new TournamentAdapter(getApplicationContext(),R.layout.team_list_item,modified));}
		catch(Exception e){Toast.makeText(getApplicationContext(), "Enter valid number", Toast.LENGTH_SHORT).show();}
	

	}
	
	public ArrayList<Tournament> sortTournaments(int season, ArrayList<Tournament> tourneys)
	{
		
		ArrayList<Tournament> tourns = new ArrayList<Tournament>();
		for (Tournament t:tourneys)
		{
			if(t.getSeason()==season)
			{
				tourns.add(t);
			}
		}
		Tournament first;
		Tournament holder;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		try{
		for(int i =0; i<tourns.size();i++)
		{
			first = tourns.get(i);
			holder=first;
			cal1.setTime((df.parse(tourns.get(i).getDate())));
			for(int j=i+1;j<tourns.size();j++)
			{
				cal2.setTime((df.parse(tourns.get(j).getDate())));
				if(cal1.after(cal2))
				{
					cal1.setTime((df.parse(tourns.get(j).getDate())));
					first = tourns.get(j);
				}
			}
			tourns.set(i, first);
			tourns.set(tourns.indexOf(first),holder);
		}
		
		}catch(Exception e){
			Log.w("hatai shmi","hatai shmi");
			return tourns;
		}
		return tourns;
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_tournament, menu);
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
		public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_view_tournament,
					container, false);
			return rootView;
		}
	}

}
