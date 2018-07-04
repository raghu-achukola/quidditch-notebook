package com.example.quidditchnotebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;

public class BUBBDialogFragment extends DialogFragment {
	public interface BUBBListener
	{
		public void onBUBBDialogSelect(DialogFragment dialog, int which);

	}
	BUBBListener mListener;
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try{
			mListener = (BUBBListener) activity;
		}catch(Exception e)
		{
			Log.e("exception","budf");
		}
		
	}
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Which team gained bludger control after the brooms up drive?")
		.setItems(R.array.brooms_up_other, new OnClickListener(){
			
			public void onClick(DialogInterface dialog, int which)
			{
				mListener.onBUBBDialogSelect(BUBBDialogFragment.this, which);
			}
			
			
		});
		return builder.create();
	}

}
