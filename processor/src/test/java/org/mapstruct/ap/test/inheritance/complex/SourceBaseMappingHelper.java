/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance.complex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SourceBaseMappingHelper {
    public Reference asReference(SourceBase source) {
        if ( null == source ) {
            return null;
        }

        Reference result = new Reference();
        result.setFoo( source.getFoo() );

        return result;
    }

    public Integer numberToInteger(Number number) {
        throw new RuntimeException( "This method should not have been called" );
    }

    public Integer integerToInteger(Integer number) {
        return number == null ? null : number + 1;
    }

    public List<Number> integerCollectionToNumberList(Collection<Integer> collection) {
        if ( collection == null ) {
            return null;
        }

        List<Number> list = new ArrayList<Number>( collection.size() );

        for ( Integer original : collection ) {
            list.add( Integer.valueOf( original + 1 ) );
        }

        return list;
    }
}
