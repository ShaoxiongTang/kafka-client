package com.mls.kafka.config;

public class KafkaConfig {
	public String metaBrokerList;
	public int rebalanceMaxTry = 10;
	public Boolean autoCommit = true;
	public long autocommitInterval = 10000;
}
