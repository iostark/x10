/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.base.beta.internal;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.common.io.ByteStreams;
import com.iostark.x10.base.ModuleConfig;
import com.iostark.x10.base.annotation.api.API_Internal;
import com.iostark.x10.base.logging.Logger;
import com.iostark.x10.base.logging.LoggingRegistry;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@API_Internal
public class AndroidResource {
  private final Context context;

  private static Logger log;

  static {
    log = LoggingRegistry.getLoggerOrCreate(ModuleConfig.MODULE_NAME);
  }

  public AndroidResource(final Context context) {
    this.context = context;
  }

  private File getDefaultExternalStoragePath() {
    final ContextWrapper contextWrapper = new ContextWrapper(this.context);
    return contextWrapper.getFilesDir();
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  public File getDefaultFilePath(final String filename) throws IOException {
    return new File(getDefaultExternalStoragePath().getCanonicalPath() + File.separator + filename);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  public AndroidResource copyToDefaultExternalStorage(
      final InputStream inputStream, final String filename) throws IOException {
    copyTo(inputStream, getDefaultFilePath(filename).getCanonicalPath());
    return this;
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  public AndroidResource copyRawResourceTo(final int resourceId, final String filename)
      throws IOException {
    final InputStream inputStream = context.getResources().openRawResource(resourceId);
    copyToDefaultExternalStorage(inputStream, filename);
    return this;
  }

  @SuppressWarnings("PMD.LongVariable")
  public AndroidResource copyTo(
      final InputStream inputStream, final String absoluteFilePathDestination) throws IOException {
    final File targetFile = new File(absoluteFilePathDestination);
    final FileOutputStream outputStream = new FileOutputStream(targetFile);
    final long bytesCopied = ByteStreams.copy(inputStream, outputStream);
    log.d("Bytes copied: %d", bytesCopied);
    return this;
  }

  public Bitmap getBitmapFromDrawableResource(final int resourceId) {
    final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
    return bitmap;
  }
}
