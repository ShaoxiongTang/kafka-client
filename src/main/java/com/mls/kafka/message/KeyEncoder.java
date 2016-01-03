package com.mls.kafka.message;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

public class KeyEncoder<K> implements Encoder<K> {
	public KeyEncoder(VerifiableProperties properties){
		
	}

	public byte[] toBytes(K key) {
		if (key instanceof String || key instanceof Integer || key instanceof Long) {
			return String.valueOf(key).getBytes();
		} else {
			// TODO
			return null;
		}
	}
}
