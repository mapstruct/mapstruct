/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection;

import java.util.Set;

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
        @Mapping(source = "integerList", target = "integerCollection"),
        @Mapping(source = "integerSet", target = "set"),
        @Mapping(source = "anotherIntegerSet", target = "anotherStringSet"),
        @Mapping(source = "stringList2", target = "stringListNoSetter"),
        @Mapping(source = "stringSet2", target = "stringListNoSetter2"),
        @Mapping(source = "stringList3", target = "nonGenericStringList"),
        @Mapping(source = "stringLongMapForNonGeneric", target = "nonGenericMapStringtoLong")
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

    public abstract Set<Number> integerSetToNumberSet(Set<Integer> integers);

    protected StringHolder toStringHolder(String string) {
        return new StringHolder( string );
    }

    protected String toString(StringHolder string) {
        return string.getString();
    }
}
