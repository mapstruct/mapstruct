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
        @Mapping(target = "integerCollection", source = "integerList"),
        @Mapping(target = "set", source = "integerSet"),
        @Mapping(target = "anotherStringSet", source = "anotherIntegerSet"),
        @Mapping(target = "stringListNoSetter", source = "stringList2"),
        @Mapping(target = "stringListNoSetter2", source = "stringSet2"),
        @Mapping(target = "nonGenericStringList", source = "stringList3"),
        @Mapping(target = "nonGenericMapStringtoLong", source = "stringLongMapForNonGeneric")
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
