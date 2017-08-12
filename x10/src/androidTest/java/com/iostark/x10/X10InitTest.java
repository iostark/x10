/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10;

import android.support.test.runner.AndroidJUnit4;
import com.iostark.x10.base.androidtest.ActivityInstrumentedTest;
import com.iostark.x10.base.androidtest.AsyncAssert;
import java.util.concurrent.TimeUnit;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class X10InitTest extends ActivityInstrumentedTest<X10TestActivity> {

  public X10InitTest() {
    super(X10TestActivity.class);
  }

  @Before
  @Override
  public void setUp() throws Exception {
    super.setUp();
  }

  @After
  @Override
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  public void testFOSSLicence() throws Exception {
    final AsyncAssert asyncTest = new AsyncAssert();

    X10Init data = new X10Init().useContext(activity.getApplicationContext()).useFOSSLicense();

    X10.init(
        data,
        new X10InitializationCallback() {
          @Override
          public void onFailure(Exception exception) {
            asyncTest.setAssertion(
                new AsyncAssert.Assertable() {
                  @Override
                  public void evaluate() {
                    Assert.fail();
                  }
                });
          }

          @Override
          public void onSuccess() {
            asyncTest.setAssertion(
                new AsyncAssert.Assertable() {
                  @Override
                  public void evaluate() {
                    Assert.assertTrue(true);
                  }
                });
          }
        });

    asyncTest.waitForLimitedTimeForResult(10, TimeUnit.SECONDS);
  }

  @Test
  public void testCommercialLicence() throws Exception {
    final AsyncAssert asyncTest = new AsyncAssert();

    X10Init data =
        new X10Init().useContext(activity.getApplicationContext()).useCommercialLicense("");

    X10.init(
        data,
        new X10InitializationCallback() {
          @Override
          public void onFailure(Exception exception) {
            asyncTest.setAssertion(
                new AsyncAssert.Assertable() {
                  @Override
                  public void evaluate() {
                    Assert.fail();
                  }
                });
          }

          @Override
          public void onSuccess() {
            asyncTest.setAssertion(
                new AsyncAssert.Assertable() {
                  @Override
                  public void evaluate() {
                    Assert.assertTrue(true);
                  }
                });
          }
        });

    asyncTest.waitForLimitedTimeForResult(10, TimeUnit.SECONDS);
  }
}
