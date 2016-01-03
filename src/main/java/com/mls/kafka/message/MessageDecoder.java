package com.mls.kafka.message;

import java.nio.charset.Charset;

import kafka.serializer.Decoder;

public class MessageDecoder implements Decoder<String> {
	Charset charset = Charset.forName("utf8");

	public String fromBytes(byte[] content) {
		return new String(content, charset);
	}
}
