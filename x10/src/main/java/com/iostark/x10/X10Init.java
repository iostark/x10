/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.common.base.Preconditions;

/**
 * Data to initialize the library.
 *
 * @since V0_5_0
 */
public class X10Init {

  /**
   * Get context lasting the duration of the application.
   *
   * @return Context
   * @since V0_5_0
   */
  public Context getContext() {
    return context;
  }

  private Context context;
  private Licensing licensing = Licensing.OpenSource;

  /**
   * Constructor.
   *
   * @since V0_5_0
   */
  public X10Init() {}

  /**
   * Set a context existing for the duration of the application.
   *
   * @param context Context
   * @return The current object
   * @since V0_5_0
   */
  public X10Init useContext(@NonNull final Context context) {
    Preconditions.checkNotNull(context, "Context cannot be null");
    Preconditions.checkArgument(
        context instanceof Application, "Context must be Application wide context");
    this.context = context;
    return this;
  }

  /**
   * Declare this library is used in a non commercial project. Read LICENSES.md for details
   *
   * @return The current object
   * @since V0_5_0
   */
  public X10Init useFOSSLicense() {
    this.licensing = Licensing.OpenSource;
    return this;
  }

  /**
   * Declare this library is used in a commercial project. Read LICENSES.md for details
   *
   * @param licenseData Licencing data
   * @return The current object
   * @since V0_5_0
   */
  public X10Init useCommercialLicense(@NonNull final String licenseData) {
    Preconditions.checkNotNull(licenseData, "No license data provided");
    // FIXME What to do with the license data ?
    this.licensing = Licensing.Commercial;
    return this;
  }

  /**
   * Get licensing mode.
   *
   * @return The licensing mode
   * @since V0_5_0
   */
  public Licensing getLicense() {
    return this.licensing;
  }
}
