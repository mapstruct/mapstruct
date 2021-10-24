/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.nested;

import javax.annotation.processing.Generated;
import org.mapstruct.ap.test.imports.nested.other.TargetInOtherPackage;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-24T13:45:02+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_161 (Oracle Corporation)"
)
public class TargetInOtherPackageMapperImpl implements TargetInOtherPackageMapper {

    @Override
    public TargetInOtherPackage map(Source source) {
        if ( source == null ) {
            return null;
        }

        TargetInOtherPackage targetInOtherPackage = new TargetInOtherPackage();

        targetInOtherPackage.setValue( nestedToNested( source.getValue() ) );

        return targetInOtherPackage;
    }

    protected TargetInOtherPackage.Nested.Inner innerToInner(Source.Nested.Inner inner) {
        if ( inner == null ) {
            return null;
        }

        TargetInOtherPackage.Nested.Inner inner1 = new TargetInOtherPackage.Nested.Inner();

        inner1.setValue( inner.getValue() );

        return inner1;
    }

    protected TargetInOtherPackage.Nested nestedToNested(Source.Nested nested) {
        if ( nested == null ) {
            return null;
        }

        TargetInOtherPackage.Nested nested1 = new TargetInOtherPackage.Nested();

        nested1.setInner( innerToInner( nested.getInner() ) );

        return nested1;
    }
}
