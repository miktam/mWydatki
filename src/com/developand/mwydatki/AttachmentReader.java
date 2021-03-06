package com.developand.mwydatki;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.developand.mwydatki.tools.ToastMaker;

public class AttachmentReader extends Activity {

	private static final String TAG = "AttachmentReader";
	private static final String PATH_TO_FILE = "/sdcard/mwydatki/savedFromAttachment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.d(TAG, "AttachmentReader.onCreate");

		super.onCreate(savedInstanceState);

		readIntent(getIntent());

		Uri path = getIntent().getData();
		Log.v(TAG, "attachment to read = " + path);

		ContentResolver cr = getContentResolver();

		try {
			InputStream stream = cr.openInputStream(path);
			saveFileInPeace(stream);

			Intent prodigy = new Intent(this, Main.class);
			this.startActivity(prodigy);

		} catch (Exception e) {
			ToastMaker.getToast(this, e.getLocalizedMessage());
			this.finish();
		}

	}

	private void saveFileInPeace(InputStream fis) throws Exception {

		File destinationFile = new File(PATH_TO_FILE);
		byte[] readData = new byte[1024];
		FileOutputStream fos = new FileOutputStream(destinationFile);
		int i = fis.read(readData);

		while (i != -1) {
			fos.write(readData, 0, i);
			i = fis.read(readData);
		}
		fis.close();
		fos.close();

	}

	private void readIntent(Intent intent) {
		String path = intent.getData().getPath();
		Log.v(TAG, "path = " + path);

		String dataString = intent.getDataString();
		Log.v(TAG, "dataString = " + dataString);

		String uri = intent.getData().toString();
		Log.v(TAG, "uri = " + uri);
	}

	public static boolean isFileExist() {
		File destinationFile = new File(PATH_TO_FILE);
		boolean fileExist = destinationFile.exists();
		Log.d(TAG, "is file exist? " + fileExist);
		return fileExist;
	}
}
