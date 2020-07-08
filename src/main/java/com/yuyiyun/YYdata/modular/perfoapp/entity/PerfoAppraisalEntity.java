package com.yuyiyun.YYdata.modular.perfoapp.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import javax.validation.constraints.NotBlank;
import com.baomidou.mybatisplus.annotation.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * perfo_appraisal
 * @author: Duhao
 * @date 2020-07-07 14:47
 */

@TableName("perfo_appraisal")
public class PerfoAppraisalEntity {

    /** 绩效id */ 

	@TableId(type = IdType.AUTO)
    private Long PERFOID;
    /** 被考核人 */
    private Long thePerson;

    /** 所属部门 */
    private String DEPARTMENT;

    /** 考核月份 */
    private String ckMonth;

    /** 直属上级 */
    private String SUPERIOR;

    /** 考核日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date insDate;

    /** 工作业绩 */
    private String wkPerform;

    /** 工作能力 */
    private String seAbility;

    /** 工作态度 */
    private String attWard;

    /** 例外考核 */
    private String exeInspec;

    /** 出勤状况 */
    private String attStas;

    /** 总分 */
    private Long TATAL;

    /** 总评 */
    private String OVERALL;

}
