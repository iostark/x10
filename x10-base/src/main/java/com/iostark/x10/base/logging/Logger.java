package com.iostark.x10.base.logging;

import com.iostark.x10.base.annotation.api.API_Internal;

@API_Internal
public interface Logger {
    @SuppressWarnings("PMD.ShortMethodName")
    void d(final String format, final Object... args);
    @SuppressWarnings("PMD.ShortMethodName")
    void v(final String format, final Object... args);
    @SuppressWarnings("PMD.ShortMethodName")
    void i(final String format, final Object... args);
    @SuppressWarnings("PMD.ShortMethodName")
    void w(final String format, final Object... args);
    @SuppressWarnings("PMD.ShortMethodName")
    void e(final String format, final Object... args);
}
