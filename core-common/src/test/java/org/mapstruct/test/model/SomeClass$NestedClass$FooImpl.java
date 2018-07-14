/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.test.model;

/**
 * For testing naming of implementations of nested mappers (issue 611).
 *
 * @author Tillmann Gaida
 */
/*
 * Turn off checkstyle since the underscore introduced by issue 611 violates the class naming
 * convention.
 */
// CHECKSTYLE:OFF
public class SomeClass$NestedClass$FooImpl implements SomeClass.NestedClass.Foo {

}
