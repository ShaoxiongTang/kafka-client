package com.mls.kafka.factory;

import com.mls.kafka.consumer.KafkaConsumer;
import com.mls.kafka.consumer.MessageExecutor;
import com.mls.kafka.producer.MessageProducer;

public class SimpleMessageSeesionFactory implements MessageSeesionFactory{

	public KafkaConsumer createConsumer(String topic, MessageExecutor<?> executor) {
		// TODO Auto-generated method stub
		return null;
	}

	public MessageProducer<?> createProducer() {
		
		return null;
	}

}
