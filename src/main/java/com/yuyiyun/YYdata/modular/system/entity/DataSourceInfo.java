package com.yuyiyun.YYdata.modular.system.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 数据配置信息类
 * 
 * @describe 关联data_config_info
 * @author duhao
 * @date 2019年9月12日下午4:34:06
 */
@Data
@TableName(value = "data_source_info")
public class DataSourceInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(value = "uuid", type = IdType.ID_WORKER)
	private String uuid;
	@TableField("name")
	private String name;
	@TableField("website_url")
	private String websiteUrl;
	@TableField("website_name")
	private String websiteName;
	@TableField("platform")
	private Integer platform;
	@TableField("country")
	private String country;
	@TableField("region")
	private String region;
	@TableField("proxy")
	private Integer proxy;
	@TableField("language")
	private String language;
	@TableField("remark")
	private String remark;
	@TableField("state")
	private Integer state;
	@TableField(value = "creator",fill = FieldFill.INSERT)
	private String creator;
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;
	@TableField(value = "update_time", fill = FieldFill.UPDATE)
	private Date updateTime;

	@Override
	public String toString() {
		return "DataSourceInfo [uuid=" + uuid + ", name=" + name + ", websiteUrl=" + websiteUrl + ", websiteName="
				+ websiteName + ", platform=" + platform + ", country=" + country + ", region=" + region + ", proxy="
				+ proxy + ", language=" + language + ", remark=" + remark + ", state=" + state + ", creator=" + creator
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
}
