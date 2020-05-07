package com.yuyiyun.YYdata.modular.datasource.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuyiyun.YYdata.core.util.HtmlTagUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 调取数据权限
 * </p>
 *
 * @author duhao
 * @since 2020-04-07
 */
@Data
@ApiModel(value = "ApiDataAuth",description = "调取数据权限实体")
@TableName("api_data_auth")
public class ApiDataAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "uuid", type = IdType.ID_WORKER)
    private Long uuid;

    /**
     * 调取者用户
     */
    @ApiModelProperty(value = "调取者用户")
    @TableField("sys_user")
    private Long sysUser;

    /**
     * 授权数据源
     */
    @ApiModelProperty(value = "授权数据源")
    @TableField("data_source")
    private Long dataSource;

    /**
     * 数据源中文名
     */
    @ApiModelProperty(value = "数据源中文名")
    @TableField("data_source_chsname")
    private String dataSourceChsName;
    
    /**
     * 有效期
     */
    @ApiModelProperty(value = "有效期")
    @TableField("validity")
    private Date validity;

    /**
     * 级别(备用)
     */
    @ApiModelProperty(value = "级别")
    @TableField("level")
    private Integer level;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    /**
     * 状态
     */
    @ApiModelProperty(value = "")
    @TableField("state")
    private String state;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @TableField("creator")
    private String creator;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public void setSysUser(Long sysUser) {
		this.sysUser = sysUser;
	}

	public void setDataSource(Long dataSource) {
		this.dataSource = dataSource;
	}

	public void setDataSourceChsName(String dataSourceChsName) {
		this.dataSourceChsName = HtmlTagUtil.convertTag(dataSourceChsName);
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void setRemark(String remark) {
		this.remark = HtmlTagUtil.convertTag(remark);
	}

	public void setState(String state) {
		this.state = HtmlTagUtil.convertTag(state);
	}

	public void setCreator(String creator) {
		this.creator = HtmlTagUtil.convertTag(creator);
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}