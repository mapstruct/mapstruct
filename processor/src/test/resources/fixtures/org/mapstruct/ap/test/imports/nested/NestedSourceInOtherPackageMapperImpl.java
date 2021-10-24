/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.nested;

import javax.annotation.processing.Generated;
import org.mapstruct.ap.test.imports.nested.other.SourceInOtherPackage;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-24T19:26:14+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_161 (Oracle Corporation)"
)
public class NestedSourceInOtherPackageMapperImpl implements NestedSourceInOtherPackageMapper {

    @Override
    public Target.Nested map(SourceInOtherPackage.Nested source) {
        if ( source == null ) {
            return null;
        }

        Target.Nested nested = new Target.Nested();

        nested.setInner( innerToInner( source.getInner() ) );

        return nested;
    }

    protected Target.Nested.Inner innerToInner(SourceInOtherPackage.Nested.Inner inner) {
        if ( inner == null ) {
            return null;
        }

        Target.Nested.Inner inner1 = new Target.Nested.Inner();

        inner1.setValue( inner.getValue() );

        return inner1;
    }
}
