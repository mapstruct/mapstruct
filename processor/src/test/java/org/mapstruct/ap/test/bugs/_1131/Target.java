/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1131;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class Target {

    public static class Nested {
        private final String internal;
        private String property;

        public Nested(String internal) {
            this.internal = internal;
        }

        public String getInternal() {
            return internal;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }
    }

    private Nested nested;
    private List<Nested> moreNested;

    public Nested getNested() {
        return nested;
    }

    public void setNested(Nested nested) {
        this.nested = nested;
    }

    public List<Nested> getMoreNested() {
        return moreNested;
    }

    public void setMoreNested(List<Nested> moreNested) {
        this.moreNested = moreNested;
    }
}
