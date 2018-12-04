package com.lxf.ichat.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

public class ParameterErrorException extends Throwable {

    public ParameterErrorException() {
        super();
    }

    public ParameterErrorException(String message) {
        super(message);
    }

    public ParameterErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterErrorException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected ParameterErrorException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
