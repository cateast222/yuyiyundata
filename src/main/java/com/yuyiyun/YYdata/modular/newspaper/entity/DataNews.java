package com.yuyiyun.YYdata.modular.newspaper.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Data
@TableName("data_news")
public class DataNews implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String uuid;

	/**
	 * 原名称
	 */
	private String orgName;

	/**
	 * 中文名称
	 */
	private String chsName;

	/**
	 * 数据源
	 */
	private String dataSource;

	/**
	 * 数据源
	 */
	private String dataNewspaper;

	/**
	 * 新闻发布时间
	 */
	private LocalDateTime pubtime;

	/**
	 * 新闻标题
	 */
	private String title;

	/**
	 * 新闻源链接
	 */
	private String url;

	/**
	 * 新闻前置标题
	 */
	private String pretitlt;

	/**
	 * 新闻副标题
	 */
	private String subtitle;

	/**
	 * 新闻作者
	 */
	private String author;

	/**
	 * 新闻编辑
	 */
	private String editor;

	/**
	 * 新闻描述
	 */
	private String abstracts;

	/**
	 * 新闻正文
	 */
	private String content;

	/**
	 * 新闻内容
	 */
	private String tagContent;

	/**
	 * 新闻图片源链接
	 */
	private String websitePictures;

	/**
	 * 新闻图片本地链接
	 */
	private String localPictures;

	/**
	 * 新闻图片描述
	 */
	private String picturesDescription;

	/**
	 * 新闻语种
	 */
	private String language;

	/**
	 * 新闻缩略图源链接
	 */
	private String websiteThumbnail;

	/**
	 * 新闻缩略图本地链接
	 */
	private String localThumbnail;

	/**
	 * 新闻PDF源链接
	 */
	private String websitePdf;

	/**
	 * 新闻PDF本地链接
	 */
	private String localPdf;

	/**
	 * 新闻所属位置
	 */
	private String ha;

	/**
	 * 新闻所属频道
	 */
	private String channel;

	/**
	 * 新闻所属页码
	 */
	private String number;

	/**
	 * 新闻所属版面
	 */
	private String page;

	/**
	 * 新闻版面名称
	 */
	private String pageName;

	/**
	 * 新闻频道排序
	 */
	private Integer paperCount;

	/**
	 * 新闻头版头条
	 */
	private Integer frontPage;

	/**
	 * 新闻采集时间
	 */
	private LocalDateTime insertTime;

	/**
	 * 新闻备注
	 */
	private String remark;

	/**
	 * 新闻状态（-2：系统测试数据；-1：测试数据；0：无；1：正常数据；2：已归档数据）
	 */
	private Integer state;

	/**
	 * 创建者
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;

}
