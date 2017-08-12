/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.base.id.internal;

import com.iostark.x10.base.annotation.api.API_Internal;
import com.iostark.x10.base.id.SequenceGenerator;

@API_Internal
public class NumberIdentifierGenerator<T extends Number> implements SequenceGenerator<T> {
  private Number value;

  public NumberIdentifierGenerator(final T initialValue) {
    this.value = initialValue;
  }

  @Override
  public T next() {
    if (value instanceof Long) {
      nextLong();
    } else if (value instanceof Integer) {
      nextInteger();
    } else if (value instanceof Short) {
      nextShort();
    } else {
      nextDouble();
    }
    onNewIdentifier((T) value);
    return (T) value;
  }

  private void nextLong() {
    long value = this.value.longValue();
    ++value;
    this.value = value;
  }

  private void nextInteger() {
    int value = this.value.intValue();
    ++value;
    this.value = value;
  }

  private void nextShort() {
    short value = this.value.shortValue();
    ++value;
    this.value = value;
  }

  private void nextDouble() {
    double value = this.value.doubleValue();
    ++value;
    this.value = value;
  }

  public void onNewIdentifier(final T newValue) {}
}
