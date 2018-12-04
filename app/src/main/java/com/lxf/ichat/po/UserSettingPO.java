package com.lxf.ichat.po;

public class UserSettingPO {

    public static final String UPDATE_PWD = "0"; // 修改密码
    public static final String ABOUT_APP = "1"; // 退出系统
    public static final String EXTI_SYSTEM = "2"; // 退出系统

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
