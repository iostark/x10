package com.iostark.x10.base.logging;

import com.iostark.x10.base.annotation.api.API_Beta;
import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

@API_Beta
@KeepEntryPoint
public interface Appender {
    @SuppressWarnings("PMD.ShortMethodName")
    void d(String moduleName, String message);
    @SuppressWarnings("PMD.ShortMethodName")
    void v(String moduleName, String message);
    @SuppressWarnings("PMD.ShortMethodName")
    void i(String moduleName, String message);
    @SuppressWarnings("PMD.ShortMethodName")
    void w(String moduleName, String message);
    @SuppressWarnings("PMD.ShortMethodName")
    void e(String moduleName, String message);
}
