/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2092;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedSourcePolicy = ReportingPolicy.ERROR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface Issue2092Mapper {

    Issue2092Mapper INSTANCE = Mappers.getMapper( Issue2092Mapper.class );

    @Mapping(source = "a1", target = "aa1")
    @BeanMapping(ignoreUnmappedSourceProperties = { "a2" })
    TargetBeanA mapA(BeanA beanA);

    @InheritConfiguration
    @Mapping(source = "b1", target = "bb1") // inheritance works as expected
    @BeanMapping(ignoreUnmappedSourceProperties = { "b2" })
        // compiler error: Unmapped source property: "a2"
    TargetBeanB mapB(BeanB beanB);

}
