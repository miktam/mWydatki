package com.developand.mwydatki;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.developand.mwydatki.concurrent.data.DataDownloader;
import com.developand.mwydatki.data.OperationEntry;
import com.developand.mwydatki.data.OperationEntryAdapter;
import com.developand.mwydatki.data.common.OperationType;

public class IncomeView extends ListActivity {
	private static final String TAG = IncomeView.class.getName();
	private ProgressDialog progressDialog = null;
	private List<OperationEntry> operations = null;
	private OperationEntryAdapter opEntryAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "start " + this.toString());
		super.onCreate(savedInstanceState);
		operations = new ArrayList<OperationEntry>();
		this.opEntryAdapter = new OperationEntryAdapter(this,
				R.layout.detailed_view, operations, this);
		setListAdapter(this.opEntryAdapter);

		progressDialog = ProgressDialog.show(IncomeView.this, "",
				this.getString(R.string.converting), true);

		DataDownloader dd = new DataDownloader(operations, OperationType.PLUS,
				this, opEntryAdapter, progressDialog);
		dd.enableCache();
		Thread thread = new Thread(null, dd, "parser");
		thread.start();
	}
}
