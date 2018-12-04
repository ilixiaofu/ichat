package com.lxf.ichat.service;

import com.lxf.ichat.httpclient.HttpResponseCallback;
import com.lxf.ichat.po.ChangePasswordPO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.view.viewholder.LoginActivityViewHolder;
import com.lxf.ichat.view.viewholder.RegisterActivityViewHolder;

public interface UserService {

    void register(RegisterActivityViewHolder mViewHolder, HttpResponseCallback responseCallback);

    void login(LoginActivityViewHolder mViewHolder, HttpResponseCallback responseCallback);

    void findUser(String UID, HttpResponseCallback responseCallback);

    void logout(String UID, HttpResponseCallback responseCallback);

    void addFriend(String UID, String FID, HttpResponseCallback responseCallback);

    void delFriend(String UID, String FID, HttpResponseCallback responseCallback);

    void loadFriends(String UID, HttpResponseCallback responseCallback);

    void updateUserInfo(UserPO userPO, HttpResponseCallback responseCallback);

    void changePassword(ChangePasswordPO changePasswordPO, HttpResponseCallback responseCallback);
}
