/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exclusions;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
class Source {

    static class DeepNestedType {
        //CHECKSTYLE:OFF
        public List<MyType> types;
        //CHECKSTYLE:ON
    }

    static class NestedMyType {
        //CHECKSTYLE:OFF
        public DeepNestedType deepNestedType;
        //CHECKSTYLE:ON
    }

    static class MyType {
        //CHECKSTYLE:OFF
        public String someProperty;
        //CHECKSTYLE:ON
    }

    //CHECKSTYLE:OFF
    public MyType date;
    public MyType calendar;
    public List<MyType> types;
    public NestedMyType nestedMyType;
    //CHECKSTYLE:ON
}
