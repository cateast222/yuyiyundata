package com.yuyiyun.YYdata.modular.datasource.entity;

import java.io.Serializable;
import java.util.Date;

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
@Data
@TableName("data_source")
public class DataSource implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "uuid",type = IdType.ID_WORKER)
	private Long uuid;

	/**
	 * 中文名称
	 */
	@TableField("chs_name")
	private String chsName;

	/**
	 * 原名称
	 */
	@TableField("org_name")
	private String orgName;

	/**
	 * 网站地址
	 */
	@TableField("website_url")
	private String websiteUrl;

	/**
	 * 网站名称
	 */
	@TableField("website_name")
	private String websiteName;

	/**
	 * 所属平台
	 */
	@TableField("platform")
	private Integer platform;

	/**
	 * 国家
	 */
	@TableField("country")
	private String country;

	/**
	 * 地址（省市县）
	 */
	@TableField("region")
	private String region;

	/**
	 * 语种
	 */
	@TableField("language")
	private String language;

	/**
	 * 编码
	 */
	@TableField("encoded")
	private String encoded;

	/**
	 * 所属境区
	 */
	@TableField("proxy")
	private Integer proxy;

	/**
	 * 备注
	 */
	@TableField("remark")
	private String remark;

	/**
	 * 状态：-1.弃用、0.测试数据、1.启用、2.配置、3.测试完成、4.测试通过
	 */
	@TableField("state")
	private Integer state;

	/**
	 * 创建者
	 */
	@TableField("creator")
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

}
