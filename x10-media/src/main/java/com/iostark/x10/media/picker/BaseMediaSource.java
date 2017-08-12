/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.media.picker;

import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/**
 * Base class for all framework provided {@link MediaSource} implementations.
 *
 * @since V0_5_0
 */
@KeepEntryPoint
public abstract class BaseMediaSource<MediaType> implements MediaSource<MediaType> {
  /** Constructor. */
  @KeepEntryPoint
  public BaseMediaSource() {}
}
