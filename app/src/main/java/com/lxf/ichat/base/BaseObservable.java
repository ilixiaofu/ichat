package com.lxf.ichat.base;

import java.util.Observable;

public class BaseObservable extends Observable {

    private static volatile BaseObservable observable = null;

    private BaseObservable() {
    }

    public static synchronized BaseObservable getObservable() {
        if (observable == null) {
            observable = new BaseObservable();
        }
        return observable;
    }

//    public static BaseObservable getBaseObservable() {
//        if (observable == null) {
//            synchronized (BaseObservable.class) {
//                if (observable == null) {
//                    observable = new BaseObservable();
//                }
//            }
//        }
//        return observable;
//    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
        this.setChanged();
    }
}
