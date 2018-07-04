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

public class BroomsUpDialogFragment extends DialogFragment {
	public interface BroomsUpListener
	{
		public void onBUSDialogSelect(DialogFragment dialog, int which);

	}
	BroomsUpListener mListener;
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try{
			mListener = (BroomsUpListener) activity;
		}catch(Exception e)
		{
			Log.e("exception","budf");
		}
		
	}
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Brooms Up Score?")
		.setItems(R.array.brooms_up_score, new OnClickListener(){
			
			public void onClick(DialogInterface dialog, int which)
			{
				mListener.onBUSDialogSelect(BroomsUpDialogFragment.this, which);
			}
			
			
		});
		return builder.create();
	}

}
