package com.developand.mwydatki.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class GroupedEntry {

	private static final String TAG = "GroupedEntry";
	Map<GroupedEntryDescripton, List<OperationEntry>> ops = new HashMap<GroupedEntryDescripton, List<OperationEntry>>();
	Double sum = 0.0;

	public void add(OperationEntry op) {
		String main = op.getMainTitle();
		Log.v(TAG, "main = " + main);
		if (ops.keySet().contains(main)) {
			Log.v(TAG, main + " already there");
			// TODO finish that
			ops.get(main).add(op);
		}
	}

}

class GroupedEntryDescripton {
	final public String operation;
	private Double saldo = 0.0;

	public GroupedEntryDescripton() {
		operation = "";
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return this.operation.equals(o);
	}
}
