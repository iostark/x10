/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.base.androidtest;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import com.iostark.x10.base.annotation.api.API_Internal;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Context instrumented test.
 *
 * @since V0_5_0
 */
@API_Internal
public class ContextInstrumentedTest {
  /**
   * Get instrumented context.
   *
   * @return Context instance
   * @since V0_5_0
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  public Context getContext() {
    final Context context = InstrumentationRegistry.getContext();
    context.getResources();
    return context;
  }

  /**
   * Get Resources.
   *
   * @return Resources
   * @since V0_5_0
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  public Resources getResources() {
    final Context context = getContext();
    final Resources res = context.getResources();
    return res;
  }

  /**
   * Example test.
   *
   * @since V0_5_0
   */
  @Test
  public void example() {
    Assert.assertTrue(true);
  }
}
