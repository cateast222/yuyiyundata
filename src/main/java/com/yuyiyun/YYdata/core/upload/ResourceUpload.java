package com.yuyiyun.YYdata.core.upload;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.yuyiyun.YYdata.core.util.ToolsUtil;

/**
 * 资源下载、保存及上传
 * 
 * @author duhao
 *
 */
public class ResourceUpload {
	/**
	 * 请求超时时间
	 */
	private static final int TIME_OUT = 120000;
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
	
	private static String getContentTypes(String fileExt) {
		String fileName = "src/main/resources/config.json";
		JSONObject config_json;
		String result = "";
		try {
			File configfile = new File(fileName);
			config_json = JSON.parseObject(Files.toString(configfile, Charsets.UTF_8));
			JSONObject contentTypes_json = config_json.getJSONObject("ContentTypes");
			fileExt = fileExt.contains(".") ? fileExt.substring(fileExt.lastIndexOf(".")) : "anno";
			result = contentTypes_json.getString(fileExt.toLowerCase());
			if (result == null) {
				result = contentTypes_json.getString("anno");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("getContentTypes " + result);
		return result;
	}

	private static String getTicks() {
		long milli = System.currentTimeMillis() + 8 * 3600 * 1000;
		long ticks = (milli * 10000) + 621355968000000000L;
		String hexString = Long.toHexString(ticks).toUpperCase();
		System.out.println("getTicks " + hexString);
		return hexString;
	}
	
	private static Map<String, String> getString2Map(String str0,String str1,String ste2) {
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
	
	private static Response getResponse(String url, Map<String, String> headers, boolean proxy) {
		Response response = null;
		Connection conn = Jsoup.connect(url).method(Method.GET);
		if (headers != null && headers.size() > 0) {
			conn.headers(headers);
		}
		if (proxy) {
			conn.proxy("192.168.0.88", 8125);
		}
		conn.ignoreContentType(true).maxBodySize(0);

		int count = 1;
		while (count <= 3 && response == null) {
			try {
				response = conn.execute();
			} catch (IOException e) {
				count++;
				if(e instanceof HttpStatusException) {
					HttpStatusException hse = (HttpStatusException)e;
					System.err.print(hse.getStatusCode()+"-");
				}
				System.err.println(e.getMessage());
				try {
					Thread.sleep(1000 * 2);
				} catch (Exception e1) {
				}
			}
		}
		return response;
	}

	/**
	 * :爬取网络资源
	 * 
	 * @param downloadUrl      资源地址
	 * @param isProxy          代理
	 * @param uploadUrl        文件服务器地址
	 * @param saveLocalPath    本地保存路径
	 * @param originalFilename 文件及扩展名
	 * @return
	 * @throws Exception
	 */
	public static String downloadFile(String downloadUrl, String headerStr, boolean isProxy,
			String uploadUrl, String saveLocalPath, String originalFilename) throws Exception {

		String result = "";
		HttpClient client = HttpClients.createDefault();
		HttpGet httpget = new HttpGet();
		httpget.setURI(URI.create(downloadUrl));
		if (isProxy) {
			RequestConfig config = RequestConfig.custom().setProxy(new HttpHost("127.0.0.1", 8124))
					.setConnectTimeout(8000).setSocketTimeout(8000).build();
			httpget.setConfig(config);
		}
		HttpResponse response = client.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			byte[] byteArray = EntityUtils.toByteArray(entity);
			originalFilename = ToolsUtil.isNotEmpty(originalFilename)
					? UUID.randomUUID().toString().replaceAll("-", "") + originalFilename
					: downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1).replaceAll("\\?.*", "");
			if (uploadUrl != null && !uploadUrl.equals("")) {
				result += uploadFile(uploadUrl, byteArray, originalFilename);
			}

			if (saveLocalPath != null && !saveLocalPath.equals("")) {
				result += saveLocalFile(saveLocalPath, byteArray, originalFilename);
			}
		}
		return result;
	}

	/**
	 * :资源本地保存
	 * 
	 * @param saveLocalPath    本地保存路径
	 * @param byteArray        资源数据
	 * @param originalFilename 文件及扩展名
	 * @return
	 */
	public static String saveLocalFile(String saveLocalPath, byte[] byteArray, String originalFilename) {
		String result = "";
		try {
			saveLocalPath += originalFilename;
			File file = new File(saveLocalPath);
			if (!file.exists()) {
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(byteArray);
				fos.flush();
				fos.close();
				result = saveLocalPath;
			} else {
				file.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("saveLocalFile " + result);
		return result;
	}

	/**
	 * :资源上传服务器
	 * 
	 * @param uploadUrl        文件服务器地址
	 * @param byteArray        资源数据
	 * @param originalFilename 文件及扩展名
	 * @return
	 */
	public static String uploadFile(String uploadUrl, byte[] byteArray, String originalFilename) {
		String result = "";
		try {
			// 换行符
			final String newLine = "\r\n";
			final String boundaryPrefix = "--";

			// 定义数据分隔线
			String BOUNDARY = "----------" + getTicks();

			// 服务器的域名
			URL url = new URL(uploadUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// 设置为POST情
			conn.setRequestMethod("POST");

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			// 设置请求头参数
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			OutputStream out = new DataOutputStream(conn.getOutputStream());

			// 上传文件
			StringBuilder sb = new StringBuilder();
			sb.append(boundaryPrefix);
			sb.append(BOUNDARY);
			sb.append(newLine);

			// 文件参数,photo参数名可以随意修改
			sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + originalFilename + "\"" + newLine);
			sb.append("Content-Type: " + getContentTypes(originalFilename));

			// 参数头设置完以后需要两个换行，然后才是参数内容
			sb.append(newLine);
			sb.append(newLine);

			// 将参数头的数据写入到输出流中
			out.write(sb.toString().getBytes());

			// 数据输入流,用于读取文件数据
			out.write(byteArray);
			/*
			 * DataInputStream in = new DataInputStream(file.getInputStream()); byte[]
			 * bufferOut = new byte[1024 * 8]; int bytes = 0; // 每次读8KB数据,并且将文件数据写入到输出流中
			 * while ((bytes = in.read(bufferOut)) != -1) { out.write(bufferOut, 0, bytes);
			 * }
			 */

			// 最后添加换行
			out.write(newLine.getBytes());
			// in.close();

			// 定义最后数据分隔线，即--加上BOUNDARY再加上--。
			byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine).getBytes();

			// 写上结尾标识
			out.write(end_data);
			out.flush();
			out.close();

			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				// 这里读取的是上边url对应的上传文件接口的返回值，读取出来后，然后接着返回到前端，实现接口中调用接口的方式
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		System.out.println("uploadFile " + result);
		return result;
	}

	public static void main(String[] args) throws Exception, Throwable {
		String headers = "Accept: image/webp,image/apng,image/*,*/*;q=0.8\r\n" + 
				"Accept-Encoding: gzip, deflate\r\n" + 
				"Accept-Language: zh-CN,zh;q=0.9\r\n" + 
				"Host: img.qikan.com.cn\r\n" + 
				"Proxy-Connection: keep-alive\r\n" + 
				"Referer: http://www.qikan.com/articleinfo/xwzk20201503.html\r\n" + 
				"User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
		
		System.out.println(headers.toUpperCase());
//		Map<String, String> map = getString2Map(headers, "\\r?\\n", ":");
//		Response response = getResponse("https://i.prcdn.co/img?file=59b12020051400000000001001&page=1&width=290", null, true);
//		String type = response.contentType();
//		System.out.println(type);
//		byte[] bodyBytes = response.bodyAsBytes();
//		saveLocalFile("D:/", bodyBytes, "134.jpg");
		
//		System.out.println(getContentTypes("6370555030261494447433236.JPG"));
//		https://www.straitstimes.com/sites/default/files/styles/article_pictrure_780x520_/public/articles/2020/03/21/ST_20200321_VNNEX_5541198.jpg?itok=12Alnawf&timestamp=1584728847
//		https://www.straitstimes.com/sites/default/files/styles/article_pictrure_780x520_/public/articles/2020/03/21/ST_20200321_VNLAWRENCEFTR7_5541276.jpg?itok=jCsbmDmq&timestamp=1584728796
//		https://www.straitstimes.com/sites/default/files/styles/article_pictrure_780x520_/public/articles/2020/03/21/ST_20200321_VNLAWRENCE_5541269.jpg?itok=wRCuHjny&timestamp=1584728782
//		http://yuyiyun.net:8093/document/fileupload/yunyidata
//		http://yuyiyun.net:8093/yunyidata/document/image/49a7429acaef43b782a12fe55ae047be
//		downloadFile(
//				"https://www.straitstimes.com/sites/default/files/styles/article_pictrure_780x520_/public/articles/2020/03/21/ST_20200321_VNNEX_5541198.jpg?itok=12Alnawf&timestamp=1584728847",
//				true, "", "D:/", ".jpg");
	}

}
