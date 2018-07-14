/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.prism;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlElementRef;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;
import org.mapstruct.Qualifier;
import org.mapstruct.TargetType;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

import net.java.dev.hickory.prism.GeneratePrism;
import net.java.dev.hickory.prism.GeneratePrisms;

/**
 * Triggers the generation of prism types using <a href="https://java.net/projects/hickory">Hickory</a>.
 *
 * @author Gunnar Morling
 */
@GeneratePrisms({
    @GeneratePrism(value = Mapper.class, publicAccess = true),
    @GeneratePrism(value = Mapping.class, publicAccess = true),
    @GeneratePrism(value = Mappings.class, publicAccess = true),
    @GeneratePrism(value = IterableMapping.class, publicAccess = true),
    @GeneratePrism(value = BeanMapping.class, publicAccess = true),
    @GeneratePrism(value = MapMapping.class, publicAccess = true),
    @GeneratePrism(value = TargetType.class, publicAccess = true),
    @GeneratePrism(value = MappingTarget.class, publicAccess = true),
    @GeneratePrism(value = DecoratedWith.class, publicAccess = true),
    @GeneratePrism(value = MapperConfig.class, publicAccess = true),
    @GeneratePrism(value = InheritConfiguration.class, publicAccess = true),
    @GeneratePrism(value = InheritInverseConfiguration.class, publicAccess = true),
    @GeneratePrism(value = Qualifier.class, publicAccess = true),
    @GeneratePrism(value = Named.class, publicAccess = true),
    @GeneratePrism(value = ObjectFactory.class, publicAccess = true),
    @GeneratePrism(value = AfterMapping.class, publicAccess = true),
    @GeneratePrism(value = BeforeMapping.class, publicAccess = true),
    @GeneratePrism(value = ValueMapping.class, publicAccess = true),
    @GeneratePrism(value = ValueMappings.class, publicAccess = true),
    @GeneratePrism(value = Context.class, publicAccess = true),
    @GeneratePrism(value = Builder.class, publicAccess = true),

    // external types
    @GeneratePrism(value = XmlElementDecl.class, publicAccess = true),
    @GeneratePrism(value = XmlElementRef.class, publicAccess = true)
})
public class PrismGenerator {

}
