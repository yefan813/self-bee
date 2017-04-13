package com.dmall.bee;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 应用程序配置
 * 
 */
class AppSetting {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppSetting.class);
	private static Properties properties;

	private static Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			try {
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties");
				properties.load(is);
				is.close();
			} catch (IOException e) {
				LOGGER.error("加载配置文件失败", e);
			}
		}
		return properties;
	}

	/**
	 * 获取配置项
	 * 
	 * @param key
	 * @return
	 */
	public static String getConf(String key) {
		return getProperties().getProperty(key);
	}
}
