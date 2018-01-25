package com.example.hugo.njupter.bean;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by hugo on 2017/3/10.
 */
@JsonObject
public class BaseBean {
    @JsonField(name = "code")
    public int code;
    @JsonField(name = "data")
    public String data;
    @JsonField(name="message")
    public String message;
}