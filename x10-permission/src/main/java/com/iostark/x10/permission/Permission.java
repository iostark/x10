package com.iostark.x10.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;
import com.iostark.x10.base.id.internal.GlobalNumberIdentifierGenerator;
import com.iostark.x10.base.internal.X10Bundle;
import com.iostark.x10.base.logging.Logger;
import com.iostark.x10.base.logging.LoggingRegistry;

/**
 * Usage.
 * <pre>
 * {@code
 * Setup Activity.onCreate() {
 *  // permission is an instance member of myActivity
 *  permission = new Permission(myActivity);
 * }
 * Setup Activity.onSaveInstanceState(Bundle outState)
 * {
 *  permmission.onSaveInstanceState(outState);
 * }
 *
 * Setup Activity.onRestoreInstanceState(Bundle outState)
 * {
 *  permmission.onRestoreInstanceState(outState);
 * }
 *
 * Setup Activity.onRequestPermissionsResult (requestCode, permmissions, grantResults)
 * {
 *  permission.onRequestPermissionsResult (requestCode, permmissions, grantResults);
 * }
 *
 * Setup in the function that requires to ask the user's permission
 * {
 *  PermissionRequestHandler result = new PermissionRequestHandler() {
 *      // FIXME Write implementation
 *  };
 *
 *  permission.requestAppPermission(Manifest.permission.ACCESS_FINE_LOCATION, result);
 *      // FIXME Write example
 *  }
 * }
 * </pre>
 *
 * @since V0_5_0
 */
@KeepEntryPoint
public class Permission {
    private static final int REQUEST_CODE = GlobalNumberIdentifierGenerator.INT_IDENTIFIER_GENERATOR.next().intValue();
    private static final String REQUEST_CODE_KEY = "request_code";
    private static final String SHOWN_RATIONALE_AFTER_PERMISSION_REFUSAL_KEY = "show_rationale_after_permission_refusal";

    private final Activity context;
    private int requestedCode;

    private PermissionRequestHandler requestHandler;
    private PermissionRationaleHandler rationaleHandler;
    private boolean shownRationaleAfterPermissionRefused = false;

    private final static Logger LOGGER;

    static {
        LOGGER = LoggingRegistry.getLoggerOrCreate(ModuleConfig.MODULE_NAME);
    }

    /**
     * Constructor.
     *
     * @param activity The activity to request permission with
     * @since V0_5_0
     */
    @KeepEntryPoint
    public Permission(final @NonNull Activity activity) {
        this.context = activity;
    }


    /**
     * Requests app permission.
     *
     * @param permissionName   The permission to request. Eg: {@link android.Manifest.permission#ACCESS_FINE_LOCATION}
     * @param requestHandler   Permission handler receive permission results. You can use {@link BasePermissionRequestHandler}
     * @param rationaleHandler Permission rationale handler to receive decision from the user.
     * @since V0_5_0
     */
    @KeepEntryPoint
    public void requestAppPermission(@NonNull
                                     final String permissionName, @Nullable
                                     final PermissionRequestHandler requestHandler, @Nullable
                                     final PermissionRationaleHandler rationaleHandler) {
        requestAppPermission(permissionName, REQUEST_CODE, requestHandler, rationaleHandler);
    }

    /**
     * Requests app permission.
     *
     * @param permissionName   The permission to request. Eg: Manifest.permission.ACCESS_FINE_LOCATION
     * @param requestCode      Request code
     * @param requestHandler   Object to receive permission results. You can use {@link BasePermissionRequestHandler}
     * @param rationaleHandler Permission rationale handler to receive decision from the user.
     * @since V0_5_0
     */
    @SuppressWarnings("PMD.SwitchDensity")
    @KeepEntryPoint
    public void requestAppPermission(@NonNull
                                     final String permissionName, final int requestCode, @Nullable
                                     final PermissionRequestHandler requestHandler, @Nullable
                                     final PermissionRationaleHandler rationaleHandler) {
        this.requestHandler = requestHandler;
        this.rationaleHandler = rationaleHandler;
        this.requestedCode = requestCode;

        LOGGER.v("[%d] Requesting app permission %s with request code %d", requestCode, permissionName, requestCode);

        // 1. Verify if permission is already granted
        final int permissionResult = checkSelfPermission(context, permissionName);

        LOGGER.d("[%d] Currently app permission %s is %s", requestedCode, permissionName, PermissionAccess.from(permissionResult));

        switch (permissionResult) {
            case PackageManager.PERMISSION_GRANTED:
                requestHandler.onPermissionRequestResult(permissionName, PackageManager.PERMISSION_GRANTED);
                break;
            case PackageManager.PERMISSION_DENIED:
                if (this.rationaleHandler.showRationaleBeforeRequestingPermission()) {
                    // 2a. Show Rationale
                    final PermissionRationaleResult rationaleResult = new PermissionRationaleResult() {
                        @Override
                        public void onContinue() {
                            LOGGER.d("[%d] Continue asking for permission %s", requestedCode, permissionName);

                            requestPermission2(context, permissionName, requestedCode);
                        }

                        @Override
                        public void onAbort() {
                            LOGGER.d("[%d] Aborted asking for permission %s", requestedCode, permissionName);

                            final int finalPermissionResult = checkSelfPermission(context, permissionName);
                            requestHandler.onPermissionRequestResult(permissionName, finalPermissionResult);
                        }
                    };
                    LOGGER.d("[%d] Show permission rationale before requesting permission for %s", requestedCode, permissionName);
                    requestHandler.onShowPermissionRationale(permissionName, rationaleResult);
                    return;
                } else {
                    // 2b. Request permission
                    requestPermission2(context, permissionName, requestedCode);
                }
                break;
            default:
                // Should never happen
                break;
        }
    }

    /**
     * To be called when {@link Activity#onRequestPermissionsResult(int, String[], int[])} is called.
     * @param requestCode See {@linkplain Activity#onRequestPermissionsResult(int, String[], int[])}
     * @param permissions See {@linkplain Activity#onRequestPermissionsResult(int, String[], int[])}
     * @param grantResults See {@linkplain Activity#onRequestPermissionsResult(int, String[], int[])}
     * @since V0_5_0
     */
    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.UseVarargs"})
    @KeepEntryPoint
    public void onRequestPermissionsResult(final int requestCode,
                                           final String[] permissions,
                                           final int[] grantResults) {
        if (requestCode != requestedCode) {
            return;
        }

        // Check permission request not interrupted
        if (permissions.length == 0) {
            LOGGER.d("[%d] Permission request interrupted.");

            requestHandler.onPermissionRequestInterrupted("");
            return;
        }

        for (int i = 0; i < permissions.length; i++) {
            final String permissionName = permissions[i];
            final int permissionResult = grantResults[i];

            if (permissionResult == PackageManager.PERMISSION_DENIED && rationaleHandler.showRationaleAfterRefusingPermission() && !shownRationaleAfterPermissionRefused) {
                shownRationaleAfterPermissionRefused = true;

                // 3a. Show Rationale
                final PermissionRationaleResult rationaleResult = new PermissionRationaleResult() {
                    @Override
                    public void onContinue() {
                        LOGGER.d("[%d] Continue asking for permission %s", requestedCode, permissionName);

                        requestPermission2(context, permissionName, requestedCode);
                    }

                    @Override
                    public void onAbort() {
                        LOGGER.d("[%d] Aborted asking for permission %s", requestedCode, permissionName);

                        final int finalPermissionResult = checkSelfPermission(context, permissionName);
                        requestHandler.onPermissionRequestResult(permissionName, finalPermissionResult);
                    }
                };
                LOGGER.d("[%d] Show permission rationale after requesting permission for %s", requestedCode, permissionName);
                requestHandler.onShowPermissionRationale(permissionName, rationaleResult);
                return;
            } else {
                // 3b. Final permission result
                LOGGER.d("[%d] Final permission request result for %s: %s", requestedCode, permissionName, PermissionAccess.from(permissionResult));
                requestHandler.onPermissionRequestResult(permissionName, permissionResult);
            }
        }
    }

    /**
     * To be called when {@link Activity#onSaveInstanceState(Bundle)} or {@link Activity#onSaveInstanceState(Bundle, PersistableBundle)} is called.
     * @param outState See {@linkplain Activity#onSaveInstanceState(Bundle)}
     * @since V0_5_0
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @KeepEntryPoint
    public void onSaveInstanceState(final Bundle outState) {
        final X10Bundle x10Bundle = new X10Bundle(outState, ModuleConfig.MODULE_NAME, Permission.class.getSimpleName());
        x10Bundle.putInt(REQUEST_CODE_KEY, requestedCode);
        x10Bundle.putBoolean(SHOWN_RATIONALE_AFTER_PERMISSION_REFUSAL_KEY, shownRationaleAfterPermissionRefused);
    }

    /**
     * To be called when {@link Activity#onRestoreInstanceState(Bundle)} or {@link Activity#onRestoreInstanceState(Bundle, PersistableBundle)} is called.
     * @param savedInstanceState See {@linkplain Activity#onRestoreInstanceState(Bundle)}
     * @since V0_5_0
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @KeepEntryPoint
    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        final X10Bundle x10Bundle = new X10Bundle(savedInstanceState, ModuleConfig.MODULE_NAME, Permission.class.getSimpleName());
        requestedCode = x10Bundle.getInt(REQUEST_CODE_KEY, REQUEST_CODE);
        shownRationaleAfterPermissionRefused = x10Bundle.getBoolean(SHOWN_RATIONALE_AFTER_PERMISSION_REFUSAL_KEY, false);
    }

    private void requestPermission2(final Activity activity, final String permissionName, final int requestCode) {
        requestPermission2(activity, new String[]{permissionName}, requestCode);
    }

    private void requestPermission2(final Activity activity, final String[] permissionNames, final int requestCode) {
        ActivityCompat.requestPermissions(activity, permissionNames, requestCode);
    }

    @SuppressWarnings("PMD.UnnecessaryLocalBeforeReturn")
    private int checkSelfPermission(final Activity activity, final String permissionName) {
        final int permissionResult = ContextCompat.checkSelfPermission(activity, permissionName);
        return permissionResult;
    }


//    private boolean isDevicePromptinForDangerousPermissionAtRuntime() {
//        // https://developer.android.com/guide/topics/permissions/requesting.html
//        ApplicationInfo applicationInfo = context.getApplicationInfo();
//        boolean isTargetSDK23OrHigher = applicationInfo.targetSdkVersion >= 23;
//        boolean isAndroid6_0OrHigher = Build.VERSION.SDK_INT >= 23;
//
//        return isAndroid6_0OrHigher && isTargetSDK23OrHigher;
//    }

}
