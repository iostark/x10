package com.iostark.x10.base.androidtest;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;

import com.iostark.x10.base.annotation.api.API_Internal;

import junit.framework.Assert;

import org.junit.Test;

@API_Internal
public class ContextInstrumentedTest {
    @SuppressWarnings("PMD.LawOfDemeter")
    public Context getContext() {
        final Context context = InstrumentationRegistry.getContext();
        context.getResources();
        return context;
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    public Resources getResources() {
        final Context context = getContext();
        final Resources res = context.getResources();
        return res;
    }

    @Test
    public void example() {
        Assert.assertTrue(true);
    }
}
