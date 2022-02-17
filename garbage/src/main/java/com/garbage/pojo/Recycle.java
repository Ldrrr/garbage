package com.garbage.pojo;

import java.util.Date;

public class Recycle {
    private Integer id;

    private String location;

    private String longitude;

    private String latitude;

    private String picture;

    private Date updateTime;

    private Date createTime;

    public Recycle(Integer id, String location, String longitude, String latitude, String picture, Date updateTime, Date createTime) {
        this.id = id;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.picture = picture;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public Recycle() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
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
        return "Recycle{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", picture='" + picture + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }
}