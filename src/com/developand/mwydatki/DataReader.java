package com.developand.mwydatki;

import java.io.IOException;
import java.util.List;

import android.text.Spanned;

public interface DataReader {
	
	List<Spanned> readData() throws IOException;

}
