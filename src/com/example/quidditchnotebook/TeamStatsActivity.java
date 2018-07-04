package com.example.quidditchnotebook;

import java.util.ArrayList;
import java.util.Locale;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class TeamStatsActivity extends ActionBarActivity {
	ArrayList<Game> games;
	Team team;
	String teamName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_stats);

		
		
		
		Bundle b =getIntent().getExtras();
		games=(ArrayList<Game>)(b.getSerializable("games"));
		team = (Team)(b.get("team"));
		teamName = team.getName();
		setData(Statistics.process(games));
		
	}

	

	
	private void setData(double[] process) {
		
		((TextView)findViewById(R.id.title)).setText(teamName.toUpperCase(Locale.getDefault()));
		((TextView)findViewById(R.id.team1Qg0)).setText(String.valueOf((int)process[0]));
		((TextView)findViewById(R.id.team1Qa0)).setText(String.valueOf((int)process[1]));
		((TextView)findViewById(R.id.team1Qg1)).setText(String.valueOf((int)process[2]));
		((TextView)findViewById(R.id.team1Qa1)).setText(String.valueOf((int)process[3]));
		((TextView)findViewById(R.id.team1Qg2)).setText(String.valueOf((int)process[4]));
		((TextView)findViewById(R.id.team1Qa2)).setText(String.valueOf((int)process[5]));
		((TextView)findViewById(R.id.team2Qg0)).setText(String.valueOf((int)process[6]));
		((TextView)findViewById(R.id.team2Qa0)).setText(String.valueOf((int)process[7]));
		((TextView)findViewById(R.id.team2Qg1)).setText(String.valueOf((int)process[8]));
		((TextView)findViewById(R.id.team2Qa1)).setText(String.valueOf((int)process[9]));
		((TextView)findViewById(R.id.team2Qg2)).setText(String.valueOf((int)process[10]));
		((TextView)findViewById(R.id.team2Qa2)).setText(String.valueOf((int)process[11]));
		((TextView)findViewById(R.id.team1Qs0)).setText(Statistics.toPercent(process[12]));
		((TextView)findViewById(R.id.team2Qs0)).setText(Statistics.toPercent(process[15]));
		((TextView)findViewById(R.id.team1Qs1)).setText(Statistics.toPercent(process[13]));
		((TextView)findViewById(R.id.team2Qs1)).setText(Statistics.toPercent(process[16]));
		((TextView)findViewById(R.id.team1Qs2)).setText(Statistics.toPercent(process[14]));
		((TextView)findViewById(R.id.team2Qs2)).setText(Statistics.toPercent(process[17]));
		((TextView)findViewById(R.id.length)).setText(String.valueOf(process[18]));
		((TextView)findViewById(R.id.team1BludgerControl)).setText(Statistics.toPercent(process[19]));
		((TextView)findViewById(R.id.team1BOI)).setText(String.valueOf(process[20]));
		((TextView)findViewById(R.id.team2BOI)).setText(String.valueOf(process[21]));
		((TextView)findViewById(R.id.oto)).setText(String.valueOf(process[22]));
		((TextView)findViewById(R.id.otd)).setText(String.valueOf(process[23]));
		((TextView)findViewById(R.id.otcum)).setText(String.valueOf(process[24]));
		((TextView)findViewById(R.id.team1Raw)).setText(Statistics.toPercent(process[25]));
		((TextView)findViewById(R.id.team2Raw)).setText(Statistics.toPercent(process[26]));

		((TextView)findViewById(R.id.team2NoBludgers)).setText(Statistics.toPercent(process[27]));
		((TextView)findViewById(R.id.team1BludgerStability)).setText(String.valueOf(process[28])+" drives");
		((TextView)findViewById(R.id.team2BludgerStability)).setText(String.valueOf(process[29])+" drives");
		((TextView)findViewById(R.id.buq)).setText(Statistics.toPercent(process[30])+"::"+Statistics.toPercent(process[31]));
		((TextView)findViewById(R.id.bubb)).setText(Statistics.toPercent(process[31]));
		((TextView)findViewById(R.id.swim)).setText(Statistics.toPercent((process[35]+process[36]==0)? -1: process[35]/(process[35]+process[36]+process[38])));
((TextView)findViewById(R.id.isr)).setText(Statistics.toPercent((process[37]+process[38]+process[39]==0)? -1: (process[35]+process[36]+process[38])/(process[37]+process[38]+process[39])));

((TextView)findViewById(R.id.score)).setText(String.valueOf((int)(process[37]-process[35]))+"-"+String.valueOf((int)process[35])+"-"+String.valueOf((int)process[38])+"-"
												+String.valueOf((int)process[36])+"-"+String.valueOf((int)(process[39]-process[36])));
		((TextView)findViewById(R.id.record)).setText(String.valueOf((int)process[37])+"-"+String.valueOf((int)process[38])+"-"+String.valueOf((int)process[39]));
	}














	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.team_stats, menu);
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

	
}
