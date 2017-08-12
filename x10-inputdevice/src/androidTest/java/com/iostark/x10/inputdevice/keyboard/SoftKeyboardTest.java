/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.inputdevice.keyboard;

import static org.junit.Assert.*;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.runner.AndroidJUnit4;
import android.view.inputmethod.InputMethodManager;
import com.iostark.x10.base.androidtest.ActivityInstrumentedTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SoftKeyboardTest extends ActivityInstrumentedTest<SoftKeyboardActivity> {

  private CountingIdlingResource countingResource;

  public SoftKeyboardTest() {
    super(SoftKeyboardActivity.class);
  }

  @Before
  @Override
  public void setUp() throws Exception {
    super.setUp();

    countingResource = new CountingIdlingResource("Keyboard show up or hidden");
  }

  @After
  @Override
  public void tearDown() throws Exception {
    activity.finish();

    super.tearDown();
  }

  @Test(timeout = 3000)
  public void testShowKeyboard() {
    ResultReceiver resultReceiver =
        new ResultReceiver(new Handler(Looper.getMainLooper())) {
          @Override
          protected void onReceiveResult(final int resultCode, Bundle resultData) {
            countingResource.decrement();
            assertTrue(resultCode == InputMethodManager.RESULT_SHOWN);

            super.onReceiveResult(resultCode, resultData);
          }
        };

    countingResource.increment();
    new SoftKeyboard(activity).showKeyboard(activity.getTextView(), 0, resultReceiver);
    // Espresso waits for the keyboard to show up (ie go idle) and then continues.
  }

  @Test(timeout = 3000)
  public void testHideKeyboard() {
    ResultReceiver showResultReceiver =
        new ResultReceiver(new Handler(Looper.getMainLooper())) {
          @Override
          protected void onReceiveResult(int resultCode, Bundle resultData) {
            ResultReceiver hideResultReceiver =
                new ResultReceiver(new Handler(Looper.getMainLooper())) {
                  @Override
                  protected void onReceiveResult(int resultCode, Bundle resultData) {
                    countingResource.decrement();
                    assertTrue(resultCode == InputMethodManager.RESULT_HIDDEN);
                    super.onReceiveResult(resultCode, resultData);
                  }
                };

            countingResource.increment();
            new SoftKeyboard(activity).hideKeyboard(activity.getTextView(), 0, hideResultReceiver);
            // Espresso waits for the keyboard to be hidden (ie go idle) and then continues.

            super.onReceiveResult(resultCode, resultData);
          }
        };

    new SoftKeyboard(activity).showKeyboard(activity.getTextView(), 0, showResultReceiver);
  }
}
