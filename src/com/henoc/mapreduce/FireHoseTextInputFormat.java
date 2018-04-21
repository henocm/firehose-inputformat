package com.henoc.mapreduce;

import java.io.IOException;

import com.google.common.base.Charsets;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;

public class FireHoseTextInputFormat extends TextInputFormat{
	
	@Override	  
	public RecordReader<LongWritable, Text> getRecordReader(InputSplit genericSplit, JobConf job, Reporter reporter) throws IOException {
	
		reporter.setStatus(genericSplit.toString());
		String delimiter = job.get("textinputformat.record.delimiter");
		byte[] recordDelimiterBytes = null;
		if (null != delimiter) {
			recordDelimiterBytes = delimiter.getBytes(Charsets.UTF_8);
		}
		return new FireHoseRecordReader(job, (FileSplit) genericSplit, recordDelimiterBytes);
	}	  
	  
}
