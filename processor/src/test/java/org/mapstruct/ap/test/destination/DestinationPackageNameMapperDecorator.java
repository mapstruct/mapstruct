/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.destination;

/**
 * @author Christophe Labouisse on 31/05/2015.
 */
public abstract class DestinationPackageNameMapperDecorator implements DestinationPackageNameMapperDecorated {
    final DestinationPackageNameMapperDecorated delegate;

    protected DestinationPackageNameMapperDecorator(DestinationPackageNameMapperDecorated delegate) {
        this.delegate = delegate;
    }

    @Override
    public Target map(Integer source) {
        return delegate.map( source );
    }
}
