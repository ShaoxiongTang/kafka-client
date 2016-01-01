package com.mls.kafka.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartitionInfoSupport implements PartitionInfoAware{
	// <topicName,partitionInfo(0-1)>
	public Map<String, List<String>> partitionInfo = new HashMap<String, List<String>>();
	
	public void setTopicPartitionInfo(Map<String, List<String>> partitionInfoMap) {
		this.partitionInfo = partitionInfoMap;
	}
	
}
