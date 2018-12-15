package com.lxf.ichat.service;

import com.lxf.ichat.po.ChangePasswordPO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.util.OKHttpUtil;
import com.lxf.ichat.view.viewholder.LoginActivityViewHolder;
import com.lxf.ichat.view.viewholder.RegisterActivityViewHolder;

public interface UserService {

    void register(RegisterActivityViewHolder mViewHolder, OKHttpUtil.ResponseCallback responseCallback);

    void login(LoginActivityViewHolder mViewHolder, OKHttpUtil.ResponseCallback responseCallback);

    void findUser(String UID, OKHttpUtil.ResponseCallback responseCallback);

    void logout(String UID, OKHttpUtil.ResponseCallback responseCallback);

    void addFriend(String UID, String FID, OKHttpUtil.ResponseCallback responseCallback);

    void delFriend(String UID, String FID, OKHttpUtil.ResponseCallback responseCallback);

    void loadFriends(String UID, OKHttpUtil.ResponseCallback responseCallback);

    void updateUserInfo(UserPO userPO, OKHttpUtil.ResponseCallback responseCallback);

    void changePassword(ChangePasswordPO changePasswordPO, OKHttpUtil.ResponseCallback responseCallback);
}
