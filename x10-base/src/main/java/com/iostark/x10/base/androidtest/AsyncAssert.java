/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.base.androidtest;

import android.support.annotation.NonNull;

import junit.framework.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Utility class to assert asynchronously.
 *
 * @since V0_5_0
 */
public class AsyncAssert {

  private CountDownLatch latch;
  private Assertable assertion = new FailAssertable();

  private static class FailAssertable implements Assertable {
    @Override
    public void evaluate() {
      Assert.fail("No assertion provided. Call AsyncAssert.setAssertion");
    }
  }

  /**
   * Constructor.
   *
   * @since V0_5_0
   */
  public AsyncAssert() {
    this(new CountDownLatch(1));
  }

  /**
   * Constructor with specific latch used a condition to wait for results.
   *
   * @param latch Latch instance
   * @since V0_5_0
   */
  public AsyncAssert(final @NonNull CountDownLatch latch) {
    this.latch = latch;
  }

  /**
   * Set the assertion to be executed.
   *
   * @param assertion Assertion to be evaluated
   * @since V0_5_0
   */
  public void setAssertion(final Assertable assertion) {
    this.assertion = assertion;
    this.latch.countDown();
  }

  /**
   * Wait for an assertion to be specified and then evaluate it.
   *
   * @throws InterruptedException If waiting for assertion is interrupted
   * @since V0_5_0
   */
  public void waitForResult() throws InterruptedException {
    this.latch.await();
    this.assertion.evaluate();
  }

  /**
   * Wait for an assertion to be specified and then evaluate it.
   *
   * @param timeout Timeout in TimeUnit to wait before trying to evaluate the assertion
   * @param timeUnit Time Unit for timeout
   * @throws InterruptedException If waiting for assertion is interrupted
   * @since V0_5_0
   */
  public void waitLimitedTimeForResult(final long timeout, final TimeUnit timeUnit)
      throws InterruptedException {
    final boolean countDownIsZero = this.latch.await(timeout, timeUnit);
    if (countDownIsZero) {
      this.assertion.evaluate();
    }
  }
}
