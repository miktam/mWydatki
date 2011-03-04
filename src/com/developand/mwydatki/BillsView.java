package com.developand.mwydatki;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developand.mwydatki.data.DataReader;
import com.developand.mwydatki.data.DataReaderImpl;
import com.developand.mwydatki.data.OperationEntry;
import com.developand.mwydatki.data.OperationEntryAdapter;
import com.developand.mwydatki.data.common.OperationType;

public class BillsView extends ListActivity {

	private static final String TAG = "BillView";
	private ProgressDialog progressDialog = null;
	private List<OperationEntry> operations = null;
	private OperationEntryAdapter opEntryAdapter;
	private Runnable dataReader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		operations = new ArrayList<OperationEntry>();
		this.opEntryAdapter = new OperationEntryAdapter(this, R.layout.row,
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
		progressDialog = ProgressDialog.show(BillsView.this, wait, converting,
				true);
	}

	private void downloadData() {

		DataReader dr = new DataReaderImpl();
		try {
			dr.readData();
			operations = dr.getOperationsByIndex(0, OperationType.ALL);

			Log.v(TAG, "size = " + operations.size());

			this.runOnUiThread(new Runnable() {
				
				public void run() {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}