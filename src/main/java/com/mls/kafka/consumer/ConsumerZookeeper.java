package com.mls.kafka.consumer;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import com.mls.kafka.util.ZkUtils.ZkConfig;


/** 
 * @author shaoxiongtang
 * @date 2015年12月31日 
 */
public class ConsumerZookeeper {
	protected ZkClient zkClient;
	public ZkConfig zkConfig;
	public Charset charset = Charset.forName("utf8");
	
	public ConcurrentHashMap<String, FetchManager> registerMap = new ConcurrentHashMap<String, FetchManager>();
	public ConcurrentHashMap<String, List<Integer>> partitiomMap = new ConcurrentHashMap<String, List<Integer>>();
	
	public static class ZkLoadBalanceListener implements IZkChildListener,Runnable{

		public void run() {
			
		}

		public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
			System.out.println("Path " + parentPath + " change!");
		}
		
	}
}
