package com.mls.kafka.support;

import java.util.Map;

import kafka.common.Topic;

public class BrokerInfo {
	public String id;
	public String url;
	public Map<String, Topic> partitionMap;
}
