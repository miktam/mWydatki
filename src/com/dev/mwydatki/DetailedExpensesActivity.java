package com.dev.mwydatki;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.dev.mwydatki.data.DetailedOperationEntryAdapter;
import com.dev.mwydatki.data.OperationEntry;
import com.dev.mwydatki.data.OperationEntryAdapter;
import com.dev.mwydatki.data.common.OperationType;
import com.dev.mwydatki.data.concurrent.DataDownloader;
import com.dev.mwydatki.tools.ToastMaker;

public class DetailedExpensesActivity extends ListActivity {
	
	private static final String TAG = DetailedExpensesActivity.class.getName();
	private ProgressDialog progressDialog = null;
	private List<OperationEntry> operations = null;
	private OperationEntryAdapter opEntryAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "start " + this.toString());
		super.onCreate(savedInstanceState);
		operations = new ArrayList<OperationEntry>();
		this.opEntryAdapter = new DetailedOperationEntryAdapter(this,
				R.layout.detailed_view, operations, this);
		setListAdapter(this.opEntryAdapter);

		progressDialog = ProgressDialog.show(DetailedExpensesActivity.this, "",
				this.getString(R.string.converting), true);

		DataDownloader dd = new DataDownloader(operations, OperationType.DETAILED_MINUS,
				this, opEntryAdapter, progressDialog);
		dd.enableCache();
		Thread thread = new Thread(null, dd, "parser");
		thread.start();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.v(TAG, "position = " + position);
		OperationEntry opEntry = (OperationEntry) operations.toArray()[position];
		// skip toast if it is summary
		if (!opEntry.isFaked)
		ToastMaker.getToast(this, opEntry.toString());
	}

}
