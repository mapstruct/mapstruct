/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.factory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mapstruct.test.model.Foo;
import org.mapstruct.test.model.SomeClass;

/**
 * Unit test for {@link Mappers}.
 *
 * @author Gunnar Morling
 */
public class MappersTest {

    @Test
    public void shouldReturnImplementationInstance() {

        Foo mapper = Mappers.getMapper( Foo.class );
        assertThat( mapper ).isNotNull();
    }

    /**
     * Checks if an implementation of a nested mapper can be found. This is a special case since
     * it is named
     */
    @Test
    public void findsNestedMapperImpl() throws Exception {
        assertThat( Mappers.getMapper( SomeClass.Foo.class ) ).isNotNull();
        assertThat( Mappers.getMapper( SomeClass.NestedClass.Foo.class ) ).isNotNull();
    }

    @Test
    public void shouldReturnPackagePrivateImplementationInstance() {
        assertThat( Mappers.getMapper( PackagePrivateMapper.class ) ).isNotNull();
    }
}
