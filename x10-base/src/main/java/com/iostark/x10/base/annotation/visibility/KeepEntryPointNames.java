/*
 * Copyright (c) $year IO Stark
 */

package com.iostark.x10.base.annotation.visibility;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Signifies this API (public class, method or field) is an library entry point and must not be renamed by Proguard.
 * <p>
 *     You can ignore this annotation.
 * </p>
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
public @interface KeepEntryPointNames {
}
