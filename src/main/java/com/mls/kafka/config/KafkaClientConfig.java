package com.mls.kafka.config;

import com.mls.kafka.util.ZkUtils.ZkConfig;


public class KafkaClientConfig {
	public ZkConfig zkConfig;
	
	public KafkaClientConfig(ZkConfig config){
		this.zkConfig = config;
	}
	
}
