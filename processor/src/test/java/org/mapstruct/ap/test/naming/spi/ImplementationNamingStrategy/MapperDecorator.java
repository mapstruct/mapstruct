/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming.spi.ImplementationNamingStrategy;

public class MapperDecorator implements DecoratedMapper {

    private final DecoratedMapper delegate;

    public MapperDecorator(DecoratedMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public MyObjectDto toDto(MyObject myObject) {
        return delegate.toDto( myObject );
    }
}
