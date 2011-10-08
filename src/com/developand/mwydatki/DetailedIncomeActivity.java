package com.developand.mwydatki;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.developand.mwydatki.data.DetailedOperationEntryAdapter;
import com.developand.mwydatki.data.OperationEntry;
import com.developand.mwydatki.data.common.OperationType;
import com.developand.mwydatki.data.concurrent.DataDownloader;

public class DetailedIncomeActivity extends ListActivity {

	private ProgressDialog progressDialog;
	private List<OperationEntry> operations = null;
	private DetailedOperationEntryAdapter opEntryAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		operations = new ArrayList<OperationEntry>();
		this.opEntryAdapter = new DetailedOperationEntryAdapter(this,
				R.layout.detailed_view, operations, this);
		setListAdapter(this.opEntryAdapter);

		progressDialog = ProgressDialog.show(DetailedIncomeActivity.this, "",
				this.getString(R.string.converting), true);

		Thread thread = new Thread(null, new DataDownloader(operations,
				OperationType.DETAILED_PLUS, this, opEntryAdapter, progressDialog),
				"parser");
		thread.start();
	}
}