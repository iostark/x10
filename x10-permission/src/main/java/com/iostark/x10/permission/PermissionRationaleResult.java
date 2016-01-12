package com.iostark.x10.permission;

import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/**
 * @since V0_5_0
 */
@KeepEntryPoint
interface PermissionRationaleResult {
    /**
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onContinue();

    /**
     * @since V0_5_0
     */
    @KeepEntryPoint
    void onAbort();
}
