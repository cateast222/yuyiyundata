package com.yuyiyun.YYdata.core.util;

import java.util.ArrayList;
import java.util.List;

import org.seimicrawler.xpath.JXDocument;
import org.springframework.web.util.HtmlUtils;

public class HtmlUtil {

	public static void main(String[] args) {
		String html = "<div class=\"layui-form-item\">\r\n" + 
				"							<label class=\"layui-form-label\">输入框</label>\r\n" + 
				"							<div class=\"layui-input-block\">\r\n" + 
				"								<input type=\"text\" name=\"title\" lay-verify=\"title\" autocomplete=\"off\" placeholder=\"请输入标题\" class=\"layui-input\">\r\n" + 
				"							</div>\r\n" + 
				"						</div>\r\n" + 
				"<div class=\"layui-form-item\">\r\n" + 
				"							<label class=\"layui-form-label\">单选框</label>\r\n" + 
				"							<div class=\"layui-input-block\">\r\n" + 
				"								<input type=\"radio\" name=\"sex\" value=\"男\" title=\"男\" checked=\"\">\r\n" + 
				"								<input type=\"radio\" name=\"sex\" value=\"女\" title=\"女\">\r\n" + 
				"							</div>\r\n" + 
				"						</div>";
		List<String> jxNodes = getJXNodes(html, "//div[@class='layui-form-item']");
		System.out.println(jxNodes);
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
