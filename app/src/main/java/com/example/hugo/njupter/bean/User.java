package com.example.hugo.njupter.bean;

import android.content.Context;

import com.amap.api.services.nearby.NearbyInfo;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.hugo.njupter.App;
import com.example.hugo.njupter.db.DatabaseHelper;
import com.example.hugo.njupter.utils.TimeUtils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugo on 2017/3/24.
 */
@DatabaseTable(tableName = "user")
@JsonObject
public class User extends BaseBean implements Serializable {
    private static User user;

    @DatabaseField(generatedId = true)
    public int tid;

    @DatabaseField(columnName = "userId")
    @JsonField(name = "id")
    public int id;

    @DatabaseField(columnName = "phone")
    @JsonField(name = "phone")
    public String phone;

    @DatabaseField(columnName = "nickName")
    @JsonField(name = "nickName")
    public String nickName;


    @JsonField
    @DatabaseField(columnName = "realName")
    private String realName;

    @JsonField
    @DatabaseField
    private String sex;

    @JsonField
    @DatabaseField
    private int point;

    @JsonField
    @DatabaseField
    private Integer age;

    @JsonField
    @DatabaseField
    private String home;

    @JsonField
    @DatabaseField
    private String am;

    @JsonField
    @DatabaseField
    private String image;

    @JsonField
    @DatabaseField
    private String cTime;

    @JsonField
    @DatabaseField
    private String sId;

    @JsonField
    @DatabaseField
    private String sPassword;

    @JsonField
    @DatabaseField
    private String lPassword;

    @JsonField
    @DatabaseField
    private String aPassword;



    private String distance;

    private String timeStamp;


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        User.user = user;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsPassword() {
        return sPassword;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    public String getlPassword() {
        return lPassword;
    }

    public void setlPassword(String lPassword) {
        this.lPassword = lPassword;
    }

    public String getaPassword() {
        return aPassword;
    }

    public void setaPassword(String aPassword) {
        this.aPassword = aPassword;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public static User getCurrentUser(Context context) {
        if (user != null)
            return user;
        try {
            Dao<User, Integer> userDao = DatabaseHelper.getHelper(App.s).getUserDao();
            List<User> users = userDao.queryForAll();
            user = users.get(0);
        } catch (Exception e) {
        }
        return user;
    }

    public static boolean checkUser(Context context){
        if(getCurrentUser(context)==null){
            return false;
        }
        return true;
    }

    /**
     * 更新本地数据库的user
     * @param context
     * @param newUser
     */
    public static void updateUser(Context context, User newUser) {
        Dao<User, Integer> userDao = DatabaseHelper.getHelper(App.s).getUserDao();
        try {
            userDao.deleteBuilder().delete();
            userDao.create(newUser);
            user = newUser;
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 清除本地数据库的logout
     * @param context
     */
    public static void logout(Context context) {
        Dao<User, Integer> userDao = DatabaseHelper.getHelper(context).getUserDao();
        try {
            userDao.deleteBuilder().delete();
            user = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
