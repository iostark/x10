package com.iostark.x10.base.id;

import com.iostark.x10.base.annotation.api.API_Internal;

/**
 *
 * @since V0_5_0
 */
@API_Internal
public interface SequenceGenerator<T> {
    /**
     * @since V0_5_0
     * @return Value from T
     */
    T next();
}
