package com.example.quidditchnotebook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.net.ParseException;
import android.text.format.DateFormat;

public class Tournament implements Serializable {

private int id;
private int season;
private String name;
private String date;
private boolean day2;
private static final String DATE_PATTERN = "MM/dd/yyyy";
public Tournament()
{
	id=-1; season=-1; day2=false;
}
public Tournament(int tournamentId, int tournamentSeason, String tournamentName, String tournamentDate, boolean dayTwo)
{
	id=tournamentId;season=tournamentSeason;name=tournamentName;date=tournamentDate;day2=dayTwo;
}
public int getId(){return id;}
public int getSeason(){return season;}
public String getName(){return name;}
public String getDate(){return date;}
public boolean getDay2(){return day2;}
public void setId(int i) {id = i;}
public void setSeason(int s){season=s;}
public void setName(String n){name=n;}
public void setDate(String d){date=d;}
public void setDay2(boolean t){day2=t;}

public static int getLargestID(ArrayList<Tournament> a)
{
	int max = 0;
	for(Tournament t: a)
	{
		max = (t.getId()>max)?t.getId():max;
	}
	return max;
}
public static boolean isDateValid(String date) 
{
        try {
            SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
}

}
