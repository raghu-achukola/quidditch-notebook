package com.example.quidditchnotebook;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TeamAdapter extends ArrayAdapter<Team>{
	   private Context context;
	    private ArrayList<Team> teams;
	    private static LayoutInflater inflater = null;

	    public TeamAdapter(Context context, int resource, ArrayList<Team> teamList)
	    {
	    	super(context,resource,teamList);
	    	 try {
	             this.context=context;
	            
	             teams = teamList;
	             Log.w("size",String.valueOf(teams.size()));
	             inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	         } catch (Exception e) {
	        	 Log.w("exception","construct");
	         }
	    }
	    public int getCount() {
	        return teams.size();
	    }

	    public Team getItem(Team position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public static class ViewHolder {
	        public TextView firstLine;
	        public TextView region;
	        public TextView secondLine;

	    }
	    public View getView(int position,View convertView, ViewGroup parent)
	    {
	    	
	    	final ViewHolder holder;
	    	try
	    	{
	    		if (convertView == null) {
	    			
	    			convertView = inflater.inflate(R.layout.team_list_item, null);
	                holder = new ViewHolder();
	             
	                holder.region = (TextView)(convertView.findViewById(R.id.region));
	              
	    	    	holder.firstLine= (TextView)(convertView.findViewById(R.id.firstLine));
	    	    	
	    	    	holder.secondLine= (TextView)(convertView.findViewById(R.id.secondLine));
	    	    	
	    	    	convertView.setTag(holder);
	    		}
	    		else
	    		{
	    			holder = (ViewHolder) convertView.getTag();
	    		}
	    		
	  
	    	holder.region.setText(Team.getStringRegion(teams.get(position).getRegion()));
	    	Log.w("adapter",teams.get(position).getName());
	    	holder.secondLine.setText(teams.get(position).getName());
	    	holder.firstLine.setText(teams.get(position).getCity());
	    	
	    	
	    	}catch(Exception e)
	    	{
	    		Log.w("exception","view");
	    	}
	    	return convertView;
	    }

	    
}
