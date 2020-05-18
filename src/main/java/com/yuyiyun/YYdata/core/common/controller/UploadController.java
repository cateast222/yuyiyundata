package com.yuyiyun.YYdata.core.common.controller;

import java.util.UUID;

import org.jsoup.Connection.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuyiyun.YYdata.core.upload.ResourceUpload;
import com.yuyiyun.YYdata.core.util.HttpJsoup;
import com.yuyiyun.YYdata.core.util.ToolsUtil;

import cn.hutool.crypto.SecureUtil;
import cn.stylefeng.roses.core.reqres.response.ResponseData;

@RestController
@RequestMapping("/yydataApi")
public class UploadController {

	@GetMapping("/upload/image")
	public ResponseData images(String urls, String headerStr, String fileName, boolean proxy) {
		if (ToolsUtil.isEmpty(urls)) {
			return ResponseData.error(401, "urls不能为空！");
		} else {
			try {
				String[] urlArray = urls.split("#");
				StringBuffer bf = new StringBuffer();
				for (String url : urlArray) {
//					String urlMd5 = SecureUtil.md5(url);
					String urlUuid = UUID.randomUUID().toString().replaceAll("-", "");
					Response response = HttpJsoup.getResources(url, headerStr, proxy);
					byte[] bodyAsBytes = response.bodyAsBytes();
					
//					String saveLocalFile = ResourceUpload.saveLocalFile("D:/", bodyAsBytes, urlMd5 + fileName);
//					bf.append(saveLocalFile);
					
					String file = ResourceUpload.uploadFile("http://yuyiyun.net:8093/document/fileupload/yunyidata",
							bodyAsBytes, urlUuid + fileName);
					bf.append("http://yuyiyun.net:8093/yunyidata/document/" + file.replaceAll(".*,", "") + "#");
				}
				bf.deleteCharAt(bf.length() - 1);
				return ResponseData.success(bf.toString());
			} catch (Exception e) {
				return ResponseData.error(402, urls);
			}
		}
	}
}
