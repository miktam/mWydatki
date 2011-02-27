package com.developand.mwydatki;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.developand.mwydatki.data.DataReader;
import com.developand.mwydatki.data.DataReaderImpl;
import com.developand.mwydatki.data.common.OperationType;

public class AttachmentReader extends Activity {

	private static String encoding = "iso-8859-2";

	private static final String TAG = "AttachmentReader";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		readIntent(getIntent());

		Uri path = getIntent().getData();
		Log.v(TAG, "attachment to read = " + path);

		ContentResolver cr = getContentResolver();

		try {
			InputStream stream = cr.openInputStream(path);

			StringBuilder text = new StringBuilder();
			String NL = System.getProperty("line.separator");
			Scanner scanner = new Scanner(stream, encoding);
			try {
				while (scanner.hasNextLine()) {
					text.append(scanner.nextLine() + NL);
				}
			} finally {
				scanner.close();
			}

			Log.v(TAG, text.toString());

			DataReader dr = new DataReaderImpl();
			List<String> list = dr.getOperationsFromStringByType(
					text.toString(), OperationType.ALL);

			for (String s : list)
				Log.v(TAG, s);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void readIntent(Intent intent) {
		String path = intent.getData().getPath();
		Log.v(TAG, "path = " + path);

		String dataString = intent.getDataString();
		Log.v(TAG, "dataString = " + dataString);

		String uri = intent.getData().toString();
		Log.v(TAG, "uri = " + uri);
	}

}
