package com.yuyiyun.YYdata.modular.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yuyiyun.YYdata.core.util.HtmlTagUtil;
import com.yuyiyun.YYdata.modular.system.entity.DataConfigInfo;

public class    implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dsiUuid;
	private String key;
	private String listUrl;
	private Integer requestType = -1;
	private String header;
	private Integer bodyType = -1;
	private String body;
	private String orgDataDec;
	private String dataExtRul;
	private String level = "-1";
	private String dataCleanDec;
	private String dataCleanReg;
	private String preData;
	private String posreData;
	private Integer proxy = -1;
	private Integer retDataType = -1;
	private String dataLink;
	private String value;

	public DataConfigdto(String dsiUuid, String key) {
		this.dsiUuid = dsiUuid;
		this.key = key;
	}

	public DataConfigdto() {
	}

	public List<DataConfigInfo> get() {
		List<DataConfigInfo> list = new ArrayList<DataConfigInfo>();
		if (this.body != null && !this.body.equals("")) {
			list.add(new DataConfigInfo(this.key, this.key + "-Body", this.body));
		}
		if (this.bodyType != null && !this.bodyType.equals(-1)) {
			list.add(new DataConfigInfo(this.key, this.key + "-BodyType", this.bodyType.toString()));
		}
		if (this.dataCleanDec != null && !this.dataCleanDec.equals("")) {
			list.add(new DataConfigInfo(this.key, this.key + "-DataCleanDec", this.dataCleanDec));
		}
		if (this.dataCleanReg != null && !this.dataCleanReg.equals("")) {
			list.add(new DataConfigInfo(this.key, this.key + "-DataCleanReg", this.dataCleanReg));
		}
		if (this.dataExtRul != null && !this.dataExtRul.equals("")) {
			String lvl = "";
			if (this.level != null && !this.level.equals("-1")) {
				lvl = this.level;
			}
			list.add(new DataConfigInfo(this.key, this.key + "-DataExtRul", lvl + this.dataExtRul));
		}
		if (this.dataLink != null && !this.dataLink.equals("")) {
			list.add(new DataConfigInfo(this.key, this.key + "-DataLink", this.dataLink));
		}
		if (this.header != null && !this.header.equals("")) {
			list.add(new DataConfigInfo(this.key, this.key + "-Header", this.header));
		}
		if (this.listUrl != null && !this.listUrl.equals("")) {
			list.add(new DataConfigInfo(this.key, this.key + "-ListUrl", this.listUrl));
		}
		if (this.orgDataDec != null && !this.orgDataDec.equals("")) {
			list.add(new DataConfigInfo(this.key, this.key + "-OrgDataDec", this.orgDataDec));
		}
		if (this.posreData != null && !this.posreData.equals("")) {
			list.add(new DataConfigInfo(this.key, this.key + "-PosreData", this.posreData));
		}
		if (this.preData != null && !this.preData.equals("")) {
			list.add(new DataConfigInfo(this.key, this.key + "-PreData", this.preData));
		}
		if (this.proxy != null && !this.proxy.equals(-1)) {
			list.add(new DataConfigInfo(this.key, this.key + "-Proxy", this.proxy.toString()));
		}
		if (this.requestType != null && !this.requestType.equals(-1)) {
			list.add(new DataConfigInfo(this.key, this.key + "-RequestType", this.requestType.toString()));
		}
		if (this.retDataType != null && !this.retDataType.equals(-1)) {
			list.add(new DataConfigInfo(this.key, this.key + "-RetDataType", this.retDataType.toString()));
		}
		if (this.value != null && !this.value.equals("")) {
			if (this.key.contentEquals("lastcollectiontime")) {
				list.add(new DataConfigInfo(this.key, "LastCollectionTime", this.value));
			}
			if (this.key.contentEquals("collectionfrequency")) {
				list.add(new DataConfigInfo(this.key, "CollectionFrequency", this.value));
			}
		}
		return list;
	}

	public void set(List<DataConfigInfo> list) {
		for (DataConfigInfo info : list) {
			if (info.getValue() != null) {
				if (info.getDdiValue().endsWith("ListUrl")) {
					setListUrl(info.getValue());
				}
				if (info.getDdiValue().endsWith("RequestType")) {
					setRequestType(info.getValue().equals("") ? -1 : new Integer(info.getValue()));
				}
				if (info.getDdiValue().endsWith("Header")) {
					setHeader(info.getValue());
				}
				if (info.getDdiValue().endsWith("BodyType")) {
					setBodyType(info.getValue().equals("") ? -1 : new Integer(info.getValue()));
				}
				if (info.getDdiValue().endsWith("Body")) {
					setBody(info.getValue());
				}
				if (info.getDdiValue().endsWith("OrgDataDec")) {
					setOrgDataDec(info.getValue());
				}
				if (info.getDdiValue().endsWith("DataExtRul")) {
					String dataExtRul = info.getValue();
					String lvl = dataExtRul.startsWith("news.") ? "news."
							: dataExtRul.startsWith("newslist_List.") ? "newslist_List."
									: dataExtRul.startsWith("newslist_Page.") ? "newslist_Page."
											: dataExtRul.startsWith("channel_List.") ? "channel_List."
													: dataExtRul.startsWith("channel_Page.") ? "channel_Page." : "-1";
					setLevel(lvl);
					setDataExtRul(dataExtRul.replace(lvl, ""));
				}
				if (info.getDdiValue().endsWith("DataCleanDec")) {
					setDataCleanDec(info.getValue());
				}
				if (info.getDdiValue().endsWith("DataCleanReg")) {
					setDataCleanReg(info.getValue());
				}
				if (info.getDdiValue().endsWith("PreData")) {
					setPreData(info.getValue());
				}
				if (info.getDdiValue().endsWith("PosreData")) {
					setPosreData(info.getValue());
				}
				if (info.getDdiValue().endsWith("Proxy")) {
					setProxy(info.getValue().equals("") ? -1 : new Integer(info.getValue()));
				}
				if (info.getDdiValue().endsWith("RetDataType")) {
					setRetDataType(info.getValue().equals("") ? -1 : new Integer(info.getValue()));
				}
				if (info.getDdiValue().endsWith("DataLink")) {
					setDataLink(info.getValue());
				}
				if (info.getDdiValue().equals("CollectionFrequency")) {
					setValue(info.getValue());
				}
				if (info.getDdiValue().equals("LastCollectionTime")) {
					setValue(info.getValue());
				}
			}
		}
	}

	public String getDsiUuid() {
		return dsiUuid;
	}

	public void setDsiUuid(String dsiUuid) {
		this.dsiUuid = dsiUuid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getListUrl() {
		return listUrl;
	}

	public void setListUrl(String listUrl) {
		this.listUrl = HtmlTagUtil.convertTag(listUrl);
	}

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = HtmlTagUtil.convertTag(header);
	}

	public Integer getBodyType() {
		return bodyType;
	}

	public void setBodyType(Integer bodyType) {
		this.bodyType = bodyType;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = HtmlTagUtil.convertTag(body);
	}

	public String getOrgDataDec() {
		return orgDataDec;
	}

	public void setOrgDataDec(String orgDataDec) {
		this.orgDataDec = HtmlTagUtil.convertTag(orgDataDec);
	}

	public String getDataExtRul() {
		return dataExtRul;
	}

	public void setDataExtRul(String dataExtRul) {
		this.dataExtRul = HtmlTagUtil.convertTag(dataExtRul);
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = HtmlTagUtil.convertTag(level);
	}

	public String getDataCleanDec() {
		return dataCleanDec;
	}

	public void setDataCleanDec(String dataCleanDec) {
		this.dataCleanDec = HtmlTagUtil.convertTag(dataCleanDec);
	}

	public String getDataCleanReg() {
		return dataCleanReg;
	}

	public void setDataCleanReg(String dataCleanReg) {
		this.dataCleanReg = HtmlTagUtil.convertTag(dataCleanReg);
	}

	public String getPreData() {
		return preData;
	}

	public void setPreData(String preData) {
		this.preData = HtmlTagUtil.convertTag(preData);
	}

	public String getPosreData() {
		return posreData;
	}

	public void setPosreData(String posreData) {
		this.posreData = HtmlTagUtil.convertTag(posreData);
	}

	public Integer getProxy() {
		return proxy;
	}

	public void setProxy(Integer proxy) {
		this.proxy = proxy;
	}

	public Integer getRetDataType() {
		return retDataType;
	}

	public void setRetDataType(Integer retDataType) {
		this.retDataType = retDataType;
	}

	public String getDataLink() {
		return dataLink;
	}

	public void setDataLink(String dataLink) {
		this.dataLink = HtmlTagUtil.convertTag(dataLink);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = HtmlTagUtil.convertTag(value);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DataConfigdto [dsiUuid=" + dsiUuid + ", key=" + key + ", listUrl=" + listUrl + ", requestType="
				+ requestType + ", header=" + header + ", bodyType=" + bodyType + ", body=" + body + ", orgDataDec="
				+ orgDataDec + ", dataExtRul=" + dataExtRul + ", level=" + level + ", dataCleanDec=" + dataCleanDec
				+ ", dataCleanReg=" + dataCleanReg + ", preData=" + preData + ", posreData=" + posreData + ", proxy="
				+ proxy + ", retDataType=" + retDataType + ", dataLink=" + dataLink + ", value=" + value + "]";
	}

	

}
