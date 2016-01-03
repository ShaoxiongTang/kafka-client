package com.mls.kafka.config;

import com.mls.kafka.util.ZkUtils.ZkConfig;

public class KafkaClientConfig {
	public ZkConfig zkConfig;
	public KafkaConfig kafkaConfig;

	public KafkaClientConfig(ZkConfig config, KafkaConfig kafkaConfig) {
		super();
		this.zkConfig = config;
		this.kafkaConfig = kafkaConfig;
	}
	
	public KafkaClientConfig(){
		super();
	}
}	
