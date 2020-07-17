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
	private String uuid;
	/** 被考核人 */
	@TableField("the_person")
	private String thePerson;

	@Override
	public String toString() {
		return "PerfoAppraisalEntity{" +
				"uuid=" + uuid +
				", thePerson='" + thePerson + '\'' +
				", department='" + department + '\'' +
				", ckMonth='" + ckMonth + '\'' +
				", superior='" + superior + '\'' +
				", insDate=" + insDate +
				", wkPerform='" + wkPerform + '\'' +
				", seAbility='" + seAbility + '\'' +
				", attWard='" + attWard + '\'' +
				", exeInspec='" + exeInspec + '\'' +
				", attStas='" + attStas + '\'' +
				", tatal=" + tatal +
				", overall='" + overall + '\'' +
				'}';
	}

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
	private String tatal;

	/** 总评 */
	@TableField("overall")
	private String overall;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getThePerson() {
		return thePerson;
	}

	public void setThePerson(String thePerson) {
		this.thePerson = thePerson;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCkMonth() {
		return ckMonth;
	}

	public void setCkMonth(String ckMonth) {
		this.ckMonth = ckMonth;
	}

	public String getSuperior() {
		return superior;
	}

	public void setSuperior(String superior) {
		this.superior = superior;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getWkPerform() {
		return wkPerform;
	}

	public void setWkPerform(String wkPerform) {
		this.wkPerform = wkPerform;
	}

	public String getSeAbility() {
		return seAbility;
	}

	public void setSeAbility(String seAbility) {
		this.seAbility = seAbility;
	}

	public String getAttWard() {
		return attWard;
	}

	public void setAttWard(String attWard) {
		this.attWard = attWard;
	}

	public String getExeInspec() {
		return exeInspec;
	}

	public void setExeInspec(String exeInspec) {
		this.exeInspec = exeInspec;
	}

	public String getAttStas() {
		return attStas;
	}

	public void setAttStas(String attStas) {
		this.attStas = attStas;
	}

	public String getTatal() {
		return tatal;
	}

	public void setTatal(String tatal) {
		this.tatal = tatal;
	}

	public String getOverall() {
		return overall;
	}

	public void setOverall(String overall) {
		this.overall = overall;
	}
}
