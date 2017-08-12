/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.base.logging;

import com.iostark.x10.base.annotation.api.API_Beta;
import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/**
 * Append logs statements.
 *
 * @since V0_5_0
 */
@API_Beta
@KeepEntryPoint
public interface Appender {
  /**
   * Append debug log.
   *
   * @param moduleName Module where this log statement come from
   * @param message Log Message
   * @since V0_5_0
   */
  @SuppressWarnings("PMD.ShortMethodName")
  void d(String moduleName, String message);

  /**
   * Append verbose log.
   *
   * @param moduleName Module where this log statement come from
   * @param message Log Message*
   * @since V0_5_0
   */
  @SuppressWarnings("PMD.ShortMethodName")
  void v(String moduleName, String message);

  /**
   * Append info log.
   *
   * @param moduleName Module where this log statement come from
   * @param message Log Message
   * @since V0_5_0
   */
  @SuppressWarnings("PMD.ShortMethodName")
  void i(String moduleName, String message);

  /**
   * Append warning log.
   *
   * @param moduleName Module where this log statement come from
   * @param message Log Message
   * @since V0_5_0
   */
  @SuppressWarnings("PMD.ShortMethodName")
  void w(String moduleName, String message);

  /**
   * Append error log.
   *
   * @param moduleName Module where this log statement come from
   * @param message Log Message
   * @since V0_5_0
   */
  @SuppressWarnings("PMD.ShortMethodName")
  void e(String moduleName, String message);
}
