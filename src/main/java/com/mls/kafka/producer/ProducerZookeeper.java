package com.mls.kafka.producer;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;

import com.mls.kafka.config.KafkaClientConfig;
import com.mls.kafka.support.BrokerInfo;
import com.mls.kafka.util.ZkUtils;

import kafka.producer.Partitioner;
import kafka.producer.ProducerConfig;

public class ProducerZookeeper {
	private static Logger logger = Logger.getLogger(ProducerZookeeper.class);
	
	public ConcurrentHashMap<String, BrokerInfo> topicBrokersInfo = new ConcurrentHashMap<String, BrokerInfo>();
	public KafkaClientConfig clientConfig;
	public ZkClient zkClient;
	
	public ProducerZookeeper(KafkaClientConfig clientConfig){
		this.zkClient = ZkUtils.createClient(clientConfig.zkConfig);
		this.clientConfig = clientConfig;
	}
	
	public ProducerConfig buildProducerConfig(Partitioner<?> partitioner){
		if (this.clientConfig == null){
			throw new RuntimeException("KafkaClientConfig need to init!");
		}
		Properties producerProperties = new Properties();
		producerProperties.put("zk.sessiontimeout.ms", clientConfig.zkConfig.sessionTimeout);
		producerProperties.put("zk.connectiontimeout.ms", clientConfig.zkConfig.connectionTimeout);
		producerProperties.put("metadata.broker.list", clientConfig.kafkaConfig.metaBrokerList);
		producerProperties.put("autocommit.enable", clientConfig.kafkaConfig.autoCommit);
		producerProperties.put("autocommit.interval.ms", clientConfig.kafkaConfig.autocommitInterval);
		producerProperties.put("rebalance.retries.max", clientConfig.kafkaConfig.rebalanceMaxTry);
		if (partitioner !=null ){
			producerProperties.put("partitioner.class", partitioner.getClass().getName());
		}
		//producerProperties.put("serializer.class", "kafka.serializer.StringEncoder");
		System.out.println("producerProperties is [" + producerProperties + "]");
		return new ProducerConfig(producerProperties);
	}
}	
