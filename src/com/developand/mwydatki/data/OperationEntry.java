package com.developand.mwydatki.data;

import java.util.Calendar;
import java.util.Date;

import com.developand.mwydatki.tools.Converter;

public class OperationEntry {

	Calendar dataKsiegowania;
	Calendar dataOperacji;
	String opisOperacji;
	Double saldoPoOperacji;
	Double kwotaOperacji;
	String mainTitle;

	@Override
	public String toString() {
		return "" + getDataOperacji().get(Calendar.DAY_OF_MONTH) + "/"
				+ getDataOperacji().get(Calendar.MONTH) + "/"
				+ getDataOperacji().get(Calendar.YEAR) + "\n"
				+ getMainTitle() + "\n"
				+ getOpisOperacji() + "\n" + getKwotaOperacji();
	}

	public OperationEntry(String fullOpis, String mainTitle) {
		// 31-07-2009 ZAKUP PRZY

		String[] ops = fullOpis.split(" ");
		StringBuilder opis = new StringBuilder();
		String dataKsiegowania = ops[0];
		String saldoPoOp = ops[ops.length - 2];
		String kwota = ops[ops.length - 3];
		String dataOperacji = ops[ops.length - 1];

		this.kwotaOperacji = Converter.toDouble(kwota);
		this.saldoPoOperacji = Converter.toDouble(saldoPoOp);
		this.dataKsiegowania = Converter.toCalendar(dataKsiegowania);
		this.dataOperacji = Converter.toCalendar(dataOperacji);
		this.mainTitle = mainTitle;

		for (int i = 1; i < ops.length - 3; i++) {

			opis.append(ops[i] + " ");
		}

		this.opisOperacji = opis.toString();

	}

	public String getMainTitle()
	{
		String [] all = mainTitle.split(" ");
		StringBuffer clean = new StringBuffer();
		for (int i = 1; i<all.length; i++)
			clean.append(all[i] + " ");
		return clean.toString();
	}
	
	public Calendar getDataOperacji() {
		return dataOperacji;
	}

	public void setDataOperacji(Calendar dataOperacji) {
		this.dataOperacji = dataOperacji;
	}

	public Calendar getDataKsiegowania() {
		return dataKsiegowania;
	}

	public void setDataKsiegowania(Calendar dataKsiegowania) {
		this.dataKsiegowania = dataKsiegowania;
	}

	public String getOpisOperacji() {
		return opisOperacji.toLowerCase();
	}

	public void setOpisOperacji(String opisOperacji) {
		this.opisOperacji = opisOperacji;
	}

	public Double getSaldoPoOperacji() {
		return saldoPoOperacji;
	}

	public void setSaldoPoOperacji(Double saldoPoOperacji) {
		this.saldoPoOperacji = saldoPoOperacji;
	}

	public Double getKwotaOperacji() {
		return kwotaOperacji;
	}

	public void setKwotaOperacji(Double kwotaOperacji) {
		this.kwotaOperacji = kwotaOperacji;
	}

}
