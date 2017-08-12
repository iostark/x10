/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.email.internal;

import com.iostark.x10.base.annotation.api.API_Internal;

@API_Internal
public interface EmailSender {
  void send(final Email email);

  void send(final Email email, final SendListener listener);

  interface SendListener {
    void onCancelled();

    void onProbablySent();

    void onSent();

    void onError(Exception exception);
  }
}
