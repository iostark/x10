package com.iostark.x10.base.androidtest;

import android.support.annotation.NonNull;

import junit.framework.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Utility class to assert asynchronously
 * @since
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

    public AsyncAssert() {
        this(new CountDownLatch(1));
    }

    public AsyncAssert(final @NonNull CountDownLatch latch) {
        this.latch = latch;
    }

    public void setAssertion(Assertable assertion) {
        this.assertion = assertion;
        this.latch.countDown();
    }

    public void waitForResult() throws InterruptedException {
        this.latch.await();
        this.assertion.evaluate();
    }

    public void waitForLimitedTimeForResult(long timeout, TimeUnit timeUnit) throws InterruptedException {
        this.latch.await(timeout, timeUnit);
        this.assertion.evaluate();
    }

    public interface Assertable {
        void evaluate();
    }
}
