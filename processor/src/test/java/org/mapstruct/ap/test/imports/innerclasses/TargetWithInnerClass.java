/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.innerclasses;

public class TargetWithInnerClass {

    private TargetInnerClass innerClassMember;

    public TargetInnerClass getInnerClassMember() {
        return innerClassMember;
    }

    public void setInnerClassMember(TargetInnerClass innerClassMember) {
        this.innerClassMember = innerClassMember;
    }

    public static class TargetInnerClass {
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public static class TargetInnerInnerClass {
            private int value;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }
        }
    }
}
