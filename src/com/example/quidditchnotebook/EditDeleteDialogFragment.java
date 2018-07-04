package com.example.quidditchnotebook;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;

public class EditDeleteDialogFragment extends DialogFragment{
	public interface EditDeleteListener
	{
		public void onEditDeleteDialogSelect(DialogFragment dialog, int which);

	}
	EditDeleteListener mListener;
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try{
			mListener = (EditDeleteListener) activity;
		}catch(Exception e)
		{
			Log.e("exception","budf");
		}
		
	}
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Do you want to edit or delete this item?")
		.setItems(R.array.edit_delete, new OnClickListener(){
			
			public void onClick(DialogInterface dialog, int which)
			{
				mListener.onEditDeleteDialogSelect(EditDeleteDialogFragment.this, which);
			}
			
			
		});
		return builder.create();
	}
}
