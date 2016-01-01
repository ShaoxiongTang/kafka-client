package com.mls.kafka.support;

import java.util.List;
import java.util.Map;

public interface PartitionInfoAware {
	public void setTopicPartitionInfo(Map<String, List<String>> partitionInfoMap);
}
