package com.yuyiyun.YYdata.core.util;

import java.io.File;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.stfl.Socks5ServerListMode;
import com.stfl.misc.Config;
import com.stfl.network.proxy.IProxy.GROUP;

/**
 * 
 * @describe 代理处理工具
 * @author duhao
 * @date 2019年9月17日上午9:28:49
 */
public class Proxy {
	private static String localIP = "localhost";

	public String getLocalIP() {
		return localIP;
	}

	public static String getLocalPro() {
		return localPro;
	}

	private static String localPro;
	static {
		String fileName = "gui-config.json";
		// 启动代理
		File configfile = new File(fileName);
		JSONObject config_json;
		try {
			config_json = JSON.parseObject(Files.toString(configfile, Charsets.UTF_8));
			/** 装载配置，启动代理 **/
			Socks5ServerListMode.startSocks5(config_json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Integer setProxy() {
//		Socks5ServerListMode.
		Config config = Socks5ServerListMode.getRandomSocksWithGroup(GROUP.GROUP_OUT);
//		String proxyIP=config.getRemoteIpAddress();
//		int prot = config.getRemotePort();
		localIP = config.getLocalIpAddress();
		localPro = config.getLocalPort() + "";
		System.out.println(localPro);
//		while(true){
//			LOG.debug("proxyIP="+proxyIP);
//			LOG.debug("prot="+prot);
//			LOG.debug("localIP="+localIP);
//			LOG.debug("localPro="+localPro);
//			String data = JsoupCrawler.downloadByProxy(url, localIP, localPro);
//			if (data!=null) {
//				try {
//					data=null;
//					Thread.sleep(1000*60);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			} else {
//				LOG.debug(">>>>>>>>> 代理【"+proxyIP+":"+prot+"】失效！重新获取");
		config = Socks5ServerListMode.getRandomSocksWithGroup(GROUP.GROUP_OUT);
		return Integer.valueOf(localPro);
	}

	public static void main(String[] args) {
		Proxy.setProxy();
	}
}
