package com.developand.mwydatki.data.concurrent;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.developand.mwydatki.AttachmentReader;
import com.developand.mwydatki.R;
import com.developand.mwydatki.data.DataReader;
import com.developand.mwydatki.data.DataReaderImpl;
import com.developand.mwydatki.data.OperationEntry;
import com.developand.mwydatki.data.OperationEntryAdapter;
import com.developand.mwydatki.data.common.OperationType;

public class DataDownloader implements Runnable {

	private static final String TAG = DataDownloader.class.getName();
	private Activity activity;
	private final OperationEntryAdapter opEnAdapter;
	private List<OperationEntry> operations;
	private OperationType opType;
	private ProgressDialog progressDialog;
	private boolean cacheMode = false;

	public DataDownloader(List<OperationEntry> list, OperationType op,
			Activity act, OperationEntryAdapter adapter, ProgressDialog prog) {
		activity = act;
		opEnAdapter = adapter;
		operations = list;
		opType = op;
		progressDialog = prog;

		// if file not exist - show message that it is not OK
		if (!AttachmentReader.isFileExist()) {

			Dialog dialog = new Dialog(activity);
			dialog.setContentView(R.layout.dialog);
			dialog.setTitle(R.string.howto_label);
			// do not let the user to go away
			dialog.setCancelable(false);
			TextView text = (TextView) dialog.findViewById(R.id.textViewDialog);
			text.setText(R.string.howto);

			Button openMailButton = (Button) dialog.findViewById(R.id.openMail);
			openMailButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// start main activity with asking to stop
					Log.d(TAG, "go to main and die");
					activity.finish();
				}
			});

			dialog.show();
		}
	}

	/*
	 * what to run in background (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		downloadData();
	}

	/**
	 * only if you're sure that cached data is still valid (no need to re-read
	 * file on sdcard)
	 */
	public void enableCache() {
		cacheMode = true;
	}

	/**
	 * read and parse data in file
	 */
	private void downloadData() {

		Log.v(TAG, "start download data in separated thread");

		DataReader dr = new DataReaderImpl();
		try {
			dr.readData(cacheMode);
			operations = dr.getOperationsByIndex(0, opType);

			if (null == operations || operations.size() == 0)
				return;

			Log.d(TAG, "size = " + operations.size());

			activity.runOnUiThread(new Runnable() {
				public void run() {
					opEnAdapter.clear();
					if (operations != null && operations.size() > 0) {
						opEnAdapter.notifyDataSetChanged();
						for (int i = 0; i < operations.size(); i++)
							opEnAdapter.add(operations.get(i));
					}
					progressDialog.dismiss();
					opEnAdapter.notifyDataSetChanged();
					
					
					Toast.makeText(activity, "Dotknij wpis żeby zobaczyć szczegóły", Toast.LENGTH_SHORT).show();

				}
			});
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
	}

}
