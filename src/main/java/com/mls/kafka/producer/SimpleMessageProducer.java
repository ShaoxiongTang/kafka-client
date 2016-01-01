package com.mls.kafka.producer;

import java.util.ArrayList;
import java.util.List;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

public class SimpleMessageProducer<T> implements MessageProducer<T> {
	public ProducerZookeeper producerZookeeper;
	public String topic;
	public AbstractPartitioner<T> partitioner;
	private Producer<String, T> innerProducer;

	public SimpleMessageProducer(String topic) {
		this.topic = topic;
	}

	public void send(String topicName, T messageObj) {
		if (topicName == null || messageObj == null) {
			return;
		}
		KeyedMessage<String, T> km = new KeyedMessage<String, T>(topicName, messageObj);
		innerProducer.send(km);
	}

	public void send(String topicName, List<T> messages) {
		if (topicName == null || messages == null) {
			return;
		}
		if (messages.isEmpty()) {
			return;
		}
		List<KeyedMessage<String, T>> kms = new ArrayList<KeyedMessage<String, T>>();
		int i = 0;
		for (T entry : messages) {
			KeyedMessage<String, T> km = new KeyedMessage<String, T>(topicName, entry);
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
}
