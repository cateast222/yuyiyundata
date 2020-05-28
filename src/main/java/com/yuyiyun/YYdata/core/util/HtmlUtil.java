package com.yuyiyun.YYdata.core.util;

import java.util.ArrayList;
import java.util.List;

import org.seimicrawler.xpath.JXDocument;
import org.springframework.web.util.HtmlUtils;

public class HtmlUtil {

	public static void main(String[] args) {
		String html = "<div class=\"layui-form-item\"> \r\n" + 
				" <label class=\"layui-form-label\">输入框</label> \r\n" + 
				" <div class=\"layui-input-block\"> \r\n" + 
				"  <input type=\"text\" name=\"title-1\" id=\"title\" autocomplete=\"off\" placeholder=\"请输入标题\" class=\"layui-input\"> \r\n" + 
				" </div> \r\n" + 
				"</div>\r\n" + 
				"<div class=\"layui-form-item\"> \r\n" + 
				" <label class=\"layui-form-label\">选择框</label> \r\n" + 
				" <div class=\"layui-input-block\"> \r\n" + 
				"  <select name=\"aihao-1\" id=\"aihao\"> <option value=\"\"></option> <option value=\"0\">写作</option> <option value=\"1\">阅读</option> <option value=\"2\">游戏</option> <option value=\"3\">音乐</option> <option value=\"4\">旅行</option> </select> \r\n" + 
				" </div> \r\n" + 
				"</div>\r\n" + 
				"<div class=\"layui-form-item\"> \r\n" + 
				" <label class=\"layui-form-label\">复选框</label> \r\n" + 
				" <div class=\"layui-input-block\"> \r\n" + 
				"  <input type=\"checkbox\" name=\"like-1\" value=\"write\" title=\"写作\"> \r\n" + 
				"  <input type=\"checkbox\" name=\"like-1\" value=\"read\" title=\"阅读\" checked=\"\"> \r\n" + 
				"  <input type=\"checkbox\" name=\"like-1\" value=\"game\" title=\"游戏\"> \r\n" + 
				" </div> \r\n" + 
				"</div>\r\n" + 
				"<div class=\"layui-form-item\"> \r\n" + 
				" <label class=\"layui-form-label\">单选框</label> \r\n" + 
				" <div class=\"layui-input-block\"> \r\n" + 
				"  <input type=\"radio\" name=\"sex-1\" value=\"男\" title=\"男\" checked=\"\"> \r\n" + 
				"  <input type=\"radio\" name=\"sex-1\" value=\"女\" title=\"女\"> \r\n" + 
				" </div> \r\n" + 
				"</div>\r\n" + 
				"<div class=\"layui-form-item layui-form-text\"> \r\n" + 
				" <label class=\"layui-form-label\">文本域</label> \r\n" + 
				" <div class=\"layui-input-block\"> \r\n" + 
				"  <textarea placeholder=\"请输入内容\" class=\"layui-textarea\" name=\"desc-1\" id=\"desc\"></textarea> \r\n" + 
				" </div> \r\n" + 
				"</div>\r\n" + 
				"" + 
				"";
		List<String> jxNodes = getJXNodes(html, "//div[@class='layui-form-item']//*[@name]/@name");
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
