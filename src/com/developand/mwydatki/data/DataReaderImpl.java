package com.developand.mwydatki.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.developand.mwydatki.data.common.OperationType;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;

public class DataReaderImpl implements DataReader {

	private static String directory = "/sdcard/mwydatki/";
	private static String encoding = "iso-8859-2";

	private static final String TAG = "DataReader";

	private static Map<String, Spanned> mapFileData = null;

	private static List<String> currentOperationsAll = new ArrayList<String>();
	private static List<String> currentOperationsPlus = new ArrayList<String>();
	private static List<String> currentOperationsMinus = new ArrayList<String>();

	/**
	 * @param path
	 *            to directory with files
	 */
	public void setPath(String path) {
		directory = path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.developand.mwydatki.DataReader#readData()
	 */
	public Map<String, Spanned> readData(boolean allowCache) throws IOException {

		Log.v(TAG, "starting reading file");
		
		if (allowCache && null != mapFileData)
			return mapFileData;

		mapFileData = new HashMap<String, Spanned>();

		File dir = new File(directory);

		// set of file
		List<String> files = new ArrayList<String>();
		files = Arrays.asList(dir.list());

		Log.v(TAG, "found files #:" + files.size());

		for (String file : files) {
			// File f = new File(directory, file);
			Log.v(TAG, "found file:" + file);
			Spanned parsedData = Html.fromHtml(read(directory + file));
			mapFileData.put(file, parsedData);
			Log.v(TAG, "parsed this file");
		}

		Log.v(TAG, "found spanned lists #:" + mapFileData.size());

		return mapFileData;

	}

	// TODO add parsing only EKONTO ...
	// TODO add handling lack of access to sd card
	private String read(String fileName) throws IOException {

		StringBuilder text = new StringBuilder();
		String NL = System.getProperty("line.separator");
		Scanner scanner = new Scanner(new FileInputStream(fileName), encoding);
		try {
			while (scanner.hasNextLine()) {
				text.append(scanner.nextLine() + NL);
			}
		} finally {
			scanner.close();
		}
		return text.toString();
	}

	public Set<String> getFiles() {
		if (mapFileData != null)
			return mapFileData.keySet();
		return null;
	}

	public List<OperationEntry> getOperations(String file) {
		MonthBillData mb = MonthBillData.getInstance();
		mb.parseSource(mapFileData.get(file).toString());
		return mb.getOperations();
	}

	public List<String> getOperationsFromFileByType(String file,
			OperationType type) {
		MonthBillData mb = MonthBillData.getInstance();
		mb.parseSource(mapFileData.get(file).toString());
		List<String> opsString = new ArrayList<String>();
		for (OperationEntry op : mb.getOperations()) {
			switch (type) {
			case ALL:
				opsString.add(op.toString());
				break;
			case MINUS:
				if (op.getKwotaOperacji() < 0)
					opsString.add(op.toString());
			case PLUS:
				if (op.getKwotaOperacji() >= 0)
					opsString.add(op.toString());
			}
		}
		return opsString;
	}

	public List<String> getOperationsStringByIndex(Integer index,
			OperationType type) {
		List<String> ops = new ArrayList<String>();

		if (getFiles().size() <= index)
			return null;

		return getOperationsFromFileByType(
				getFiles().toArray()[index].toString(), type);
	}

	// used in attachment reading
	public List<String> getOperationsFromStringByType(String source,
			OperationType type) {
		MonthBillData mb = MonthBillData.getInstance();
		mb.parseSource(source);
		List<String> opsString = new ArrayList<String>();
		for (OperationEntry op : mb.getOperations()) {
			switch (type) {
			case ALL:
				opsString.add(op.toString());
				break;
			case MINUS:
				if (op.getKwotaOperacji() < 0)
					opsString.add(op.toString());
			case PLUS:
				if (op.getKwotaOperacji() >= 0)
					opsString.add(op.toString());
			}
		}
		return opsString;
	}

	public List<OperationEntry> getOperationsByIndex(Integer index,
			OperationType op) {
		MonthBillData mb = MonthBillData.getInstance();
		mb.parseSource(mapFileData.values().toArray()[index].toString());
		return mb.getOperationsByType(op);
	}

	public List<OperationEntry> getSortedOperationsByIndex(Integer index,
			OperationType op) {

		Map<String, List<OperationEntry>> map = new HashMap<String, List<OperationEntry>>();
		List<OperationEntry> newList = new ArrayList<OperationEntry>();

		List<OperationEntry> wholeList = getOperationsByIndex(index, op);
		String tmpMainTitle = new String();
		for (final OperationEntry o : wholeList) {
			if (map.containsKey(o.getMainTitle())) {
				map.get(o.getMainTitle()).add(o);
			} else {
				map.put(o.getMainTitle(), new ArrayList<OperationEntry>() {
					{
						add(o);
					}
				});
			}
		}

		Iterator i = map.entrySet().iterator();
		while (i.hasNext()) {

			Map.Entry<String, List<OperationEntry>> pair = (Entry<String, List<OperationEntry>>) i
					.next();
			
			Double saldo = 0.0;

			for (OperationEntry opMagic : pair.getValue()) {
				saldo += opMagic.getKwotaOperacji();
				newList.add(opMagic);
			}
			
			OperationEntry opDupa = new OperationEntry("", pair.getKey());
			opDupa.setCategory(true);
			opDupa.setKwotaOperacji(saldo);
			newList.add(opDupa);

		}
		return newList;
	}

}
