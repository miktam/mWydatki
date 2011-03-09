package com.developand.mwydatki.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class GroupedOperationEntry {

	private static GroupedOperationEntry INSTANCE = null;
	private static final String TAG = GroupedOperationEntry.class.toString();
	
	public static Map<String, List<OperationEntry>> ops = new HashMap<String, List<OperationEntry>>();

	public static GroupedOperationEntry getInstance() {
		if (null == INSTANCE)
			INSTANCE = new GroupedOperationEntry();

		return INSTANCE;
	}
	
	public void add(OperationEntry op)
	{
		
		if (ops.containsKey(op.getMainTitle()))
		{
			ops.get(op.getMainTitle()).add(op);
			Log.v(TAG, op.getMainTitle() + " is added for first time");
		}
		else
		{
			List<OperationEntry> list = new ArrayList<OperationEntry>();
			list.add(op);
			ops.put(op.getMainTitle(), list);
			Log.v(TAG, op.getKwotaOperacji() + " is added to existing " + op.getMainTitle());
		}
	}

}
