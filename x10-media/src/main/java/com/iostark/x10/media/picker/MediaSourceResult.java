/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.media.picker;

import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/**
 * @param <MediaType> Media data type
 * @since V0_5_0
 */
@KeepEntryPoint
public interface MediaSourceResult<MediaType> {
  /**
   * Called when a media is available.
   *
   * @param media Media provided
   * @since V0_5_0
   */
  @KeepEntryPoint
  void onMediaAvailable(MediaType media);

  /**
   * Called when a media is not available.
   *
   * @since V0_5_0
   */
  @KeepEntryPoint
  void onMediaUnavailable();

  /**
   * Called when an error occurred.
   *
   * @param exception Exception
   * @since V0_5_0
   */
  @KeepEntryPoint
  void onError(Exception exception);
}
