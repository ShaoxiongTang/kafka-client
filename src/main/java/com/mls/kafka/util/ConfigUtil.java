package com.mls.kafka.util;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;



/** 
 * @author shaoxiongtang
 * @date 2015年12月21日 
 */
public class ConfigUtil {
	private static final Logger LOG = Logger.getLogger(ConfigUtil.class);
	
	public static Properties loadProperties(String location) {
		Assert.hasText(location, "Properties location is null");

		File file = new File(location);
		if (file == null || file.exists()) {
			throw new RuntimeException("Properties location [" + location + "] not exsits!");
		}

		try {
			Properties properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(location.trim()));
			return properties;
		} catch (Exception e) {
			LOG.error("ConfigUtil load file at " + location + " config failed!", e);
			throw new RuntimeException(e);
		}
	}
}
