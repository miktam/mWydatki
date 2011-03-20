package com.developand.mwydatki.data;

import java.util.List;

import com.developand.mwydatki.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailedOperationEntryAdapter extends OperationEntryAdapter {

	public DetailedOperationEntryAdapter(Context context,
			int textViewResourceId, List<OperationEntry> items, Activity ac) {
		super(context, textViewResourceId, items, ac);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.detailed_view, null);
		}
		OperationEntry o = items.get(position);
		if (o != null) {
			TextView saldo = (TextView) v.findViewById(R.id.icon);
			TextView mainTitle = (TextView) v.findViewById(R.id.secondLine);
			TextView descrOpOperation = (TextView) v.findViewById(R.id.opis);
			TextView date = (TextView) v.findViewById(R.id.date);

			if (position % 2 == 0) {

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
			} else
			{				
				// made invisible not needed fields
				date.setVisibility(View.GONE);
				descrOpOperation.setVisibility(View.GONE);
				if (null != mainTitle) {
					mainTitle.setText(o.getTag());
					mainTitle.setBackgroundColor(android.R.color.darker_gray);
					
				}
				if (null != saldo) {
					saldo.setText("" + o.getKwotaOperacji());
					saldo.setBackgroundColor(android.R.color.darker_gray);
				}				
			}

		}
		return v;
	}

}
