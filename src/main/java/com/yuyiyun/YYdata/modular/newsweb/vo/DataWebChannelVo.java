package com.yuyiyun.YYdata.modular.newsweb.vo;

import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebChannel;

public class DataWebChannelVo extends DataWebChannel{
		/**
	     * 用户主键ID
	     */
	    private Long userid;
	
	    public Long getUserid() {
	        return userid;
	    }
	
	    public void setUserid(Long userid) {
	        this.userid = userid;
	    }
	    
	    //用户名
	    private String name;
	    
	
		
	    public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		 /** 所属媒体 */
		private Long data_web_media;
	    /** 媒体名称 */
	    private String website_name;

	    /** 所属网站(关联网站表) */
	    private Long data_web_website;

	    /** 网站名称 */
	    private String website_sub_name;

	    /** 频道名称 */
	    private String module_name;

	    /** 频道网址 */
	    private String sub_module_url;
	    
	    /** 采集标识 */
	    private String collect_sign;


		/** 采集状态 */
	    private String collect_state;

	    public String getCollect_sign() {
			return collect_sign;
		}

		public void setCollect_sign(String collect_sign) {
			this.collect_sign = collect_sign;
		}

		public String getWebsite_name() {
			return website_name;
		}

		public void setWebsite_name(String website_name) {
			this.website_name = website_name;
		}



		public String getWebsite_sub_name() {
			return website_sub_name;
		}

		public void setWebsite_sub_name(String website_sub_name) {
			this.website_sub_name = website_sub_name;
		}

		public String getModule_name() {
			return module_name;
		}

		public void setModule_name(String module_name) {
			this.module_name = module_name;
		}

		public String getSub_module_url() {
			return sub_module_url;
		}

		public void setSub_module_url(String sub_module_url) {
			this.sub_module_url = sub_module_url;
		}

		public String getCollect_state() {
			return collect_state;
		}

		public void setCollect_state(String collect_state) {
			this.collect_state = collect_state;
		}


		
		

		public DataWebChannelVo(Long userid, String name, Long data_web_media, String website_name,
				Long data_web_website, String website_sub_name, String module_name, String sub_module_url,
				String collect_sign, String collect_state) {
			super();
			this.userid = userid;
			this.name = name;
			this.data_web_media = data_web_media;
			this.website_name = website_name;
			this.data_web_website = data_web_website;
			this.website_sub_name = website_sub_name;
			this.module_name = module_name;
			this.sub_module_url = sub_module_url;
			this.collect_sign = collect_sign;
			this.collect_state = collect_state;
		}

		public DataWebChannelVo() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "DataWebChannelVo [userid=" + userid + ", name=" + name + ", data_web_media=" + data_web_media
					+ ", website_name=" + website_name + ", data_web_website=" + data_web_website
					+ ", website_sub_name=" + website_sub_name + ", module_name=" + module_name + ", sub_module_url="
					+ sub_module_url + ", collect_sign=" + collect_sign + ", collect_state=" + collect_state + "]";
		}

		
		
		
	

}
