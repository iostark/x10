/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.base.logging;

import android.util.Log;
import com.iostark.x10.base.annotation.api.API_Internal;

@API_Internal
class AndroidAppender implements Appender {
  @Override
  @SuppressWarnings("PMD.ShortMethodName")
  public void d(final String moduleName, final String message) {
    Log.d(moduleName, message);
  }

  @Override
  @SuppressWarnings("PMD.ShortMethodName")
  public void v(final String moduleName, final String message) {
    Log.v(moduleName, message);
  }

  @Override
  @SuppressWarnings("PMD.ShortMethodName")
  public void i(final String moduleName, final String message) {
    Log.i(moduleName, message);
  }

  @Override
  @SuppressWarnings("PMD.ShortMethodName")
  public void w(final String moduleName, final String message) {
    Log.w(moduleName, message);
  }

  @Override
  @SuppressWarnings("PMD.ShortMethodName")
  public void e(final String moduleName, final String message) {
    Log.e(moduleName, message);
  }
}
