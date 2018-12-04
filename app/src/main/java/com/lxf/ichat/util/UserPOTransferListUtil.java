package com.lxf.ichat.util;

import com.lxf.ichat.po.UserPO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserPOTransferListUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );

    public static void getUserInfoList(String[] keys, UserPO userPO, List< Map<String, String> > list) {
        int len = keys.length;
        String[] values = new String[len];

        values[0] = userPO.getUID();
        values[1] = userPO.getNickname();
        values[2] = userPO.getSignature() == null ? "未填写": userPO.getSignature();
        values[3] = userPO.getSex();
        Long birthday = userPO.getBirthday();
        values[4] = dateFormat.format( new Date( birthday ) );
        values[5] = userPO.getPlace() == null ? "未填写": userPO.getPlace();
        values[6] = userPO.getHometown() == null ? "未填写": userPO.getHometown();
        values[7] = userPO.getEmail() == null ? "未填写": userPO.getEmail();

        for (int i=0; i<len; i++) {
            Map<String, String> map = new HashMap<>(  );
            map.put( "key", keys[i] );
            map.put( "value", values[i] );
            list.add( map );
        }
    }
}
