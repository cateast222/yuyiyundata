package com.yuyiyun.YYdata.core.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

/**
 * 
 * @ClassName: HttpJsoup
 * @Description: 网络资源请求
 * @author duhao
 * @date 2020年5月15日
 *
 */
public class HttpJsoup {
	/**
	 * 请求超时时间
	 */
	private static final int TIME_OUT = 120000;
	/**
	 * 境外代理IP
	 */
	private static final String PROXY_IP = "192.168.0.88";
	/**
	 * 境外代理端口
	 */
	private static final int PROXY_PORT = 8125;
	/**
	 * Https请求
	 */
	private static final String HTTPS = "https";
	/**
	 * Content-Type
	 */
	private static final String CONTENT_TYPE = "Content-Type";
	/**
	 * 表单提交方式Content-Type
	 */
	private static final String FORM_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
	/**
	 * JSON提交方式Content-Type
	 */
	private static final String JSON_TYPE = "application/json;charset=UTF-8";

	/**
	 * @Description 发送Get请求
	 * @author duhao
	 * @date 2020年5月15日
	 * @version V1.0
	 * @param url     请求URL
	 * @param headers 请求头参数
	 * @param isProxy 启用代理
	 * @return HTTP响应对象
	 */
	private static Response doGetRequest(String url, Map<String, String> headers, boolean isProxy) {
		// 判断url
		if (null == url || url.isEmpty()) {
			throw new RuntimeException("请求URL为空。");
		}
		// 如果是Https请求
		if (url.startsWith(HTTPS)) {
			getTrust();
		}
		Connection connection = Jsoup.connect(url).method(Method.GET).timeout(TIME_OUT).ignoreHttpErrors(true)
				.ignoreContentType(true).maxBodySize(0);
		if (null != headers) {
			connection.headers(headers);
		}
		if (isProxy) {
			connection.proxy(PROXY_IP, PROXY_PORT);
		}
		return execute(connection);
	}

	/**
	 * @Description 普通表单方式发送POST请求
	 * @author duhao
	 * @date 2020年5月15日
	 * @version V1.0
	 * @param url      请求URL地址
	 * @param headers  请求头
	 * @param paramMap 请求字符串参数集合
	 * @param fileMap  请求文件参数集合
	 * @param isProxy  启用代理
	 * @return HTTP响应对象
	 */
	private static Response doFormPostRequest(String url, Map<String, String> headers, Map<String, String> bodyParams,
			Map<String, File> fileMap, boolean isProxy) {
		if (null == url || url.isEmpty()) {
			throw new RuntimeException("请求URL为空。");
		}
		if (url.startsWith(HTTPS)) {
			getTrust();
		}
		Connection connection = Jsoup.connect(url).method(Method.POST).timeout(TIME_OUT).ignoreHttpErrors(true)
				.ignoreContentType(true).maxBodySize(0);
		if (null != headers) {
			connection.headers(headers);
		}
		if (isProxy) {
			connection.proxy(PROXY_IP, PROXY_PORT);
		}
		// 收集上传文件输入流，最终全部关闭
		List<InputStream> inputStreamList = null;
		try {
			if (null != fileMap && !fileMap.isEmpty()) {
				// 添加文件参数
				inputStreamList = new ArrayList<InputStream>();
				InputStream in = null;
				File file = null;
				for (Entry<String, File> e : fileMap.entrySet()) {
					file = e.getValue();
					in = new FileInputStream(file);
					inputStreamList.add(in);
					connection.data(e.getKey(), file.getName(), in);
				}
			} else {
				// 普通表单提交方式
				connection.header(CONTENT_TYPE, FORM_TYPE);
			}
			if (null != bodyParams && !bodyParams.isEmpty()) {
				// 添加字符串类参数
				connection.data(bodyParams);
			}
			return execute(connection);
		} catch (Exception e) {
			System.err.println("HttpJsoup.doPostRequest():e--->" + e.getMessage());
		} finally {
			// 关闭上传文件的输入流
			closeStream(inputStreamList);
		}
		return null;
	}

	/**
	 * @Description JSON方式发送POST请求
	 * @author duhao
	 * @date 2020年5月15日
	 * @version V1.0
	 * @param url        请求URL地址
	 * @param headers    请求头
	 * @param jsonParams 请求JSON格式字符串参数
	 * @param isProxy    启用代理
	 * @return HTTP响应对象
	 */
	private static Response doJsonPostRequest(String url, Map<String, String> headers, String jsonParams,
			boolean isProxy) {
		if (null == url || url.isEmpty()) {
			throw new RuntimeException("请求URL为空。");
		}
		if (url.startsWith(HTTPS)) {
			getTrust();
		}
		Connection connection = Jsoup.connect(url).method(Method.POST).timeout(TIME_OUT).ignoreHttpErrors(true)
				.ignoreContentType(true).maxBodySize(0);
		if (null != headers) {
			connection.headers(headers);
		}
		if (isProxy) {
			connection.proxy(PROXY_IP, PROXY_PORT);
		}
		connection.header(CONTENT_TYPE, JSON_TYPE);
		connection.requestBody(jsonParams);
		return execute(connection);
	}

	/**
	 * 
	 * @Description Connection执行方法
	 * @author duhao
	 * @date 2020年5月15日
	 * @version V1.0
	 * @param connection 执行对象
	 * @return HTTP响应对象
	 */
	private static Response execute(Connection connection) {
		Response response = null;
		int count = 1;
		while (count <= 3 && response == null) {
			try {
				response = connection.execute();
			} catch (IOException e) {
				System.err.print("HttpJsoup.execute():e--->");
				count++;
				if (e instanceof HttpStatusException) {
					HttpStatusException hse = (HttpStatusException) e;
					System.err.print(hse.getStatusCode() + "-");
				}
				System.err.println(e.getMessage());
				try {
					Thread.sleep(1000 * 2);
				} catch (Exception e1) {
					System.err.print("HttpJsoup.execute():e1--->" + e.getMessage());
				}
			}
		}
		return response;
	}

	/**
	 * @Description 关流
	 * @author duhao
	 * @date 2020年5月15日
	 * @version V1.0
	 * @param streamList 流集合
	 */
	private static void closeStream(List<? extends Closeable> streamList) {
		if (null != streamList) {
			for (Closeable stream : streamList) {
				try {
					stream.close();
				} catch (IOException e) {
					System.err.println("HttpJsoup.closeStream():e--->" + e.getMessage());
				}
			}
		}
	}

	private static Map<String, String> getString2Map(String str0, String str1, String ste2) {
		Map<String, String> hashMap = new HashMap<String, String>();
		if (!StringUtils.isBlank(str0)) {
			String[] split = str0.split(str1);
			for (String eachStr : split) {
				String[] key_val = eachStr.split(ste2);
				if (key_val.length > 1) {
					String key = key_val[0].trim();
					String val = key_val[1].trim();
					hashMap.put(key, val);
				}
			}
		}
		return hashMap;
	}

	/**
	 * @Description 获取服务器信任
	 * @author duhao
	 * @date 2020年5月15日
	 * @version V1.0
	 */
	private static void getTrust() {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		} catch (Exception e) {
			System.err.println("HttpJsoup.getTrust():e--->" + e.getMessage());
		}
	}

	/**
	 * @Description 获取网络文本数据
	 * @author duhao
	 * @date 2020年5月15日
	 * @version V1.0
	 * @param url         网络地址
	 * @param headerStr   请求头信息
	 * @param requestType 请求类型
	 * @param bodyType    数据传输类型
	 * @param bodyStr     请求体信息
	 * @param isProxy     网络代理
	 * @return HTTP响应信息
	 */
	public static Response getSource(String url, String headerStr, Integer requestType, Integer bodyType,
			String bodyStr, Boolean isProxy) {
		Response response = null;
		Map<String, String> headerMap = getString2Map(headerStr, "\\r?\\n", ":");
		if (requestType == null || requestType == 1) {
			response = doGetRequest(url, headerMap, isProxy);
		} else if (requestType == 2) {
			if (bodyType == null || bodyType == 1) {
				Map<String, String> bodyMap = getString2Map(bodyStr, "\\r?\\n", ":");
				response = doFormPostRequest(url, headerMap, bodyMap, null, isProxy);
			} else if (bodyType == 2) {
				response = doJsonPostRequest(url, headerMap, bodyStr, isProxy);
			}
		}
		return response;
	}

	/**
	 * @Description 获取网络资源数据
	 * @author duhao
	 * @date 2020年5月15日
	 * @version V1.0
	 * @param url       网络地址
	 * @param headerStr 请求头信息
	 * @param isProxy   网络代理
	 * @return HTTP响应信息
	 */
	public static Response getResources(String url, String headerStr, Boolean isProxy) {
		Map<String, String> headerMap = getString2Map(headerStr, "\\r?\\n", ":");
		Response response = doGetRequest(url, headerMap, isProxy);
		return response;
	}

	public static void main(String[] args) {
		String url = "https://amfzbao.cn/content/newspaper_past/?page=1";
		Response response = doGetRequest(url, null, false);
		String body = response.body();
		System.out.println(body);
	}

}
