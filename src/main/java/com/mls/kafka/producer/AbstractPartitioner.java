package com.mls.kafka.producer;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.util.StringUtils;

import com.mls.kafka.support.PartitionInfoSupport;

import kafka.producer.Partitioner;

public abstract class AbstractPartitioner<T> implements Partitioner<T>{
	public String topic;
	public int numPartitions;
	protected Lock lock = new ReentrantLock();
	private PartitionInfoSupport partitionInfoSupport;
	
	
	/**
	 * 分区函数
	 */
	public abstract int partition(T key);

	
	public int partition(T key, int numPartitions) {
		return partition(key);
	}

	public AbstractPartitioner() {
		if (StringUtils.isEmpty(topic)){
			throw new RuntimeException("topic to choose partition is null!");
		}
		List<String> partitionInfo = partitionInfoSupport.partitionInfo.get(topic);
		this.numPartitions = (partitionInfo == null ? 0 : partitionInfo.size());
	}
}
