package com.mls.kafka.producer;

import java.util.concurrent.ConcurrentHashMap;

import org.I0Itec.zkclient.ZkClient;

import com.mls.kafka.config.KafkaClientConfig;
import com.mls.kafka.support.BrokerInfo;
import com.mls.kafka.util.ZkUtils;

public class ProducerZookeeper {
	public ConcurrentHashMap<String, BrokerInfo> topicBrokersInfo = new ConcurrentHashMap<String, BrokerInfo>();
	public KafkaClientConfig clientConfig;
	public ZkClient zkClient;
	
	public ProducerZookeeper(){
		this.zkClient = ZkUtils.createClient(clientConfig.zkConfig);
	}
}	
