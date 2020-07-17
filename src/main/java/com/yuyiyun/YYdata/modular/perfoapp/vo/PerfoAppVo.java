package com.yuyiyun.YYdata.modular.perfoapp.vo;

import com.yuyiyun.YYdata.modular.perfoapp.entity.PerfoAppraisalEntity;

public class PerfoAppVo extends PerfoAppraisalEntity {

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

    //开始时间
    private String  startTime;

    //用户名
    private String name;

    //uuid
    private String uuid;

    //审核月份
    private String ckMonth;

    //被审核人
     private  String thePerson;


    @Override
    public String toString() {
        return "PerfoAppVo{" +
                "userid=" + userid +
                ", startTime='" + startTime + '\'' +
                ", name='" + name + '\'' +
                ", ckMonth='" + ckMonth + '\'' +
                ", thePerson='" + thePerson + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCkMonth() {
        return ckMonth;
    }

    @Override
    public void setCkMonth(String ckMonth) {
        this.ckMonth = ckMonth;
    }

    @Override
    public String getThePerson() {
        return thePerson;
    }

    @Override
    public void setThePerson(String thePerson) {
        this.thePerson = thePerson;
    }

    //结束时间
    private String  endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
