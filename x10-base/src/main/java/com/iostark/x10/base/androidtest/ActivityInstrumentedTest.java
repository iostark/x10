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

/**
 * Activity Testing.
 *
 * @param <T> Instrumented Activity
 * @since V0_5_0
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
@API_Internal
public abstract class ActivityInstrumentedTest<T extends Activity> {

    /**
     * Activity rule.
     *
     * @since V0_5_0
     */
    @Rule
    public ActivityTestRule<T> activityRule;

    /**
     * Instrumentation context.
     * @since V0_5_0
     */
    @SuppressFBWarnings("URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD")
    protected Instrumentation instrumentation;

    /**
     * Instrumented activity.
     * @since V0_5_0
     */
    @SuppressFBWarnings("UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR")
    protected T activity;

    /**
     * Constructor.
     *
     * @param tClass Activity to be instrumented
     * @since V0_5_0
     */
    protected ActivityInstrumentedTest(final Class<T> tClass) {
        activityRule = new ActivityTestRule<>(tClass);
    }

    /**
     * Setup test.
     *
     * @throws Exception If an unknow exception happens
     * @since V0_5_0
     */
    public void setUp() throws Exception {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        activity = activityRule.getActivity();
    }

    /**
     * Tear down test.
     *
     * @throws Exception If an unknow exception happens
     * @since V0_5_0
     */
    public void tearDown() throws Exception {
        // Call finish() on all activities in @After to avoid exceptions in
        // later calls to getActivity() in subsequent tests
        activity.finish();
        instrumentation = null;
    }

    /**
     * Dummy example.
     *
     * @since V0_5_0
     */
    @Test
    public void example() {
        Assert.assertTrue(true);
    }
}
