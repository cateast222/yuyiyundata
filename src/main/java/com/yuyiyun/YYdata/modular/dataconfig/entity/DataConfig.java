package com.yuyiyun.YYdata.modular.dataconfig.entity;

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
 * 数据配置表
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
@Data
@TableName("data_config")
public class DataConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId(value = "uuid", type = IdType.ID_WORKER)
    private Long uuid;

    /**
     * 数据源
     */
    @TableField("data_source")
    private Long dataSource;

    /**
     * 数据字典
     */
    @TableField("data_dict")
    private Long dataDict;

    /**
     * 配置K值
     */
    @TableField("key")
    private String key;

    /**
     * 配置V值
     */
    @TableField("value")
    private String value;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 描述
     */
    @TableField("summary")
    private String summary;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态
     */
    @TableField("state")
    private String state;

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
