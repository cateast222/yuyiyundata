package com.yuyiyun.YYdata.modular.system.model.params;

import java.io.Serializable;
import java.util.Date;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 闻歌电子报纸新闻类
 * 
 * @describe 关联wg_ele_news_data
 * @author duhao
 * @date 2019年9月12日下午4:33:49
 */
@Data
@ApiModel(value = "WgEleNewsDataParam", description = "闻歌电子报纸新闻数据输入实体")
public class WgEleNewsDataParam implements Serializable, BaseValidatingParam {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "UUID")
	private String uuid;
	@ApiModelProperty(value = "新闻所属数据源")
	private String dsiUuid;
	@ApiModelProperty(value = "新闻所属数据源名称")
	private String dsiName;
	@ApiModelProperty(value = "新闻标题")
	private String title;
	@ApiModelProperty(value = "新闻源链接")
	private String url;
	@ApiModelProperty(value = "新闻前置标题")
	private String pretitlt;
	@ApiModelProperty(value = "新闻副标题")
	private String subtitle;
	@ApiModelProperty(value = "新闻发布时间")
	private Date pubtime;
	@ApiModelProperty(value = "新闻作者")
	private String author;
	@ApiModelProperty(value = "新闻编辑")
	private String editor;
	@ApiModelProperty(value = "新闻描述")
	private String abstracts;
	@ApiModelProperty(value = "新闻正文")
	private String content;
	@ApiModelProperty(value = "新闻内容")
	private String tagContent;
	@ApiModelProperty(value = "新闻图片源链接")
	private String websitePictures;
	@ApiModelProperty(value = "新闻图片本地链接")
	private String localPictures;
	@ApiModelProperty(value = "新闻图片描述")
	private String picturesDescription;
	@ApiModelProperty(value = "新闻语种")
	private String language;
	@ApiModelProperty(value = "新闻缩略图源链接")
	private String websiteThumbnail;
	@ApiModelProperty(value = "新闻缩略图本地链接")
	private String localThumbnail;
	@ApiModelProperty(value = "新闻PDF源链接")
	private String websitePdf;
	@ApiModelProperty(value = "新闻PDF本地链接")
	private String localPdf;
	@ApiModelProperty(value = "新闻所属位置")
	private String ha;
	@ApiModelProperty(value = "新闻所属频道")
	private String channel;
	@ApiModelProperty(value = "新闻所属页码")
	private String number;
	@ApiModelProperty(value = "新闻所属版面")
	private String page;
	@ApiModelProperty(value = "新闻版面名称")
	private String pageName;
	@ApiModelProperty(value = "新闻频道排序")
	private Integer paperCount;
	@ApiModelProperty(value = "新闻头版头条")
	private Integer frontPage;
	@ApiModelProperty(value = "新闻采集时间")
	private Date insertTime;
	@ApiModelProperty(value = "新闻备注")
	private String remark;
	@ApiModelProperty(value = "新闻状态")
	private Integer state;
	@ApiModelProperty(value = "创建者")
	private String creator;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	/**
	 * 查询条件
	 */
	@ApiModelProperty(value = "查询条件")
	private String condition;

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
