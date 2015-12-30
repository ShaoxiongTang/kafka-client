package com.mls.kafka.watch;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;


/** 
 * @author shaoxiongtang
 * @date 2015年12月28日 
 */
public class WatchDemo implements IZkChildListener{
	public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
		System.out.println("parentPath changed and currentChilds is " + currentChilds);
	}
}
