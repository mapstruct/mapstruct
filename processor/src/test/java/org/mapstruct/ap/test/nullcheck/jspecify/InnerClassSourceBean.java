/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

/**
 * A container class whose {@link Inner} nested class is used as a mapping source.
 */
public final class InnerClassSourceBean {

    private InnerClassSourceBean() {
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
