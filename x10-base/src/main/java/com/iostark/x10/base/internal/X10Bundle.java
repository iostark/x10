package com.iostark.x10.base.internal;

import android.os.Bundle;

import com.iostark.x10.base.X10BuildConfig;
import com.iostark.x10.base.annotation.api.API_Internal;

@API_Internal
public class X10Bundle {

    private final Bundle bundle;
    private final String keyPrefix;

    public X10Bundle(final Bundle bundle, final String moduleName, final String className) {
        this.bundle = bundle;
        this.keyPrefix = X10BuildConfig.LIBRARY_ID + moduleName + "." + className + ".";
    }

    private String getRawKey(final String key) {
        return keyPrefix + key;
    }

    public Object get(final String key) {
        return bundle.get(getRawKey(key));
    }

    public byte getByte(final String key) {
        return bundle.getByte(getRawKey(key));
    }

    public Byte getByte(final String key, final byte defaultValue) {
        return bundle.getByte(getRawKey(key), defaultValue);
    }

    public boolean getBoolean(final String key) {
        return bundle.getBoolean(getRawKey(key));
    }

    public boolean getBoolean(final String key, final boolean defaultValue) {
        return bundle.getBoolean(getRawKey(key), defaultValue);
    }

    public char getChar(final String key) {
        return bundle.getChar(getRawKey(key));
    }

    public char getChar(final String key, final char defaultValue) {
        return bundle.getChar(getRawKey(key), defaultValue);
    }

    public short getShort(final String key) {
        return bundle.getShort(getRawKey(key));
    }

    public short getShort(final String key, final short defaultValue) {
        return bundle.getShort(getRawKey(key), defaultValue);
    }

    public int getInt(final String key) {
        return bundle.getInt(getRawKey(key));
    }

    public int getInt(final String key, final int defaultValue) {
        return bundle.getInt(getRawKey(key), defaultValue);
    }

    public long getLong(final String key) {
        return bundle.getLong(getRawKey(key));
    }

    public long getLong(final String key, final long defaultValue) {
        return bundle.getLong(getRawKey(key), defaultValue);
    }

    public float getFloat(final String key) {
        return bundle.getFloat(getRawKey(key));
    }

    public float getFloat(final String key, final float defaultValue) {
        return bundle.getFloat(getRawKey(key), defaultValue);
    }

    public double getDouble(final String key) {
        return bundle.getDouble(getRawKey(key));
    }

    public double getDouble(final String key, final double defaultValue) {
        return bundle.getDouble(getRawKey(key), defaultValue);
    }

    public String getString(final String key) {
        return bundle.getString(getRawKey(key));
    }

    public String getString(final String key, final String defaultValue) {
        return bundle.getString(getRawKey(key), defaultValue);
    }

    public void remove(final String key) {
        bundle.remove(getRawKey(key));
    }

    public void putBoolean(final String key, final boolean value) {
        bundle.putBoolean(getRawKey(key), value);
    }

    public void putByte(final String key, final byte value) {
        bundle.putByte(getRawKey(key), value);
    }

    public void putChar(final String key, final char value) {
        bundle.putChar(getRawKey(key), value);
    }

    public void putShort(final String key, final short value) {
        bundle.putShort(getRawKey(key), value);
    }

    public void putInt(final String key, final int value) {
        bundle.putInt(getRawKey(key), value);
    }

    public void putLong(final String key, final long value) {
        bundle.putLong(getRawKey(key), value);
    }

    public void putFloat(final String key, final float value) {
        bundle.putFloat(getRawKey(key), value);
    }

    public void putDouble(final String key, final double value) {
        bundle.putDouble(getRawKey(key), value);
    }

    public void putString(final String key, final String value) {
        bundle.putString(getRawKey(key), value);
    }
}
