/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.test.model;

/**
 * The sole purpose of this class is to have a place to nest the Foo interface.
 *
 * @author Tillmann Gaida
 */
public class SomeClass {
    public interface Foo { }

    public static class NestedClass {
        public interface Foo { }
    }
}
