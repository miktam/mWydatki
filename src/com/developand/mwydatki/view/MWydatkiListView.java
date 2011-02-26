package com.developand.mwydatki.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.developand.mwydatki.DataReader;
import com.developand.mwydatki.DataReaderImpl;
import com.developand.mwydatki.data.MonthBill;
import com.developand.mwydatki.data.OperationEntry;

public class MWydatkiListView extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		DataReader dr = new DataReaderImpl();

		String readData;
		try {
			readData = dr.readData().get(0).toString();

			MonthBill mb = new MonthBill();
			mb.parseSource(readData);

			List<String> listaWydatkow = new ArrayList<String>();
			for (OperationEntry op : mb.getOperations()) {
				listaWydatkow.add(op.toString());
			}

			setListAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, listaWydatkow));
			getListView().setTextFilterEnabled(true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
