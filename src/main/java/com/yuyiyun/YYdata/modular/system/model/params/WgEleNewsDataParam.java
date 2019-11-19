package com.yuyiyun.YYdata.modular.system.model.params;

import java.io.Serializable;
import java.util.Date;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;

/**
 * 闻歌电子报纸新闻类
 * 
 * @describe 关联wg_ele_news_data
 * @author duhao
 * @date 2019年9月12日下午4:33:49
 */
public class WgEleNewsDataParam implements Serializable, BaseValidatingParam {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String dsiUuid;
	private String dsiName;
	private String title;
	private String url;
	private String pretitlt;
	private String subtitle;
	private Date pubtime;
	private String author;
	private String editor;
	private String abstracts;
	private String content;
	private String tagContent;
	private String websitePictures;
	private String localPictures;
	private String picturesDescription;
	private String language;
	private String websiteThumbnail;
	private String localThumbnail;
	private String websitePdf;
	private String localPdf;
	private String ha;
	private String channel;
	private String number;
	private String page;
	private String pageName;
	private Integer paperCount;
	private Integer frontPage;
	private Date insertTime;
	private String remark;
	private Integer state;
	private String creator;
	private Date createTime;
	private Date updateTime;
	/**
	 * 查询条件
	 */
	private String condition;
	public Integer getPaperCount() {
		return paperCount;
	}

	public void setPaperCount(Integer paperCount) {
		this.paperCount = paperCount;
	}

	public Integer getFrontPage() {
		return frontPage;
	}

	public void setFrontPage(Integer frontPage) {
		this.frontPage = frontPage;
	}


	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDsiUuid() {
		return dsiUuid;
	}

	public void setDsiUuid(String dsiUuid) {
		this.dsiUuid = dsiUuid;
	}

	public String getDsiName() {
		return dsiName;
	}

	public void setDsiName(String dsiName) {
		this.dsiName = dsiName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPretitlt() {
		return pretitlt;
	}

	public void setPretitlt(String pretitlt) {
		this.pretitlt = pretitlt;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Date getPubtime() {
		return pubtime;
	}

	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getAbstracts() {
		return abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTagContent() {
		return tagContent;
	}

	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}

	public String getWebsitePictures() {
		return websitePictures;
	}

	public void setWebsitePictures(String websitePictures) {
		this.websitePictures = websitePictures;
	}

	public String getLocalPictures() {
		return localPictures;
	}

	public void setLocalPictures(String localPictures) {
		this.localPictures = localPictures;
	}

	public String getPicturesDescription() {
		return picturesDescription;
	}

	public void setPicturesDescription(String picturesDescription) {
		this.picturesDescription = picturesDescription;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getWebsiteThumbnail() {
		return websiteThumbnail;
	}

	public void setWebsiteThumbnail(String websiteThumbnail) {
		this.websiteThumbnail = websiteThumbnail;
	}

	public String getLocalThumbnail() {
		return localThumbnail;
	}

	public void setLocalThumbnail(String localThumbnail) {
		this.localThumbnail = localThumbnail;
	}

	public String getWebsitePdf() {
		return websitePdf;
	}

	public void setWebsitePdf(String websitePdf) {
		this.websitePdf = websitePdf;
	}

	public String getLocalPdf() {
		return localPdf;
	}

	public void setLocalPdf(String localPdf) {
		this.localPdf = localPdf;
	}

	public String getHa() {
		return ha;
	}

	public void setHa(String ha) {
		this.ha = ha;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "WgEleNewsDataParam [uuid=" + uuid + ", dsiUuid=" + dsiUuid + ", dsiName=" + dsiName + ", title=" + title
				+ ", url=" + url + ", pretitlt=" + pretitlt + ", subtitle=" + subtitle + ", pubtime=" + pubtime
				+ ", author=" + author + ", editor=" + editor + ", abstracts=" + abstracts + ", websitePictures="
				+ websitePictures + ", localPictures=" + localPictures + ", picturesDescription=" + picturesDescription
				+ ", language=" + language + ", websiteThumbnail=" + websiteThumbnail + ", localThumbnail="
				+ localThumbnail + ", websitePdf=" + websitePdf + ", localPdf=" + localPdf + ", ha=" + ha + ", channel="
				+ channel + ", number=" + number + ", page=" + page + ", pageName=" + pageName + ", insertTime="
				+ insertTime + ", remark=" + remark + ", state=" + state + ", creator=" + creator + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", condition=" + condition + "]";
	}

	public String allString() {
		return "WgEleNewsDataParam [uuid=" + uuid + ", dsiUuid=" + dsiUuid + ", dsiName=" + dsiName + ", title=" + title
				+ ", url=" + url + ", pretitlt=" + pretitlt + ", subtitle=" + subtitle + ", pubtime=" + pubtime
				+ ", author=" + author + ", editor=" + editor + ", abstracts=" + abstracts + ", content=" + content
				+ ", tagContent=" + tagContent + ", websitePictures=" + websitePictures + ", localPictures="
				+ localPictures + ", picturesDescription=" + picturesDescription + ", language=" + language
				+ ", websiteThumbnail=" + websiteThumbnail + ", localThumbnail=" + localThumbnail + ", websitePdf="
				+ websitePdf + ", localPdf=" + localPdf + ", ha=" + ha + ", channel=" + channel + ", number=" + number
				+ ", page=" + page + ", pageName=" + pageName + ", insertTime=" + insertTime + ", remark=" + remark
				+ ", state=" + state + ", creator=" + creator + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", condition=" + condition + "]";
	}

	@Override
	public String checkParam() {
		return null;
	}

}
