package com.developand.mwydatki.data;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developand.mwydatki.R;

public class OperationEntryAdapter extends ArrayAdapter<OperationEntry> {

	protected List<OperationEntry> items;
	protected Activity activity;

	public OperationEntryAdapter(Context context, int textViewResourceId,
			List<OperationEntry> items, Activity ac) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.activity = ac;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.detailed_view, null);
		}
		OperationEntry o = items.get(position);
		if (o != null) {
			TextView saldo = (TextView) v.findViewById(R.id.icon);
			TextView mainTitle = (TextView) v.findViewById(R.id.secondLine);
			TextView descrOpOperation = (TextView) v
					.findViewById(R.id.opis);
			TextView date = (TextView) v.findViewById(R.id.date);

			if (null != mainTitle) {
				mainTitle.setText(o.getMainTitle());
			}
			if (null != descrOpOperation) {
				descrOpOperation.setText(o.getTag());
			}
			if (null != saldo) {
				saldo.setText("" + o.getKwotaOperacji());
			}

			if (null != date) {
				date.setText(o.getDataKsiegowaniaFormatted());
			}

		}
		return v;
	}
}