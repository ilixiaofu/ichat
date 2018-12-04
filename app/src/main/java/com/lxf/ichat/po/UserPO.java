package com.lxf.ichat.po;

import java.io.Serializable;


public class UserPO implements Serializable {

    private String UID;
    private String nickname;
    private String password;
    private String sex;
    private String email;
    private String place;
    private String hometown;
    private String signature;
    private Long birthday;
    private Long regDate;
    private String status;
    private String headportraitURL;

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Long getRegDate() {
        return regDate;
    }

    public void setRegDate(Long regDate) {
        this.regDate = regDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHeadportraitURL() {
        return headportraitURL;
    }

    public void setHeadportraitURL(String headportraitURL) {
        this.headportraitURL = headportraitURL;
    }

    @Override
    public String toString() {
        return "UserPO{" +
                "UID='" + UID + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", place='" + place + '\'' +
                ", hometown='" + hometown + '\'' +
                ", signature='" + signature + '\'' +
                ", birthday=" + birthday +
                ", regDate=" + regDate +
                ", status='" + status + '\'' +
                ", headportraitURL='" + headportraitURL + '\'' +
                '}';
    }
}