package com.example.quidditchnotebook;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.example.quidditchnotebook.QuaffleDialogFragment.QuaffleListener;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddDataActivity extends ActionBarActivity implements QuaffleDialogFragment.QuaffleListener, BludgerDialogFragment.BludgerListener{

	TextView tournamentText;
	TextView gameText;
	String tournamentName;
	String t1Name;
	String t2Name;
	boolean firstControl; 
	CheckBox bc1;
	CheckBox bc2;
	int q1ID;
	int b1ID;
	int BUSselection=-1;
	int Qselection = -1;
	int Bselection = -1;
	Game g;
	TextView currentView;
	TextView startingBox;
	 String broomsUp = "";
	ArrayList<Game> games;
	String score1 = "0";
	String score2 = "0";
	boolean isBroomsUpFilled = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_data);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		Bundle b = getIntent().getExtras();
		tournamentName = b.getString("tournamentName");
		tournamentText=(TextView)(findViewById(R.id.textView1));
		tournamentText.setText(tournamentName);
		t1Name = b.getString("team1Name");
		t2Name = b.getString("team2Name");
	
		games = (ArrayList<Game>)b.get("games");
		gameText = (TextView)(findViewById(R.id.textView2));
		gameText.setText(t1Name+"-"+t2Name);
		q1ID = R.id.q1;
		b1ID =R.id.b1;
		bc1 = (CheckBox)findViewById(R.id.bc1);
		bc2 = (CheckBox)findViewById(R.id.bc2);
		g = (Game)(b.getSerializable("game"));
		((TextView)findViewById(R.id.name1)).setText(t1Name);
		((TextView)findViewById(R.id.name2)).setText(t2Name);
		((TextView)findViewById(R.id.score1)).setText("0");
		((TextView)findViewById(R.id.score2)).setText("0");
		
		
		
	}
	public void showBroomsUpDialog(){
	//	DialogFragment dialog = new BroomsUpDialogFragment();
	//	dialog.show(getFragmentManager(), "BroomsUpDialogFragment");
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 LayoutInflater inflater = getLayoutInflater();
		 View v = inflater.inflate(R.layout.brooms_up_data,null);
		 builder.setView(v);
		 Button submit = (Button)v.findViewById(R.id.button1);
		final  RadioGroup buAfter=(RadioGroup)v.findViewById(R.id.radioBUAfter);
		 final RadioGroup bus=(RadioGroup)v.findViewById(R.id.radioBUS);
		 final RadioGroup bubc=(RadioGroup)v.findViewById(R.id.radioBUBC);
		 ((RadioButton)v.findViewById(R.id.radioBUAfter01)).setText(t1Name);
		 ((RadioButton)v.findViewById(R.id.radioBUS01)).setText(t1Name);
		 ((RadioButton)v.findViewById(R.id.radioBUBC01)).setText(t1Name);
		 ((RadioButton)v.findViewById(R.id.radioBUAfter02)).setText(t2Name);
		 ((RadioButton)v.findViewById(R.id.radioBUS02)).setText(t2Name);
		 ((RadioButton)v.findViewById(R.id.radioBUBC02)).setText(t2Name);
		 if(!broomsUp.equals("")&&broomsUp.length()==3)
		 {
			 if (broomsUp.charAt(0)=='0')
			 {
				 bus.check(R.id.radioBUS00);
			 }
			 else if(broomsUp.charAt(0)=='1')
			 {
				 bus.check(R.id.radioBUS01);
				 score1=String.valueOf(Integer.parseInt(score1)-10);
				 Log.w("WHY",score1); }
			 else if(broomsUp.charAt(0)=='2')
			 {
				 bus.check(R.id.radioBUS02);
				 score2=String.valueOf(Integer.parseInt(score2)-10);
				 Log.w("WHY",score2);
						 }
			 
			 if (broomsUp.charAt(1)=='1')
			 {
				 bubc.check(R.id.radioBUBC01);
				 firstControl = true;
			 }
			 else if(broomsUp.charAt(1)=='2')
			 {
				 bubc.check(R.id.radioBUBC02);
				 firstControl = false;
			 }
			 if (broomsUp.charAt(2)=='1')
			 {
				 buAfter.check(R.id.radioBUAfter01);
			 }
			 else if(broomsUp.charAt(2)=='2')
			 {
				 buAfter.check(R.id.radioBUAfter02);
			 }
			
		 }
		final EditText e = new EditText(getApplicationContext());
		 final AlertDialog alert = builder.create();
		 submit.setOnClickListener(new OnClickListener(){
			 
			 String buData="";
			 
			 public void onClick(View view)
			 {
	
				 boolean error=false;
				 if(bus.getCheckedRadioButtonId()==R.id.radioBUS00)
				 {
					 buData="0";
				 }
				 else if (bus.getCheckedRadioButtonId()==R.id.radioBUS01)
				 {
					 buData="1";
				 }
				 else if (bus.getCheckedRadioButtonId()==R.id.radioBUS02)
				 {
					 buData="2";
				 }
				 else
				 {
					 error = true;
					 Toast.makeText(getApplicationContext(), "Please select an option for each question", Toast.LENGTH_SHORT).show();
				 }
				 if(bubc.getCheckedRadioButtonId()==R.id.radioBUBC01)
				 {
					 buData=buData+"1";
				 }
			
				 else if (bubc.getCheckedRadioButtonId()==R.id.radioBUBC02)
				 {
					 buData=buData+"2";
				 }
				 else
				 {
					 error = true;
					 Toast.makeText(getApplicationContext(), "Please select an option for each question", Toast.LENGTH_SHORT).show();
				 }
				 if(buAfter.getCheckedRadioButtonId()==R.id.radioBUAfter01)
				 {
					 buData=buData+"1";
				 }
			
				 else if (buAfter.getCheckedRadioButtonId()==R.id.radioBUAfter02)
				 {
					 buData=buData+"2";
				 }
				 else
				 {
					 error = true;
					 Toast.makeText(getApplicationContext(), "Please select an option for each question", Toast.LENGTH_SHORT).show();
				 }
				 if(!error)
				 {
					 Log.w("WHY","HATAISHMI");
					 e.setText(buData);
					 reWriteBU(e);
					alert.dismiss();
					Log.w("WHY","MIDBU");
				 }
			 }
		 });
		
		 alert.show();
	
	
	
	}
	private void reWriteBU(EditText e)
	{
		broomsUp = e.getEditableText().toString();
		isBroomsUpFilled=true;
		((TextView)findViewById(R.id.startingBox)).setText(broomsUp);
	 recalculateScore();
	 boolean previous = firstControl;
	 firstControl = (broomsUp.charAt(1)=='1');
	 if(!bc1.isChecked()&&!bc2.isChecked())
	 {
		 if(firstControl)
		 {
			 bc1.setChecked(true);
			 bc2.setChecked(false);
		 }
		 else
		 {
			 bc1.setChecked(false);
			 bc2.setChecked(true);
		 }
	 }
	 else if (bc1.isChecked())
	 {
		 if(previous!=firstControl)
		 {
			 bc1.setChecked(false);
			 bc2.setChecked(true);
		 }
	 }
	 else if (bc2.isChecked())
	 {
		 if(previous!=firstControl)
		 {
			 bc1.setChecked(true);
			 bc2.setChecked(false);
		 }
	 }
		
			if(broomsUp.charAt(2) =='1'){
				((TextView)findViewById(R.id.bla2)).setText(t1Name);
				((TextView)findViewById(R.id.bla4)).setText(t2Name);
				((TextView)findViewById(R.id.bla6)).setText(t1Name);
				((TextView)findViewById(R.id.bla8)).setText(t2Name);
				((TextView)findViewById(R.id.bla10)).setText(t1Name);
				
			}
			else if (broomsUp.charAt(2) =='2'){
				((TextView)findViewById(R.id.bla2)).setText(t2Name);
				((TextView)findViewById(R.id.bla4)).setText(t1Name);
				((TextView)findViewById(R.id.bla6)).setText(t2Name);
				((TextView)findViewById(R.id.bla8)).setText(t1Name);
				((TextView)findViewById(R.id.bla10)).setText(t2Name);
			}
			else{}
		
	}
	private void recalculateScore()
	{if(isBroomsUpFilled)
		{int s1 = 0;
		 int s2 = 0;
		 if(((String)((TextView)findViewById(R.id.startingBox)).getText()).charAt(0)=='1')
		 {
			 s1+=10;
		 }
		 else if(((String)((TextView)findViewById(R.id.startingBox)).getText()).charAt(0)=='2')
		 {
			 s2+=10;
		 }
		for(int i = R.id.q1;i<=R.id.b83;i++)
		{
			if(isTeam1Data(i))
			{
				String s = ((String)((TextView)findViewById(i)).getText());
				if(s.length()!=0)
				{
					if(s.charAt(0)=='g')
					{
						s1+=10;
					}
					else if(s.charAt(0)=='1')
					{
						s1+=30;
					}
					else if (s.charAt(0)=='2')
					{
						s2+=30;
					}
				}
			}
			else if (!(i==R.id.tableRow0||i==R.id.tableRow1||i==R.id.tableRow2||i==R.id.tableRow3||i==R.id.tableRow4||i==R.id.tableRow5||i==R.id.tableRow6
					||i==R.id.tableRow7||i==R.id.tableRow8||i==R.id.tableRow9||i==R.id.tableRow10||i==R.id.tableRow11||i==R.id.tableRow12||i==R.id.tableRow13||i==R.id.tableRow14))
			{
				String s = ((String)((TextView)findViewById(i)).getText());
				if(s.length()!=0)
				{
					if(s.charAt(0)=='g')
					{
						s2+=10;
					}
					else if(s.charAt(0)=='1')
					{
						s1+=30;
					}
					else if (s.charAt(0)=='2')
					{
						s2+=30;
					}
				}
			}
		}
		score1 = String.valueOf(s1);
		score2 = String.valueOf(s2);
		((TextView)findViewById(R.id.score1)).setText(score1);
		((TextView)findViewById(R.id.score2)).setText(score2);
		}
	
	}
/*	public void onBUSDialogSelect(DialogFragment dialog, int which)
	{BUSselection = which;
	broomsUp="";
	broomsUp = broomsUp+which;
	//showBUBBDialog();
	}
	/*public void showBUBBDialog()
	{
		DialogFragment dialog = new BUBBDialogFragment();
		dialog.show(getFragmentManager(), "BUBBDialogFragment");
	}
	public void onBUBBDialogSelect(DialogFragment dialog, int which)
	{
		int temp = which +1;
		broomsUp=broomsUp+temp;
		showFirstTeamDialog();
	}
	public void showFirstTeamDialog()
	{
		DialogFragment dialog = new FirstTeamDialogFragment();
		dialog.show(getFragmentManager(), "FirstTeamDialogFragment");
	}
	public void onFirstTeamDialogSelect(DialogFragment dialog, int which)
	{
		int temp = which +1;
		broomsUp=broomsUp+temp;
		startingBox.setText(broomsUp);
		TextView bla2 = (TextView)findViewById(R.id.bla2);
		TextView bla4 = (TextView)findViewById(R.id.bla4);
		TextView bla6 = (TextView)findViewById(R.id.bla6);
		TextView bla8 = (TextView)findViewById(R.id.bla8);
		TextView bla10 = (TextView)findViewById(R.id.bla10);
		if(temp ==1){
			bla2.setText(t1Name);
			bla4.setText(t2Name);
			bla6.setText(t1Name);
			bla8.setText(t2Name);
			bla10.setText(t1Name);
			
		}
		else if (temp ==2){
			bla2.setText(t2Name);
			bla4.setText(t1Name);
			bla6.setText(t2Name);
			bla8.setText(t1Name);
			bla10.setText(t2Name);
		}
		else{}
	}*/
	public void showQuaffleDialog()
	{
		if(!isBroomsUpFilled)
		{
			Toast.makeText(AddDataActivity.this, "Please fill in brooms up data first", Toast.LENGTH_SHORT).show();
		}
		else
			{DialogFragment dialog = new QuaffleDialogFragment();
		dialog.show(getFragmentManager(), "QuaffleDialogFragment");}
	}
	public void onQDialogSelect(DialogFragment dialog,
			int which) {Qselection=which;
			
			
boolean isTeam1=isTeam1Data(currentView.getId());
score1 = (String)((TextView)findViewById(R.id.score1)).getText();
score2 = (String)((TextView)findViewById(R.id.score2)).getText();
	
			String current = (String) currentView.getText();
			if(current.length()!=0)
			{
				if(current.charAt(0)=='g')
				{
					if(isTeam1)
					{
						
						score1 = String.valueOf(Integer.parseInt(score1)-10);
					}
					else
					{
						
						score2 = String.valueOf(Integer.parseInt(score2)-10);
					}
				}
				else if (current.charAt(0)=='1')
				{
					score1 = String.valueOf(Integer.parseInt(score1)-30);
				}
				else if (current.charAt(0)=='2')
				{
					score2 = String.valueOf(Integer.parseInt(score2)-30);
				}
			}
			
	switch(Qselection)
	{
	case 0 : currentView.setText("n0");
	break;
case 1 : currentView.setText("n1");
	break;
case 2 : currentView.setText("n2");
	break;
case 3 : currentView.setText("g0");

	break;
case 4 : currentView.setText("g1");

break;
case 5 : currentView.setText("g2");

break;
case 6 : currentView.setText("VOID");
break;
case 7 : currentView.setText("");
break;
case 8 : currentView.setText("1S");

break;
case 9: currentView.setText("2S"); 
default: break;


	}
current = (String) currentView.getText();
	if(current.length()!=0)
	{
		if(current.charAt(0)=='g')
		{
			if(isTeam1)
			{
				
				score1 = String.valueOf(Integer.parseInt(score1)+10);
			}
			else
			{
				
				score2 = String.valueOf(Integer.parseInt(score2)+10);
			}
		}
		else if (current.charAt(0)=='1')
		{
			score1 = String.valueOf(Integer.parseInt(score1)+30);
		}
		else if (current.charAt(0)=='2')
		{
			score2 = String.valueOf(Integer.parseInt(score2)+30);
		}
	}
	
	((TextView)findViewById(R.id.score1)).setText(score1);
	((TextView)findViewById(R.id.score2)).setText(score2);
	
	
	}
	public void showBludgerDialog()
	{
		if(!isBroomsUpFilled)
		{
			Toast.makeText(AddDataActivity.this, "Please fill in brooms up data first", Toast.LENGTH_SHORT).show();
		}
		else{DialogFragment dialog = new BludgerDialogFragment();
		dialog.show(getFragmentManager(), "BludgerDialogFragment");}
	}
	public void onBDialogSelect(DialogFragment dialog, int which )
	{
		
		
		String previousString = (String)currentView.getText();
		Bselection = which;
		switch(Bselection)
		{
		case 0 : currentView.setText("");
		break;
	case 1 : currentView.setText("C");
			break;
	default: break;


		}
		if(!previousString.equals((String)currentView.getText()))
		{
			bc1.setChecked(!bc1.isChecked());
			bc2.setChecked(!bc2.isChecked());
		}

		Log.w("dataB",String.valueOf(which));
	}

	public void changeData(View v, char c, int i)
	{
		Log.w("data","reached");
		if(c=='q')
		{
			
		
		int newId = q1ID;
		newId = newId + (i-1)*2;
		if(i>=6){newId++;} 
		if(i>=12){newId++;}
		if(i>=18){newId++;}
		if(i>=24){newId++;}
		if(i>=30){newId++;}
		if(i>=36){newId++;}
		if(i>=42){newId++;}
		if(i>=48){newId++;}
		if(i>=54){newId++;}
		if(i>=60){newId++;}
		if(i>=66){newId++;}
		if(i>=72){newId++;}
		if(i>=78){newId++;}
		showQuaffleDialog();
		

		currentView = (TextView)(findViewById(newId));
	
		Log.w("dataQ",String.valueOf(Qselection));
//setText(nText,newId, String.valueOf(Qselection));
		/*	case 0 : nText.setText("n0");
					break;
			case 1 : nText.setText("n1");
					break;
			case 2 : nText.setText("n2");
					break;
			case 3 : nText.setText("g0");
					break;
			case 4 : 
			Log.w("dataQ","set apparently");
				break;
			case 5 : nText.setText("g2");
				break;
			case 6 : nText.setText("VOID");
				break;
			default: break;
			
		}*/

		
		}
		else
		{
			int newId = b1ID;
			newId = newId + (i-1)*2;
			if(i>=6){newId++;} 
			if(i>=12){newId++;}
			if(i>=18){newId++;}
			if(i>=24){newId++;}
			if(i>=30){newId++;}
			if(i>=36){newId++;}
			if(i>=42){newId++;}
			if(i>=48){newId++;}
			if(i>=54){newId++;}
			if(i>=60){newId++;}
			if(i>=66){newId++;}
			if(i>=72){newId++;}
			if(i>=78){newId++;}
			showBludgerDialog();
			


			currentView = (TextView)(findViewById(newId));
		
			
		}
		Qselection=-1;
		Bselection=-1;
		
	}

	public void changeBroomsUp(View v)
	{

		 startingBox = (TextView)findViewById(R.id.startingBox);
		showBroomsUpDialog();

	}
	public void overtime (View v)
	{
		String data = "";
		String extraData = "";
		boolean startingTeamBludgerControl=true;
		int counter=-1;

		for(int i=R.id.startingBox;i<R.id.b83;i++)
		{
			
			Log.w("data",String.valueOf(i));
			if(i==R.id.startingBox)
			{
				data = (String) ((TextView)findViewById(i)).getText();
				if(data.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Brooms Up Data must be filled in", Toast.LENGTH_SHORT);
				}
			}
			else if (i==R.id.tableRow0||i==R.id.tableRow1||i==R.id.tableRow2||i==R.id.tableRow3||i==R.id.tableRow4||i==R.id.tableRow5||i==R.id.tableRow6
					||i==R.id.tableRow7||i==R.id.tableRow8||i==R.id.tableRow9||i==R.id.tableRow10||i==R.id.tableRow11||i==R.id.tableRow12||i==R.id.tableRow13||i==R.id.tableRow14){
				
			}
			
			else
			{
				counter++;
				String raw = (String) ((TextView)findViewById(i)).getText();
				if(raw.equals("C"))
				{
					extraData = (startingTeamBludgerControl)?"T":"R";
					startingTeamBludgerControl=!startingTeamBludgerControl;
					data=data+extraData;
				}
				else if(counter%2==0)
				{}
				else if (!(raw.equals("1S")||raw.equals("2S")))
				{
					extraData = (String) ((TextView)findViewById(i)).getText();
					switch(extraData)
					{
						case "n0": data=data+"0";
									break;
						case "n1": data=data+"1";
						break;
						case "n2": data=data+"2";
						break;
						case "g0": data=data+"3";
						break;
						case "g1": data=data+"4";
						break;
						case "g2": data=data+"5";
						break;
						case "VOID": data=data+"6";
						break;
						default:	data = data+" ";
								break;
									
					}
				}
				else
				{
					
					data = (raw.equals("1S"))?(data+"FS"):(data+"DS");
				}
			
			}
			
		}
		String newData = data.substring(0,data.indexOf('S')+1);
	
		if(newData.contains(" "))
		{
			Toast.makeText(getApplicationContext(),"There are blank spaces between plays in your data. If a voided play occured, please enter it as such",Toast.LENGTH_LONG).show();
		}
		else if(!newData.contains("S"))
		{
			Toast.makeText(getApplicationContext(), "Please include a snitch catch", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Intent i = new Intent (AddDataActivity.this,OvertimeActivity.class);
			i.putExtra("game", new Game(g.getID(),g.getFirstTeam(),g.getSecondTeam(),g.getTournamentID(),g.getGameLevel(),newData));
			i.putExtra("t1Name",t1Name);
			i.putExtra("t2Name", t2Name);
			i.putExtra("tournName",tournamentName);
			i.putExtra("score1", (String)((TextView)findViewById(R.id.score1)).getText());

			i.putExtra("score2", (String)((TextView)findViewById(R.id.score2)).getText());
			startActivity(i);
		}
	}
	
	public void readStringData(View v)
	{
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
		final View popUp = inflater.inflate(R.layout.string_data, null);
		builder.setView(popUp);
		AlertDialog alert = builder.create();
		Button button = (Button)(popUp.findViewById(R.id.button1));
		button.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v)
			{
				
				String data = ((EditText)popUp.findViewById(R.id.editText1)).getEditableText().toString();
				boolean error = false;
				try{
					
					double[] lol = Statistics.process(data);
					
				}
				catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), "Please enter valid processable data", Toast.LENGTH_LONG).show();
					error=true;
				}
				if(!error)
				{
					g.setData(data);
					try{
					
					FileOutputStream fos = openFileOutput("games.txt",Context.MODE_APPEND);
				String write = String.valueOf(g.getID())+";"+String.valueOf(g.getFirstTeam())+";"+String.valueOf(g.getSecondTeam())+";"
					
							+String.valueOf(g.getTournamentID())+";"+String.valueOf(g.getGameLevel())+";"+g.getData()+"\n" ;
						fos.write(write.getBytes());
						
					
					fos.close();
					Intent i = new Intent (AddDataActivity.this,ViewGameStatsActivity.class);
					i.putExtra("game", g);
					i.putExtra("t1Name",t1Name);
					i.putExtra("t2Name",t2Name);
					i.putExtra("tournName", tournamentName);
					startActivity(i);}
				catch(Exception e)
				{Log.w("stringdata","hatai shmi");}
				}
			}
		});
		alert.show();
	}
	
	
	public void readData(View v)
	{
		String data = "";
		String extraData = "";
		boolean startingTeamBludgerControl=true;
		int counter=-1;

		for(int i=R.id.startingBox;i<=R.id.b83;i++)
		{
			
			Log.w("data",String.valueOf(i));
			if(i==R.id.startingBox)
			{
				data = (String) ((TextView)findViewById(i)).getText();
				if(data.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Brooms Up Data must be filled in", Toast.LENGTH_SHORT);
				}
			}
			else if (i==R.id.tableRow0||i==R.id.tableRow1||i==R.id.tableRow2||i==R.id.tableRow3||i==R.id.tableRow4||i==R.id.tableRow5||i==R.id.tableRow6
					||i==R.id.tableRow7||i==R.id.tableRow8||i==R.id.tableRow9||i==R.id.tableRow10||i==R.id.tableRow11||i==R.id.tableRow12||i==R.id.tableRow13||i==R.id.tableRow14){
				
			}
			
			else
			{
				counter++;
				String raw = (String) ((TextView)findViewById(i)).getText();
				if(raw.equals("C"))
				{
					extraData = (startingTeamBludgerControl)?"T":"R";
					startingTeamBludgerControl=!startingTeamBludgerControl;
					data=data+extraData;
				}
				else if(counter%2==0)
				{}
				else if (!(raw.equals("1S")||raw.equals("2S")))
				{
					extraData = (String) ((TextView)findViewById(i)).getText();
					switch(extraData)
					{
						case "n0": data=data+"0";
									break;
						case "n1": data=data+"1";
						break;
						case "n2": data=data+"2";
						break;
						case "g0": data=data+"3";
						break;
						case "g1": data=data+"4";
						break;
						case "g2": data=data+"5";
						break;
						case "VOID": data=data+"6";
						break;
						default:	data = data+" ";
								break;
									
					}
				}
				else
				{
					
					data = (raw.equals("1S"))?(data+"FS"):(data+"DS");
				}
			
			}
			
		}
		String newData = data.substring(0,data.indexOf('S')+1);
	
		if(newData.contains(" "))
		{
			Toast.makeText(getApplicationContext(),"There are blank spaces between plays in your data. If a voided play occured, please enter it as such",Toast.LENGTH_LONG).show();
		}
		else if(!newData.contains("S"))
		{
			Toast.makeText(getApplicationContext(), "Please include a snitch catch", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Game game = new Game(g.getID(),g.getFirstTeam(),g.getSecondTeam(),g.getTournamentID(),g.getGameLevel(),newData);
			String write="";
			try{
				
				FileOutputStream fos = openFileOutput("games.txt",Context.MODE_APPEND);
				write = String.valueOf(game.getID())+";"+String.valueOf(game.getFirstTeam())+";"+String.valueOf(game.getSecondTeam())+";"
						+String.valueOf(g.getTournamentID())+";"+String.valueOf(g.getGameLevel())+";"+game.getData()+"\n" ;
					fos.write(write.getBytes());
					
				fos.close();
				Log.w("data",write);
			}catch(Exception e)
			{
			}
			
			Intent i = new Intent (AddDataActivity.this,ViewGameStatsActivity.class);
			i.putExtra("game", game); 
			i.putExtra("t1Name",t1Name);
			i.putExtra("t2Name", t2Name);
			i.putExtra("tournName",tournamentName);
			startActivity(i);
		}
	}
	public void changeq1(View v){changeData(v,'q',1);}public void changeq2(View v){changeData(v,'q',2);}public void changeq3(View v){changeData(v,'q',3);}public void changeq4(View v){changeData(v,'q',4);}
	public void changeq5(View v){changeData(v,'q',5);}public void changeq6(View v){changeData(v,'q',6);}public void changeq7(View v){changeData(v,'q',7);}public void changeq8(View v){changeData(v,'q',8);}
	public void changeq9(View v){changeData(v,'q',9);}public void changeq10(View v){changeData(v,'q',10);}public void changeq11(View v){changeData(v,'q',11);}public void changeq12(View v){changeData(v,'q',12);}
	public void changeq13(View v){changeData(v,'q',13);}public void changeq14(View v){changeData(v,'q',14);}public void changeq15(View v){changeData(v,'q',15);}public void changeq16(View v){changeData(v,'q',16);}
	public void changeq17(View v){changeData(v,'q',17);}public void changeq18(View v){changeData(v,'q',18);}public void changeq19(View v){changeData(v,'q',19);}public void changeq20(View v){changeData(v,'q',20);}
	public void changeq21(View v){changeData(v,'q',21);}public void changeq22(View v){changeData(v,'q',22);}public void changeq23(View v){changeData(v,'q',23);}public void changeq24(View v){changeData(v,'q',24);}
	public void changeq25(View v){changeData(v,'q',25);}public void changeq26(View v){changeData(v,'q',26);}public void changeq27(View v){changeData(v,'q',27);}public void changeq28(View v){changeData(v,'q',28);}
	public void changeq29(View v){changeData(v,'q',29);}public void changeq30(View v){changeData(v,'q',30);}public void changeq31(View v){changeData(v,'q',31);}public void changeq32(View v){changeData(v,'q',32);}
	public void changeq33(View v){changeData(v,'q',33);}public void changeq34(View v){changeData(v,'q',34);}public void changeq35(View v){changeData(v,'q',35);}public void changeq36(View v){changeData(v,'q',36);}
	public void changeq37(View v){changeData(v,'q',37);}public void changeq38(View v){changeData(v,'q',38);}public void changeq39(View v){changeData(v,'q',39);}public void changeq40(View v){changeData(v,'q',40);}
public void changeq41(View v){changeData(v,'q',41);}
public void changeq42(View v){changeData(v,'q',42);}public void changeq43(View v){changeData(v,'q',43);}public void changeq44(View v){changeData(v,'q',44);}public void changeq45(View v){changeData(v,'q',45);}
public void changeq46(View v){changeData(v,'q',46);}public void changeq47(View v){changeData(v,'q',47);}public void changeq48(View v){changeData(v,'q',48);}public void changeq49(View v){changeData(v,'q',49);}
public void changeq50(View v){changeData(v,'q',50);}public void changeq51(View v){changeData(v,'q',51);}public void changeq52(View v){changeData(v,'q',52);}public void changeq53(View v){changeData(v,'q',53);}
public void changeq54(View v){changeData(v,'q',54);}public void changeq55(View v){changeData(v,'q',55);}public void changeq56(View v){changeData(v,'q',56);}public void changeq57(View v){changeData(v,'q',57);}
public void changeq58(View v){changeData(v,'q',58);}public void changeq59(View v){changeData(v,'q',59);}public void changeq60(View v){changeData(v,'q',60);}public void changeq61(View v){changeData(v,'q',61);}
public void changeq62(View v){changeData(v,'q',62);}public void changeq63(View v){changeData(v,'q',63);}public void changeq64(View v){changeData(v,'q',64);}public void changeq65(View v){changeData(v,'q',65);}
public void changeq66(View v){changeData(v,'q',66);}public void changeq67(View v){changeData(v,'q',67);}public void changeq68(View v){changeData(v,'q',68);}public void changeq69(View v){changeData(v,'q',69);}
public void changeq70(View v){changeData(v,'q',70);}public void changeq71(View v){changeData(v,'q',71);}public void changeq72(View v){changeData(v,'q',72);}public void changeq73(View v){changeData(v,'q',73);}
public void changeq74(View v){changeData(v,'q',74);}public void changeq75(View v){changeData(v,'q',75);}public void changeq76(View v){changeData(v,'q',76);}public void changeq77(View v){changeData(v,'q',77);}
public void changeq78(View v){changeData(v,'q',78);}public void changeq79(View v){changeData(v,'q',79);}public void changeq80(View v){changeData(v,'q',80);}public void changeq81(View v){changeData(v,'q',81);}
public void changeq82(View v){changeData(v,'q',82);}public void changeq83(View v){changeData(v,'q',83);}

public void changeb1(View v){changeData(v,'b',1);}public void changeb2(View v){changeData(v,'b',2);}public void changeb3(View v){changeData(v,'b',3);}public void changeb4(View v){changeData(v,'b',4);}
public void changeb5(View v){changeData(v,'b',5);}public void changeb6(View v){changeData(v,'b',6);}public void changeb7(View v){changeData(v,'b',7);}public void changeb8(View v){changeData(v,'b',8);}
public void changeb9(View v){changeData(v,'b',9);}public void changeb10(View v){changeData(v,'b',10);}public void changeb11(View v){changeData(v,'b',11);}public void changeb12(View v){changeData(v,'b',12);}
public void changeb13(View v){changeData(v,'b',13);}public void changeb14(View v){changeData(v,'b',14);}public void changeb15(View v){changeData(v,'b',15);}public void changeb16(View v){changeData(v,'b',16);}
public void changeb17(View v){changeData(v,'b',17);}public void changeb18(View v){changeData(v,'b',18);}public void changeb19(View v){changeData(v,'b',19);}public void changeb20(View v){changeData(v,'b',20);}
public void changeb21(View v){changeData(v,'b',21);}public void changeb22(View v){changeData(v,'b',22);}public void changeb23(View v){changeData(v,'b',23);}public void changeb24(View v){changeData(v,'b',24);}
public void changeb25(View v){changeData(v,'b',25);}public void changeb26(View v){changeData(v,'b',26);}public void changeb27(View v){changeData(v,'b',27);}public void changeb28(View v){changeData(v,'b',28);}
public void changeb29(View v){changeData(v,'b',29);}public void changeb30(View v){changeData(v,'b',30);}public void changeb31(View v){changeData(v,'b',31);}public void changeb32(View v){changeData(v,'b',32);}
public void changeb33(View v){changeData(v,'b',33);}public void changeb34(View v){changeData(v,'b',34);}public void changeb35(View v){changeData(v,'b',35);}public void changeb36(View v){changeData(v,'b',36);}
public void changeb37(View v){changeData(v,'b',37);}public void changeb38(View v){changeData(v,'b',38);}public void changeb39(View v){changeData(v,'b',39);}public void changeb40(View v){changeData(v,'b',40);}
public void changeb41(View v){changeData(v,'b',41);}
public void changeb42(View v){changeData(v,'b',42);}public void changeb43(View v){changeData(v,'b',43);}public void changeb44(View v){changeData(v,'b',44);}public void changeb45(View v){changeData(v,'b',45);}
public void changeb46(View v){changeData(v,'b',46);}public void changeb47(View v){changeData(v,'b',47);}public void changeb48(View v){changeData(v,'b',48);}public void changeb49(View v){changeData(v,'b',49);}
public void changeb50(View v){changeData(v,'b',50);}public void changeb51(View v){changeData(v,'b',51);}public void changeb52(View v){changeData(v,'b',52);}public void changeb53(View v){changeData(v,'b',53);}
public void changeb54(View v){changeData(v,'b',54);}public void changeb55(View v){changeData(v,'b',55);}public void changeb56(View v){changeData(v,'b',56);}public void changeb57(View v){changeData(v,'b',57);}
public void changeb58(View v){changeData(v,'b',58);}public void changeb59(View v){changeData(v,'b',59);}public void changeb60(View v){changeData(v,'b',60);}public void changeb61(View v){changeData(v,'b',61);}
public void changeb62(View v){changeData(v,'b',62);}public void changeb63(View v){changeData(v,'b',63);}public void changeb64(View v){changeData(v,'b',64);}public void changeb65(View v){changeData(v,'b',65);}
public void changeb66(View v){changeData(v,'b',66);}public void changeb67(View v){changeData(v,'b',67);}public void changeb68(View v){changeData(v,'b',68);}public void changeb69(View v){changeData(v,'b',69);}
public void changeb70(View v){changeData(v,'b',70);}public void changeb71(View v){changeData(v,'b',71);}public void changeb72(View v){changeData(v,'b',72);}public void changeb73(View v){changeData(v,'b',73);}
public void changeb74(View v){changeData(v,'b',74);}public void changeb75(View v){changeData(v,'b',75);}public void changeb76(View v){changeData(v,'b',76);}public void changeb77(View v){changeData(v,'b',77);}
public void changeb78(View v){changeData(v,'b',78);}public void changeb79(View v){changeData(v,'b',79);}public void changeb80(View v){changeData(v,'b',80);}public void changeb81(View v){changeData(v,'b',81);}
public void changeb82(View v){changeData(v,'b',82);}public void changeb83(View v){changeData(v,'b',83);}



private boolean isTeam1Data(int id)
{
	if(!isBroomsUpFilled)
	{return false;}
	else
	{
	
		boolean found = false;
		int[] list = getTeam1IdList();
		for(int i : list)
		{
			if(i==id)
			{found =true;}
		}
		return found;
	}
}


private int[] getTeam1IdList()
{
	int[] result;
	if(!isBroomsUpFilled)
	{return null;}
	else
	{
		if(broomsUp.charAt(2)=='1')
		{
			result = new int[42];
			int counter =0;
			for(int i=R.id.q1;i<=R.id.q83;i++)
			{
			
			
				 if (!(i==R.id.tableRow0||i==R.id.tableRow1||i==R.id.tableRow2||i==R.id.tableRow3||i==R.id.tableRow4||i==R.id.tableRow5||i==R.id.tableRow6
						||i==R.id.tableRow7||i==R.id.tableRow8||i==R.id.tableRow9||i==R.id.tableRow10||i==R.id.tableRow11||i==R.id.tableRow12||i==R.id.tableRow13||i==R.id.tableRow14)){
					
					 counter++;
					 if(counter%4==1)
					 {
						 result[(counter-1)/4]=i;
					 }
					 
				}
			}
		}
		else
		{
			result = new int[41];
			int counter =0;
			for(int i=R.id.q1;i<=R.id.q83;i++)
			{
			
			
				 if (!(i==R.id.tableRow0||i==R.id.tableRow1||i==R.id.tableRow2||i==R.id.tableRow3||i==R.id.tableRow4||i==R.id.tableRow5||i==R.id.tableRow6
						||i==R.id.tableRow7||i==R.id.tableRow8||i==R.id.tableRow9||i==R.id.tableRow10||i==R.id.tableRow11||i==R.id.tableRow12||i==R.id.tableRow13||i==R.id.tableRow14)){
					
					 counter++;
					 if(counter%4==3)
					 {
						 result[(counter-3)/4]=i;
					 }
					 
				}
			}
		}
		return result;
	}
}





@Override
public boolean onCreateOptionsMenu(Menu menu) {

	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.add_data, menu);
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
		View rootView = inflater.inflate(R.layout.fragment_add_data,
				container, false);
		return rootView;
	}
}
	
}
