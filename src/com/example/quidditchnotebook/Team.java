package com.example.quidditchnotebook;



import java.io.Serializable;
import java.util.ArrayList;


public class Team implements Serializable {
	public final int REGION_CANADA =1;
	public final int REGION_EUROPE=2;
	public final int REGION_OCEANIA=3;
	public final int REGION_MEXICO=4;
	public final int REGION_SW = 5;
	public final int REGION_MW = 6;
	public final int REGION_NE = 7;
	public final int REGION_S = 8;
	public final int REGION_W = 9;
	public final int REGION_NW = 10;
	public final int REGION_MA = 11;
	public final int REGION_OTHER=12;
	
	private int region;
	private String name;
	private String city;
	private int id;
	
	
	public Team()
	{
		id =-1;region=-1;
	}
	public Team (int team_ID,String tName, int tRegion, String tCity )
	{
		id=team_ID;name=tName;city=tCity;region=tRegion;
	}
	public int getRegion(){return region;}
	public int getId(){return id;}
	public String getCity(){return city;}
	public String getName(){return name;}
	public void setRegion(int r){region=r;}
	public void setId(int i){ id=i;}
	public void setCity(String c){city=c;}
	public void setName(String n){name=n;}
	public static int getLargestID(ArrayList<Team> a)
	{
		int max = 0;
		for(Team t: a)
		{
			max = (t.getId()>max)?t.getId():max;
		}
		return max;
	}
	public static String getStringRegion(int r)
	{
		if(r==1){return "CA";}
		else if(r==2){return "EU";}
		else if(r==3){return "OC";}
		else if(r==4){return "MX";}
		else if(r==5){return "SW";}
		else if(r==6){return "MW";}
		else if(r==7){return "NE";}
		else if(r==8){return "S";}
		else if(r==9){return "W";}
		else if(r==10){return "NW";}
		else if(r==11){return "MA";}
		else if (r==12){return "OT";}
		else {return "N/A";}
	}
}
