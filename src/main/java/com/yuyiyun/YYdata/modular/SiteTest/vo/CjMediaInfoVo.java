package com.yuyiyun.YYdata.modular.SiteTest.vo;

import com.yuyiyun.YYdata.modular.SiteTest.entity.CjMediaInfoEntity;

public class CjMediaInfoVo extends CjMediaInfoEntity {
	private Long media_id;
	private String mname;
	private String dom_for;
	private String country_code;
	private String province_code;
	private String city_code;
	private String area_code;
	private String label;
	private String state;
	public Long getMedia_id() {
		return media_id;
	}
	public void setMedia_id(Long media_id) {
		this.media_id = media_id;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getDom_for() {
		return dom_for;
	}
	public void setDom_for(String dom_for) {
		this.dom_for = dom_for;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "CjMediaInfoVo [media_id=" + media_id + ", mname=" + mname + ", dom_for=" + dom_for + ", country_code="
				+ country_code + ", province_code=" + province_code + ", city_code=" + city_code + ", area_code="
				+ area_code + ", label=" + label + ", state=" + state + "]";
	}
	
}
