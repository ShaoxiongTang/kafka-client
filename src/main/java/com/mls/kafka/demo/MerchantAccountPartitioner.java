package com.mls.kafka.demo;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class MerchantAccountPartitioner implements Partitioner<MerchantMsg> {

	public MerchantAccountPartitioner(VerifiableProperties properties){
	}
	
	public MerchantAccountPartitioner(){
	}

	public int partition(MerchantMsg msg, int arg1) {
		return msg.getKey() %3;
	}
}
