package com.yuyiyun.YYdata.modular.perfoapp.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * perfo_appraisal
 * 
 * @author: Duhao
 * @date 2020-07-07 14:47
 */
@Data
@TableName("perfo_appraisal")
public class PerfoAppraisalEntity {

	/** 绩效id */
	@TableId(value = "uuid", type = IdType.ID_WORKER)
	private Long uuid;
	/** 被考核人 */
	@TableField("the_person")
	private Long thePerson;

	/** 所属部门 */
	@TableField("department")
	private String department;

	/** 考核月份 */
	@TableField("ck_month")
	private String ckMonth;

	/** 直属上级 */
	@TableField("superior")
	private String superior;

	/** 考核日期 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField("ins_date")
	private Date insDate;

	/** 工作业绩 */
	@TableField("wk_perform")
	private String wkPerform;

	/** 工作能力 */
	@TableField("se_ability")
	private String seAbility;

	/** 工作态度 */
	@TableField("att_ward")
	private String attWard;

	/** 例外考核 */
	@TableField("exe_inspec")
	private String exeInspec;

	/** 出勤状况 */
	@TableField("att_stas")
	private String attStas;

	/** 总分 */
	@TableField("tatal")
	private Long tatal;

	/** 总评 */
	@TableField("overall")
	private String overall;

}
