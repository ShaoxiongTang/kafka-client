package com.mls.kafka;

import com.mls.kafka.config.KafkaClientConfig;
import com.mls.kafka.config.KafkaConfig;
import com.mls.kafka.demo.MerchantAccountPartitioner;
import com.mls.kafka.demo.MerchantMsg;
import com.mls.kafka.factory.SimpleMessageSeesionFactory;
import com.mls.kafka.producer.ProducerZookeeper;
import com.mls.kafka.producer.SimpleMessageProducer;
import com.mls.kafka.util.ZkUtils.ZkConfig;

import junit.framework.TestCase;

public class PartitionFailTest extends TestCase {
	KafkaClientConfig clientConfig ;
	SimpleMessageSeesionFactory seesionFactory;
	SimpleMessageProducer<Integer, MerchantMsg> producer;
	ProducerZookeeper producerZookeeper;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ZkConfig config = new ZkConfig("/", "localhost:2181", 2000);
		KafkaConfig kafkaConfig = new KafkaConfig();
		kafkaConfig.autoCommit = true;
		kafkaConfig.autocommitInterval = 10000;
		kafkaConfig.rebalanceMaxTry = 10;
		kafkaConfig.metaBrokerList = "127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094";
		clientConfig = new KafkaClientConfig(config, kafkaConfig);
		producerZookeeper = new ProducerZookeeper(clientConfig);
	}

	public void testPartition() {
		SimpleMessageProducer<Integer,MerchantMsg> producer = 
				new SimpleMessageProducer<Integer,MerchantMsg>("merchantaccount2", new MerchantAccountPartitioner(), producerZookeeper);
		int i = 0;
		while (true) {
			try {
				MerchantMsg msg = new MerchantMsg(i);
				producer.send(msg.getKey(), msg);
				i ++ ;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
