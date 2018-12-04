package com.lxf.ichat.base;

import com.lxf.ichat.po.UserPO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseExecutorService {

    private static BaseExecutorService mBaseExecutorService;
    private ExecutorService mExecutorService;

    private UserPO userPO;

    private BaseExecutorService() {
        mExecutorService = Executors.newCachedThreadPool();
    }

    public static BaseExecutorService getExecutorServiceInstance() {
        if (mBaseExecutorService == null) {
            synchronized (BaseExecutorService.class) {
                if (mBaseExecutorService == null) {
                    mBaseExecutorService = new BaseExecutorService();
                }
            }
        }
        return mBaseExecutorService;
    }

    public void execute(Runnable task) {
        mExecutorService.execute(task);
    }

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }
}
