package com.cocopass.iot.model;

import java.util.List;

public class CPageRecord<T> {

	long RecordsCount=0;
	List<T> Records;
	public long getRecordsCount() {
		return RecordsCount;
	}
	public void setRecordsCount(long recordsCount) {
		this.RecordsCount = recordsCount;
	}
	public List<T> getRecords() {
		return Records;
	}
	public void setRecords(List<T> records) {
		this.Records = records;
	}
}
