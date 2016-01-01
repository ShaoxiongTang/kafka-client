package com.mls.kafka.demo;

import com.mls.kafka.producer.AbstractPartitioner;

public class MerchantAccountParttioner<AccountMessage> extends AbstractPartitioner<AccountMessage>{
	
	
	public MerchantAccountParttioner(String topic) {
		
	}

	@Override
	public int partition(AccountMessage key) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
