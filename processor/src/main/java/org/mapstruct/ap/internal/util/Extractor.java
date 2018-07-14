/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

/**
 * This is a helper interface until we migrate to Java 8. It allows us to abstract our code easier.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 *
 * @author Filip Hrisafov
 */
public interface Extractor<T, R> {

    /**
     * Extract a value from the passed parameter.
     *
     * @param t the value that we need to extract from
     *
     * @return the result from the extraction
     */
    R apply(T t);
}
