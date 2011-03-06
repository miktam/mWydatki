package com.developand.mwydatki.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import android.util.Log;

import com.developand.mwydatki.data.common.OperationType;
import com.developand.mwydatki.tools.Converter;

public class MonthBillData {

	private static final String TAG = "MonthBill";
	String month;
	int year;
	Double saldoPoczatkowe;
	Double saldoKoncowe;
	
	List<OperationEntry> opsList = new ArrayList<OperationEntry>();

	List<OperationEntry> opsListInPlus = null;
	List<OperationEntry> opsListInMinus = null;

	private static MonthBillData INSTANCE = null;

	public List<OperationEntry> getOperations() {
		// Log.v(TAG, "amount of operations = " + opsList.size());
		return opsList;
	}

	public void setYear(String year) {
		this.year = Integer.parseInt(year);
	}

	public void addOperationEntry(OperationEntry op) {
		opsList.add(op);
	}

	public void setMonth(String month) {

		this.month = month;
	}

	public void setSaldoPoczatkowe(String saldoPoczatkowe) {

		this.saldoPoczatkowe = Converter.toDouble(saldoPoczatkowe);

	}

	public void setSaldoKoncowe(String saldoKoncowe) {

		String[] st = saldoKoncowe.split(" ");
		this.saldoKoncowe = Converter.toDouble(st[st.length - 1]);
	}

	private MonthBillData() {
	}

	public static MonthBillData getInstance() {
		if (null == INSTANCE)
			INSTANCE = new MonthBillData();

		return INSTANCE;
	}

	public void parseSource(String source) {

		Log.v(TAG, "start parsing file");
		Log.v(TAG, source);
		
		Scanner s = new Scanner(source);
		parseMonthYear(s);

		setSaldoPoczatkowe(parseSaldoPoczatkowe(s));

		Date firstDate = parseFirstOpDate(s);

		parseEachOperation(s);

		// currently remove this op
		//normalizeOperationDates(firstDate);

	}
	
	private String parseSaldoPoczatkowe(Scanner s) {
		String saldoPoczatkowe = null;
		while (s.hasNextLine()) {
			String line = s.nextLine();

			if (line.startsWith("Saldo")) {
				String[] lineSaldo = line.split(" ");
				saldoPoczatkowe = lineSaldo[lineSaldo.length - 1];
				break;
			}
		}
		return saldoPoczatkowe;
	}

	private void parseMonthYear(Scanner s) {
		while (s.hasNextLine()) {
			String line = s.nextLine();

			if (line.startsWith("Elektroniczne zestawienie operacji za")) {
				String[] arr = line.split(" ");
				setYear(arr[arr.length - 1]);
				setMonth(arr[arr.length - 2]);
				break;
			}
		}
	}


	/**
	 * move dates to one ahead - as parsing doing not so great
	 * 
	 * @param firstDate
	 */
	private void normalizeOperationDates(Date firstDate) {

		Log.v(TAG, "\nbefore normalizing");
		for (OperationEntry op:opsList)
			Log.v(TAG, op.toString());
		
		Object[] split = opsList.toArray();

		for (int i = split.length - 1; i > 0; i--) {
			// Date tmpDate = ((OperationEntry)split[i]).dataOperacji;
			// Date dateToKeep = ((OperationEntry)split[i-1]).dataOperacji;
			((OperationEntry) split[i]).dataOperacji = ((OperationEntry) split[i - 1]).dataOperacji;
		}

		Calendar cal = Calendar.getInstance();
		try {
		cal.setTime(firstDate);
		((OperationEntry) split[0]).dataOperacji = cal;
		}
		catch (Exception e)
		{
			// TODO fix that
			Log.w(TAG, e.getMessage());
		}
		
		Log.v(TAG, "\nafter normalizing");
		for (OperationEntry op:opsList)
			Log.v(TAG, op.toString());

	}

	/**
	 * get saldo koncowe
	 * 
	 * @param s
	 * @return
	 */
	private String parseSaldoKoncowe(Scanner s) {
		String saldoKoncowe = null;
		while (s.hasNextLine()) {
			String line = s.nextLine();

			if (line.startsWith("Saldo ko")) {
				String[] lineSaldo = line.split(" ");
				saldoKoncowe = lineSaldo[lineSaldo.length - 1];
				break;
			}

		}
		return saldoKoncowe;
	}

	private Date parseFirstOpDate(Scanner s) {
		Date d = null;
		while (s.hasNextLine()) {
			String line = s.nextLine();

			if (line.matches(".*Saldo po operacji.*")) {
				String[] split = line.split(" ");
				d = Converter.toDate(split[split.length - 1]);
				break;
			}
		}

		return d;
	}

	/**
	 * parsing each operation
	 * 
	 * @param s
	 */
	private void parseEachOperation(Scanner s) {
		String line = new String();
		StringBuffer mainTitle = new StringBuffer();
		while (s.hasNextLine()) {
			if (line.equals(""))
				line = s.nextLine();

			if (line.matches("[0-9][0-9]\\-[0-9][0-9]\\-[0-9][0-9][0-9][0-9] .*")) {
				StringBuffer whole = new StringBuffer(line);
				mainTitle = new StringBuffer(line);
				line = s.nextLine();
				// replace with do while
				while (s.hasNextLine()
						&& !line.matches("[0-9][0-9]\\-[0-9][0-9]\\-[0-9][0-9][0-9][0-9] .*")
						&& !line.startsWith("Saldo ko")) {

					whole.append(line);
					line = s.nextLine();
				}

				if (line.startsWith("Saldo ko")) {
					// remove saldo koncowe - last part
					String beforeRemoveLastPart = whole.toString();
					String[] arr = beforeRemoveLastPart.split(" ");
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < arr.length - 1; i++) {
						sb.append(arr[i] + " ");
					}

					// add date to the end of the string
					sb.append(" 01-01-2011");
					this.addOperationEntry(new OperationEntry(sb.toString(),
							mainTitle.toString()));
					setSaldoKoncowe(line);
					break;
				}

				this.addOperationEntry(new OperationEntry(whole.toString(),
						mainTitle.toString()));
			}
		}
	}

	
	public List<OperationEntry> getOperationsInPlus() {

		if (this.opsListInPlus == null) {
			opsListInPlus = new ArrayList<OperationEntry>();
			for (OperationEntry op : opsList) {
				if (op.getKwotaOperacji() > 0)
					opsListInPlus.add(op);
			}
		}

		return opsListInPlus;
	}

	public List<OperationEntry> getOperationsInMinus() {

		if (this.opsListInMinus == null) {
			opsListInMinus = new ArrayList<OperationEntry>();
			for (OperationEntry op : opsList) {

				if (op.getKwotaOperacji() < 0)
					opsListInMinus.add(op);
			}
		}

		return opsListInMinus;
	}

	public List<OperationEntry> getOperationsByType(OperationType opType) {
		List<OperationEntry> toReturn = null;
		switch (opType) {
		case ALL:
			toReturn = opsList;
			break;
		case MINUS:
			toReturn = getOperationsInMinus();
			break;
		case PLUS:
			toReturn = getOperationsInPlus();
			break;
		}
		return toReturn;
	}
}
