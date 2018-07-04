package com.example.quidditchnotebook;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;


public class BludgerDialogFragment extends DialogFragment {
	public interface BludgerListener
	{
		public void onBDialogSelect(DialogFragment dialog, int which);

	}
	BludgerListener mListener;
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try{
			mListener = (BludgerListener) activity;
		}catch(Exception e)
		{
			Log.e("exception","budf");
		}
	}
	
	
	
	
	@Override
public Dialog onCreateDialog(Bundle savedInstanceState)
{
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	builder.setTitle("Choose an option")
	.setItems(R.array.bludger_types, new DialogInterface.OnClickListener(){
		public void onClick(DialogInterface dialog, int which)
		{
			mListener.onBDialogSelect(BludgerDialogFragment.this, which);
		}
		
		
		
	});
	return builder.create();
}
}
