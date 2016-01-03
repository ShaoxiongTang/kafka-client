package com.mls.kafka.demo;

import java.io.Serializable;

/**
 * Created by tangshaoxiong on 16/1/3.
 */
public class MerchantMsg implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7419170874565617329L;
	public int key;

	public MerchantMsg(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

}
