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
				operations);
		setListAdapter(this.opEntryAdapter);

		dataReader = new Runnable() {

			public void run() {
				downloadData();
			}
		};
		Thread thread = new Thread(null, dataReader, "MagentoBackground");
		thread.start();

		String converting = this.getString(R.string.converting);
		String wait = this.getString(R.string.please_wait);
		progressDialog = ProgressDialog.show(BillsView.this, wait, converting,
				true);
	}

	private Runnable returnRes = new Runnable() {

		public void run() {
			if (operations != null && operations.size() > 0) {
				opEntryAdapter.notifyDataSetChanged();
				for (int i = 0; i < operations.size(); i++)
					opEntryAdapter.add(operations.get(i));
			}
			progressDialog.dismiss();
			opEntryAdapter.notifyDataSetChanged();
		}
	};

	private void downloadData() {

		DataReader dr = new DataReaderImpl();
		try {
			dr.readData();
			operations = dr.getSortedOperationsByIndex(0, OperationType.ALL);

			Log.v(TAG, "size = " + operations.size());

			runOnUiThread(returnRes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class OperationEntryAdapter extends ArrayAdapter<OperationEntry> {

		private List<OperationEntry> items;

		public OperationEntryAdapter(Context context, int textViewResourceId,
				List<OperationEntry> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			OperationEntry o = items.get(position);
			if (o != null) {
				TextView saldo = (TextView) v.findViewById(R.id.icon);
				TextView mainTitle = (TextView) v.findViewById(R.id.secondLine);
				TextView descrOpOperation = (TextView) v
						.findViewById(R.id.opis);
				TextView date = (TextView) v.findViewById(R.id.date);

				if (o.isCategory()) {
					if (null != mainTitle) {
						mainTitle.setText(o.getMainTitle());
					}
					if (null != descrOpOperation) {
						descrOpOperation.setText("");
					}
					if (null != saldo) {
						saldo.setText("" + o.getKwotaOperacji());
					}

					if (null != date) {
						date.setText("");
					}
					
				} else {

					if (null != mainTitle) {
						mainTitle.setText(o.getMainTitle());
					}
					if (null != descrOpOperation) {
						descrOpOperation.setText(o.getOpisOperacji());
					}
					if (null != saldo) {
						saldo.setText("" + o.getKwotaOperacji());
					}

					if (null != date) {
						date.setText(o.getDataOperacjiFormatted());
					}

				}
			}
			return v;
		}
	}
}