package com.developand.mwydatki.concurrent.data;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
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
	
	public DataDownloader(List<OperationEntry> list, OperationType op, Activity act, OperationEntryAdapter adapter, ProgressDialog prog) {
		activity = act;
		opEnAdapter = adapter;
		operations = list;
		opType = op;
		progressDialog = prog;
	}
	
	public void run() {
		downloadData();
	}
	
	/**
	 *  only if you're sure that cached data is still valid (no need to re-read file on sdcard)
	 */
	public void enableCache()
	{
		cacheMode = true;
	}
	
	private void downloadData() {
		
		Log.v(TAG, "start download data in separated thread");

		DataReader dr = new DataReaderImpl();
		try {
			// no not allow cache - it is first view to show
			dr.readData(cacheMode);
			operations = dr.getOperationsByIndex(0, opType);

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
					
				}
			});
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
	}	

}
