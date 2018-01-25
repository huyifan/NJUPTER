package com.example.hugo.njupter.bean;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hugo on 2017/4/14.
 */

@JsonObject
public class ULocation extends BaseBean implements Serializable{
    @JsonField
    private Integer uid;

    @JsonField
    private String location;

    @JsonField
    private String onTime;

    @JsonField
    private String distance;

    @JsonField
    private String sex;

    @JsonField
    private String nickName;

    @JsonField
    private String am;

    @JsonField
    private String age;

    @JsonField
    private String image;

    @JsonField
    private String home;

    @JsonField
    private String dongTai;

    public String getDongTai() {
        return dongTai;
    }

    public void setDongTai(String dongTai) {
        this.dongTai = dongTai;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getOnTime() {
        return onTime;
    }

    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public Integer toIntDist(){
        return Integer.valueOf(distance);
    }
    @JsonObject
    public static class Pojo implements Serializable {
        @JsonField(name = "data")
        public List<ULocation> locations;
        @JsonField
        public int code;
        @JsonField
        public String message;
    }

    @Override
    public String toString() {
        return "ULocation{" +
                "uid=" + uid +
                ", location='" + location + '\'' +
                ", onTime='" + onTime + '\'' +
                ", distance='" + distance + '\'' +
                ", sex='" + sex + '\'' +
                ", nickName='" + nickName + '\'' +
                ", am='" + am + '\'' +
                ", age='" + age + '\'' +
                ", image='" + image + '\'' +
                ", home='" + home + '\'' +
                ", dongTai='" + dongTai + '\'' +
                '}';
    }
}