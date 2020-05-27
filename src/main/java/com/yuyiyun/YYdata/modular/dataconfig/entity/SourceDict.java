package com.yuyiyun.YYdata.modular.dataconfig.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SourceDict", description = "数据源字典关联实体")
@TableName("rel_source_dict")
public class SourceDict implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * uuid
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "uuid", type = IdType.ID_WORKER)
	private Long uuid;

	/**
	 * 数据源主键
	 */
	@ApiModelProperty(value = "数据源主键")
	@TableField("data_source")
	private Long dataSource;

	/**
	 * 数据字典主键
	 */
	@ApiModelProperty(value = "数据字典主键")
	@TableField("data_dict")
	private Long dataDict;
}
