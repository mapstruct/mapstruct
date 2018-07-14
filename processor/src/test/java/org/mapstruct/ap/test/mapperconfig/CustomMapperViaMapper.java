/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mapperconfig;

/**
 *
 * @author Sjaak Derksen
 */
public class CustomMapperViaMapper {

    public FooEntity toFooEntity( FooDto dto ) {
        return new FooEntity( this.getClass().getSimpleName() );
    }
}
