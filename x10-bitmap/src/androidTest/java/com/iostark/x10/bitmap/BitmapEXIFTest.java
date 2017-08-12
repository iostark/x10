/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.bitmap;

import static org.junit.Assert.assertNotNull;

import android.graphics.Bitmap;
import android.support.test.filters.Suppress;
import android.support.test.runner.AndroidJUnit4;
import com.iostark.android.x10.bitmap.test.R;
import com.iostark.x10.base.androidtest.ContextInstrumentedTest;
import com.iostark.x10.base.beta.internal.AndroidResource;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BitmapEXIFTest extends ContextInstrumentedTest {
  @Test
  @Suppress
  public void sampleRotateBitmapToEXIFOrientationValueNumber1() throws IOException {
    AndroidResource androidResource = new AndroidResource(getContext());
    Bitmap bitmap = androidResource.getBitmapFromDrawableResource(R.drawable.default_image);
    BitmapEXIFOperation operation = new BitmapEXIFOperation(bitmap);

    Bitmap image = null; //= operation.rotate()

    assertNotNull(image);
  }
}
