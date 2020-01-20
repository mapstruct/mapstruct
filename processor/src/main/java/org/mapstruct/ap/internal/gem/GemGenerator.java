/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.gem;

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
import org.mapstruct.annotations.GemDefinition;

/**
 * Triggers the generation of ge  types using <a href="https://java.net/projects/hickory">Hickory</a>.
 *
 * @author Gunnar Morling
 */
@GemDefinition(Mapper.class)
@GemDefinition(Mapping.class)
@GemDefinition(Mappings.class)
@GemDefinition(IterableMapping.class)
@GemDefinition(BeanMapping.class)
@GemDefinition(MapMapping.class)
@GemDefinition(TargetType.class)
@GemDefinition(MappingTarget.class)
@GemDefinition(DecoratedWith.class)
@GemDefinition(MapperConfig.class)
@GemDefinition(InheritConfiguration.class)
@GemDefinition(InheritInverseConfiguration.class)
@GemDefinition(Qualifier.class)
@GemDefinition(Named.class)
@GemDefinition(ObjectFactory.class)
@GemDefinition(AfterMapping.class)
@GemDefinition(BeforeMapping.class)
@GemDefinition(ValueMapping.class)
@GemDefinition(ValueMappings.class)
@GemDefinition(Context.class)
@GemDefinition(Builder.class)

// external types
@GemDefinition(XmlElementDecl.class)
@GemDefinition(XmlElementRef.class)
public class GemGenerator {
}
