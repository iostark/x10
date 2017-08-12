package com.iostark.x10.permission;

import android.support.annotation.NonNull;

import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/**
 * Permission request handler.
 *
 * @since V0_5_0
 */
@KeepEntryPoint
public interface PermissionRequestHandler {
    /**
     * Called when a permssion rationale must be displayed.
     *
     * @param permissionName  Permission name to show rationale for
     * @param rationaleResult Permission result callback
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onShowPermissionRationale(String permissionName, @NonNull PermissionRationaleResult rationaleResult);

    /**
     * Called when the user requested to never show the permission request again.
     *
     * @param permissionName          Permission name to never ask permission for
     * @param currentPermissionStatus Current permission status. // FIXME Indicated permission value
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onNeverAskAgainPermission(String permissionName, int currentPermissionStatus);

    /**
     * Called when the system interrupted the permission request.
     *
     * @param permissionName Interrupted permission name
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onPermissionRequestInterrupted(String permissionName);

    /**
     * Called when the permission request result is available.
     *
     * @param permissionName   Permission name whose permission result is available
     * @param permissionStatus Permission status. // FIXME Indicated permission value
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onPermissionRequestResult(String permissionName, int permissionStatus);
}
