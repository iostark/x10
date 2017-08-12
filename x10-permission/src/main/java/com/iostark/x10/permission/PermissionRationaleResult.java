/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.permission;

import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/**
 * Permission rationale callback.
 *
 * @since V0_5_0
 */
@KeepEntryPoint
interface PermissionRationaleResult {
  /**
   * Continue processing.
   *
   * @since V0_5_0
   */
  @KeepEntryPoint
  void onContinue();

  /**
   * Abort permission rationale processing.
   *
   * @since V0_5_0
   */
  @KeepEntryPoint
  void onAbort();
}
