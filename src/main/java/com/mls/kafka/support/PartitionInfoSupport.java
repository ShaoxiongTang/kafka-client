package com.mls.kafka.support;

import com.mls.kafka.util.ZkUtils;
import org.I0Itec.zkclient.ZkClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartitionInfoSupport implements PartitionInfoAware{
	// <topicName,partitionInfo(0-1)>
	public Map<String, List<String>> partitionInfo = new HashMap<String, List<String>>();
	public ZkClient zkClient;

	public PartitionInfoSupport(ZkUtils.ZkConfig zkConfig){
		this.zkClient = ZkUtils.createClient(zkConfig);
	}

	public void addTopicPartitionInfo(String topic, List<PartitionInfo> partitionInfos) {

	}
}
