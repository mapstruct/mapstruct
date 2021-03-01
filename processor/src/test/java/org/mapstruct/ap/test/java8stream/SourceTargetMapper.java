/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream;

import java.util.Set;
import java.util.stream.Stream;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class SourceTargetMapper {

    static final SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mappings({
        @Mapping(target = "stringList", source = "stringStream"),
        @Mapping(target = "stringArrayList", source = "stringArrayStream"),
        @Mapping(target = "stringSet", source = "stringStreamToSet"),
        @Mapping(target = "integerCollection", source = "integerStream"),
        @Mapping(target = "anotherStringSet", source = "anotherIntegerStream"),
        @Mapping(target = "stringListNoSetter", source = "stringStream2"),
        @Mapping(target = "nonGenericStringList", source = "stringStream3")
    })
    public abstract Target sourceToTarget(Source source);

    @InheritInverseConfiguration( name = "sourceToTarget" )
    public abstract Source targetToSource(Target target);

    @InheritConfiguration
    public abstract Target sourceToTargetTwoArg(Source source, @MappingTarget Target target);

    public abstract Set<String> integerSetToStringSet(Set<Integer> integers);

    @InheritInverseConfiguration
    public abstract Set<Integer> stringSetToIntegerSet(Set<String> strings);

    public abstract Set<String> colourSetToStringSet(Set<Colour> colours);

    @InheritInverseConfiguration
    public abstract Set<Colour> stringSetToColourSet(Set<String> colours);

    public abstract Set<Number> integerStreamToNumberSet(Stream<Integer> integers);

    protected StringHolder toStringHolder(String string) {
        return new StringHolder( string );
    }

    protected String toString(StringHolder string) {
        return string.getString();
    }
}
