package com.yuyiyun.YYdata.core.upload;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.boot.autoconfigure.http.HttpProperties.Encoding;

import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion.Setting;

public class Test {
	public static DateTime FromUnixMillis(long unixMillis) {
	    DateTime origin = new DateTime(1970, 1, 1, 0, 0, 0, 0, DateTimeKind.Utc);
	    return origin.AddMilliseconds(unixMillis);
	}

	private void downloadImage(String url, String savePath) throws Exception, Throwable {
		HttpClient client = HttpClients.createDefault();
		HttpGet httpget = new HttpGet();
		httpget.setURI(URI.create(url));
		HttpResponse response = client.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			byte[] byteArray = EntityUtils.toByteArray(entity);
			String originalFilename = url.substring(url.lastIndexOf("/") + 1);
			savePath += originalFilename;
			System.out.println("123 " + savePath);
			File file = new File(savePath);
			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
			}

			FileOutputStream fos = new FileOutputStream(file);
			fos.write(byteArray);
			fos.flush();
			fos.close();
		}
	}

	public static String uploadFile(String uploadUrl, byte[] byteArray, String originalFilename) {
		String result = "";

		try {
			// 换行符
			final String newLine = "\r\n";
			final String boundaryPrefix = "--";
			// 定义数据分隔线
			String BOUNDARY = "----------7d4a6d158c9";
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
			sb.append("Content-Disposition: form-data;name=\"photo\";filename=\"" + originalFilename + "\"" + newLine);
			sb.append("Content-Type:application/octet-stream");
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
			//		in.close();

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
		return result;
	}

	public static void main(String[] args) throws Exception, Throwable {
		new Test().downloadImage("http://192.168.0.88:8093/Web/images/head.jpg", "D:/123/");
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
