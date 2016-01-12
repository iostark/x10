package com.iostark.x10.permission;

import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

@KeepEntryPoint
public interface PermissionRationaleHandler {
    @KeepEntryPoint
    boolean showRationaleBeforeRequestingPermission();
    @KeepEntryPoint
    boolean showRationaleAfterRefusingPermission();
}
