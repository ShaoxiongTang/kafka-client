package com.mls.kafka.demo;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class MerchantAccountPartitioner implements Partitioner<Integer> {

	public MerchantAccountPartitioner(VerifiableProperties properties) {
	}

	public MerchantAccountPartitioner() {
	}

	public int partition(MerchantMsg msg, int arg1) {
		return msg.getKey() % 3;
	}

	public int partition(Integer arg0, int arg1) {
		return arg0.intValue() % 3;
	}
}
