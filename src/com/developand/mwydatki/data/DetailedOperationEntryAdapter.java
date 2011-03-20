package com.developand.mwydatki.data;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developand.mwydatki.R;

public class DetailedOperationEntryAdapter extends OperationEntryAdapter {

	private static final String TAG = DetailedOperationEntryAdapter.class.getName();
	private int colorToShowGroup = android.R.color.darker_gray;

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
		Log.i(TAG, "size = " + items.size());
		
		OperationEntry o = items.get(position);
		if (o != null) {
			TextView saldo = (TextView) v.findViewById(R.id.icon);
			TextView mainTitle = (TextView) v.findViewById(R.id.secondLine);
			TextView descrOpOperation = (TextView) v.findViewById(R.id.opis);
			TextView date = (TextView) v.findViewById(R.id.date);

			if (o.isFaked) {
				// made invisible not needed fields
				date.setVisibility(View.GONE);
				descrOpOperation.setVisibility(View.GONE);
				if (null != mainTitle) {
					mainTitle.setText(o.getMainTitle());
					mainTitle.setBackgroundColor(colorToShowGroup);
				}
//				if (null != saldo) {
//					saldo.setText("" + o.getKwotaOperacji());
//					saldo.setBackgroundColor(colorToShowGroup);
//				}

			} else {
				
				descrOpOperation.setVisibility(View.GONE);
				if (null != mainTitle) {
					mainTitle.setText(o.getTag());
				}
				
				if (null != saldo) {
					saldo.setText("" + o.getKwotaOperacji());
				}

				if (null != date) {
					date.setText(o.getDataKsiegowaniaFormatted());
				}
			}

		}
		return v;
	}

}
