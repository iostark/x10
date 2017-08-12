/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.permission;

import android.support.annotation.NonNull;
import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/** @since V0_5_0 */
@KeepEntryPoint
public class BasePermissionRequestHandler implements PermissionRequestHandler {
  /**
   * By default, calls {@link PermissionRationaleResult#onContinue()}.
   *
   * @since V0_5_0
   */
  @Override
  public void onShowPermissionRationale(
      final String permissionName, final @NonNull PermissionRationaleResult rationaleResult) {
    rationaleResult.onContinue();
  }

  /**
   * @param permissionName
   * @param currentPermissionStatus
   * @since V0_5_0
   */
  @Override
  @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
  public void onNeverAskAgainPermission(
      final String permissionName, final int currentPermissionStatus) {}

  /**
   * @param permissionName
   * @since V0_5_0
   */
  @Override
  @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
  public void onPermissionRequestInterrupted(final String permissionName) {}

  /**
   * @param permissionName
   * @param permissionStatus
   * @since V0_5_0
   */
  @Override
  @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
  public void onPermissionRequestResult(final String permissionName, final int permissionStatus) {}
}
