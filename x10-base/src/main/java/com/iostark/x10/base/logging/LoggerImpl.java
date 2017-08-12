/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.base.logging;

import com.iostark.x10.base.annotation.api.API_Internal;

/**
 * Logger implementation.
 *
 * @since V0_5_0
 */
@API_Internal
public class LoggerImpl implements Logger {
  public String moduleName;
  public Appender impl;

  private LoggerImpl() {}

  /**
   * Constructor.
   *
   * @param moduleName Module this logger represents
   * @param impl Default logger appender
   * @since V0_5_0
   */
  public LoggerImpl(final String moduleName, final Appender impl) {
    this.moduleName = moduleName;
    this.impl = impl;
  }

  @Override
  @SuppressWarnings("PMD.ShortMethodName")
  public void d(final String format, final Object... args) {
    impl.d(moduleName, String.format(format, args));
  }

  @Override
  @SuppressWarnings("PMD.ShortMethodName")
  public void v(final String format, final Object... args) {
    impl.v(moduleName, String.format(format, args));
  }

  @Override
  @SuppressWarnings("PMD.ShortMethodName")
  public void i(final String format, final Object... args) {
    impl.i(moduleName, String.format(format, args));
  }

  @Override
  @SuppressWarnings("PMD.ShortMethodName")
  public void w(final String format, final Object... args) {
    impl.w(moduleName, String.format(format, args));
  }

  @Override
  @SuppressWarnings("PMD.ShortMethodName")
  public void e(final String format, final Object... args) {
    impl.e(moduleName, String.format(format, args));
  }
}
