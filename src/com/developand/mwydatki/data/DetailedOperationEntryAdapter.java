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
		
		OperationEntry opEntry = items.get(position);
		if (opEntry != null) {
			TextView saldoView = (TextView) v.findViewById(R.id.icon);
			TextView mainTitleView = (TextView) v.findViewById(R.id.secondLine);
			TextView descView = (TextView) v.findViewById(R.id.opis);
			TextView dateView = (TextView) v.findViewById(R.id.date);

			if (opEntry.isFaked) {
				// made invisible not needed fields
				dateView.setVisibility(View.GONE);
				descView.setVisibility(View.GONE);
				if (null != mainTitleView) {
					mainTitleView.setText(opEntry.getMainTitle());
					mainTitleView.setBackgroundColor(colorToShowGroup);
				}
				if (null != saldoView) {
					saldoView.setText("" + opEntry.getKwotaOperacji());
					saldoView.setBackgroundColor(colorToShowGroup);
				}

			} else {
				
				descView.setVisibility(View.GONE);
				dateView.setVisibility(View.GONE);
				if (null != mainTitleView) {
					mainTitleView.setText(opEntry.getTag());
				}
				
				if (null != saldoView) {
					saldoView.setText("" + opEntry.getKwotaOperacji());
				}

//				if (null != dateView) {
//					dateView.setText(opEntry.getDataKsiegowaniaFormatted());
//				}
			}

		}
		return v;
	}

}
