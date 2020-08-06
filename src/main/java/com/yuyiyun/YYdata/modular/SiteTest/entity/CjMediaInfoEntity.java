package com.yuyiyun.YYdata.modular.SiteTest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
@Data
public class CjMediaInfoEntity {
	@TableId(value = "media_id", type = IdType.ID_WORKER)
	@TableField("media_id")
	private Long mediaid;
	@TableField("mname")
	private String mname;
	@TableField("dom_for")
	private String domfor;
	@TableField("country_code")
	private String countrycode;
	@TableField("province_code")
	private String provincecode;
	@TableField("city_code")
	private String citycode;
	@TableField("area_code")
	private String areacode;
	@TableField("label")
	private String label;
	@TableField("state")
	private String state;
}
