package com.lxf.ichat.constant;

public class URLConstant {
    private static final String REMOTE_IP = "47.106.83.27";
    private static final String LOCAL_IP = "127.0.0.1";
    public static final String HTTP_URL = "http://" + REMOTE_IP + ":8080/ichat/";
    public static final String WS_URL = "ws://" + REMOTE_IP + ":8080/ichat/dochat/";

    public static final String LOGIN_URL = HTTP_URL + "login.do?";
    public static final String LOGINOUT_URL = HTTP_URL + "logout.do?";
    public static final String LOAD_USER_INFO_URL = HTTP_URL + "loadUserInfo.do?";
    public static final String ADD_FRIEND_URL = HTTP_URL + "addFriend.do?";
    public static final String DEL_FRIEND_URL = HTTP_URL + "delFriend.do?";
    public static final String LOAD_USER_FRIENGS_URL = HTTP_URL + "loadFriends.do?";

    public static final String REGISTER_URL = HTTP_URL + "register.do";
    public static final String MODIFY_USER_INFO_URL = HTTP_URL + "updateUserInfo.do";
    public static final String CHANGE_PASSWORD_URL = HTTP_URL + "changePassword.do";

}
