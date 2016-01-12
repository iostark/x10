package com.iostark.x10.media;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.iostark.x10.media.picker.MediaPickerListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasPackage;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by davidandreoletti on 10/01/2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MediaPickerWithDefaultCameraSourceTest {

    @Rule
    public IntentsTestRule<MediaPickerActivity> mIntentsRule = new IntentsTestRule<MediaPickerActivity>(MediaPickerActivity.class);

    private static final String CAMERA_APP_PKG_NAME = "com.android.camera";
    private MediaPickerActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = mIntentsRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        // Call finish() on all activities in @After to avoid exceptions in
        // later calls to getActivity() in subsequent tests
        activity.finish();
    }

    @Test
    public void testWithImagePicked() {
        // Build a result to return from the Camera app
        Intent intent = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(toPackage(CAMERA_APP_PKG_NAME)).respondWith(result);

        activity.pickMedia(new MediaPickerListener<String>() {
            @Override
            public void onMediaAvailable(@NonNull String media) {
                assertTrue(media.length() > 0);
            }

            @Override
            public void onCancel() {
                fail();
            }

            @Override
            public void onError(Exception exception) {
                fail(exception.toString());
            }
        });

        // We can also validate that an intent resolving to the "camera" activity has been sent out by our app
        intended(toPackage(CAMERA_APP_PKG_NAME));
    }

    @Test
    public void testWithImageNotPicked() {
        // Build a result to return from the Camera app
        Intent intent = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_CANCELED, intent);

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(toPackage(CAMERA_APP_PKG_NAME)).respondWith(result);

        activity.pickMedia(new MediaPickerListener<String>() {
            @Override
            public void onMediaAvailable(@NonNull String media) {
                fail();
            }

            @Override
            public void onCancel() {
                assertTrue(true);
            }

            @Override
            public void onError(Exception exception) {
                fail(exception.toString());
            }
        });

        // We can also validate that an intent resolving to the "camera" activity has been sent out by our app
        intended(toPackage(CAMERA_APP_PKG_NAME));
    }

    @Test
    public void testWithUnknownError() {
        assertTrue(true);
    }
}
