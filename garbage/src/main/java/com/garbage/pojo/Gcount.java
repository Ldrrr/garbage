package com.garbage.pojo;

import java.util.Date;

public class Gcount {
    private Integer id;

    private Integer userId;

    private String phone;

    private Integer recyclable;

    private Integer wet;

    private Integer harm;

    private Integer dry;

    private Date updateTime;

    private Date createTime;

    public Gcount(Integer id, Integer userId, String phone, Integer recyclable, Integer wet, Integer harm, Integer dry, Date updateTime, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.phone = phone;
        this.recyclable = recyclable;
        this.wet = wet;
        this.harm = harm;
        this.dry = dry;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public Gcount() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getRecyclable() {
        return recyclable;
    }

    public void setRecyclable(Integer recyclable) {
        this.recyclable = recyclable;
    }

    public Integer getWet() {
        return wet;
    }

    public void setWet(Integer wet) {
        this.wet = wet;
    }

    public Integer getHarm() {
        return harm;
    }

    public void setHarm(Integer harm) {
        this.harm = harm;
    }

    public Integer getDry() {
        return dry;
    }

    public void setDry(Integer dry) {
        this.dry = dry;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Gcount{" +
                "id=" + id +
                ", userId=" + userId +
                ", phone='" + phone + '\'' +
                ", recyclable=" + recyclable +
                ", wet=" + wet +
                ", harm=" + harm +
                ", dry=" + dry +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }
}