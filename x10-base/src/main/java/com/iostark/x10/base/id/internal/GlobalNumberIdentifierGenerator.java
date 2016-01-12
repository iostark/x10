package com.iostark.x10.base.id.internal;

import com.iostark.x10.base.annotation.api.API_Internal;

@API_Internal
public class GlobalNumberIdentifierGenerator {
    public static final NumberIdentifierGenerator<Long> LONG_IDENTIFIER_GENERATOR = new NumberIdentifierGenerator<Long>(0L);
    public static final NumberIdentifierGenerator<Integer> INT_IDENTIFIER_GENERATOR = new NumberIdentifierGenerator<Integer>(0);
}
