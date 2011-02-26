package com.developand.mwydatki;

import java.io.IOException;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.developand.mwydatki.data.DataReader;
import com.developand.mwydatki.data.DataReaderImpl;
import com.developand.mwydatki.data.common.OperationType;

public class DochodyListView extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		try {

			DataReader dr = new DataReaderImpl();
			dr.readData();

			setListAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1,
					dr.getOperationsStringByIndex(0, OperationType.PLUS)));
			getListView().setTextFilterEnabled(true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
