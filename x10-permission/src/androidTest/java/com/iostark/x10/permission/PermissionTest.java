/*
 * Copyright (c) $year IO Stark
 */

package com.iostark.x10.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.iostark.x10.base.androidtest.ActivityInstrumentedTest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class PermissionTest extends ActivityInstrumentedTest<PermissionActivity> {

    private UiDevice device;
    private Permission permission;
    private static String PACKAGE_NAME = null;

    public PermissionTest() {
        super(PermissionActivity.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        device = UiDevice.getInstance(instrumentation);
        permission = new Permission(activity);
        activity.setPermissionContext(permission);
        PermissionTest.PACKAGE_NAME = activity.getPackageName();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        permission = null;

        super.tearDown();
    }

    @AfterClass
    public static void classTearDown() throws Exception {
        PermissionTest.resetPermission(PACKAGE_NAME, Manifest.permission.ACCESS_FINE_LOCATION);
    }


    @Test(timeout = 3000)
    public void testPermission1() throws UiObjectNotFoundException {
        PermissionRequestHandler requestHandler = new PermissionRequestHandler() {
            @Override
            public void onShowPermissionRationale(String permissionName, @NonNull PermissionRationaleResult rationaleResult) {
                fail();
            }

            @Override
            public void onNeverAskAgainPermission(String permissionName, int currentPermissionStatus) {
                fail();
            }

            @Override
            public void onPermissionRequestInterrupted(String permissionName) {
                fail();
            }

            @Override
            public void onPermissionRequestResult(String permissionName, int permissionStatus) {
                assertEquals(PackageManager.PERMISSION_DENIED, permissionStatus);
            }
        };

        PermissionRationaleHandler rationaleHandler = new PermissionRationaleHandler() {
            @Override
            public boolean showRationaleBeforeRequestingPermission() {
                return false;
            }

            @Override
            public boolean showRationaleAfterRefusingPermission() {
                return false;
            }
        };

        permission.requestAppPermission(Manifest.permission.ACCESS_FINE_LOCATION, 100, requestHandler, rationaleHandler);

        // Espresso waits for the permission to show up (ie go idle) and then continues.
        waitUntilPermissionDialogIsDisplayed();
        clickRejectPermission();
    }

    @SdkSuppress(minSdkVersion = 23)
    @Test(timeout = 3000)
    public void testPermission2() throws UiObjectNotFoundException {
        final String GOT_PERMISSION_1_RATIONALE = "pmr10";
        final String GOT_PERMISSION_1_RESULT = "pmr11";

        final Map<String, Boolean> result = new HashMap<>();

        result.put(GOT_PERMISSION_1_RATIONALE, false);
        result.put(GOT_PERMISSION_1_RESULT, false);

        PermissionRequestHandler requestHandler = new PermissionRequestHandler() {
            @Override
            public void onShowPermissionRationale(String permissionName, @NonNull PermissionRationaleResult rationaleResult) {
                result.put(GOT_PERMISSION_1_RATIONALE, true);
                rationaleResult.onContinue();
            }

            @Override
            public void onNeverAskAgainPermission(String permissionName, int currentPermissionStatus) {
                fail();
            }

            @Override
            public void onPermissionRequestInterrupted(String permissionName) {
                fail();
            }

            @Override
            public void onPermissionRequestResult(String permissionName, int permissionStatus) {
                result.put(GOT_PERMISSION_1_RESULT, PackageManager.PERMISSION_DENIED == permissionStatus);

                assertTrue(result.get(GOT_PERMISSION_1_RATIONALE));
                assertTrue(result.get(GOT_PERMISSION_1_RESULT));
            }
        };

        PermissionRationaleHandler rationaleHandler = new PermissionRationaleHandler() {
            @Override
            public boolean showRationaleBeforeRequestingPermission() {
                return true;
            }

            @Override
            public boolean showRationaleAfterRefusingPermission() {
                return false;
            }
        };

        permission.requestAppPermission(Manifest.permission.ACCESS_FINE_LOCATION, 100, requestHandler, rationaleHandler);

        waitUntilPermissionDialogIsDisplayed();
        clickRejectPermission();
    }

    @SdkSuppress(minSdkVersion = 23)
    @Test(timeout = 10000)
    public void testPermission3() throws UiObjectNotFoundException, InterruptedException {
        final CountDownLatch pmr1Executed = new CountDownLatch(1);
        final CountDownLatch pmr2Executed = new CountDownLatch(1);

        final String GOT_PERMISISON_2_RESULT = "pmr21";
        final String GOT_PERMISSION_2_RATIONALE = "pmr10";
        final String EXPECT_PERMISSION_2 = "epmr2";

        final Map<String, Boolean> result = new HashMap<>();

        result.put(GOT_PERMISISON_2_RESULT, false);
        result.put(GOT_PERMISSION_2_RATIONALE, false);
        result.put(EXPECT_PERMISSION_2, false);

        PermissionRequestHandler requestHandler = new PermissionRequestHandler() {
            @Override
            public void onShowPermissionRationale(String permissionName, @NonNull PermissionRationaleResult rationaleResult) {
                result.put(GOT_PERMISSION_2_RATIONALE, true);
                result.put(EXPECT_PERMISSION_2, true);

                rationaleResult.onContinue();

                pmr1Executed.countDown();
            }

            @Override
            public void onNeverAskAgainPermission(String permissionName, int currentPermissionStatus) {
                fail();
            }

            @Override
            public void onPermissionRequestInterrupted(String permissionName) {
                fail();
            }

            @Override
            public void onPermissionRequestResult(String permissionName, int permissionStatus) {
                if (result.get(EXPECT_PERMISSION_2)) {
                    result.put(GOT_PERMISISON_2_RESULT, PackageManager.PERMISSION_DENIED == permissionStatus);

                    assertTrue(result.get(GOT_PERMISSION_2_RATIONALE));
                    assertTrue(result.get(GOT_PERMISISON_2_RESULT));

                    pmr2Executed.countDown();
                }
            }
        };

        PermissionRationaleHandler rationaleHandler = new PermissionRationaleHandler() {
            @Override
            public boolean showRationaleBeforeRequestingPermission() {
                return false;
            }

            @Override
            public boolean showRationaleAfterRefusingPermission() {
                return true;
            }
        };

        permission.requestAppPermission(Manifest.permission.ACCESS_FINE_LOCATION, 100, requestHandler, rationaleHandler);

        waitUntilPermissionDialogIsDisplayed();
        clickRejectPermission();

        pmr1Executed.await();

        waitUntilPermissionDialogIsDisplayed();
        clickRejectPermission();

        pmr2Executed.await();
    }

    @SdkSuppress(minSdkVersion = 23)
    @Test(timeout = 10000)
    public void testPermission4() throws UiObjectNotFoundException, InterruptedException {
        final CountDownLatch pmr1Executed = new CountDownLatch(1);
        final CountDownLatch pmr2Executed = new CountDownLatch(1);

        final String EXPECT_PMR_2 = "epmr1";
        final String GOT_PERMISSION_1_RATIONALE = "gpmr10";
        final String GOT_PERMISSION_2_RATIONALE = "gpmr20";
        final String GOT_PERMISSION_2_RESULT = "gpmr21";
        final Map<String, Boolean> result = new HashMap<>();

        result.put(GOT_PERMISSION_2_RESULT, false);
        result.put(GOT_PERMISSION_1_RATIONALE, false);
        result.put(GOT_PERMISSION_2_RATIONALE, false);
        result.put(EXPECT_PMR_2, false);

        PermissionRequestHandler requestHandler = new PermissionRequestHandler() {
            @Override
            public void onShowPermissionRationale(String permissionName, @NonNull PermissionRationaleResult rationaleResult) {
                if (result.get(EXPECT_PMR_2)) {
                    result.put(GOT_PERMISSION_2_RATIONALE, true);

                    rationaleResult.onContinue();

                } else {
                    result.put(EXPECT_PMR_2, true);
                    result.put(GOT_PERMISSION_1_RATIONALE, true);

                    rationaleResult.onContinue();

                    pmr1Executed.countDown();
                }
            }

            @Override
            public void onNeverAskAgainPermission(String permissionName, int currentPermissionStatus) {
                fail();
            }

            @Override
            public void onPermissionRequestInterrupted(String permissionName) {
                fail();
            }

            @Override
            public void onPermissionRequestResult(String permissionName, int permissionStatus) {
                if (result.get(EXPECT_PMR_2)) {
                    result.put(GOT_PERMISSION_2_RESULT, PackageManager.PERMISSION_DENIED == permissionStatus);

                    assertTrue(result.get(GOT_PERMISSION_1_RATIONALE));

                    assertTrue(result.get(GOT_PERMISSION_2_RATIONALE));
                    assertTrue(result.get(GOT_PERMISSION_2_RESULT));

                    pmr2Executed.countDown();
                }
            }
        };

        PermissionRationaleHandler rationaleHandler = new PermissionRationaleHandler() {
            @Override
            public boolean showRationaleBeforeRequestingPermission() {
                return true;
            }

            @Override
            public boolean showRationaleAfterRefusingPermission() {
                return true;
            }
        };

        permission.requestAppPermission(Manifest.permission.ACCESS_FINE_LOCATION, 100, requestHandler, rationaleHandler);

        waitUntilPermissionDialogIsDisplayed();
        clickRejectPermission();

        pmr1Executed.await();

        waitUntilPermissionDialogIsDisplayed();
        clickRejectPermission();

        pmr2Executed.await();
    }

    @SdkSuppress(minSdkVersion = 23)
    @Test(timeout = 3000)
    public void testPermissionLast() throws UiObjectNotFoundException {
        PermissionRequestHandler requestHandler = new PermissionRequestHandler() {
            @Override
            public void onShowPermissionRationale(String permissionName, @NonNull PermissionRationaleResult rationaleResult) {
                fail();
            }

            @Override
            public void onNeverAskAgainPermission(String permissionName, int currentPermissionStatus) {
                fail();
            }

            @Override
            public void onPermissionRequestInterrupted(String permissionName) {
                fail();
            }

            @Override
            public void onPermissionRequestResult(String permissionName, int permissionStatus) {
                assertEquals(PackageManager.PERMISSION_GRANTED, permissionStatus);
            }
        };

        PermissionRationaleHandler rationaleHandler = new PermissionRationaleHandler() {
            @Override
            public boolean showRationaleBeforeRequestingPermission() {
                return false;
            }

            @Override
            public boolean showRationaleAfterRefusingPermission() {
                return false;
            }
        };

        permission.requestAppPermission(Manifest.permission.ACCESS_FINE_LOCATION, 100, requestHandler, rationaleHandler);

        waitUntilPermissionDialogIsDisplayed();
        clickAllowPermission();
    }

    @SdkSuppress(minSdkVersion = 23)
    @Test(timeout = 3000)
    public void testPermissionLast2() throws UiObjectNotFoundException, InterruptedException {
        final CountDownLatch pmr1AlreadyGranted = new CountDownLatch(1);

        PermissionRequestHandler requestHandler = new PermissionRequestHandler() {
            @Override
            public void onShowPermissionRationale(String permissionName, @NonNull PermissionRationaleResult rationaleResult) {
                fail();
            }

            @Override
            public void onNeverAskAgainPermission(String permissionName, int currentPermissionStatus) {
                fail();
            }

            @Override
            public void onPermissionRequestInterrupted(String permissionName) {
                fail();
            }

            @Override
            public void onPermissionRequestResult(String permissionName, int permissionStatus) {
                assertEquals(PackageManager.PERMISSION_GRANTED, permissionStatus);

                pmr1AlreadyGranted.countDown();
            }
        };

        PermissionRationaleHandler rationaleHandler = new PermissionRationaleHandler() {
            @Override
            public boolean showRationaleBeforeRequestingPermission() {
                return false;
            }

            @Override
            public boolean showRationaleAfterRefusingPermission() {
                return false;
            }
        };

        assertEquals(PackageManager.PERMISSION_GRANTED, ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION));

        permission.requestAppPermission(Manifest.permission.ACCESS_FINE_LOCATION, 100, requestHandler, rationaleHandler);

        pmr1AlreadyGranted.await();
    }

    private UiObject clickPermissionDialog(String buttonText) throws UiObjectNotFoundException {
        if (Build.VERSION.SDK_INT >= 23) {
            UiObject uiObject = device.findObject(new UiSelector().clickable(true).enabled(true).textContains(buttonText));
            if (uiObject.exists()) {
                uiObject.click();
            }
            return uiObject;
        }
        return null;
    }

    private UiObject clickAllowPermission() throws UiObjectNotFoundException {
        return clickPermissionDialog("ALLOW");
    }

    private UiObject clickRejectPermission() throws UiObjectNotFoundException {
        return clickPermissionDialog("DENY");
    }

    private UiObject clickRejectPermanentlyPermission() throws UiObjectNotFoundException {
        return clickPermissionDialog("Never ask again");
    }

    private void waitUntilPermissionDialogIsDisplayed() {
        instrumentation.waitForIdleSync();

        if (Build.VERSION.SDK_INT >= 23) {
            //UiObject permissionDialogProof1 = device.findObject(new UiSelector().clickable(true).enabled(true).textContains("Never Ask Again"));
            UiObject permissionDialogProof2 = device.findObject(new UiSelector().clickable(true).enabled(true).textContains("ALLOW"));
            UiObject permissionDialogProof3 = device.findObject(new UiSelector().clickable(true).enabled(true).textContains("DENY"));

            boolean isPermissionDialogDisplayed = false;

            while (!isPermissionDialogDisplayed) {
                isPermissionDialogDisplayed = /*permissionDialogProof1.exists() && */ permissionDialogProof2.exists() && permissionDialogProof3.exists();
            }
        } else {
            // Not supported
        }
    }

    private static void resetPermission(String packageName, String permissionName) throws IOException, InterruptedException {
        // adb shell pm revoke com.your.package android.permission.WRITE_EXTERNAL_STORAGE
        Log.d(packageName, "Resetting permissions");
        Process process = Runtime.getRuntime().exec("su");
        DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
        outputStream.writeBytes("pm revoke " + packageName + " " + permissionName + ";\n");
        outputStream.flush();
        outputStream.writeBytes("exit\n");
        outputStream.flush();
        outputStream.close();

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            Log.d(packageName, "Command Exit Code: " + process.exitValue());

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                Log.d(packageName, line);
            }
        }


    }
}
