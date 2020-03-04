package com.yuyiyun.YYdata.modular.newspaper.model.param;

import java.io.Serializable;
import java.util.Date;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DataNewsParam", description = "电子报纸输入实体")
public class DataNewsParam implements Serializable, BaseValidatingParam {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long uuid;

	@ApiModelProperty(value = "原名称")
	private String orgName;

	@ApiModelProperty(value = "中文名称")
	private String chsName;

	@ApiModelProperty(value = "数据源")
	private Long dataSource;

	@ApiModelProperty(value = "电子报纸")
	private Long dataNewspaper;
	
	@ApiModelProperty(value = "数据提供方")
	private String provider;

	@ApiModelProperty(value = "发布时间")
	private Date pubtime;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "源链接")
	private String url;

	@ApiModelProperty(value = "前置标题")
	private String pretitle;

	@ApiModelProperty(value = "副标题")
	private String subtitle;

	@ApiModelProperty(value = "作者")
	private String author;

	@ApiModelProperty(value = "编辑")
	private String editor;

	@ApiModelProperty(value = "描述")
	private String abstracts;

	@ApiModelProperty(value = "正文")
	private String content;

	@ApiModelProperty(value = "内容")
	private String tagContent;

	@ApiModelProperty(value = "图片源链接")
	private String websitePictures;

	@ApiModelProperty(value = "音频源链接")
	private String websiteAudios;
	
	@ApiModelProperty(value = "视频源链接")
	private String websiteVideos;

	@ApiModelProperty(value = "文件源链接")
	private String websiteFiles;
	
	@ApiModelProperty(value = "图片描述")
	private String picturesDescription;

	@ApiModelProperty(value = "语种")
	private String language;

	@ApiModelProperty(value = "缩略图源链接")
	private String websiteThumbnail;

	@ApiModelProperty(value = "PDF源链接")
	private String websitePdf;

	@ApiModelProperty(value = "所属位置")
	private String ha;

	@ApiModelProperty(value = "所属频道")
	private String channel;

	@ApiModelProperty(value = "所属页码")
	private String number;

	@ApiModelProperty(value = "所属版面")
	private String page;

	@ApiModelProperty(value = "版面名称")
	private String pageName;

	@ApiModelProperty(value = "频道排序")
	private Integer paperCount;

	@ApiModelProperty(value = "头版头条")
	private Integer frontPage;

	@ApiModelProperty(value = "采集时间")
	private Date insertTime;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "状态")
	private String state;

	@ApiModelProperty(value = "创建者")
	private String creator;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	
	@ApiModelProperty(value = "查询条件")
	private String condition;

	@Override
	public String checkParam() {
		return null;
	}

}
