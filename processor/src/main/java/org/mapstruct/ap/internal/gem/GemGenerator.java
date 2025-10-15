/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.gem;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlElementRef;

import org.mapstruct.AfterMapping;
import org.mapstruct.AnnotateWith;
import org.mapstruct.AnnotateWiths;
import org.mapstruct.BeanMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Builder;
import org.mapstruct.Condition;
import org.mapstruct.Context;
import org.mapstruct.DecoratedWith;
import org.mapstruct.EnumMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Javadoc;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.Ignored;
import org.mapstruct.IgnoredList;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;
import org.mapstruct.Qualifier;
import org.mapstruct.SourcePropertyName;
import org.mapstruct.SubclassMapping;
import org.mapstruct.SubclassMappings;
import org.mapstruct.TargetPropertyName;
import org.mapstruct.TargetType;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.control.MappingControl;
import org.mapstruct.control.MappingControls;
import org.mapstruct.tools.gem.GemDefinition;

/**
 * Triggers the generation of gem types using <a href="https://github.com/mapstruct/tools-gem">Gem Tools</a>.
 *
 * @author Gunnar Morling
 */
@GemDefinition(Deprecated.class)
@GemDefinition(AnnotateWith.class)
@GemDefinition(AnnotateWith.Element.class)
@GemDefinition(AnnotateWiths.class)
@GemDefinition(Mapper.class)
@GemDefinition(Mapping.class)
@GemDefinition(Ignored.class)
@GemDefinition(IgnoredList.class)
@GemDefinition(Mappings.class)
@GemDefinition(IterableMapping.class)
@GemDefinition(BeanMapping.class)
@GemDefinition(EnumMapping.class)
@GemDefinition(MapMapping.class)
@GemDefinition(SourcePropertyName.class)
@GemDefinition(SubclassMapping.class)
@GemDefinition(SubclassMappings.class)
@GemDefinition(TargetType.class)
@GemDefinition(TargetPropertyName.class)
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
@GemDefinition(Condition.class)
@GemDefinition(Javadoc.class)

@GemDefinition(MappingControl.class)
@GemDefinition(MappingControls.class)

// external types
@GemDefinition(XmlElementDecl.class)
@GemDefinition(XmlElementRef.class)
public class GemGenerator {
}
