/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10;

/**
 * Callback for the library initialization.
 *
 * @since V0_5_0
 */
public interface X10InitializationCallback {
  /**
   * Called when the SDK initialization failed.
   *
   * @param exception Reason (as an Exception)
   * @since V0_5_0
   */
  void onFailure(Exception exception);

  /**
   * Called when the SDK initialization succeeds.
   *
   * @since V0_5_0
   */
  void onSuccess();
}
