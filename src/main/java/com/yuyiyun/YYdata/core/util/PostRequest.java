package com.yuyiyun.YYdata.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.mysql.cj.util.StringUtils;


/**
 * 
 * @describe 数据抓取工具类
 * @author duhao
 * @date 2019年9月17日上午9:29:27
 */
public class PostRequest {
	/**
	 * 
	 * @describe post请求
	 * @author duhao
	 * @date 2019年9月17日上午9:30:21
	 * @param url
	 * @param param
	 * @param head
	 * @param json
	 * @return
	 */
	public static String post(String url, Map<String, String> param, Map<String, String> head, String json) {
		String resp = "";
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		if (head != null) {
			for (String key : head.keySet()) {
				post.setHeader(key, head.get(key));
			}
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (param != null) {
			for (String key : param.keySet()) {
				params.add(new BasicNameValuePair(key, param.get(key)));
			}
		}
		if (!StringUtils.isEmptyOrWhitespaceOnly(json)) {
			StringEntity strEntit = new StringEntity(json, "utf-8");
			post.setEntity(strEntit);
		} else {
			try {
				post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		try {
			HttpResponse response = client.execute(post);

			// 自动获取字符集并进行设置
			String charset = getCharset(EntityUtils.toString(response.getEntity()));
			if (charset != null) {
				resp = EntityUtils.toString(response.getEntity());
			} else {
				resp = EntityUtils.toString(response.getEntity(), charset);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		post.abort();
		return resp;
	}

	/**
	 * 
	 * @describe get请求
	 * @author duhao
	 * @date 2019年9月17日上午9:30:37
	 * @param url
	 * @param head
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String Get(String url, Map<String, String> head) throws ClientProtocolException, IOException {
		String resp = "";
		HttpClient client = HttpClients.createDefault();
		HttpGet requestGet = new HttpGet(url);
		for (String key : head.keySet()) {
			requestGet.setHeader(key, head.get(key));
		}
		HttpResponse response = client.execute(requestGet);
		// 自动获取字符集并进行设置
		String charset = getCharset(EntityUtils.toString(response.getEntity()));
		if (charset != null) {
			resp = EntityUtils.toString(response.getEntity());
		} else {
			resp = EntityUtils.toString(response.getEntity(), charset);
		}
		requestGet.abort();
		return resp;
	}

	public static String Get(String url, Map<String, String> head, String ip, int port)
			throws ClientProtocolException, IOException {
		String resp = "";
		HttpHost proxy = new HttpHost(ip, port, "http");
		RequestConfig config = null;
		if (port != 0) {
			config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(8000).setSocketTimeout(8000).build();
		} else {
			config = RequestConfig.custom().setConnectTimeout(8000).setSocketTimeout(8000).build();
		}
		HttpClient client = HttpClients.createDefault();
		HttpGet requestGet = new HttpGet(url);
		requestGet.setConfig(config);
		for (String key : head.keySet()) {
			requestGet.setHeader(key, head.get(key));
		}
		HttpResponse response = client.execute(requestGet);
		// 自动获取字符集并进行设置
		String charset = getCharset(EntityUtils.toString(response.getEntity()));
		if (charset != null) {
			resp = EntityUtils.toString(response.getEntity());
		} else {
			resp = EntityUtils.toString(response.getEntity(), charset);
		}
		requestGet.abort();
		return resp;
	}

	public static HttpResponse GetReturnHttpResponse(String url, Map<String, String> head)
			throws ClientProtocolException, IOException {
		HttpClient client = HttpClients.createDefault();
		HttpGet requestGet = new HttpGet(url);
		for (String key : head.keySet()) {
			requestGet.setHeader(key, head.get(key));
		}
		HttpResponse response = client.execute(requestGet);
		return response;
	}

	// 返回responseEntity
	public static HttpResponse postReturnEntity(String url, Map<String, String> param, Map<String, String> head) {
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		for (String key : head.keySet()) {
			post.setHeader(key, head.get(key));
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : param.keySet()) {
			params.add(new BasicNameValuePair(key, param.get(key)));
		}
		HttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			response = client.execute(post);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String getByJsoup(String url, Integer region) {
		String result = null;
		Response execute = null;
		int count = 1;
		while (count <= 3 && execute == null) {
			try {
				if (region != null && region == 1) {
					new Proxy();
					execute = Jsoup.connect(url).method(Method.GET)
							.header("Content-Type", "application/json; charset=utf-8")
							.proxy("127.0.0.1", Proxy.setProxy()).timeout(20000).maxBodySize(0).ignoreContentType(true)
							.execute();
				} else {
					execute = Jsoup.connect(url).method(Method.GET)
							.header("Content-Type", "application/json; charset=utf-8").timeout(20000).maxBodySize(0)
							.ignoreContentType(true).execute();
				}
			} catch (IOException e) {
				count++;
				e.printStackTrace();
				try {
					Thread.sleep(1000 * 2);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (execute == null) {
			return null;
		}

		// 自动获取字符集并进行设置
		String charset = getCharset(execute.body());
		if (charset != null) {
			execute.charset(charset);
		}
		result = execute.body();
		return result;
	}

	public static String getByJsoup(String url, HashMap<String, String> header, Integer region) {
		if (header == null || header.size() < 1) {
			return getByJsoup(url, region);
		}
		String result = null;
		Response execute = null;
		int count = 1;
		while (count <= 3 && execute == null) {
			try {
				if (region != null && region == 1) {
					execute = Jsoup.connect(url).method(Method.GET).headers(header).maxBodySize(0)
							.proxy("127.0.0.1", Proxy.setProxy()).timeout(20000).ignoreContentType(true).execute();
				} else {
					execute = Jsoup.connect(url).method(Method.GET).headers(header).timeout(20000).maxBodySize(0)
							.ignoreContentType(true).execute();
				}
			} catch (IOException e) {
				count++;
				e.printStackTrace();
				try {
					Thread.sleep(1000 * 2);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (execute == null) {
			return null;
		}

		// 自动获取字符集并进行设置
		String charset = getCharset(execute.body());
		if (charset != null) {
			execute.charset(charset);
		}
		result = execute.body();
		if (charset.equals("gb2312")) {
            byte[] gbkBytes = null;
			try {
				gbkBytes = new String(result.getBytes(), "gbk").getBytes();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				byte[] bs = new String(gbkBytes, "utf-8").getBytes();
				return new String(bs);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return result;
	}

	/**
	 * 
	 * @describe TODO
	 * @author duhao
	 * @date 2019年9月7日下午3:45:47
	 * @param url
	 * @param header      请求头
	 * @param requestType 请求类型（0-get;1-post）
	 * @param bodyType    请求体的类型 （0-form;1-json）
	 * @param body        请求体的Body参数
	 * @param region      请求体的代理 （0-境内;1-境外）
	 * @return
	 */
	public static String getByJsoup(String url, String header, Integer requestType, Integer bodyType, String body,
			Integer region) {
		HashMap<String, String> headerMap = new HashMap<String, String>();
		if (!org.apache.commons.lang3.StringUtils.isBlank(header)) {
			String[] split = header.split("\\r?\\n");
			for (String eachStr : split) {
				String[] key_val = eachStr.split(":");
				if (key_val.length > 1) {
					String key = key_val[0].trim();
					String val = key_val[1].trim();
					headerMap.put(key, val);
				}
			}
		}
		if (requestType == 0) {
			// get
			return getByJsoup(url, headerMap, region);
		} else if (requestType == 1) {
			// post
			return postByJsoup(url, headerMap, bodyType, body, region);
		}
		return null;
	}

	/**
	 * @param url
	 * @param header
	 * @param requestType
	 * @param body
	 * @param region
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String postByJsoup(String url, HashMap<String, String> header, Integer bodyType, String body,
			Integer region) {
		String result = null;
		if (org.apache.commons.lang3.StringUtils.isBlank(url)) {
			return result;
		}
		Connection conn = null;
		try {
			if (region != null && region == 1) {
				conn = Jsoup.connect(url).proxy("127.0.0.1", Proxy.setProxy()).ignoreContentType(true).maxBodySize(0)
						.method(Method.POST);
			} else {
				conn = Jsoup.connect(url).ignoreContentType(true).maxBodySize(0).method(Method.POST);
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			return result;
		}
		if (header != null && header.size() > 0) {
			conn = conn.headers(header);
		}
		if (bodyType != null && bodyType == 0) {
			// form表单类，格式为键值对，一行一个
			HashMap<String, String> bodyMap = new HashMap<String, String>();
			// 生成键值对，存入map中
			if (org.apache.commons.lang3.StringUtils.isNotBlank(body)) {
				String[] split = body.split("\\r?\\n");
				for (String eachStr : split) {
					String[] key_val = eachStr.split(":");
					if (key_val.length > 1) {
						String key = key_val[0];
						String val = key_val[1];
						bodyMap.put(key, val);
					}
				}
			}
			conn.data(bodyMap);
		} else if (bodyType != null && bodyType == 1) {
			// json类
			if (org.apache.commons.lang3.StringUtils.isNotBlank(body)) {
				conn.requestBody(body);
			}
		}

		Response execute = null;
		int count = 1;
		while (count <= 3 && execute == null) {
			try {
				execute = conn.execute();
			} catch (IOException e) {
				count++;
				e.printStackTrace();
				try {
					Thread.sleep(1000 * 2);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (execute != null) {
			// 自动获取字符集并进行设置
			String charset = getCharset(execute.body());
			if (charset != null) {
				execute.charset(charset);
			}

			result = execute.body();
		}
		return result;
	}

	private static String getCharset(String html) {
		String charset = "utf-8";
		// 匹配<head></head>之间，出现在<meta>标签中的字符编码
		Pattern pattern = Pattern.compile("<head>([\\s\\S]*?)<meta([\\s\\S]*?)charset\\s*=(\")?(.*?)\"");
		Matcher matcher = pattern.matcher(html.toLowerCase());
		if (matcher.find()) {
			charset = matcher.group(4);
		}
		if(charset.equals("gb2312")) {
			charset = "gb18030";
//			charset = "gb18030";
		}
		System.out.println(charset);
		return charset;
	}
	
	/**
	 * 获取网页字符编码
	 * @param url
	 * @return
	 */
	public static String getEncoded(String url) {
		String charset = "utf-8";
		if (url.equals("")||url==null) {
			
		}else {
			try {
				String html = getByJsoup(url, null, 0, null, null, 0);
				charset = getCharset(html);
			} catch (Exception e) {
			}
		}
		return charset;
	}
	
	

	public static void main(String[] args) {
//		String headerString = "Content-Type:application/json";
//		String byJsoup = getByJsoup("http://124.204.66.245:22653/taskService/getTask", headerString, 1, 1,
//				"{\"media_type\":4,\"task_type\":1}");
//		try {
//			IOUtils.write(byJsoup, new FileOutputStream(new File("byJsoup.txt")), "utf-8");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(byJsoup);		
//		String string = "http://epaper.cpd.com.cn/szb/20190909/info.cfg";
//		String string = "http://epaper.cpd.com.cn/szb/20190909/c032822040794398bacaf03c25b0ce47.xml";
//		String string = "http://127.0.0.1:8080/dataci/detail";
//		String string2 = getByJsoup(string, "Content-Type: application/x-www-form-urlencoded;charset=UTF-8\r\n"
//				+ "Cookie: rememberMe=3GOAMUoFiwLliDJ62TE4ulsaLiZC8TeM/lD6sFLelhJgLGnmkUmS9GVALfv9NKb6xLXLw2U7bVUop2RIUqRKUHIhpvgP/hQqx3pMCCeHgMO0pmccCJkBf/EpYWN5Nuc36DkiiJSd+azmrHVaZOvNdF+jVzPM06luLDcNjdNsWoCoC+JY+hGfMwCFc8v6NKghh+0TLtwcrgaYywhLsfpsNXMyY6cIWQSkWk9izCxS7IbUF3AdJ/9UjNCefICTalFLXFSJW6UqocSpXhF68egO6nwATFZPFOzdkBmxI44Ye0PKzaAK9gK9HDABOknoIMDaGsEqIdxcHCuD5hbUnnjUZwrtd5KZHVVlpL/Nd8WrVgFpZgeQaI6/cku+lxRhidFc4dBZKO4IkuPLsuER3hXkibZBVoIHEOOEwD9gZhS4Jf44zAWFzn78PMHkMvkWMnCjNI0jPhU4i+EAi4OMR+LAUF+z0S54CWisUG2tUXddzYH53CBK4j3yMILsN9Tr/A3WdwBCLEirHm3NCSJXZEGnIiKSkVGmZyzaTey4sgVOeSBPnHF21eaeJaeEj4yRfD81hRfg40uSCIco79vooKkqIep2TSm7cG2ydlNBNkJ/rcbq+V2izi2HFEC9N7KL0ZhLDvH2Q6+2P4EAUWbukulY+j0rsZzlRXKG4fMwSIbSy1bP3ya/DMM2Xa2lppye/4L3+U0Ii5Gw4Jg1kEQaOGoKe73fiMQs5YufBNVlvunOVUV/iNKxipBN9vxoT8cpCdorsll1lw5QHKOY6xXZYLwTQACGeQ3ioALBith15bEAr1J0mqYOnUpNxYY7//SlOSQhNS8SBXgZMqKQQVo8txzKcWu+uxTmc0/JkVWOghR26AoPfra3habJKsBwDwUOMBkLAJBi1k7FJpAhF/GPGPZcNrU4BqVnof2bTO7qNJIdo8iyXxNpn1mIlr03JgwOFk+jUBYga5jLD+d3gKCeCZ6FlkraFgeOhfbTu0H2vWDcvh6nzZ0iELmrpLaCgenW62W0xnWxvxGJN5ayPQLwHUJFeY1rYDk7BU1LuvMIMAKLAhG64HYUA6EmVIXKpUn2Mt/XFpuR6Yn8OP/QShqd6EYt/Owgi0MtTwkbwsdmqlpeu1WCLUydM7RzBuccj0kg4XQl; shiroCookie=3460c5ee-c1bc-4eb9-a7b9-a485fe7cbc38",
//				1, 0, "dsiUuid: E0C909C02E214FA7B288B157C4B8614E\r\n" + 
//						"key: newschannel", 0);
//		System.out.println(string2);
		String string = "http://epaper.xkb.com.cn/index.php";
		String string2 = getByJsoup(string, "X-Requested-With:XMLHttpRequest\r\n" + 
				"Content-Type:application/x-www-form-urlencoded",
				1, 0, "act:list\r\n" + 
						"date:2019-11-21", 0);
		System.out.println(string2);
	}
}
