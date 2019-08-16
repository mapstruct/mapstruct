/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.wildcard;

import java.math.BigDecimal;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public abstract class BeanMapper {

    public static final BeanMapper STM = Mappers.getMapper( BeanMapper.class );

    public abstract CunningPlan transformA(GoodIdea in);

    BigDecimal map(JAXBElement<? extends BigDecimal> value) {
        return value != null ? value.getValue() : null;
    }

    JAXBElement<? super BigDecimal> map(BigDecimal value) {
        return new JAXBElement<>( new QName( "test" ), BigDecimal.class, value );
    }

}
