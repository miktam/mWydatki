package com.developand.mwydatki;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.developand.mwydatki.data.DetailedOperationEntryAdapter;
import com.developand.mwydatki.data.OperationEntry;
import com.developand.mwydatki.data.OperationEntryAdapter;
import com.developand.mwydatki.data.common.OperationType;
import com.developand.mwydatki.data.concurrent.DataDownloader;

public class DetailedBillsView extends ListActivity {
	
	private static final String TAG = DetailedBillsView.class.getName();
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

		progressDialog = ProgressDialog.show(DetailedBillsView.this, "",
				this.getString(R.string.converting), true);

		DataDownloader dd = new DataDownloader(operations, OperationType.DETAILED,
				this, opEntryAdapter, progressDialog);
		dd.enableCache();
		Thread thread = new Thread(null, dd, "parser");
		thread.start();
	}

}
