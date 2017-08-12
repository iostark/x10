/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.base.annotation.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Signifies that a public API (public class, method or field) is subject to incompatible changes,
 * or even removal, in a future release. An API bearing this annotation is exempt from any
 * compatibility guarantees made by its containing library. Note that the presence of this
 * annotation may also imply about the quality or performance of the API in question, not only the
 * fact that it is not "API-frozen."
 *
 * <p>You are strongly advised to no rely on this API but you can use it at your own risk.
 */
@Retention(RetentionPolicy.CLASS)
@Target({
  ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.FIELD,
  ElementType.METHOD,
  ElementType.TYPE
})
@Documented
public @interface API_Beta {}
