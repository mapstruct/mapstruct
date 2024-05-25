/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1830;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-19T09:49:36+0200",
    comments = "version: , compiler: Eclipse JDT (Batch) 3.20.0.v20191203-2131, environment: Java 21.0.2 (Azul Systems, Inc.)"
)
public class BeanMapperWithClearOnNonCollectionTypesImpl implements BeanMapperWithClearOnNonCollectionTypes {

    @Override
    public BeanDTOWithId map(BeanWithId source, BeanDTOWithId target) {
        if ( source == null ) {
            return target;
        }

        target.setId( source.getId() );
        if ( target.getList() != null ) {
            Collection<String> collection = source.getList();
            if ( collection != null ) {
                target.getList().clear();
                target.getList().addAll( collection );
            }
            else {
                target.setList( null );
            }
        }
        else {
            Collection<String> collection = source.getList();
            if ( collection != null ) {
                target.setList( new ArrayList<String>( collection ) );
            }
        }

        return target;
    }
}
