package com.yuyiyun.YYdata.core.util;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @describe 使用httpclient发送请求
 * @author duhao
 * @date 2019年9月20日下午4:18:23
 */
public class ApiHttpClientUtil {
	// 编码格式。发送编码格式统一用UTF-8
	private static final String ENCODING = "UTF-8";
	// 设置连接超时时间，单位毫秒。
	private static final int CONNECT_TIMEOUT = 6000;
	// 请求获取数据的超时时间(即响应时间)，单位毫秒。
	private static final int SOCKET_TIMEOUT = 6000;

	/**
	 * setConnectTimeout：设置连接超时时间，单位毫秒。 setConnectionRequestTimeout：设置从connect
	 * Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
	 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
	 */
//	private static final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setProxy(new HttpHost("127.0.0.1", Proxy.setProxy()))
//			.setSocketTimeout(SOCKET_TIMEOUT).build();
	private static final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
			.setSocketTimeout(SOCKET_TIMEOUT).build();

	/**
	 * 
	 * @describe 向指定URL发送GET方法的请求 不带参数的get请求
	 * @author duhao
	 * @date 2019年9月20日下午4:19:01
	 * @param url       发送请求的URL
	 * @param headerMap 头参数
	 * @return 所代表远程资源的响应结果
	 */
	public static String doGet(String url, Map<String, String> headerMap) {
		return sendGet(url, headerMap, null, null);
	}

	/**
	 * 
	 * @describe 向指定URL发送GET方法的请求 带参数 不带字符集编码
	 * @author duhao
	 * @date 2019年9月20日下午4:20:59
	 * @param url       发送请求的URL 例如：http://localhost:8080/demo/login
	 * @param headerMap 头参数
	 * @param param     请求参数 例：{ "userName":"admin", "password":"123456" }
	 * @return 所代表远程资源的响应结果
	 */
	public static String doGet(String url, Map<String, String> headerMap, Map<String, String> param) {
		return sendGet(url, headerMap, param, null);
	}

	/**
	 * 
	 * @describe 向指定URL发送GET方法的请求 带参数和字符集的get请求
	 * @author duhao
	 * @date 2019年9月20日下午4:21:44
	 * @param url       发送请求的URL 例如：http://localhost:8080/demo/login
	 * @param headerMap 请求头参数
	 * @param param     请求参数 例：{ "userName":"admin", "password":"123456" }
	 * @param charset   字符集编码 例："UTF-8"
	 * @return 所代表远程资源的响应结果
	 */
	public static String doGet(String url, Map<String, String> headerMap, Map<String, String> param, String charset) {
		return sendGet(url, headerMap, param, charset);
	}

	/**
	 * 
	 * @describe 向指定URL发送POST方法的请求
	 * @author duhao
	 * @date 2019年9月20日下午4:23:02
	 * @param url    资源地址
	 * @param params 参数列表
	 * @return
	 */
	public static String doPost(String url, Map<String, String> params) {
		return sendPost(url, null, params, null);
	}

	/**
	 * 
	 * @describe 向指定URL发送POST方法的请求
	 * @author duhao
	 * @date 2019年9月20日下午4:26:52
	 * @param url     资源地址
	 * @param params  参数列表
	 * @param charset 字符编码集
	 * @return
	 */
	public static String doPost(String url, Map<String, String> params, String charset) {
		return sendPost(url, null, params, charset);
	}

	/**
	 * 
	 * @describe 向指定URL发送带请求头参数的POST方法的请求
	 * @author duhao
	 * @date 2019年9月20日下午4:27:37
	 * @param url        url地址 例如：http://localhost:8080/demo/login
	 * @param headerMap  请求头参数 { "user_head":"hah*********ha" }
	 * @param contentMap 需要发送的参数 { "userName":"admin", "password":"123456" }
	 * @return 发送结果的返回字符串
	 */
	public static String doPost(String url, Map<String, String> headerMap, Map<String, String> contentMap) {
		return sendPost(url, headerMap, contentMap, null);
	}

	/**
	 * 
	 * @describe 发送json数据请求
	 * @author duhao
	 * @date 2019年9月24日下午4:51:12
	 * @param url        路径
	 * @param headerJson 头参数(json类型)
	 * @param bodyJson   body参数(json类型)
	 * @param encoding   编码格式
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String sendJson(String url, JSONObject headerJson, JSONObject bodyJson, String encoding)
			throws ParseException, IOException {
		String body = "";
		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);
		if (headerJson != null) {
			// 循环增加header
			for (Entry<String, Object> elem : headerJson.entrySet()) {
				httpPost.addHeader(elem.getKey(), elem.getValue().toString());
			}
		}
		// 装填参数
		StringEntity s = new StringEntity(bodyJson.toString(), "utf-8");
		s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		// 设置参数到请求对象中
		httpPost.setEntity(s);
		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		// 获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		// 释放链接
		response.close();
		return body;
	}

	/**
	 * 
	 * @describe 封装HTTP POST方法 有参数的Get请求
	 * @author duhao
	 * @date 2019年9月20日下午4:28:49
	 * @param url        资源地址
	 * @param headerMap  头参数
	 * @param contentMap 发送的参数
	 * @param charset    字符集编码
	 * @return
	 */
	private static String sendPost(String url, Map<String, String> headerMap, Map<String, String> contentMap,
			String charset) {
		String result = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		try {
			// 创建httpclient对象
			httpClient = createHttpClient();
			// 创建post方式请求对象
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			if (headerMap != null) {
				// 循环增加header
				for (Entry<String, String> elem : headerMap.entrySet()) {
					httpPost.addHeader(elem.getKey(), elem.getValue());
				}
			}
			// 装填参数
			List<NameValuePair> nvps = new ArrayList<>();
			if (contentMap != null) {
				for (Entry<String, String> entry : contentMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
			if (charset == null) {
				charset = ENCODING;
			}
			if (nvps.size() > 0) {
				// 装填参数
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, charset);
				// 设置参数到请求对象中
				httpPost.setEntity(entity);
			}
			response = httpClient.execute(httpPost);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				// 获取response的body部分
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// 按指定编码转换结果实体为String类型
					result = EntityUtils.toString(entity, charset);
				}
			}
			return result;
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		} catch (KeyManagementException e) {
			System.out.println("绕过秘钥验证失败！" + e);
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("找不到恢复密钥的算法！" + e);
			e.printStackTrace();
		} finally {
			try {
				release(response, httpClient);
			} catch (IOException e) {
				System.out.println("释放连接错误！" + e);
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @describe 封装HTTP GET方法 有参数的Get请求
	 * @author duhao
	 * @date 2019年9月20日下午4:14:53
	 * @param url       资源地址
	 * @param headerMap 头参数
	 * @param paramMap  发送的参数
	 * @param charset   字符集编码
	 * @return
	 */
	private static String sendGet(String url, Map<String, String> headerMap, Map<String, String> paramMap,
			String charset) {
		String result = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		try {
			httpClient = createHttpClient();
			HttpGet httpGet = new HttpGet();
			httpGet.setConfig(requestConfig);
			if (headerMap != null) {
				// 循环增加header
				for (Entry<String, String> elem : headerMap.entrySet()) {
					httpGet.addHeader(elem.getKey(), elem.getValue());
				}
			}
			List<NameValuePair> params = new ArrayList<>();
			if (paramMap != null) {
				Set<Entry<String, String>> set = paramMap.entrySet();
				for (Map.Entry<String, String> entry : set) {
					params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
			if (charset == null) {
				charset = ENCODING;
			}
			String param = URLEncodedUtils.format(params, charset);
			httpGet.setURI(URI.create(url + "?" + param));
			response = httpClient.execute(httpGet);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, charset);
				}
			}
			return result;
		} catch (IOException e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} catch (KeyManagementException e) {
			System.out.println("绕过秘钥验证失败！" + e);
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("找不到恢复密钥的算法！" + e);
			e.printStackTrace();
		} finally {
			try {
				release(response, httpClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @describe 释放资源
	 * @author duhao
	 * @date 2019年9月20日下午4:29:49
	 * @param httpResponse
	 * @param httpClient
	 * @throws IOException
	 */
	private static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
		// 释放资源
		if (httpResponse != null) {
			httpResponse.close();
		}
		if (httpClient != null) {
			httpClient.close();
		}
	}

	/**
	 * 
	 * @describe 绕过验证
	 * @author duhao
	 * @date 2019年9月20日下午4:30:07
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");
		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

	/**
	 * 
	 * @describe 创建自定义的httpclient对象
	 * @author duhao
	 * @date 2019年9月20日下午4:30:32
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	private static CloseableHttpClient createHttpClient() throws KeyManagementException, NoSuchAlgorithmException {
		SSLContext sslcontext = createIgnoreVerifySSL();
		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);
		// 创建自定义的httpclient对象
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
		return client;
	}

	public static void main(String[] args) {
		String tokenUrl = "http://www.yuyiai.com:8000/api/oauth/token";
		Map<String, String> contentMap = new HashMap<String, String>();
		contentMap.put("grant_type", "password");
		contentMap.put("username", "dh");
		contentMap.put("password", "321");
		contentMap.put("client_id", "zkwg");
		contentMap.put("client_secret", "zkwg");
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		String post = ApiHttpClientUtil.doPost(tokenUrl, headerMap, contentMap);
		JSONObject token = JSON.parseObject(post);
		System.out.println(token);

//		String getUserUrl = "http://localhost:2740/api/services/app/user/GetUser";
//		Map<String, String> getUsercontentMap = new HashMap<String, String>();
//		getUsercontentMap.put("UserName", "dh");
//		Map<String, String> getUserheaderMap = new HashMap<String, String>();
//		getUserheaderMap.put("Content-Type", "application/json");
//		getUserheaderMap.put("Authorization", token.getString("token_type") + " " + token.getString("access_token"));
//		String doGet = ApiHttpClientUtil.doGet(getUserUrl, getUserheaderMap, getUsercontentMap);
//		System.out.println(doGet);
//
//		Map<String, String> refcontentMap = new HashMap<String, String>();
//		refcontentMap.put("grant_type", "refresh_token");
//		refcontentMap.put("refresh_token", token.getString("refresh_token"));
//		refcontentMap.put("client_id", "zkwg");
//		refcontentMap.put("client_secret", "zkwg");
//		Map<String, String> refheaderMap = new HashMap<String, String>();
//		refheaderMap.put("Content-Type", "application/json");
//		String refpost = ApiHttpClientUtil.doPost(tokenUrl, refheaderMap, refcontentMap);
//		JSONObject reftoken = JSON.parseObject(refpost);
//		System.out.println(reftoken);

//		String articleUrl = "http://localhost:2740/api/services/app/article/GetArticleById?articleId=1033884305010458624";
//		Map<String, String> articlecontentMap = new HashMap<String, String>();
////		articlecontentMap.put("articleId", "1033884305010458624");
//		Map<String, String> articleheaderMap = new HashMap<String, String>();
//		articleheaderMap.put("Authorization", reftoken.getString("token_type")+" "+reftoken.getString("access_token"));
//		articleheaderMap.put("Content-Type", "application/json");
//		String article = HttpClientUtil.doPost(articleUrl, articleheaderMap, articlecontentMap);
//		JSONObject articleJSON = JSON.parseObject(article);
//		System.out.println(articleJSON);

//		String articleUrl = "http://localhost:2740/api/services/app/article/GetArchiveArticleList?archiveDate=2019-09-20";
//		Map<String, String> articlecontentMap = new HashMap<String, String>();
//		articlecontentMap.put("searchText", "");
//		articlecontentMap.put("sortName", "");
//		articlecontentMap.put("sortOrder", "");
//		articlecontentMap.put("sorting", "");
//		articlecontentMap.put("pageSize", "1");
//		articlecontentMap.put("pageNumber", "1");
//		articlecontentMap.put("skipCount", "0");
//		Map<String, String> articleheaderMap = new HashMap<String, String>();
//		articleheaderMap.put("Authorization",
//				reftoken.getString("token_type") + " " + reftoken.getString("access_token"));
////		articleheaderMap.put("Content-Type", "application/json");
//		String article = HttpClientUtil.doPost(articleUrl, articleheaderMap, articlecontentMap);
//		JSONObject articleJSON = JSON.parseObject(article);
//		System.out.println(articleJSON);	

//		String articleUrl = "http://localhost:2740/api/services/app/article/GetArchiveArticleList?archiveDate=2019-09-19";
//		JSONObject articlecontentMap = new JSONObject();
//		articlecontentMap.put("searchText", "");
//		articlecontentMap.put("sortName", "");
//		articlecontentMap.put("sortOrder", "");
//		articlecontentMap.put("sorting", "");
//		articlecontentMap.put("pageSize", 2);
//		articlecontentMap.put("pageNumber", 1);
//		articlecontentMap.put("skipCount", 0);
//		JSONObject articleheaderMap = new JSONObject();
//		articleheaderMap.put("Authorization",
//				reftoken.getString("token_type") + " " + reftoken.getString("access_token"));
//		articleheaderMap.put("Content-Type", "application/json");
//		String article = null;
//		try {
//			article = HttpClientUtil.sendJson(articleUrl, articleheaderMap, articlecontentMap,"utf-8");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		JSONObject articleJSON = JSON.parseObject(article);
//		System.out.println(articleJSON);

//		String articleUrl = "http://localhost:2740/api/services/app/wg_Ele_News_Data/CreateWg_Ele_News_Data";
//		Map<String, String> articlecontentMap = new HashMap<String, String>();
//		articlecontentMap.put("uuid", "00056685C35244A2BD9027C8F812693E");
//		articlecontentMap.put("paper_Id", "3481721025E344A48C3FA2F5CCE8E17E");
//		articlecontentMap.put("site", "人民公安报");
//		articlecontentMap.put("title", "北京市局召开“不忘初心、牢记使命”主题教育工作会");
//		articlecontentMap.put("source_Url", "http://epaper.cpd.com.cn/szb/20190611/#a00056685C35244A2BD9027C8F812693E");
//		articlecontentMap.put("preTitle", "");
//		articlecontentMap.put("subTitle", "王小洪出席并讲话");
//		articlecontentMap.put("pubTime", "2019-06-11 00:00:00");
//		articlecontentMap.put("author", "");
//		articlecontentMap.put("editor", "");
//		articlecontentMap.put("abstract", "");
//		articlecontentMap.put("content",
//				"王小洪强调，深入开展“不忘初心、牢记使命”主题教育意义重大、影响深远。全局上下要更加紧密地团结在以习近平同志为核心的党中央周围，以坚韧顽强的斗争精神、较真碰硬的工作态度、务实创新的工作作风，切实把主题教育组织好、开展好，以党和人民满意的优异成绩庆祝新中国成立70周年。");
//		articlecontentMap.put("tag_Content",
//				"<font><p>王小洪强调，深入开展“不忘初心、牢记使命”主题教育意义重大、影响深远。全局上下要更加紧密地团结在以习近平同志为核心的党中央周围，以坚韧顽强的斗争精神、较真碰硬的工作态度、务实创新的工作作风，切实把主题教育组织好、开展好，以党和人民满意的优异成绩庆祝新中国成立70周年。</font></p>");
//		articlecontentMap.put("paper_Picture",
//				"http://paper.tywbw.com/tywb/20190820/24c0ed505f2ea7cf3d243df18a23a2b9.jpg");
//		articlecontentMap.put("local_Pictures", "");
//		articlecontentMap.put("pictures_Description", "");
//		articlecontentMap.put("language", "中文");
//		articlecontentMap.put("layoutSource", "http://paper.tywbw.com/tywb/20190820/page_24.jpg");
//		articlecontentMap.put("local_Thumbnail", "");
//		articlecontentMap.put("pdf_Url", "");
//		articlecontentMap.put("local_Pdf", "");
//		articlecontentMap.put("ha", "474,332,474,757,664,757,664,332");
//		articlecontentMap.put("channel", "要闻");
//		articlecontentMap.put("revision", "01");
//		articlecontentMap.put("paperCount", "第01版");
//		articlecontentMap.put("edition_Name", "要闻");
//		articlecontentMap.put("insert_Time", "2019-09-18 08:34:19");
//		articlecontentMap.put("remark", "测试");
//		articlecontentMap.put("state", "-1");
//		articlecontentMap.put("creator", "duhao");
//		articlecontentMap.put("create_Time", "2019-09-18 08:34:19");
//		articlecontentMap.put("update_Time", "2019-09-18 08:34:19");
//		articlecontentMap.put("id", "0");
//		Map<String, String> articleheaderMap = new HashMap<String, String>();
//		articleheaderMap.put("Authorization",
//				reftoken.getString("token_type") + " " + reftoken.getString("access_token"));
//		String article = ApiHttpClientUtil.doPost(articleUrl, articleheaderMap, articlecontentMap);
//		JSONObject articleJSON = JSON.parseObject(article);
//		System.out.println(articleJSON);

	}

}