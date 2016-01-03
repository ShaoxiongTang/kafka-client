package com.mls.kafka.message;

import java.nio.charset.Charset;

public class MessageDecoder{
	static Charset charset = Charset.forName("utf8");

	public static String fromBytes(byte[] content) {
		return new String(content, charset);
	}
}
