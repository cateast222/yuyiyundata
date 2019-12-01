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
 * @describe 关联data_source_info
 * @author duhao
 * @date 2019年9月12日下午4:34:34
 */
@Data
@TableName(value = "data_config_info")
public class DataConfigInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(value = "uuid", type = IdType.ID_WORKER)
	private String uuid;
	@TableField("dsi_uuid")
	private String dsiUuid;
	@TableField("ddi_key")
	private String ddiKey;
	@TableField("ddi_value")
	private String ddiValue;
	@TableField("`value`")
	private String value;
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
	
	

	public DataConfigInfo(String ddiKey, String ddiValue, String value) {
		this.ddiKey = ddiKey;
		this.ddiValue = ddiValue;
		this.value = value;
	}

	public DataConfigInfo() {
	}

}
