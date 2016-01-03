package com.mls.kafka.factory;

import com.mls.kafka.consumer.KafkaConsumer;
import com.mls.kafka.consumer.MessageExecutor;
import com.mls.kafka.producer.MessageProducer;

public interface MessageSeesionFactory {
	/**
	 * 创建消费消费者
	 * 
	 * @param topic
	 * @param executor
	 * @return
	 */
	public KafkaConsumer createConsumer(String topic, MessageExecutor<?> executor);

	/**
	 * 创建生产者
	 * @return
	 */
	public MessageProducer<?, ?> createProducer();
}
