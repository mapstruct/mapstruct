/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

/**
 * A container class whose deeply nested {@code Middle.Inner} class is used as a mapping source.
 */
public final class DeepNestedSourceBean {

    private DeepNestedSourceBean() {
    }

    public static class Middle {

        private Middle() {
        }

        public static class Inner {

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
