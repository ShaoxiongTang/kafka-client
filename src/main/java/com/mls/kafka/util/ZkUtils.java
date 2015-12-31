package com.mls.kafka.util;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;

/**
 * @author shaoxiongtang
 * @date 2015年12月28日
 */
public class ZkUtils {
	private static Log logger = LogFactory.getLog(ZkUtils.class);
	
	private static String TOPIC_PATH = "/brokers/topics/%s/partitions";
	

	/**
	 * Create an ephemeral node with the given path and data. Create parents if
	 * necessary.
	 */
	public static void createEphemeralPath(final ZkClient client, final String path, final String data) throws Exception {
		try {
			client.createEphemeral(path, data);
		} catch (final ZkNoNodeException e) {
			createParentPath(client, path);
			client.createEphemeral(path, data);
		}
	}

	/**
	 * create the parent path
	 */
	public static void createParentPath(final ZkClient client, final String path) throws Exception {
		final String parentDir = path.substring(0, path.lastIndexOf('/'));
		if (parentDir.length() != 0) {
			client.createPersistent(parentDir, true);
		}
	}

	/**
	 * delete path
	 * @param client
	 * @param path
	 * @throws Exception
	 */
	public static void deletePath(final ZkClient client, final String path) throws Exception {
		try {
			client.delete(path);
		} catch (final ZkNoNodeException e) {
			logger.info(path + " deleted during connection loss; this is ok");
		} catch (final Exception e) {
			throw e;
		}
	}
	
	public boolean writeData(ZkClient client, String path, String data,int version){
        Stat stat = client.writeDataReturnStat(path, data, version);
		logger.info( "更新数据成功, path：" + path + ", stat: " + stat );
		return true;
    }
	
	
	public static String readData(ZkClient client,String path){
		String data = null;
		try {
			data = String.valueOf(client.readData(path, true));
			logger.info(path + " readData is ok");
		} catch (Exception e) {
			logger.info("ZkUtils readData error!", e);
		}
		return data;
	}
	
	public static int countPartitions(ZkClient client, String topic) {
		if (StringUtils.isEmpty(topic)){
			throw new RuntimeException("topic to count partitions is blank!");
		}
		return client.countChildren(String.format(TOPIC_PATH, topic));
	}


	public static void main(String[] args) {
		ZkClient client = new ZkClient("localhost:2181", 2000);
		try {
			//int  i = 0;
			System.out.println(countPartitions(client, "test5"));
			//client.subscribeChildChanges("/controller", new WatchDemo());
			//createEphemeralPath(client, "/brokers/ids/4", data);
			/*client.createEphemeral("/brokers/ids/4");
			Thread.sleep(2000);
			System.out.println(client.getChildren("/brokers/ids"));
			
			client.delete("/brokers/ids/2");
			Thread.sleep(2000);
			System.out.println(client.getChildren("/brokers/ids"));*/
			
			
			/*client.watchForData("/brokers/topics/test5");
			createEphemeralPath(client, "/zkUtil/test2", "tsx"); */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class ZkConfig {
		private String rootPath;
		private String connectStr;
		private int sessionTimeout = 1000;

		public String getRootPath() {
			return rootPath;
		}

		public void setRootPath(String rootPath) {
			this.rootPath = rootPath;
		}

		public String getConnectStr() {
			return connectStr;
		}

		public void setConnectStr(String connectStr) {
			this.connectStr = connectStr;
		}

		public int getSessionTimeout() {
			return sessionTimeout;
		}

		public void setSessionTimeout(int sessionTimeout) {
			this.sessionTimeout = sessionTimeout;
		}

		public ZkConfig(String rootPath, String connectStr, int sessionTimeout) {
			super();
			this.rootPath = rootPath;
			this.connectStr = connectStr;
			this.sessionTimeout = sessionTimeout;
		}
	}
}
