package com.mls.kafka.consumer;

/**
 * @author shaoxiongtang
 * @date 2015年12月21日
 */
public interface MessageExecutor<T> {
	public T execute(String message);
}
