package com.mls.kafka.message;

import com.alibaba.fastjson.JSON;

import kafka.serializer.Encoder;

public class MessageEncoder<T> implements Encoder<T>{
	
	public byte[] toBytes(T obj) {
		try {
			return JSON.toJSONString(obj).getBytes();
		} catch (Exception e) {
			throw new RuntimeException("convert to json error!");
		}
	}
}
