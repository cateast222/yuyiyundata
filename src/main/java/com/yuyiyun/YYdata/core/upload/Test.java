package com.yuyiyun.YYdata.core.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

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

	private void downloadImage(String url, String savePath) throws Exception, Throwable {
		HttpClient client = HttpClients.createDefault();
		HttpGet httpget = new HttpGet();
		httpget.setURI(URI.create(url));
		HttpResponse response = client.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
	        byte[] byteArray = EntityUtils.toByteArray(entity);
	        savePath+=url.substring(url.lastIndexOf("/")+1);
	        System.out.println("123 "+savePath);
	        File file = new File(savePath);
	        if(!file.exists()){
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
