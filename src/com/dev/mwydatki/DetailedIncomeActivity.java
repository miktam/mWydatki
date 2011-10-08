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
import com.dev.mwydatki.data.common.OperationType;
import com.dev.mwydatki.data.concurrent.DataDownloader;
import com.dev.mwydatki.tools.ToastMaker;
import com.dev.mwydatki.R;

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
}