package com.yuyiyun.YYdata.core.util;

import java.util.ArrayList;
import java.util.List;

import org.seimicrawler.xpath.JXDocument;
import org.springframework.web.util.HtmlUtils;

public class HtmlUtil {

	public static void main(String[] args) {
		String convertTag = convertTag("");
		System.out.println(convertTag);
	}

	/**
	 * @describe HTML中特殊标签替换
	 * @author duhao
	 * @date 2020年5月22日下午2:29:05
	 * @param string
	 * @return
	 */
	public static String convertTag(String string) {
		if (null==string) {
			return null;
		}
		string = string.replaceAll("& ", "&");
		String unescape = HtmlUtils.htmlUnescape(string);
		return unescape;
	}
	
	/**
	 * @describe 通过xpath获取html节点
	 * @author duhao
	 * @date 2020年5月22日下午2:28:57
	 * @param html
	 * @param xpath
	 * @return
	 */
	public static List<String> getJXNodes(String html, String xpath) {
		List<String> result = new ArrayList<String>();
		if (html == null || xpath == null || "".equals(xpath)) {
			return result;
		}
		if (xpath.startsWith("//")) {
		} else if (xpath.startsWith("/")) {
			xpath = "/" + xpath;
		} else {
			xpath = "//" + xpath;
		}
		List<Object> sel = JXDocument.create(html).sel(xpath);
		for (Object object : sel) {
			result.add(object.toString());
		}
		return result;
	}
	
	

}
