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
import com.developand.mwydatki.data.common.OperationType;
import com.developand.mwydatki.data.concurrent.DataDownloader;
import com.developand.mwydatki.tools.ToastMaker;

public class DetailedIncomeActivity extends ListActivity {

	private ProgressDialog progressDialog;
	private List<OperationEntry> operations = null;
	private DetailedOperationEntryAdapter opEntryAdapter;
	private static final String TAG = DetailedIncomeActivity.class.getName();

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
				OperationType.DETAILED_PLUS, this, opEntryAdapter,
				progressDialog), "parser");
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
		Intent myIntent = new Intent(DetailedIncomeActivity.this, Main.class);
		myIntent.putExtra("exit", "yes");
		Log.i(TAG, "put exit extra to true");
		DetailedIncomeActivity.this.startActivity(myIntent);
	}
}