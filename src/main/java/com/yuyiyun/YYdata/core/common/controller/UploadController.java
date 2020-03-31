package com.yuyiyun.YYdata.core.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuyiyun.YYdata.core.upload.ResourceUpload;

import cn.stylefeng.roses.core.reqres.response.ResponseData;

@RestController
@RequestMapping("/yydataApi")
public class UploadController {

	@GetMapping("/upload/image")
	public ResponseData images(String urls, String fileName, boolean proxy) {
		if (urls == null || urls.equals("")) {
			return ResponseData.error(401,"urls不能为空！");
		}else {
			try {
				String[] urlArray = urls.split("#");
				StringBuffer bf = new StringBuffer();
				for (String url : urlArray) {
					String file = ResourceUpload.downloadFile(url, proxy, "http://yuyiyun.net:8093/document/fileupload/yunyidata", "", fileName);
					bf.append("http://yuyiyun.net:8093/yunyidata/document/image/"+file.replaceAll(".*,", "")+"#");
				}
				bf.deleteCharAt(bf.length()-1);
				return ResponseData.success(bf.toString());
			} catch (Exception e) {
				return ResponseData.error(402, urls);
			}
		}
	}
}
