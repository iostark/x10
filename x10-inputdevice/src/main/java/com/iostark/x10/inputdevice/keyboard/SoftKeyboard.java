/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.inputdevice.keyboard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.iostark.x10.base.annotation.api.API_Beta;
import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * SoftKeyboard hide/show management.
 *
 * <p>Usage: <code>
 *      // Show the keyboard
 *      new SoftKeyboard(activity).showKeyboard(activity.getTextView(), 0, null);
 *      // Hide the keyboard
 *      new SoftKeyboard(activity).hideKeyboard(activity.getTextView(), 0, null);
 * </code>
 *
 * @since V0_5_0
 */
@API_Beta
@KeepEntryPoint
public class SoftKeyboard {

  private Activity context;

  /**
   * Constructor.
   *
   * @param activity Activity to show/hide keyboard
   * @since V0_5_0
   */
  @KeepEntryPoint
  public SoftKeyboard(final @NonNull Activity activity) {
    this.context = activity;
  }

  /**
   * Shows keyboard.
   *
   * @param view View to request focus for before showing the keyboard. If null, no focus is
   *     requested
   * @param flags Valid values: 0, {@link InputMethodManager#SHOW_FORCED}, {@link
   *     InputMethodManager#SHOW_IMPLICIT}
   * @since V0_5_0
   */
  @KeepEntryPoint
  public void showKeyboard(final @Nullable View view, final int flags) {
    showKeyboard(context, view, 0, null);
  }

  /**
   * Shows keyboard.
   *
   * @param view View to request focus for before showing the keyboard. If null, no focus is
   *     requested
   * @param flags Valid values: 0, {@link InputMethodManager#SHOW_FORCED}, {@link
   *     InputMethodManager#SHOW_IMPLICIT}
   * @param resultReceiver If null, a result receiver will be created to be executed on the UI
   *     Thread
   * @since V0_5_0
   */
  @KeepEntryPoint
  public void showKeyboard(
      final @Nullable View view, final int flags, final @Nullable ResultReceiver resultReceiver) {
    showKeyboard(context, view, flags, resultReceiver);
  }

  private void showKeyboard(
      final @NonNull Activity activity,
      final @Nullable View view,
      final int flags,
      final @Nullable ResultReceiver resultReceiver) {
    final InputMethodManager inputMethodManager =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

    if (view != null) {
      view.requestFocus();
    }

    ResultReceiver receiver = resultReceiver;
    if (resultReceiver == null) {
      receiver = new ResultReceiver(new Handler(Looper.getMainLooper()));
    }
    inputMethodManager.showSoftInput(view, flags, receiver);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  private void hideKeyboard(
      final @NonNull Activity activity,
      final @Nullable View view,
      final int flags,
      final @Nullable ResultReceiver resultReceiver) {
    final InputMethodManager inputMethodManager =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

    @SuppressFBWarnings(value = "SIC_INNER_SHOULD_BE_STATIC_ANON")
    class WrapperResultReceiver extends ResultReceiver {
      public WrapperResultReceiver(final Handler handler) {
        super(handler);
      }

      @Override
      public void send(final int resultCode, final Bundle resultData) {
        if (view != null) {
          view.clearFocus();
        }

        super.send(resultCode, resultData);

        if (resultReceiver != null) {
          resultReceiver.send(
              resultCode, resultData); // Will call resultReceiver.onReceiveResult(...)
        }
      }
    }

    final ResultReceiver resultReceiverWrapper =
        new WrapperResultReceiver(new Handler(Looper.getMainLooper()));

    final IBinder windowToken =
        view != null ? view.getWindowToken() : context.getCurrentFocus().getWindowToken();
    inputMethodManager.hideSoftInputFromWindow(windowToken, flags, resultReceiverWrapper);
  }

  /**
   * Hides the keyboard.
   *
   * @param view View to clear focus from after hiding keyboard
   * @param flags Valid values: 0, {@link InputMethodManager#HIDE_IMPLICIT_ONLY}, {@link
   *     InputMethodManager#HIDE_NOT_ALWAYS}
   * @param resultReceiver If null, a result receiver will be created to be executed on the UI
   *     Thread
   * @since V0_5_0
   */
  @KeepEntryPoint
  public void hideKeyboard(
      final @Nullable View view, final int flags, final @Nullable ResultReceiver resultReceiver) {
    hideKeyboard(context, view, flags, resultReceiver);
  }

  /**
   * Hides the keyboard.
   *
   * @param flags Valid values: 0, {@link InputMethodManager#HIDE_IMPLICIT_ONLY}, {@link
   *     InputMethodManager#HIDE_NOT_ALWAYS}
   * @since V0_5_0
   */
  @KeepEntryPoint
  public void hideKeyboard(final int flags) {
    hideKeyboard(context, null, flags, null);
  }
}
