package com.developand.mwydatki.data;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import android.text.Spanned;

public interface DataReader {
	
	Map<String, Spanned> readData() throws IOException;
	Set<String> getFiles();

}
