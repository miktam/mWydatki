package com.developand.mwydatki;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;

public class DataReaderImpl implements DataReader {

	private static String directory = "/sdcard/mwydatki/";
	private static String encoding = "iso-8859-2";

	private static final String TAG = "DataReader";
	
	List<Spanned> spannedList = null;
	
	/**
	 * @param path to directory with files
	 */
	public void setPath(String path)
	{
		directory = path;
	}

	/* (non-Javadoc)
	 * @see com.developand.mwydatki.DataReader#readData()
	 */
	public List<Spanned> readData() throws IOException {

		Log.v(TAG, "starting reading file");
		
		// mimic singleton here
		if (null != this.spannedList)
			return this.spannedList;
		
		spannedList = new ArrayList<Spanned>();

		File dir = new File(directory);

		// set of file
		List<String> files = new ArrayList<String>();
		files = Arrays.asList(dir.list());
		
		Log.v(TAG, "found files #:" + files.size());

		for (String file : files) {

			Spanned data = Html.fromHtml(read(directory + file));
			spannedList.add(data);
		}
		
		Log.v(TAG, "found spanned lists #:" + spannedList.size());
		
		return spannedList;

	}
	
	  private String read(String fileName) throws IOException {

		    StringBuilder text = new StringBuilder();
		    String NL = System.getProperty("line.separator");
		    Scanner scanner = new Scanner(new FileInputStream(fileName), encoding);
		    try {
		      while (scanner.hasNextLine()){
		        text.append(scanner.nextLine() + NL);
		      }
		    }
		    finally{
		      scanner.close();
		    }
		    return text.toString();
		  }

}
