package com.iostark.x10.permission;

import android.support.annotation.NonNull;

import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/**
 *
 * @since V0_5_0
 */
@KeepEntryPoint
public interface PermissionRequestHandler {
    /**
     *
     * @param permissionName
     * @param rationaleResult
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onShowPermissionRationale(String permissionName, @NonNull PermissionRationaleResult rationaleResult);

    /**
     *
     * @param permissionName
     * @param currentPermissionStatus
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onNeverAskAgainPermission(String permissionName, int currentPermissionStatus);

    /**
     *
     * @param permission
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onPermissionRequestInterrupted(String permission);

    /**
     *
     * @param permissionName
     * @param permissionStatus
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onPermissionRequestResult(String permissionName, int permissionStatus);
}
