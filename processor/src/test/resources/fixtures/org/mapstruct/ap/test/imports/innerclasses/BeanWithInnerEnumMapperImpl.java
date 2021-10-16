/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.innerclasses;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-16T21:06:53+0200",
    comments = "version: , compiler: javac, environment: Java 17 (Oracle Corporation)"
)
public class BeanWithInnerEnumMapperImpl implements BeanWithInnerEnumMapper {

    @Override
    public BeanWithInnerEnum fromFacade(BeanFacade beanFacade) {
        if ( beanFacade == null ) {
            return null;
        }

        BeanWithInnerEnum beanWithInnerEnum = new BeanWithInnerEnum();

        beanWithInnerEnum.setTest( beanFacade.getTest() );
        if ( beanFacade.getInnerEnum() != null ) {
            beanWithInnerEnum.setInnerEnum( Enum.valueOf( BeanWithInnerEnum.InnerEnum.class, beanFacade.getInnerEnum() ) );
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
        if ( beanWithInnerEnum.getInnerEnum() != null ) {
            beanFacade.setInnerEnum( beanWithInnerEnum.getInnerEnum().name() );
        }

        return beanFacade;
    }
}
