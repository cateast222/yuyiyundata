package com.yuyiyun.YYdata.modular.newspaper.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringEscapeUtils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author duhao
 * @since 2020-01-03
 */
@SuppressWarnings("deprecation")
@Data
@TableName("data_news")
public class DataNews implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "uuid",type = IdType.ID_WORKER)
	private Long uuid;

	/**
	 * 原名称
	 */
	@TableField("org_name")
	private String orgName;

	/**
	 * 中文名称
	 */
	@TableField("chs_name")
	private String chsName;

	/**
	 * 数据源
	 */
	@TableField("data_source")
	private Long dataSource;

	/**
	 * 电子报纸
	 */
	@TableField("data_newspaper")
	private Long dataNewspaper;
	
	/**
	 * 数据提供方
	 */
	@TableField("provider")
	private String provider;

	/**
	 * 新闻发布时间
	 */
	@TableField("pubtime")
	private Date pubtime;

	/**
	 * 新闻标题
	 */
	@TableField("title")
	private String title;

	/**
	 * 新闻源链接
	 */
	@TableField("url")
	private String url;

	/**
	 * 新闻前置标题
	 */
	@TableField("pretitle")
	private String pretitle;

	/**
	 * 新闻副标题
	 */
	@TableField("subtitle")
	private String subtitle;

	/**
	 * 新闻作者
	 */
	@TableField("author")
	private String author;

	/**
	 * 新闻编辑
	 */
	@TableField("editor")
	private String editor;

	/**
	 * 新闻描述
	 */
	@TableField("abstracts")
	private String abstracts;

	/**
	 * 新闻正文
	 */
	@TableField("content")
	private String content;

	/**
	 * 新闻内容
	 */
	@TableField("tag_content")
	private String tagContent;

	/**
	 * 新闻图片源链接
	 */
	@TableField("website_pictures")
	private String websitePictures;

	/**
	 * 新闻音频源链接
	 */
	@TableField("website_audios")
	private String websiteAudios;
	
	/**
	 * 新闻视频源链接
	 */
	@TableField("website_videos")
	private String websiteVideos;

	/**
	 * 新闻文件源链接
	 */
	@TableField("website_files")
	private String websiteFiles;
	
	/**
	 * 新闻图片描述
	 */
	@TableField("pictures_description")
	private String picturesDescription;

	/**
	 * 新闻语种
	 */
	@TableField("language")
	private String language;

	/**
	 * 新闻缩略图源链接
	 */
	@TableField("website_thumbnail")
	private String websiteThumbnail;

	/**
	 * 新闻PDF源链接
	 */
	@TableField("website_pdf")
	private String websitePdf;


	/**
	 * 新闻所属位置
	 */
	@TableField("ha")
	private String ha;

	/**
	 * 新闻所属频道
	 */
	@TableField("channel")
	private String channel;

	/**
	 * 新闻所属页码
	 */
	@TableField("number")
	private String number;

	/**
	 * 新闻所属版面
	 */
	@TableField("page")
	private String page;

	/**
	 * 新闻版面名称
	 */
	@TableField("page_name")
	private String pageName;

	/**
	 * 新闻频道排序
	 */
	@TableField("paper_count")
	private Integer paperCount;

	/**
	 * 新闻头版头条
	 */
	@TableField("front_page")
	private Integer frontPage;

	/**
	 * 新闻采集时间
	 */
	@TableField("insert_time")
	private Date insertTime;

	/**
	 * 新闻备注
	 */
	@TableField("remark")
	private String remark;

	/**
	 * 新闻状态（-2：系统测试数据；-1：测试数据；0：无；1：正常数据；2：已归档数据）
	 */
	@TableField("state")
	private String state;

	/**
	 * 创建者
	 */
	@TableField(value = "creator")
	private String creator;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新时间
	 */
	@TableField(value = "update_time", fill = FieldFill.UPDATE)
	private Date updateTime;
	
//	public void setContent(String content) {
//		this.content = content.replace("& ", "&");
//	}
	
	public void setTagContent(String tagContent) {
		tagContent = tagContent.replace("& ", "&");
		this.tagContent = StringEscapeUtils.unescapeHtml4(tagContent);
	}
}
