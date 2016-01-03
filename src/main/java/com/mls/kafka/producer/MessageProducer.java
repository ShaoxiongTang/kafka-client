package com.mls.kafka.producer;

import java.util.List;
import java.util.Map;

/**
 * @author shaoxiongtang
 * @date 2015年12月17日
 */
public interface MessageProducer<K, V> {


	/**
	 * 
	 * @param topic
	 * @param data
	 */
	public void send(K key, V data);


	/**
	 * 
	 * @param topic
	 * @param keyedMessage
	 */
	public void send(String topic, Map<K, V> keyedMessage);
	
	/**
	 * 
	 * @param topic
	 * @param key
	 * @param keyedMessage
	 */
	public void send(String topic, K key , List<V> keyedMessage);

	
	/**
	 * 发布topic
	 * 
	 * @param topic
	 */
	public void publishTopic(String topic);
}
