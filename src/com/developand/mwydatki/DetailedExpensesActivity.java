package com.developand.mwydatki;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.developand.mwydatki.data.DetailedOperationEntryAdapter;
import com.developand.mwydatki.data.OperationEntry;
import com.developand.mwydatki.data.OperationEntryAdapter;
import com.developand.mwydatki.data.common.OperationType;
import com.developand.mwydatki.data.concurrent.DataDownloader;
import com.developand.mwydatki.tools.ToastMaker;

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
	
	@Override
	public void onBackPressed() {
		//this.moveTaskToBack(true);
		Intent myIntent = new Intent(DetailedExpensesActivity.this, Main.class);
		myIntent.putExtra("exit", "yes");
		Log.i(TAG, "put exit extra to true");
		DetailedExpensesActivity.this.startActivity(myIntent);

	}

}
