package com.lxf.ichat.service;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.lxf.ichat.constant.URLConstant;
import com.lxf.ichat.exception.ParameterErrorException;
import com.lxf.ichat.httpclient.HttpResponseCallback;
import com.lxf.ichat.httpclient.IchatOkHttpClient;
import com.lxf.ichat.po.ChangePasswordPO;
import com.lxf.ichat.po.MessagePO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.view.viewholder.LoginActivityViewHolder;
import com.lxf.ichat.view.viewholder.RegisterActivityViewHolder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UserServiceImpl implements UserService {

    private static final String TAG = UserServiceImpl.class.getName();

    private IchatOkHttpClient ichatOkHttpClient;

    public UserServiceImpl() {
        ichatOkHttpClient = IchatOkHttpClient.getInstance();
    }

    @Override
    public void register(RegisterActivityViewHolder mViewHolder, HttpResponseCallback responseCallback) {
        Log.i(TAG, "register: ");
        String UID = mViewHolder.UID_ET.getText().toString().trim();
        String nickname = mViewHolder.nickname_ET.getText().toString().trim();
        String sex = mViewHolder.male_RBTN.isChecked() ? "男" : "女";
        String PWD = mViewHolder.PASWD_ET.getText().toString().trim();
        String CFPWD = mViewHolder.CFPWD_ET.getText().toString().trim();

        MessagePO messagePO = new MessagePO();
        try {
            if (UID.isEmpty()) {
                throw new ParameterErrorException("账号不能为空");
            }

            if (nickname.isEmpty()) {
                throw new ParameterErrorException("昵称不能为空");
            }

            if (PWD.isEmpty()) {
                throw new ParameterErrorException("密码不能为空");
            }
            if (!PWD.equals(CFPWD)) {
                throw new ParameterErrorException("确认密码不一致");
            }
            nickname = URLEncoder.encode(nickname, "UTF-8");
            sex = URLEncoder.encode(sex, "UTF-8");
        } catch (ParameterErrorException e) {
            messagePO.setType(MessagePO.NULL);
            messagePO.setContent(e.getMessage());
            responseCallback.onErrorParam(messagePO);
            return;
        } catch (UnsupportedEncodingException e) {
            messagePO.setType(MessagePO.NULL);
            messagePO.setContent("不支持的编码类型");
            responseCallback.onErrorParam(messagePO);
            return;
        }

        UserPO userPO = new UserPO();
        userPO.setUID(UID);
        userPO.setNickname(nickname);
        userPO.setSex(sex);
        userPO.setPassword(PWD);
        StringBuffer stringBuffer = new StringBuffer(0);
        stringBuffer.append(URLConstant.REGISTER_URL);
        ichatOkHttpClient.doPost(stringBuffer.toString(),
                "ichat_register",
                JSONObject.toJSONString(userPO, true),
                responseCallback);
    }

    @Override
    public void login(LoginActivityViewHolder mViewHolder, HttpResponseCallback responseCallback) {
        Log.i(TAG, "login: ");
        String UID = mViewHolder.UID_ET.getText().toString().trim();
        String password = mViewHolder.PWD_ET.getText().toString().trim();
        try {
            if (UID.isEmpty()) {
                throw new ParameterErrorException("账号不能为空");
            }
            if (password.isEmpty()) {
                throw new ParameterErrorException("密码不能为空");
            }
        } catch (ParameterErrorException e) {
            MessagePO messagePO = new MessagePO();
            messagePO.setType(MessagePO.NULL);
            messagePO.setContent(e.getMessage());
            responseCallback.onErrorParam(messagePO);
            return;
        }

        StringBuffer stringBuffer = new StringBuffer(0);
        stringBuffer.append(URLConstant.LOGIN_URL);
        stringBuffer.append("UID=");
        stringBuffer.append(UID);
        stringBuffer.append("&");
        stringBuffer.append("password=");
        stringBuffer.append(password);
        ichatOkHttpClient.doGet(stringBuffer.toString(), responseCallback);
    }

    @Override
    public void findUser(String UID, HttpResponseCallback responseCallback) {
        Log.i(TAG, "findUser: ");
        try {
            if (UID.trim().isEmpty()) {
                throw new ParameterErrorException("查找内容不能为空");
            }
        } catch (ParameterErrorException e) {
            MessagePO messagePO = new MessagePO();
            messagePO.setType(MessagePO.NULL);
            messagePO.setContent(e.getMessage());
            responseCallback.onErrorParam(messagePO);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer(0);
        stringBuffer.append(URLConstant.LOAD_USER_INFO_URL);
        stringBuffer.append("UID=");
        stringBuffer.append(UID);
        ichatOkHttpClient.doGet(stringBuffer.toString(), responseCallback);
    }

    @Override
    public void logout(String UID, HttpResponseCallback responseCallback) {
        Log.i(TAG, "logout: ");
        try {
            if (UID.trim().isEmpty()) {
                throw new ParameterErrorException("请求参数为空");
            }
        } catch (ParameterErrorException e) {
            MessagePO messagePO = new MessagePO();
            messagePO.setType(MessagePO.NULL);
            messagePO.setContent(e.getMessage());
            responseCallback.onErrorParam(messagePO);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer(0);
        stringBuffer.append(URLConstant.LOGINOUT_URL);
        stringBuffer.append("UID=");
        stringBuffer.append(UID);
        ichatOkHttpClient.doGet(stringBuffer.toString(), responseCallback);
    }

    @Override
    public void addFriend(String UID, String FID, HttpResponseCallback responseCallback) {
        Log.i(TAG, "addFriend: ");
        try {
            if (UID.trim().isEmpty()) {
                throw new ParameterErrorException("请求参数为空");
            }
            if (FID.trim().isEmpty()) {
                throw new ParameterErrorException("请求参数为空");
            }
        } catch (ParameterErrorException e) {
            MessagePO messagePO = new MessagePO();
            messagePO.setType(MessagePO.NULL);
            messagePO.setContent(e.getMessage());
            responseCallback.onErrorParam(messagePO);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer(0);
        stringBuffer.append(URLConstant.ADD_FRIEND_URL);
        stringBuffer.append("UID=");
        stringBuffer.append(UID);
        stringBuffer.append("&");
        stringBuffer.append("FID=");
        stringBuffer.append(FID);
        ichatOkHttpClient.doGet(stringBuffer.toString(), responseCallback);
    }

    @Override
    public void delFriend(String UID, String FID, HttpResponseCallback responseCallback) {
        Log.i(TAG, "delFriend: ");
        try {
            if (UID.trim().isEmpty()) {
                throw new ParameterErrorException("请求参数为空");
            }
            if (FID.trim().isEmpty()) {
                throw new ParameterErrorException("请求参数为空");
            }
        } catch (ParameterErrorException e) {
            MessagePO messagePO = new MessagePO();
            messagePO.setType(MessagePO.NULL);
            messagePO.setContent(e.getMessage());
            responseCallback.onErrorParam(messagePO);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer(0);
        stringBuffer.append(URLConstant.DEL_FRIEND_URL);
        stringBuffer.append("UID=");
        stringBuffer.append(UID);
        stringBuffer.append("&");
        stringBuffer.append("FID=");
        stringBuffer.append(FID);
        ichatOkHttpClient.doGet(stringBuffer.toString(), responseCallback);
    }

    @Override
    public void loadFriends(String UID, HttpResponseCallback responseCallback) {
        Log.i(TAG, "loadFriends: ");
        try {
            if (UID.trim().isEmpty()) {
                throw new ParameterErrorException("请求参数为空");
            }
        } catch (ParameterErrorException e) {
            MessagePO messagePO = new MessagePO();
            messagePO.setType(MessagePO.NULL);
            messagePO.setContent(e.getMessage());
            responseCallback.onErrorParam(messagePO);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer(0);
        stringBuffer.append(URLConstant.LOAD_USER_FRIENGS_URL);
        stringBuffer.append("UID=");
        stringBuffer.append(UID);
        ichatOkHttpClient.doGet(stringBuffer.toString(), responseCallback);
    }

    @Override
    public void updateUserInfo(UserPO userPO, HttpResponseCallback responseCallback) {
        Log.i(TAG, "updateUserInfo: ");
        StringBuffer stringBuffer = new StringBuffer(0);
        stringBuffer.append(URLConstant.MODIFY_USER_INFO_URL);
        ichatOkHttpClient.doPost(stringBuffer.toString(),
                "ichat_updateUserInfo",
                JSONObject.toJSONString(userPO, true),
                responseCallback);
    }

    @Override
    public void changePassword(ChangePasswordPO changePasswordPO, HttpResponseCallback responseCallback) {
        Log.i(TAG, "changePassword: " + changePasswordPO);
        StringBuffer stringBuffer = new StringBuffer(0);
        stringBuffer.append(URLConstant.CHANGE_PASSWORD_URL);
        ichatOkHttpClient.doPost(stringBuffer.toString(),
                "ichat_changePassword",
                JSONObject.toJSONString(changePasswordPO, true),
                responseCallback);
    }
}
