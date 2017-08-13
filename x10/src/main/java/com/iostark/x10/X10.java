/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10;

import android.support.annotation.NonNull;
import com.iostark.android.x10.BuildConfig;
import com.iostark.x10.base.ModuleConfig;
import com.iostark.x10.base.logging.Logger;
import com.iostark.x10.base.logging.LoggingRegistry;

/**
 * X10 Library.
 *
 * @since V0_5_0
 */
public class X10 {
  private static X10Init initData;

  private static Logger log = LoggingRegistry.getLoggerOrCreate(ModuleConfig.MODULE_NAME);

  /**
   * Initialize the SDK.
   *
   * @param data Data used to initialise the library
   * @param sdkInitializationCallback Callback to get notify about the SDK initialization result
   * @since V0_5_0
   */
  public static void init(
      final @NonNull X10Init data, final X10InitializationCallback sdkInitializationCallback) {
    X10.initData = data;
    boolean isOpenSourceLicense = X10.initData.getLicense() == Licensing.OpenSource;
    log.i("X10: Using %s license", isOpenSourceLicense ? "OpenSource" : "Commercial");
    sdkInitializationCallback.onSuccess();
  }

  /**
   * X10 SDK Version.
   *
   * @since V0_5_0
   */
  public static class VERSION {
    /**
     * SDK version.
     *
     * @since V0_5_0
     */
    public static final int SDK_INT = BuildConfig.VERSION_CODE;

    /**
     * SDK version name.
     *
     * @since V0_5_0
     */
    public static final String SDK_STRING = BuildConfig.VERSION_NAME;
    /**
     * SDK source control hash.
     *
     * @since V0_5_0
     */
    public static final String SDK_SOURCE_CONTROL_HASH = BuildConfig.SDK_SOURCE_CONTROL_HASH;
  }
}
