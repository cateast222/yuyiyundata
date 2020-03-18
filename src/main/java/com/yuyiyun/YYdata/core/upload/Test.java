package com.yuyiyun.YYdata.core.upload;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Test {
	private static String getContentTypes(String fileExt) {
		String fileName = "src/main/resources/config.json";
		JSONObject config_json;
		String result = "";
		try {
			File configfile = new File(fileName);
			config_json = JSON.parseObject(Files.toString(configfile, Charsets.UTF_8));
			JSONObject contentTypes_json = config_json.getJSONObject("ContentTypes");
			fileExt = fileExt.contains(".") ? fileExt.substring(fileExt.indexOf(".")) : "anno";
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

	/**
	 * 
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author duhao
	 * @date 2020年3月19日
	 * @version V1.0
	 * @param downloadUrl
	 * @param uploadUrl
	 * @param saveLocalPath
	 * @return
	 * @throws Exception
	 */
	public static String downloadFile(String downloadUrl, String uploadUrl, String saveLocalPath) throws Exception {
		String result = "";
		HttpClient client = HttpClients.createDefault();
		HttpGet httpget = new HttpGet();
		httpget.setURI(URI.create(downloadUrl));
		HttpResponse response = client.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			byte[] byteArray = EntityUtils.toByteArray(entity);
			String originalFilename = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);

			if (uploadUrl != null && uploadUrl.equals("")) {
				result += "upload:" + uploadFile(uploadUrl, byteArray, originalFilename);
			}

			if (saveLocalPath != null && saveLocalPath.equals("")) {
				result += "saveLocal:" + saveLocalFile(saveLocalPath, byteArray, originalFilename);
			}
		}
		return result;
	}

	/**
	 * 
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author duhao
	 * @date 2020年3月19日
	 * @version V1.0
	 * @param saveLocalPath
	 * @param byteArray
	 * @param originalFilename
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
	 * 
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author duhao
	 * @date 2020年3月19日
	 * @version V1.0
	 * @param uploadUrl
	 * @param byteArray
	 * @param originalFilename
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
//		System.out.println(getContentTypes("6370555030261494447433236.JPG"));
		downloadFile("http://perb.puernews.com/page/1/2020-03/18/01/2020031801_pdf.pdf",
				"http://47.92.121.230:9100/document/fileupload/yunyidata", "D:/");
		// TODO Auto-generated method stub
//		String uploadUrl = "http://47.92.121.230:9100/document/fileupload/qycode";
//        String result = "";
//        Setting boundary = "----------" + DateTime.Now.Ticks.ToString("x");
//        HttpWebRequest webrequest = (HttpWebRequest)WebRequest.Create(uploadUrl);
//        webrequest.ContentType = "multipart/form-data; boundary=" + boundary;                
//        webrequest.Method = "POST";
//        StringBuilder sb = new StringBuilder();                
//        sb.append("--");
//        sb.append(boundary);                
//        sb.append("\r\n");
//        sb.append("Content-Disposition: form-data; name=\"file");
//        sb.Append("\"; filename=\"" + fileName + "\"");
//        sb.append("\"");
//        sb.append("\r\n");
//        sb.append("Content-Type: application/octet-stream");
//        sb.append("\r\n");
//        sb.append("\r\n");
//        String postHeader = sb.toString();
//        byte[] postHeaderBytes = Encoding.UTF8.GetBytes(postHeader);
//        byte[] boundaryBytes = Encoding.ASCII.GetBytes("\r\n--" + boundary + "\r\n");
//        webrequest.ContentLength = uploadFile.InputStream.Length + postHeaderBytes.Length + boundaryBytes.Length;                
//        Stream requestStream = webrequest.GetRequestStream();
//        requestStream.Write(postHeaderBytes, 0, postHeaderBytes.Length);
//        byte[] buffer = new Byte[(int)uploadFile.InputStream.Length]; //声明文件长度的二进制类型
//        uploadFile.InputStream.Read(buffer, 0, buffer.Length); //将文件转成二进制
//        requestStream.Write(buffer, 0, buffer.Length); //赋值二进制数据 
//        requestStream.Write(boundaryBytes, 0, boundaryBytes.Length);                
//        webrequest.UserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; .NET CLR 1.0.3705;)";
//        WebResponse responce = webrequest.GetResponse();
//        requestStream.Close();
//        using (Stream s = responce.GetResponseStream())
//        {
//            using (StreamReader sr = new StreamReader(s))                    {
//                result = sr.ReadToEnd();
//            }
//        }
//        responce.Close();
	}

}
