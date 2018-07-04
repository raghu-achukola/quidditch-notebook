package com.example.quidditchnotebook;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;



public class Game implements Serializable {

	private int id;
	private int team1;
	private int team2;
	private String gameData;
	private int tournamentID;
	private int level;
	
	public static final int GAME_POOL_PLAY=1;
	public static final int GAME_BRACKET_PLAY_UNDETERMINED = 2;
	public static final int GAME_ROUND_OF_16 = 3;
	public static final int GAME_QUARTERFINALS=4;
	public static final int GAME_SEMIFINALS=5;
	public static final int GAME_FINALS =6;
	public static final int GAME_OTHER = 7;
	public Game()
	{
		id=-1; level=GAME_OTHER;tournamentID=-1;
	}
	public Game (int gID, int t1, int t2, int tID, int gameLevel)
	{
		id = gID;
		team1=t1;
		team2=t2;
		gameData="";
		tournamentID=tID;
		level=gameLevel;
	}
	public Game (int gID, int t1, int t2, int tID, int gameLevel, String data )
	{
		id = gID;
		team1=t1;
		team2=t2;
		gameData=data;
		tournamentID=tID;
		level=gameLevel;
	}
	public int getID(){return id;}
	public int getFirstTeam(){return team1;}
	public int getSecondTeam(){return team2;}
	public String getData(){return gameData;}
	public int getTournamentID(){return tournamentID;}
	public int getGameLevel(){return level;}
	public void setID(int nId){id=nId;}
	public void setFirstTeam(int nT1){team1=nT1;}
	public void setSecondTeam(int nT2){team2=nT2;}
	public void setGameLevel(int type){level = type;}
	public void setTournamentID(int nTid){tournamentID=nTid;}
	public void setData(String data){gameData=data;}
	
	
	
	
	
	
	public static Game invert(Game g)
	{
		String d = g.getData();
		int t1 = g.getFirstTeam();
		int t2 = g.getSecondTeam();
		return new Game(g.getID(),t2,t1,g.getTournamentID(),g.getGameLevel(),invert(d));
		
	}
	private static String invert(String s)
	{
		String newString = "";
		switch(s.charAt(0))
		{
			case '0':	newString = newString+"0";
						break;
			case '1':	newString = newString+"2";
						break;
			case '2': 	newString = newString+"1";
		}
		switch(s.charAt(1))
		{
			case '1':	newString = newString+"2";
						break;
			case '2': 	newString = newString+"1";
						break;
			default : break;
		}
		switch(s.charAt(2))
		{
			case '1':	newString = newString+"2";
						break;
			case '2': 	newString = newString+"1";
						break;
			default : break;
		}
		newString = newString+s.substring(3,s.indexOf('S')-1);
		switch(s.charAt(s.indexOf('S')-1))
		{
			case 'F':	newString=newString+"DS";
						break;
			case 'D':	newString=newString+"FS";
						break;
			default:	break;
		}
		if(s.charAt(s.length()-2)!='F'&& s.charAt(s.length()-2)!='D')
		{
			switch(s.charAt(s.indexOf('S')+1))
			{
				case '0':	newString = newString+"0";
							break;
				case '1':	newString = newString+"2";
							break;
				case '2': 	newString = newString+"1";
			}
			switch(s.charAt(s.indexOf('S')+2))
			{
				case '1':	newString = newString+"2";
							break;
				case '2': 	newString = newString+"1";
							break;
				default : break;
			}
			switch(s.charAt(s.indexOf('S')+3))
			{
				case '1':	newString = newString+"2";
							break;
				case '2': 	newString = newString+"1";
							break;
				default : break;
			}
			newString = newString + s.substring(s.indexOf('S')+4,s.length()-2);
			switch(s.charAt(s.length()-2))
			{
				case 'O':	newString = newString+"PS";
							break;
				case 'P': 	newString = newString+"OS";
							break;
				case 'N':	newString = newString+"NS";
							break;
				default : break;
			}
		}
		return newString;
	}
	public static int getLargestID(ArrayList<Game> a)
	{
		int max = 0;
		for(Game t: a)
		{
			max = (t.getID()>max)?t.getID():max;
		}
		return max;
	}
	public String toString()
	{
		return String.valueOf(this.getID())+";"+String.valueOf(this.getFirstTeam())+";"+String.valueOf(this.getSecondTeam())+";"
	               +String.valueOf(this.getTournamentID())+";"+String.valueOf(this.getGameLevel())+";"+this.getData();
	}
	public static String convertGameType(int gameType)
	{
		if(gameType == Game.GAME_BRACKET_PLAY_UNDETERMINED )
		{return "Bracket Play";}
		else if (gameType==Game.GAME_FINALS)
		{return "Finals";}
		else if (gameType==Game.GAME_SEMIFINALS)
		{return "Semifinals";}
		
		else if (gameType==Game.GAME_POOL_PLAY)
		{return "Pool Play";		}
		else if (gameType==Game.GAME_QUARTERFINALS)
		{return "Quarterfinals";}
		else if (gameType==Game.GAME_ROUND_OF_16)
		{return "Round of 16";}
		else if (gameType==Game.GAME_OTHER)
		{return "";}
		else{return "";}
	}
	public boolean hasSameValues(Game containsNullValues)
	{
		
		if(!(this.getGameLevel()==containsNullValues.getGameLevel()||containsNullValues.getGameLevel()==-1))
		{

			Log.w("hsv","1");
			return false;
		}
		if (!(this.getTournamentID()==containsNullValues.getTournamentID()||containsNullValues.getTournamentID()==-1))
		{Log.w("hsv","2");
			return false;
			
		}
		if(containsNullValues.getFirstTeam()==-1)
		{
			if(!(containsNullValues.getSecondTeam()==this.getFirstTeam()||this.getSecondTeam()==containsNullValues.getSecondTeam()||(containsNullValues.getSecondTeam()==-1)))
			{Log.w("hsv","3");
				return false;
			}
			
		}
		if(containsNullValues.getSecondTeam()==-1)
		{
			if(!(containsNullValues.getFirstTeam()==this.getFirstTeam()||this.getSecondTeam()==containsNullValues.getFirstTeam()||(containsNullValues.getFirstTeam()==-1)))
			{Log.w("hsv","4");
				return false;
			}
		}
		if(containsNullValues.getFirstTeam()!=-1 && containsNullValues.getSecondTeam()!=-1)
		{
			if(!((containsNullValues.getFirstTeam()==this.getFirstTeam()&&containsNullValues.getSecondTeam()==this.getSecondTeam())
					||containsNullValues.getFirstTeam()==this.getSecondTeam()&&containsNullValues.getSecondTeam()==this.getFirstTeam()))
			{
				Log.w("hsv","5");
				return false;
			}
		}
		return true;

		
	}
}
