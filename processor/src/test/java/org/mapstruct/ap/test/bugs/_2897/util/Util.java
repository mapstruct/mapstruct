/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2897.util;

import org.mapstruct.ap.test.bugs._2897.Source;

/**
 * @author Filip Hrisafov
 */
public class Util {

    public static class Factory {

        public static String parse(Source source) {
            return source == null ? null : "parsed(" + source.getValue() + ")";
        }
    }
}
