/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.media.picker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;
import com.iostark.x10.base.id.internal.GlobalNumberIdentifierGenerator;
import com.iostark.x10.base.internal.X10Bundle;
import com.iostark.x10.media.ModuleConfig;

/**
 * A source obtaining a media from the phone's gallery.
 *
 * @since V0_5_0
 */
@KeepEntryPoint
public class DefaultGallerySource<MediaType> extends BaseMediaSource<MediaType> {
  private static final int REQUEST_CODE =
      GlobalNumberIdentifierGenerator.INT_IDENTIFIER_GENERATOR.next().intValue();
  private static final String REQUEST_CODE_KEY = "request_code";

  private Activity activity;
  private boolean isMediaAvailable;
  private String expectedMediaPath;
  private int requestedCode;

  /**
   * Constructor.
   *
   * @param requestCode Request code, to identify result from the Android OS
   * @since V0_5_0
   */
  @KeepEntryPoint
  public DefaultGallerySource(final int requestCode) {
    this.requestedCode = requestCode;
  }

  /**
   * Constructor.
   *
   * <p>The request code is generated automatically
   *
   * @since V0_5_0
   */
  @KeepEntryPoint
  public DefaultGallerySource() {
    this(REQUEST_CODE);
  }

  @Override
  @SuppressWarnings("PMD.SignatureDeclareThrowsException")
  @KeepEntryPoint
  public void beginPick(final @NonNull Activity activity) throws Exception {
    isMediaAvailable = false;
    this.activity = activity;
    pickMediaFromGallery(requestedCode, activity);
  }

  @Override
  @SuppressWarnings("PMD.SignatureDeclareThrowsException")
  @KeepEntryPoint
  public void pickResult(final @Nullable Intent intent, final int requestCode, final int resultCode)
      throws Exception {
    if (requestCode != requestedCode) {
      isMediaAvailable = false;
      return;
    }

    if (resultCode != Activity.RESULT_OK) {
      isMediaAvailable = false;
    } else {
      handleMediaFromGallery(intent);
    }
  }

  @Override
  @SuppressWarnings("PMD.AvoidCatchingGenericException")
  @KeepEntryPoint
  public void endPick(final MediaSourceResult<MediaType> result) {
    if (!isMediaAvailable) {
      result.onMediaUnavailable();
    } else {
      try {
        final MediaType media = convertMediaPathToMediaType(expectedMediaPath);
        result.onMediaAvailable(media);
      } catch (Exception e) {
        result.onError(e);
      }
    }
  }

  @Override
  @SuppressWarnings("PMD.LawOfDemeter")
  @KeepEntryPoint
  public void onSaveInstanceState(final Bundle outState) {
    final X10Bundle x10Bundle =
        new X10Bundle(
            outState, ModuleConfig.MODULE_NAME, DefaultGallerySource.class.getSimpleName());
    x10Bundle.putInt(REQUEST_CODE_KEY, requestedCode);
  }

  @Override
  @SuppressWarnings("PMD.LawOfDemeter")
  @KeepEntryPoint
  public void onRestoreInstanceState(final Bundle savedInstanceState) {
    final X10Bundle x10Bundle =
        new X10Bundle(
            savedInstanceState,
            ModuleConfig.MODULE_NAME,
            DefaultGallerySource.class.getSimpleName());
    requestedCode = x10Bundle.getInt(REQUEST_CODE_KEY);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  private void handleMediaFromGallery(final Intent data) {
    final Uri selectedMediaUri = data.getData();
    final String[] projection = {MediaStore.MediaColumns.DATA};
    final CursorLoader cursorLoader =
        new CursorLoader(activity, selectedMediaUri, projection, null, null, null);
    final Cursor cursor = cursorLoader.loadInBackground();
    final int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
    if (!cursor.moveToFirst()) {
      isMediaAvailable = false;
    } else {
      isMediaAvailable = true;
      expectedMediaPath = cursor.getString(column_index);
    }
  }

  private void pickMediaFromGallery(final int requestCode, final Activity activity) {
    final Intent intent =
        new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

    intent.setType("image/*");

    activity.startActivityForResult(
        Intent.createChooser(intent, "Select File"), // FIXME custom
        requestCode);
  }

  /**
   * Convert a media path to an instance of a media type.
   *
   * @param mediaPath Media path
   * @return Media type instance
   * @throws ClassCastException If casting a media path to MediaType fails
   * @since V0_5_0
   */
  @KeepEntryPoint
  protected MediaType convertMediaPathToMediaType(final String mediaPath)
      throws ClassCastException {
    try {
      return (MediaType) mediaPath;
    } catch (ClassCastException exception) {
      final ClassCastException wrappedException =
          new ClassCastException(
              "You should override DefaultGallerySource.convertMediaPathToMediaType(...)");
      wrappedException.initCause(exception);
      throw wrappedException;
    }
  }
}
