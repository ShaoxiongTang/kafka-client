package com.mls.kafka.producer;

import java.util.List;

/**
 * @author shaoxiongtang
 * @date 2015年12月17日
 */
public interface MessageProducer<T> {

	/**
	 * 
	 * @param topic
	 * @param data
	 */
	public void send(String topic, T data);

	/**
	 * 
	 * @param topic
	 * @param datas
	 */
	public void send(String topic, List<T> datas);

	/**
	 * 根据key的hash规则指定分区发送消息
	 * 
	 * @param topic
	 * @param key
	 *            消息分区key--默认随机选取一个分区存储
	 * @param datas
	 *            消息
	 */
	// public void send(String topic, K key, List<V> datas);
	
	/**
	 * 发布topic
	 * @param topic
	 */
	public void publishTopic(String topic);
}
