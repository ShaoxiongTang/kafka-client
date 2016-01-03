package com.mls.kafka.support;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import kafka.producer.Partitioner;

public abstract class AbstractPartitioner<T> implements Partitioner<T>{
	public String topic;
	protected Lock lock = new ReentrantLock();
	protected PartitionInfoSupport partitionInfoSupport;

	/**
	 * 分区函数
	 */
	public abstract int partition(T key);

//	@Override
//	public int partition(T key, int numPartitions) {
//		return partition(key);
//	}
}
