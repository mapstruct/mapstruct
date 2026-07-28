/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.innerclasses;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T14:56:03+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (IBM Corporation)"
)
public class BeanWithInnerEnumMapperImpl implements BeanWithInnerEnumMapper {

    @Override
    public BeanWithInnerEnum fromFacade(BeanFacade beanFacade) {
        if ( beanFacade == null ) {
            return null;
        }

        BeanWithInnerEnum beanWithInnerEnum = new BeanWithInnerEnum();

        beanWithInnerEnum.setTest( beanFacade.getTest() );
        String innerEnum = beanFacade.getInnerEnum();
        if ( innerEnum != null ) {
            beanWithInnerEnum.setInnerEnum( Enum.valueOf( BeanWithInnerEnum.InnerEnum.class, innerEnum ) );
        }

        return beanWithInnerEnum;
    }

    @Override
    public BeanFacade toFacade(BeanWithInnerEnum beanWithInnerEnum) {
        if ( beanWithInnerEnum == null ) {
            return null;
        }

        BeanFacade beanFacade = new BeanFacade();

        beanFacade.setTest( beanWithInnerEnum.getTest() );
        BeanWithInnerEnum.InnerEnum innerEnum = beanWithInnerEnum.getInnerEnum();
        if ( innerEnum != null ) {
            beanFacade.setInnerEnum( innerEnum.name() );
        }

        return beanFacade;
    }
}
