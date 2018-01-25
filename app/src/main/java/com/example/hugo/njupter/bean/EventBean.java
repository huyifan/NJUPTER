package com.example.hugo.njupter.bean;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hugo on 2017/3/29.
 */

@JsonObject
public class EventBean extends BaseBean {
    @JsonField
    private Integer id;
    @JsonField
    private Integer cid;
    @JsonField
    private String date;
    @JsonField
    private String title;
    @JsonField
    private String des;
    @JsonField
    private Integer nJoin;
    @JsonField
    private String host;
    @JsonField
    private String address;
    @JsonField
    private Integer nView;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Integer getnJoin() {
        return nJoin;
    }

    public void setnJoin(Integer nJoin) {
        this.nJoin = nJoin;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getnView() {
        return nView;
    }

    public void setnView(Integer nView) {
        this.nView = nView;
    }

    @JsonObject
    public static class Pojo implements Serializable {
        @JsonField(name = "data")
        public List<EventBean> events;
        @JsonField
        public int code;
        @JsonField
        public String message;
    }
}
