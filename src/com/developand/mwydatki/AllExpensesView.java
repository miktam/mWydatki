package com.developand.mwydatki;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.developand.mwydatki.data.DataReader;
import com.developand.mwydatki.data.DataReaderImpl;
import com.developand.mwydatki.data.OperationEntry;
import com.developand.mwydatki.data.OperationEntryAdapter;
import com.developand.mwydatki.data.common.OperationType;
import com.developand.mwydatki.tools.ToastMaker;

public class AllExpensesView extends ListActivity {

	private static final String TAG = "SumaListView";

	private ProgressDialog progressDialog = null;
	private List<OperationEntry> operations = null;
	private OperationEntryAdapter opEntryAdapter;
	private Runnable dataReader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		operations = new ArrayList<OperationEntry>();
		this.opEntryAdapter = new OperationEntryAdapter(this, R.layout.detailed_view,
				operations, this);
		setListAdapter(this.opEntryAdapter);

		dataReader = new Runnable() {

			public void run() {
				downloadData();
			}
		};
		Thread thread = new Thread(null, dataReader, "parser");
		thread.start();

		String converting = this.getString(R.string.converting);
		String wait = this.getString(R.string.please_wait);
		progressDialog = ProgressDialog.show(AllExpensesView.this, wait, converting,
				true);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		//super.onListItemClick(l, v, position, id);
		Log.v(TAG, "position = " + position);
		Object obj = operations.toArray()[position];
		ToastMaker.getToast(this, "you selected " + obj);
	}
	
	private void downloadData() {

		DataReader dr = new DataReaderImpl();
		try {
			// no not allow cache - it is first view to show
			dr.readData(false);
			operations = dr.getOperationsByIndex(0, OperationType.ALL);

			Log.d(TAG, "size = " + operations.size());

			this.runOnUiThread(new Runnable() {
				
				public void run() {
					opEntryAdapter.clear();
					if (operations != null && operations.size() > 0) {
						opEntryAdapter.notifyDataSetChanged();
						for (int i = 0; i < operations.size(); i++)
							opEntryAdapter.add(operations.get(i));
					}
					progressDialog.dismiss();
					opEntryAdapter.notifyDataSetChanged();					
				}
			});
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
	}	
}