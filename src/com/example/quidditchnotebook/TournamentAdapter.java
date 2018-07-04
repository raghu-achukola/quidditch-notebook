package com.example.quidditchnotebook;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TournamentAdapter extends ArrayAdapter<Tournament> {

	 private Context context;
	    private ArrayList<Tournament> tournaments;
	    private static LayoutInflater inflater = null;
	
	    public TournamentAdapter(Context context, int resource, ArrayList<Tournament> tournamentList)
	    {
	    	super(context,resource,tournamentList);
	    	 try {
	             this.context=context;
	             tournaments = tournamentList;
	             

	             inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	         } catch (Exception e) {

	         }
	    }
	    public int getCount() {
	        return tournaments.size();
	    }

	    public Tournament getItem(Tournament position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }
	    public static class ViewHolder {
	        public TextView name;
	        public TextView info;

	    }
	    public View getView(int position, View convertView, ViewGroup parent)
	    {
	    	
	    	View v = convertView;
	    	final ViewHolder holder;
	    	try
	    	{
	    		if(convertView==null)
	    		{
	    			v = inflater.inflate(R.layout.tournament_list_item, null);
	    			holder = new ViewHolder();
	    			holder.name = (TextView) v.findViewById(R.id.name);
	    			holder.info = (TextView) v.findViewById(R.id.info);
	    		}
	    		else
	    		{
	    			holder = (ViewHolder) v.getTag();
	    		}
	    		holder.name.setText(tournaments.get(position).getName());
	    		holder.info.setText(tournaments.get(position).getDate());
	    		
	    	}catch(Exception e){}
	    	return v;
	    }
	
	
}
