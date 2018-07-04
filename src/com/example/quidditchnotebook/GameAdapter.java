package com.example.quidditchnotebook;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class GameAdapter extends ArrayAdapter<Game>{
	   private Context context;
	    private ArrayList<Game> games;
	    private ArrayList<Team> teams;
	    private ArrayList<Tournament> tournaments;
	    private static LayoutInflater inflater = null;
	    boolean multiple;

	    public GameAdapter(Context context, int resource, ArrayList<Game> objects,ArrayList<Team> teamList,ArrayList<Tournament> tournamentList, boolean multiple)
	    {
	    	super(context,resource,objects);
	    	 try {
	             this.context=context;
	             games=objects;
	             teams = teamList;
	             tournaments = tournamentList;
	             this.multiple=multiple;
	             

	             inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	         } catch (Exception e) {

	         }
	    }
	    public int getCount() {
	        return games.size();
	    }

	    public Game getItem(Game position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public static class ViewHolder {
	        public TextView s1;
	        public TextView s2;
	        public TextView t1;
	        public TextView t2;
	        public TextView bottom;
	        public CheckBox checkBox1;

	    }
	    public View getView(int position,View convertView, ViewGroup parent)
	    {
	   
	    	final ViewHolder holder;
	    	try
	    	{
	    		if (convertView == null) {
	    			if(!multiple)
	                {convertView = inflater.inflate(R.layout.game_list_item, null);}
	    			else
	    			{
	    				convertView = inflater.inflate(R.layout.multiple_game_list_item, null);
	    			}
	                holder = new ViewHolder();
	                holder.s1=(TextView)convertView.findViewById(R.id.s1);
	                holder.t1=(TextView)convertView.findViewById(R.id.t1);
	                holder.t2=(TextView)convertView.findViewById(R.id.t2);
	                holder.s2=(TextView)convertView.findViewById(R.id.s2);
	                holder.bottom=(TextView)convertView.findViewById(R.id.bottom);
	                if(multiple)
	                {holder.checkBox1=(CheckBox)convertView.findViewById(R.id.checkBox1);}
	                convertView.setTag(holder);
	                
	    		}
	    		else
	    		{
	    			holder = (ViewHolder) convertView.getTag();
	    		}
	    		
	    	double[] tooMuchData = Statistics.process(games.get(position).getData());
	    	double score1 = tooMuchData[35];
	    	double score2 = tooMuchData[36];
	    	holder.s1.setText(String.valueOf((int)score1));
	    	holder.s2.setText(String.valueOf((int)score2));
	    	holder.t1.setText(teams.get(games.get(position).getFirstTeam()-1).getName());

	    	holder.t2.setText(teams.get(games.get(position).getSecondTeam()-1).getName());
	    	holder.bottom.setText(tournaments.get(games.get(position).getTournamentID()-1).getName());
	  
	    	
	    	
	    	}catch(Exception e)
	    	{
	    		
	    	}
	    	return convertView;
	    }

	    
}
