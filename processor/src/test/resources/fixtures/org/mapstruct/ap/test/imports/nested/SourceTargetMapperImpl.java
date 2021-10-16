/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.nested;

import javax.annotation.Generated;
import org.mapstruct.ap.test.imports.nested.other.Source;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-08-19T19:13:35+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_161 (Oracle Corporation)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    @Override
    public Target map(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setValue( nestedToNested( source.getValue() ) );

        return target;
    }

    protected Target.Nested.Inner innerToInner(Source.Nested.Inner inner) {
        if ( inner == null ) {
            return null;
        }

        Target.Nested.Inner inner1 = new Target.Nested.Inner();

        inner1.setValue( inner.getValue() );

        return inner1;
    }

    protected Target.Nested nestedToNested(Source.Nested nested) {
        if ( nested == null ) {
            return null;
        }

        Target.Nested nested1 = new Target.Nested();

        nested1.setInner( innerToInner( nested.getInner() ) );

        return nested1;
    }
}
