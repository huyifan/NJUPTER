package com.example.hugo.njupter.bean;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hugo on 2017/3/10.
 */
@JsonObject
public class GoodBean extends BaseBean implements Serializable {
    @JsonField
    private String title;
    @JsonField
    private String des;
    @JsonField
    private String date;
    @JsonField
    private String nickName;
    @JsonField
    private String flag;
    @JsonField
    private int cid;
    @JsonField
    private String point;

    private boolean isGood=true;

    public GoodBean() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public boolean isGood() {
        return isGood;
    }

    public void setGood(boolean good) {
        isGood = good;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @JsonObject
    public static class Pojo implements Serializable {
        @JsonField(name = "data")
        public List<GoodBean> goods;
        @JsonField
        public int code;
        @JsonField
        public String message;
    }
}
