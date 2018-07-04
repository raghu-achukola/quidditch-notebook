package com.example.quidditchnotebook;

import java.io.FileOutputStream;
import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class OvertimeActivity extends ActionBarActivity implements QuaffleDialogFragment.QuaffleListener {

	int BUSselection=-1;
	int Qselection = -1;
	int Bselection = -1;
	TextView currentView;
	TextView startingBox;
	TextView tournamentText;
	TextView gameText;
	String tournamentName;
	String t1Name;
	String t2Name;
	String broomsUp = "";
	Game game;
	String score1;
	String score2;
	int baseScore1;
	int baseScore2;
	boolean isBroomsUpFilled;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overtime);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
		Bundle b = getIntent().getExtras();
		tournamentName = b.getString("tournamentName");
		tournamentText=(TextView)(findViewById(R.id.textView1));
		tournamentText.setText(tournamentName);
		t1Name = b.getString("t1Name");
		t2Name = b.getString("t2Name");
		score1 = b.getString("score1");
		score2 = b.getString("score2");
		baseScore1 = Integer.parseInt(score1);
		baseScore2 = Integer.parseInt(score2);
		((TextView)findViewById(R.id.name1)).setText(t1Name);
		((TextView)findViewById(R.id.name2)).setText(t2Name);
		((TextView)findViewById(R.id.score1)).setText(score1);
		((TextView)findViewById(R.id.score2)).setText(score1);
		
		
		gameText = (TextView)(findViewById(R.id.textView2));
		gameText.setText(t1Name+"-"+t2Name+" Overtime");
		game = (Game)(b.getSerializable("game"));
	}
	public void showBroomsUpDialog(){
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
				 
			 }
			 else if(broomsUp.charAt(1)=='2')
			 {
				 bubc.check(R.id.radioBUBC02);
				 
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
		
		//DialogFragment dialog = new BroomsUpDialogFragment();
		//dialog.show(getFragmentManager(), "BroomsUpDialogFragment");
	}
	private void reWriteBU(EditText e)
	{
		broomsUp = e.getEditableText().toString();
		isBroomsUpFilled=true;
		((TextView)findViewById(R.id.startingBox)).setText(broomsUp);
	 recalculateScore();
	
		
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
		{int s1 = baseScore1;
		 int s2 = baseScore2;
		 if(((String)((TextView)findViewById(R.id.startingBox)).getText()).charAt(0)=='1')
		 {
			 s1+=10;
		 }
		 else if(((String)((TextView)findViewById(R.id.startingBox)).getText()).charAt(0)=='2')
		 {
			 s2+=10;
		 }
		for(int i = R.id.oq1;i<=R.id.oq23;i++)
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
				result = new int[12];
				int counter =0;
				for(int i=R.id.oq1;i<=R.id.oq23;i++)
				{
				
				
					 if (!(i==R.id.tableRow0||i==R.id.tableRow1||i==R.id.tableRow2||i==R.id.tableRow3||i==R.id.tableRow4||i==R.id.tableRow5||i==R.id.tableRow6
							||i==R.id.tableRow7||i==R.id.tableRow8||i==R.id.tableRow9||i==R.id.tableRow10||i==R.id.tableRow11||i==R.id.tableRow12||i==R.id.tableRow13||i==R.id.tableRow14)){
						
						 counter++;
						 if(counter%2==1)
						 {
							 result[(counter-1)/2]=i;
						 }
						 
					}
				}
			}
			else
			{
				result = new int[11];
				int counter =0;
				for(int i=R.id.oq1;i<=R.id.oq23;i++)
				{
				
				
					 if (!(i==R.id.tableRow0||i==R.id.tableRow1||i==R.id.tableRow2||i==R.id.tableRow3||i==R.id.tableRow4||i==R.id.tableRow5||i==R.id.tableRow6
							||i==R.id.tableRow7||i==R.id.tableRow8||i==R.id.tableRow9||i==R.id.tableRow10||i==R.id.tableRow11||i==R.id.tableRow12||i==R.id.tableRow13||i==R.id.tableRow14)){
						
						 counter++;
						 if(counter%2==0)
						 {
							 result[(counter-2)/2]=i;
						 }
						 
					}
				}
			}
			return result;
		}
	}
/*	public void onBUSDialogSelect(DialogFragment dialog, int which)
	{BUSselection = which;
	broomsUp="";
	broomsUp = broomsUp+which;
	showBUBBDialog();
	}
	public void showBUBBDialog()
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
	{if(!isBroomsUpFilled)
	{
		Toast.makeText(OvertimeActivity.this, "Please fill in brooms up data first", Toast.LENGTH_SHORT).show();
	}
	else{	DialogFragment dialog = new QuaffleDialogFragment();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.overtime, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_overtime,
					container, false);
			return rootView;
		}
	}
	public void changeBroomsUp(View v)
	{
		 startingBox = (TextView)findViewById(R.id.startingBox);
			showBroomsUpDialog();
	}
	public void changeData(View v, int pos)
	{
		int newId = R.id.oq1;
		newId = newId + (pos-1);
		
	
		showQuaffleDialog();
		


		currentView = (TextView)(findViewById(newId));
	}
	public void changeq1(View v){changeData(v,1);}public void changeq2(View v){changeData(v,2);}public void changeq3(View v){changeData(v,3);}public void changeq4(View v){changeData(v,4);}
	public void changeq5(View v){changeData(v,5);}public void changeq6(View v){changeData(v,6);}public void changeq7(View v){changeData(v,7);}public void changeq8(View v){changeData(v,8);}
	public void changeq9(View v){changeData(v,9);}public void changeq10(View v){changeData(v,10);}public void changeq11(View v){changeData(v,11);}public void changeq12(View v){changeData(v,12);}
	public void changeq13(View v){changeData(v,13);}public void changeq14(View v){changeData(v,14);}public void changeq15(View v){changeData(v,15);}public void changeq16(View v){changeData(v,16);}
	public void changeq17(View v){changeData(v,17);}public void changeq18(View v){changeData(v,18);}public void changeq19(View v){changeData(v,19);}public void changeq20(View v){changeData(v,20);}
	public void changeq21(View v){changeData(v,21);}public void changeq22(View v){changeData(v,22);}public void changeq23(View v){changeData(v,23);}

	
	public void readData(View v)
	{
		String data = "";
		String extraData = "";
		boolean startingTeamBludgerControl=true;
		int counter=0;
		data = (String) ((TextView)findViewById(R.id.startingBox)).getText();
		if(data.equals(""))
		{
			Toast.makeText(getApplicationContext(), "Brooms Up Data must be filled in", Toast.LENGTH_SHORT);
		}
		else
		{
boolean snitch;
			for(int i=R.id.oq1;i<R.id.oq23;i++)
			{
				counter++;
				String raw = (String) ((TextView)findViewById(i)).getText();
				if (!(raw.equals("1S")||raw.equals("2S")))
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
					
					if(raw.equals("1S")){data = data +"OS";}
					else if (raw.equals("2S")){data = data+"PS";}
					
				}
			
			}
			
			boolean anyspaces = false;
			boolean inspace = false;
			
		String newData = "";
		if(data.indexOf(' ')==-1)
		{
			anyspaces=false;
		}
		else
		{
			anyspaces=true;
			for(int i = data.indexOf(' ');i<data.length();i++)
			{
					if(data.charAt(i)!=' ')
					{inspace = true;}
			}
		}
		
		
	
		if(inspace)
		{
			Toast.makeText(getApplicationContext(),"There are blank spaces between plays in your data. If a voided play occured, please enter it as such",Toast.LENGTH_LONG).show();
		}
		
		else
		{
			if(anyspaces )
			{
				newData = data.substring(0,data.indexOf(' ')+1);
			}
			else
			{
				newData = data;
			}
			if(newData.charAt(newData.length()-2)!='O'&&newData.charAt(newData.length()-2)!='P')
			{
				newData = newData + "NS";
			}
			game.setData(game.getData()+newData);
			Log.w("data",game.getData());
			String write="";
			try{
				
				FileOutputStream fos = openFileOutput("games.txt",Context.MODE_APPEND);
				write = String.valueOf(game.getID())+";"+String.valueOf(game.getFirstTeam())+";"+String.valueOf(game.getSecondTeam())+";"
						+String.valueOf(game.getTournamentID())+";"+String.valueOf(game.getGameLevel())+";"+game.getData()+"\n" ;
					fos.write(write.getBytes());
					
				fos.close();
				Log.w("data",write);
			}catch(Exception e)
			{
			}
			
			Intent i = new Intent (OvertimeActivity.this,ViewGameStatsActivity.class);
			i.putExtra("game", game); 
			i.putExtra("t1Name",t1Name);
			i.putExtra("t2Name", t2Name);
			i.putExtra("tournName",tournamentName);
			startActivity(i);
		}
	}}
}
