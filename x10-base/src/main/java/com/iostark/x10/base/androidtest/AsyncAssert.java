package com.iostark.x10.base.androidtest;

import android.support.annotation.NonNull;

import junit.framework.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Utility class to assert asynchronously.
 * @since V0_5_0
 */
public class AsyncAssert {

    private CountDownLatch latch;
    private Assertable assertion;

    {
        assertion = new Assertable() {
            @Override
            public void evaluate() {
                Assert.fail("No assertion provided. Call AsyncAssert.setAssertion");
            }
        };
    }

    /**
     * Constructor.
     * @since V0_5_0
     */
    public AsyncAssert() {
        this(new CountDownLatch(1));
    }

    /**
     * Constructor with specific latch used a condition to wait for results.
     * @param latch Latch instance
     * @since V0_5_0
     */
    public AsyncAssert(final @NonNull CountDownLatch latch) {
        this.latch = latch;
    }

    /**
     * Set the assertion to be executed.
     * @since V0_5_0
     * @param assertion Assertion to be evaluated
     */
    public void setAssertion(Assertable assertion) {
        this.assertion = assertion;
        this.latch.countDown();
    }

    /**
     * Wait for an assertion to be specified and then evaluate it.
     * @since V0_5_0
     * @throws InterruptedException If waiting for assertion is interrupted
     */
    public void waitForResult() throws InterruptedException {
        this.latch.await();
        this.assertion.evaluate();
    }


    /**
     * Wait for an assertion to be specified and then evaluate it.
     * @param timeout Timeout in TimeUnit to wait before trying to evaluate the assertion
     * @param timeUnit Time Unit for timeout
     * @since V0_5_0
     * @throws InterruptedException If waiting for assertion is interrupted
     */
    public void waitForLimitedTimeForResult(long timeout, TimeUnit timeUnit) throws InterruptedException {
        this.latch.await(timeout, timeUnit);
        this.assertion.evaluate();
    }

    /**
     * Some code with an assertion inside.
     * @since V0_5_0
     */
    public interface Assertable {
        /**
         * Evaluate assertion.
         * @since V0_5_0
         */
        void evaluate();
    }
}
