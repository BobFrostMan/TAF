package com.ufo.core.common;

/**
 * Created by FOG on 21.02.2018.
 */
@FunctionalInterface
public interface Matcher<T, R> {
    R matches(T value1, T value2);
}
