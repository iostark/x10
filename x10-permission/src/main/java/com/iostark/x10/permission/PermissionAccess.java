/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.permission;

import android.content.pm.PackageManager;
import com.iostark.x10.base.annotation.api.API_Internal;
import java.util.Formattable;
import java.util.Formatter;

/** @since V0_5_0 */
@API_Internal
enum PermissionAccess implements Formattable {

  /** @since V0_5_0 */
  PERMISSION_UNKNOWN(Integer.MAX_VALUE, "PERMISSION_UNKNOWN"),
  /** @since V0_5_0 */
  PERMISSION_GRANTED(PackageManager.PERMISSION_GRANTED, "PERMISSION_GRANTED"),
  /** @since V0_5_0 */
  PERMISSION_DENIED(PackageManager.PERMISSION_DENIED, "PERMISSION_DENIED");

  private final int value;
  private final String name;

  PermissionAccess(final int value, final String name) {
    this.value = value;
    this.name = name;
  }

  /**
   * {@link Formattable#formatTo(Formatter, int, int, int)}
   *
   * @since V0_5_0
   */
  @Override
  public void formatTo(
      final Formatter formatter, final int flags, final int width, final int precision) {
    formatter.format("%s", name);
  }

  /**
   * @param androidPermissionValue
   * @return
   * @since V0_5_0
   */
  public static PermissionAccess from(final int androidPermissionValue) {
    switch (androidPermissionValue) {
      case PackageManager.PERMISSION_GRANTED:
        return PERMISSION_GRANTED;
      case PackageManager.PERMISSION_DENIED:
        return PERMISSION_DENIED;
      default:
        return PERMISSION_UNKNOWN;
    }
  }

  @Override
  @SuppressWarnings("PMD.LawOfDemeter")
  public String toString() {
    return String.format("%s(%s)", name, value);
  }
}
