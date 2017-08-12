/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.base.logging;

import com.iostark.x10.base.annotation.api.API_Internal;

/**
 * Logger.
 *
 * @since V0_5_0
 */
@API_Internal
public interface Logger {
  /**
   * Logs a debug statement.
   *
   * @param format String format
   * @param args String format arguments
   * @since V0_5_0
   */
  @SuppressWarnings("PMD.ShortMethodName")
  void d(final String format, final Object... args);

  /**
   * Logs a verbose statement.
   *
   * @param format String format
   * @param args String format arguments
   * @since V0_5_0
   */
  @SuppressWarnings("PMD.ShortMethodName")
  void v(final String format, final Object... args);

  /**
   * Logs a info statement.
   *
   * @param format String format
   * @param args String format arguments
   * @since V0_5_0
   */
  @SuppressWarnings("PMD.ShortMethodName")
  void i(final String format, final Object... args);

  /**
   * Logs a warning statement.
   *
   * @param format String format
   * @param args String format arguments
   * @since V0_5_0
   */
  @SuppressWarnings("PMD.ShortMethodName")
  void w(final String format, final Object... args);

  /**
   * Logs a error statement.
   *
   * @param format String format
   * @param args String format arguments
   * @since V0_5_0
   */
  @SuppressWarnings("PMD.ShortMethodName")
  void e(final String format, final Object... args);
}
