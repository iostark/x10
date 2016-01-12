/*
 * Copyright (c) $year IO Stark
 */

package com.iostark.x10.base.androidtest;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.iostark.x10.base.annotation.api.API_Internal;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
@API_Internal
public abstract class ActivityInstrumentedTest<T extends Activity> {

    @Rule
    public ActivityTestRule<T> activityRule;

    @SuppressFBWarnings("URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD")
    protected Instrumentation instrumentation;
    @SuppressFBWarnings("UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR")
    protected T activity;

    protected ActivityInstrumentedTest(final Class<T> tClass) {
        activityRule = new ActivityTestRule<>(tClass);
    }

    public void setUp() throws Exception {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        activity = activityRule.getActivity();
    }

    public void tearDown() throws Exception {
        // Call finish() on all activities in @After to avoid exceptions in
        // later calls to getActivity() in subsequent tests
        activity.finish();
        instrumentation = null;
    }

    @Test
    public void example() {
        Assert.assertTrue(true);
    }
}
