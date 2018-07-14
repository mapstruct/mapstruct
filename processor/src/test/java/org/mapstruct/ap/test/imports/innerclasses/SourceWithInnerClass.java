/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.innerclasses;

public class SourceWithInnerClass {

    private SourceInnerClass innerClassMember;

    public SourceInnerClass getInnerClassMember() {
        return innerClassMember;
    }

    public void setInnerClassMember(SourceInnerClass innerClassMember) {
        this.innerClassMember = innerClassMember;
    }

    public static class SourceInnerClass {
        private int value;

        public SourceInnerClass() {
        }

        public SourceInnerClass(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

}
