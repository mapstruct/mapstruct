/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.qualified;

import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;

/**
 * @author Remo Meier
 */
public class Bar10Factory {

    @ObjectFactory
    public Bar10 createBar10Lower(Foo10 foo10) {
        return new Bar10( foo10.getProp().toLowerCase() );
    }

    @TestQualifier
    @ObjectFactory
    public Bar10 createBar10Upper(Foo10 foo10) {
        return new Bar10( foo10.getProp().toUpperCase() );
    }

    @Named( "Bar10NamedQualifier" )
    @ObjectFactory
    public Bar10 createBar10Camel(Foo10 foo10) {
        char firstLetter =  Character.toUpperCase( foo10.getProp().charAt( 0 ) );
        return new Bar10( firstLetter + foo10.getProp().toLowerCase().substring( 1 ) );
    }

}
