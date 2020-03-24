package com.yuyiyun.YYdata.core.upload;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuyiyun.YYdata.core.util.ApiHttpClientUtil;
import com.yuyiyun.YYdata.core.util.DateTimeUtil;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNews;

public class DamsApiUpload {
	public static final String DAMS_API_URL = "http://www.yuyiai.com:8000/";
	public static final String DAMS_CLIENT_ID = "zkwg";
	public static final String DAMS_CLIENT_SECRET = "zkwg";
	private String username;
	private String password;
	@SuppressWarnings("unused")
	private String accesstoken;
	private String refreshtoken;

	public DamsApiUpload(String username, String password) {
		this.username = username;
		this.password = password;
		this.accesstoken = getDAMSToken();
	}

	private String getDAMSToken() {
		String url = DAMS_API_URL + "api/oauth/token";
		Map<String, String> contentMap = new HashMap<String, String>();
		contentMap.put("grant_type", "password");
		contentMap.put("username", username);
		contentMap.put("password", password);
		contentMap.put("client_id", DAMS_CLIENT_ID);
		contentMap.put("client_secret", DAMS_CLIENT_SECRET);
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		String ret = ApiHttpClientUtil.doPost(url, headerMap, contentMap);
		JSONObject data = JSON.parseObject(ret);
		if (null == data) {
			return null;
		}
		this.refreshtoken = data.getString("refresh_token");
		return data.getString("access_token");
	}

	private String getDAMSRefreshToken(String refreshToken) {
		String url = DAMS_API_URL + "api/oauth/token";
		Map<String, String> contentMap = new HashMap<String, String>();
		contentMap.put("grant_type", "refresh_token");
		contentMap.put("refresh_token", refreshToken);
		contentMap.put("client_id", DAMS_CLIENT_ID);
		contentMap.put("client_secret", DAMS_CLIENT_SECRET);
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		String ret = ApiHttpClientUtil.doPost(url, headerMap, contentMap);
		JSONObject data = JSON.parseObject(ret);
		if (null == data) {
			return null;
		}
		this.refreshtoken = data.getString("refresh_token");
		return data.getString("access_token");
	}

	/**
	 * 
	 * @describe 获取DAMS用户信息
	 * @author duhao
	 * @date 2019年9月24日下午4:42:30
	 * @param UserName 用户名称
	 * @return
	 */
	public JSONObject getDAMSUser(String UserName) {
		String accessToken = getDAMSRefreshToken(refreshtoken);
		String url = DAMS_API_URL + "api/services/app/user/GetUser";
		Map<String, String> contentMap = new HashMap<String, String>();
		contentMap.put("UserName", UserName);
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Authorization", "Bearer " + accessToken);
		String ret = ApiHttpClientUtil.doGet(url, headerMap, contentMap);
		JSONObject data = JSON.parseObject(ret);
		if (data.getBoolean("success")) {
			return data.getJSONObject("result");
		}
		return null;
	}

	/**
	 * 
	 * @describe 添加闻歌电子报纸新闻信息
	 * @author duhao
	 * @date 2019年9月24日下午4:42:20
	 * @param wg_Ele_News_Data 新闻信息
	 * @return
	 */
	public JSONObject createWg_Ele_News_Data(Map<String, String> wg_Ele_News_Data) {
		String accessToken = getDAMSRefreshToken(refreshtoken);
		String url = DAMS_API_URL + "api/services/app/wg_Ele_News_Data/CreateWg_Ele_News_Data";
		Map<String, String> contentMap = wg_Ele_News_Data;
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Authorization", "Bearer " + accessToken);
		String ret = ApiHttpClientUtil.doPost(url, headerMap, contentMap);
		JSONObject data = JSON.parseObject(ret);
		if (data != null && data.getBoolean("success")) {
			return data.getJSONObject("result");
		}
		return null;
	}

	/**
	 * 
	 * @describe 数据上线至DAMS
	 * @author duhao
	 * @date 2019年9月24日下午4:58:24
	 */
	public static boolean dataOnLineToDAMS(DataNews dataNews) {
		dataNews.setState("-1");
		try {
			Map<String, String> wg_Ele_News_Data = dataNews2Wg_Ele_News_Data(dataNews);
			new DamsApiUpload("dh", "321").createWg_Ele_News_Data(wg_Ele_News_Data);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static Map<String, String> dataNews2Wg_Ele_News_Data(DataNews dataNews) {
		Map<String, String> wg_Ele_News_Data = new HashMap<String, String>();
		wg_Ele_News_Data.put("uuid", dataNews.getUuid().toString());
		wg_Ele_News_Data.put("paper_Id", dataNews.getDataSource().toString());
		wg_Ele_News_Data.put("site", dataNews.getChsName());
		wg_Ele_News_Data.put("title", dataNews.getTitle());
		wg_Ele_News_Data.put("source_Url", dataNews.getUrl());
		wg_Ele_News_Data.put("preTitle", dataNews.getPretitle());
		wg_Ele_News_Data.put("subTitle", dataNews.getSubtitle());
		wg_Ele_News_Data.put("pubTime", DateTimeUtil.dateToString(dataNews.getPubtime()));
		wg_Ele_News_Data.put("author", dataNews.getAuthor());
		wg_Ele_News_Data.put("editor", dataNews.getEditor());
		wg_Ele_News_Data.put("abstract", dataNews.getAbstracts());
		wg_Ele_News_Data.put("content", dataNews.getContent());
		wg_Ele_News_Data.put("tag_Content", dataNews.getTagContent());
		wg_Ele_News_Data.put("paper_Picture", dataNews.getWebsitePictures());
//			wg_Ele_News_Data.put("local_Pictures", dataNews.getLocalPictures());
		wg_Ele_News_Data.put("pictures_Description", dataNews.getPicturesDescription());
		wg_Ele_News_Data.put("language", dataNews.getLanguage());
		wg_Ele_News_Data.put("layoutSource", dataNews.getWebsiteThumbnail());
//			wg_Ele_News_Data.put("local_Thumbnail", dataNews.getLocalThumbnail());
		wg_Ele_News_Data.put("pdf_Url", dataNews.getWebsitePdf());
//			wg_Ele_News_Data.put("local_Pdf", dataNews.getLocalPdf());
		wg_Ele_News_Data.put("ha", dataNews.getHa());
		wg_Ele_News_Data.put("channel", dataNews.getChannel());
		wg_Ele_News_Data.put("revision", dataNews.getNumber());
		wg_Ele_News_Data.put("page", dataNews.getPage());
		wg_Ele_News_Data.put("edition_Name", dataNews.getPageName());
		wg_Ele_News_Data.put("paper_Count", dataNews.getPaperCount().toString());
		wg_Ele_News_Data.put("front_Page", dataNews.getFrontPage().toString());
		wg_Ele_News_Data.put("insert_Time", DateTimeUtil.dateToString(dataNews.getInsertTime()));
		wg_Ele_News_Data.put("remark", dataNews.getRemark());
		wg_Ele_News_Data.put("state", dataNews.getState());
		wg_Ele_News_Data.put("creator", dataNews.getCreator());
		wg_Ele_News_Data.put("create_Time", DateTimeUtil.dateToString(dataNews.getCreateTime()));
		wg_Ele_News_Data.put("update_Time", DateTimeUtil.dateToString(new Date()));
		wg_Ele_News_Data.put("id", "0");
		return wg_Ele_News_Data;
	}

}
