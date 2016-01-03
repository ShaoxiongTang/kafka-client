package com.mls.kafka.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.Partitioner;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

public class SimpleMessageProducer<K, V> implements MessageProducer<K, V> {
	public ProducerZookeeper producerZookeeper;
	public String topic;
	public Partitioner<K> partitioner;
	private Producer<K, V> innerProducer;
	protected StringDecoder decoder = new StringDecoder(new VerifiableProperties());

	public SimpleMessageProducer(String topic, Partitioner<K> partitioner, ProducerZookeeper producerZookeeper) {
		super();
		this.producerZookeeper = producerZookeeper;
		this.topic = topic;
		this.partitioner = partitioner;
		innerProducer = new Producer<K, V>(producerZookeeper.buildProducerConfig(partitioner));
	}

	public void send(K key, V message) {
		if (topic == null || message == null) {
			return;
		}
		KeyedMessage<K, V> km = new KeyedMessage<K, V>(topic, key, message);
		innerProducer.send(km);
	}

	public void send(String topic, K key, List<V> messages) {
		if (topic == null || messages == null) {
			return;
		}
		if (messages.isEmpty()) {
			return;
		}
		List<KeyedMessage<K, V>> kms = new ArrayList<KeyedMessage<K, V>>();
		int i = 0;
		for (V entry : messages) {
			KeyedMessage<K, V> km = new KeyedMessage<K, V>(topic, key, entry);
			kms.add(km);
			i++;
			if (i % 20 == 0) {
				innerProducer.send(kms);
				kms.clear();
			}
		}

		if (!kms.isEmpty()) {
			innerProducer.send(kms);
		}
	}

	public void publishTopic(String topic) {
		try {
			// boolean isExsits = ZkUtils.isExists(producerZookeeper.zkClient,
			// producerZookeeper.clientConfig.zkConfig.rootPath +
			// PathConstants.TOPIC_PATH + topic);

		} catch (Exception e) {

		}
	}

	public void send(String topic, Map<K, V> keyedMessage) {
		// TODO Auto-generated method stub
	}
}
