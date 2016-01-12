/*
 * Copyright (c) $year IO Stark
 */

package com.iostark.x10.base.annotation.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Signifies that this API (class, method or field) must not be used by application or library.
 * An API bearing this annotation is not supported.
 *
 * It is NOT safe for applications or library to depend on this APIs.
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
public @interface API_Internal {
}
