/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.referenced;

import org.mapstruct.TargetType;
import org.mapstruct.ap.test.imports.from.Foo;

/**
 * @author Andreas Gudian
 */
public class GenericMapper {

    @SuppressWarnings("unchecked")
    public <T> T fromFoo(Foo fromFoo, @TargetType Class<T> toFooClass) {
        if ( org.mapstruct.ap.test.imports.to.Foo.class == toFooClass ) {
            org.mapstruct.ap.test.imports.to.Foo result = new org.mapstruct.ap.test.imports.to.Foo();
            result.setName( fromFoo.getName() );

            return (T) result;
        }

        return null;
    }

    public NotImportedDatatype identity(NotImportedDatatype notImported) {
        return notImported;
    }
}
