package com.example.quidditchnotebook;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.quidditchnotebook.ViewTournamentActivity.PlaceholderFragment;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ModifyTournamentActivity extends ActionBarActivity implements EditDeleteDialogFragment.EditDeleteListener {
	ArrayList<Team> teams;
	 ArrayList<Game> games;
	  ArrayList<Tournament> tournaments;
	  ArrayList<Tournament> modified;
		ListView listView1;
		EditText editText1;
		TournamentAdapter ta;
		Tournament selected;
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_view_tournament);
			if(savedInstanceState==null)
			{
				getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new PlaceholderFragment()).commit();
			}
			((TextView)findViewById(R.id.textView1)).setText("Modify Tournaments");
			Bundle b = getIntent().getExtras();
			 games = (ArrayList<Game>)b.get("games");
			 teams = (ArrayList<Team>)b.get("teams");
			tournaments = (ArrayList<Tournament>)b.get("tournaments");
			listView1 = (ListView) findViewById(R.id.listView1);
			editText1 = (EditText) findViewById(R.id.editText1);
			listView1.setOnItemClickListener(new OnItemClickListener() {

		    	 public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		    		 Tournament t = (Tournament)arg0.getAdapter().getItem(position);
		    		 selected = t;
		    		 showEditDeleteDialog();
		    	    

		    	        }
		    	 });
			editText1.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        hideKeyboard();
                    }

                }
            });

		
		}
		private void hideKeyboard() {
			InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editText1.getWindowToken(), 0);
			}
		public void go(View v)
		{
			((LinearLayout) findViewById(R.id.dummy_id)).requestFocus();
			getWindow().setSoftInputMode(
				      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			try{
			int season = Integer.parseInt(editText1.getEditableText().toString());
			ArrayList<Tournament> modified = sortTournaments(season,tournaments);
			listView1.setAdapter(new TournamentAdapter(getApplicationContext(),R.layout.tournament_list_item,modified));}
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
					 if(g.getTournamentID()==selected.getId())
					 {
						 hasGames = true;
					 }
					
				 }
				 if(hasGames)
				 {AlertDialog.Builder builder = new AlertDialog.Builder(this);
				 builder.setMessage("This tournament has attached games. Please delete the games first, and then delete the tournament. ")
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
				
				LayoutInflater inflater = getLayoutInflater();
				final View modifyLayout = inflater.inflate(R.layout.edit_tournament, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setView(modifyLayout);
				
				((EditText)modifyLayout.findViewById(R.id.editText1)).setText(selected.getName());
				((EditText)modifyLayout.findViewById(R.id.editText2)).setText(String.valueOf(selected.getSeason()));
				((EditText)modifyLayout.findViewById(R.id.editText3)).setText(selected.getDate());
				((CheckBox)modifyLayout.findViewById(R.id.checkBox1)).setChecked(selected.getDay2());
				AlertDialog alert = builder.create();
				Button button = (Button)modifyLayout.findViewById(R.id.button1);
				button.setOnClickListener(new OnClickListener(){
					
					public void onClick(View v)
					{
						boolean error= false;
						String name = ((EditText)modifyLayout.findViewById(R.id.editText1)).getEditableText().toString();
						int season=-1;
						try{season= Integer.parseInt(((EditText)modifyLayout.findViewById(R.id.editText2)).getEditableText().toString());}
						catch(Exception e){error=true;}
						String date =((EditText)modifyLayout.findViewById(R.id.editText3)).getEditableText().toString();
						boolean day2 = ((CheckBox)modifyLayout.findViewById(R.id.checkBox1)).isChecked();
						if(!Tournament.isDateValid(date))
						{
							Toast.makeText(getApplicationContext(), "Invalid Date", Toast.LENGTH_SHORT).show();
							error=true;
						}
						else if(name.contains(";"))
						{
							Toast.makeText(getApplicationContext(),"No semicolons allowed in name ", Toast.LENGTH_SHORT).show();
							error = true;
						}
						else if(date.equals("")||(((EditText)modifyLayout.findViewById(R.id.editText2)).getText().toString()).equals("")||name.equals(""))
						{
							Toast.makeText(getApplicationContext(), "Name and season must be filled in", Toast.LENGTH_SHORT).show();
							error = true;
						}
						if(!error)
						{
							int index = tournaments.indexOf(selected);
							tournaments.get(index).setName(name);
							tournaments.get(index).setDate(date);
							tournaments.get(index).setDay2(day2);
							tournaments.get(index).setSeason(season);
							reWriteTournaments(tournaments);
							finish();

						}
					}
				});
				alert.show();
			 }
			 
		}
		
		
		public void deleteData(Tournament tournament)
		{
			int id = tournament.getId();
			tournaments.remove(tournament);
			for(Tournament t : tournaments)
			{
				if(t.getId()>id)
				{
					t.setId(t.getId()-1);
				}
			}
			for(Game g: games)
			{
				if(g.getTournamentID()>id)
				{
					g.setTournamentID(g.getTournamentID()-1);
				}
		
			}
			reWriteGames(games);
			reWriteTournaments(tournaments);
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
		public  void reWriteTournaments(ArrayList<Tournament> tournamentList)
		{
			try
			{
				FileOutputStream fos = openFileOutput("tournaments.txt",Context.MODE_PRIVATE);
				for(Tournament t: tournamentList)
				{
					String write =(t.getDay2())? (String.valueOf(t.getId())+";"+String.valueOf(t.getSeason())+";"+t.getName()+";"+t.getDate()+";"+"1"+"\n")
							:(String.valueOf(t.getId())+";"+String.valueOf(t.getSeason())+";"+t.getName()+";"+t.getDate()+";"+"0"+"\n");
					fos.write(write.getBytes());
				}
				
				fos.close();
			}catch(Exception e){}
		}
			
}
