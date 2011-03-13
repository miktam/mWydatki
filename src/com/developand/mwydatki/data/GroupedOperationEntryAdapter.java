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

public class GroupedOperationEntryAdapter extends ArrayAdapter<String> {

	private List<String> items;
	private Activity activity;

	public GroupedOperationEntryAdapter(Context context, int textViewResourceId,
			List<String> items, Activity ac) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.activity = ac;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.grouped_view, null);
		}
		String o = items.get(position);
		if (o != null) {
			TextView total = (TextView) v.findViewById(R.id.total);
			TextView mainTitle = (TextView) v.findViewById(R.id.mainTitle);


			if (null != mainTitle) {
				mainTitle.setText(o);
			}
			if (null != total) {
				// TODO count
				total.setText("400");
			}
			

		}
		return v;
	}
}