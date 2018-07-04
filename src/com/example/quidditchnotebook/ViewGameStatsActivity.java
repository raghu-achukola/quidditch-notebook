package com.example.quidditchnotebook;

import java.util.Locale;

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
import android.widget.TextView;
import android.os.Build;

public class ViewGameStatsActivity extends ActionBarActivity {

	Game game;
	String team1Name;
	String team2Name;
	int team1RegularTimeCatch;
	int overTimeCatch;
	int overTimeteam1Catch;
	String tournamentName;
	
	double[] processedData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_game_stats);

	/*	if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		Bundle b =getIntent().getExtras();
		game=(Game)(b.getSerializable("game"));
		team1Name = b.getString("t1Name");
		team2Name = b.getString("t2Name");
		tournamentName = b.getString("tournName");
		Log.w("data2",game.getData());
		processedData = Statistics.process(game.getData());
		
		setData(processedData);
	
		
	}

	private void setData(double[] data)
	{

		((TextView)findViewById(R.id.title)).setText(team1Name.toUpperCase(Locale.getDefault())+" vs. "+team2Name.toUpperCase()
													+"\n"+tournamentName+" "+Game.convertGameType(game.getGameLevel()));
	((TextView)findViewById(R.id.team1QuaffleData)).setText(team1Name + " Quaffle Data");
	((TextView)findViewById(R.id.team2QuaffleData)).setText(team2Name + " Quaffle Data");
	((TextView)findViewById(R.id.team1BludgerData)).setText(team1Name + " Bludger Data");
	((TextView)findViewById(R.id.team2BludgerData)).setText(team2Name + " Bludger Data");
		((TextView)findViewById(R.id.team1Qg0)).setText(String.valueOf((int)data[0]));
		((TextView)findViewById(R.id.team1Qa0)).setText(String.valueOf((int)data[1]));
		((TextView)findViewById(R.id.team1Qg1)).setText(String.valueOf((int)data[2]));
		((TextView)findViewById(R.id.team1Qa1)).setText(String.valueOf((int)data[3]));
		((TextView)findViewById(R.id.team1Qg2)).setText(String.valueOf((int)data[4]));
		((TextView)findViewById(R.id.team1Qa2)).setText(String.valueOf((int)data[5]));
		((TextView)findViewById(R.id.team2Qg0)).setText(String.valueOf((int)data[6]));
		((TextView)findViewById(R.id.team2Qa0)).setText(String.valueOf((int)data[7]));
		((TextView)findViewById(R.id.team2Qg1)).setText(String.valueOf((int)data[8]));
		((TextView)findViewById(R.id.team2Qa1)).setText(String.valueOf((int)data[9]));
		((TextView)findViewById(R.id.team2Qg2)).setText(String.valueOf((int)data[10]));
		((TextView)findViewById(R.id.team2Qa2)).setText(String.valueOf((int)data[11]));
		((TextView)findViewById(R.id.team1Qs0)).setText(Statistics.toPercent(data[12]));
		((TextView)findViewById(R.id.team1Qs1)).setText(Statistics.toPercent(data[13]));
		((TextView)findViewById(R.id.team1Qs2)).setText(Statistics.toPercent(data[14]));
		((TextView)findViewById(R.id.team2Qs0)).setText(Statistics.toPercent(data[15]));
		((TextView)findViewById(R.id.team2Qs1)).setText(Statistics.toPercent(data[16]));
		((TextView)findViewById(R.id.team2Qs2)).setText(Statistics.toPercent(data[17]));
		((TextView)findViewById(R.id.length)).setText(String.valueOf((int)data[18])+" drives");
		((TextView)findViewById(R.id.team1BOI)).setText(String.valueOf(data[19]));
		((TextView)findViewById(R.id.team2BOI)).setText(String.valueOf(data[20]));
		((TextView)findViewById(R.id.team1Raw)).setText(Statistics.toPercent(data[21]));
		((TextView)findViewById(R.id.team2Raw)).setText(Statistics.toPercent(data[22]));


		((TextView)findViewById(R.id.team1BludgerControl)).setText(Statistics.toPercent(data[24]));

		((TextView)findViewById(R.id.team2BludgerControl)).setText(Statistics.toPercent(1-data[24]));
		
//26 is with 35+36
//27-28 not used
		((TextView)findViewById(R.id.team1NoBludgers)).setText(Statistics.toPercent(data[30]));
		((TextView)findViewById(R.id.team2NoBludgers)).setText(Statistics.toPercent(data[29]));
		((TextView)findViewById(R.id.fluidity)).setText(String.valueOf((int)data[31])+" changes in bludger contol");
		//32 not used

		((TextView)findViewById(R.id.team1BludgerStability)).setText(String.valueOf(data[34])+" drives");
		((TextView)findViewById(R.id.team2BludgerStability)).setText(String.valueOf(data[33])+" drives");
		((TextView)findViewById(R.id.stringData)).setText(game.getData());

		//26+35+36 below
		String t1Score=String.valueOf((int)data[35]);
		String t2Score=String.valueOf((int)data[36]);
		
		
		
		if(data[38]==0)
		{
			int rounded=(int)(data[26]);
			((TextView)findViewById(R.id.otSnitch)).setText("N/A");
			if(rounded==1)
			{
				((TextView)findViewById(R.id.rtSnitch)).setText(team1Name);
				t1Score=t1Score+"*";
			}
			else
			{
				((TextView)findViewById(R.id.rtSnitch)).setText(team2Name);
				t2Score=t2Score+"*";
			}
		}
		else
		{
			int rounded=(int)(data[26]);
			if(rounded==1)
			{
				((TextView)findViewById(R.id.rtSnitch)).setText(team1Name);
				t1Score=t1Score+"*";
			}
			else
			{
				((TextView)findViewById(R.id.rtSnitch)).setText(team2Name);
				t2Score=t2Score+"*";
			}
			if(data[26]-rounded <=0.15)
			{
				((TextView)findViewById(R.id.otSnitch)).setText("NO CATCH");
			}
			else if(data[26]-rounded <=0.5)
			{
				((TextView)findViewById(R.id.otSnitch)).setText(team1Name);
				t1Score=t1Score+"^";
			}
			else
			{
				((TextView)findViewById(R.id.otSnitch)).setText(team2Name);
				t2Score=t2Score+"^";
			}
		}
		String score="";
		if(data[35]==data[36]){ score = "TIE "+t1Score+"-"+t2Score;}
		else{score = (data[35]>=data[36])?(team1Name+" "+t1Score+"-"+t2Score):(team2Name + " "+t2Score+"-"+t1Score);}
		((TextView)findViewById(R.id.score)).setText(score);
		int result=-1;
		result = (int)data[37];
		if(result==Statistics.GAME_TIE){((TextView)findViewById(R.id.result)).setText("TIE");}
		else if(result==Statistics.GAME_WOSR){((TextView)findViewById(R.id.result)).setText(team1Name+" WOSR");}
		else if(result==Statistics.GAME_WISR){((TextView)findViewById(R.id.result)).setText(team1Name+" WISR");}
		else if(result==Statistics.GAME_LISR){((TextView)findViewById(R.id.result)).setText(team2Name+" WISR");}
		else if(result==Statistics.GAME_LOSR){((TextView)findViewById(R.id.result)).setText(team2Name+" WOSR");}
		else{}
		
		
		int roundedBUS=(int)(data[23]);
	
		TextView bus = (TextView)findViewById(R.id.bus);
		if(data[38]==0)
		{

			if(roundedBUS==0)
			{
				bus.setText("NO SCORE");
				
			}
			else if (roundedBUS==1)
			{
				bus.setText(team1Name);
			}
			else if (roundedBUS==2)
			{
				bus.setText(team2Name);
			}
			else{}
		}
		else
		{
			
			if(roundedBUS==0)
			{
				bus.setText("(RT): NO SCORE");
				
			}
			else if (roundedBUS==1)
			{
				bus.setText("(RT): "+team1Name);
			}
			else if (roundedBUS==2)
			{
				bus.setText("(RT): "+team2Name);
			}
			else{}
			if(data[23]-roundedBUS<.15)
			{
				bus.setText(bus.getText()+"\n"+"(OT): NO SCORE");
				
			}
			else if (data[23]-roundedBUS<.5)
			{
				bus.setText(bus.getText()+"\n"+"(OT): "+team1Name);
			}
			else 
			{
				bus.setText(bus.getText()+"\n"+"(OT): "+team2Name);
			}
			
		}
		
		int roundedBUBB = (int)data[25];
		TextView bubb = (TextView)findViewById(R.id.bubb);
		if(data[38]==0)
		{

			if (roundedBUBB==1)
			{
				bubb.setText(team1Name);
			}
			else if (roundedBUBB==2)
			{
				bubb.setText(team2Name);
			}
			else{}
		}
		else
		{
			
			if (roundedBUBB==1)
			{
				bubb.setText("(RT): "+team1Name);
			}
			else if (roundedBUBB==2)
			{
				bubb.setText("(RT): "+team2Name);
			}
			else{}
		
			if (data[25]-roundedBUBB<.5)
			{
				bubb.setText(bubb.getText()+"\n"+"(OT): "+team1Name);
			}
			else
			{
				bubb.setText(bubb.getText()+"\n"+"(OT): "+team2Name);
			}
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public void mainMenu(View v)
	{
		Intent i = new Intent(ViewGameStatsActivity.this,MainActivity.class);
		startActivity(i);
		finish();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_game_stats, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_view_game_stats,
					container, false);
			return rootView;
		}
	}

}
