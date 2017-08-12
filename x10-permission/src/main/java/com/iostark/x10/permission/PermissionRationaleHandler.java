package com.iostark.x10.permission;

import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/**
 * Permission rationale handler.
 *
 * @since V0_5_0
 */
@KeepEntryPoint
public interface PermissionRationaleHandler {
    /**
     * Indicate if a rationale must be displayed before requesting a permission.
     *
     * @return true if a rationale must be displayed. false otherwise
     * @since V0_5_0
     */
    @KeepEntryPoint
    boolean showRationaleBeforeRequestingPermission();

    /**
     * Indicate if a rationale must be displayed after requesting a permission.
     *
     * @return true if a rationale must be displayed. false otherwise
     * @since V0_5_0
     */
    @KeepEntryPoint
    boolean showRationaleAfterRefusingPermission();
}
