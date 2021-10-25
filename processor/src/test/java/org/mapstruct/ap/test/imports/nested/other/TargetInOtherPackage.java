/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.nested.other;

/**
 * @author Filip Hrisafov
 */
public class TargetInOtherPackage {

    private Nested value;

    public Nested getValue() {
        return value;
    }

    public void setValue(Nested value) {
        this.value = value;
    }

    public static class Nested {

        private Inner inner;

        public static class Inner {

            private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public Inner getInner() {
            return inner;
        }

        public void setInner(Inner inner) {
            this.inner = inner;
        }
    }
}
