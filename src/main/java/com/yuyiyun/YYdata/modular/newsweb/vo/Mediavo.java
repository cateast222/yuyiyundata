package com.yuyiyun.YYdata.modular.newsweb.vo;

import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebMedia;

public class Mediavo extends DataWebMedia {
    //用户名
    private String name;
    /**
     * 用户主键ID
     */
    private Long userid;
    /**
     * 关联媒体表
     */
    private String dataWebMedia;

    public String getDataWebMedia() {
        return dataWebMedia;
    }

    public void setDataWebMedia(String dataWebMedia) {
        this.dataWebMedia = dataWebMedia;
    }


    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
