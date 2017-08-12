/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.bitmap;

import static org.junit.Assert.*;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;
import com.google.common.io.ByteStreams;
import com.iostark.android.x10.bitmap.test.R;
import com.iostark.x10.base.androidtest.ContextInstrumentedTest;
import com.iostark.x10.base.beta.internal.AndroidResource;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BitmapDecoderTest extends ContextInstrumentedTest {
  private int imageSize = 200;

  @Test
  public void sampleDecodeWithResourceId() {
    BitmapDecoder decoder = new BitmapDecoder();
    Bitmap sampledBitmap =
        decoder.decodeSampledBitmap(R.drawable.default_image, getResources(), imageSize, imageSize);

    assertNotNull(sampledBitmap);
    assertEquals(imageSize, sampledBitmap.getWidth());
    assertEquals(imageSize, sampledBitmap.getHeight());
  }

  @Test
  public void sampleDecodeWithFilePath() throws IOException {
    String filename = "filename.image";
    AndroidResource androidResource =
        new AndroidResource(getContext()).copyRawResourceTo(R.raw.default_image, filename);

    BitmapDecoder decoder = new BitmapDecoder();
    Bitmap sampledBitmap =
        decoder.decodeSampledBitmap(
            androidResource.getDefaultFilePath(filename).getCanonicalPath(), imageSize, imageSize);

    assertNotNull(sampledBitmap);
    assertEquals(imageSize, sampledBitmap.getWidth());
    assertEquals(imageSize, sampledBitmap.getHeight());
  }

  @Test
  public void sampleDecodeWithByteArray() throws IOException {
    BitmapDecoder decoder = new BitmapDecoder();

    InputStream inputStream = getResources().openRawResource(R.drawable.default_image);
    byte[] image = ByteStreams.toByteArray(inputStream);

    Bitmap sampledBitmap = decoder.decodeSampledBitmap(image, imageSize, imageSize);

    assertNotNull(sampledBitmap);
    assertEquals(imageSize, sampledBitmap.getWidth());
    assertEquals(imageSize, sampledBitmap.getHeight());
  }
}
