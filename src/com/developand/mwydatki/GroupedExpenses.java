package com.developand.mwydatki;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.developand.mwydatki.data.DataReader;
import com.developand.mwydatki.data.DataReaderImpl;
import com.developand.mwydatki.data.GroupedOperationEntry;
import com.developand.mwydatki.data.GroupedOperationEntryAdapter;
import com.developand.mwydatki.tools.ToastMaker;

public class GroupedExpenses extends ListActivity {

	private static final String TAG = "GroupedExpenses";

	private ProgressDialog progressDialog = null;
	private List<String> operations = null;
	private GroupedOperationEntryAdapter opEntryAdapter;
	private Runnable dataReader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		operations = new ArrayList<String>();
		this.opEntryAdapter = new GroupedOperationEntryAdapter(this,
				R.layout.grouped_view, operations, this);
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
		progressDialog = ProgressDialog.show(GroupedExpenses.this, wait,
				converting, true);
	}

	private void downloadData() {

		DataReader dr = new DataReaderImpl();
		try {
			// no not allow cache
			dr.readData(false);
			List<Object> list = Arrays.asList(GroupedOperationEntry
					.getInstance().ops.keySet().toArray());
			for (Object obj : list)
				operations.add(obj.toString());

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
			ToastMaker.getToast(this, e.getLocalizedMessage());
			this.finish();
		}
	}
}