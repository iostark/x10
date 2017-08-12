/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.threading;

import android.os.Handler;
import android.os.Looper;
import com.iostark.x10.base.annotation.api.API_Internal;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/** */
@API_Internal
public class UIThreadExecutor implements Executor {
  private static final Handler HANDLER = new Handler(Looper.getMainLooper());

  @Override
  @SuppressWarnings("PMD.AvoidThrowingNullPointerException")
  public void execute(final Runnable command) {
    if (command == null) {
      throw new NullPointerException();
    }

    final boolean isInQueue = HANDLER.post(command);

    if (!isInQueue) {
      throw new RejectedExecutionException();
    }
  }
}
