package com.developand.mwydatki.data;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.developand.mwydatki.data.common.OperationType;

import android.text.Spanned;

public interface DataReader {
	
	Map<String, Spanned> readData() throws IOException;
	
	Set<String> getFiles();
	List<OperationEntry> getOperations(String file);
	List<String> getOperationsFromFileByType(String file, OperationType type);
	List<String> getOperationsStringByIndex(Integer index, OperationType type);
	List<String> getOperationsFromStringByType(String source, OperationType type);
	List<String> getCurrentLoadedOperations(OperationType type);

}
