/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.presencecheck;

import static org.mapstruct.SourceValuePresenceCheckStrategy.CUSTOM;
import static org.mapstruct.SourceValuePresenceCheckStrategy.IS_NULL;
import static org.mapstruct.SourceValuePresenceCheckStrategy.IS_NULL_INLINE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Sean Huang
 */
@Mapper(uses = { CustomMapper.class }, sourceValuePresenceCheckStrategy = CUSTOM)
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    Target sourceToTargetWithCustom(Source source);

    void sourceToTargetWithCustom(Source source, @MappingTarget Target target);

    @Mappings( {
        @Mapping(target = "somePrimitiveDouble", defaultValue = "111.1"),
        @Mapping(target = "someInteger", defaultValue = "222"),
        @Mapping(target = "someLong1", defaultValue = "333"),
        @Mapping(target = "someLong2", defaultValue = "444"),
    } )
    Target sourceToTargetWithCustomAndDefault(Source source);

    @Mappings( {
        @Mapping(target = "somePrimitiveDouble", sourceValuePresenceCheckStrategy = IS_NULL),
        @Mapping(target = "someInteger", sourceValuePresenceCheckStrategy = IS_NULL),
        @Mapping(target = "noCheckObject", sourceValuePresenceCheckStrategy = IS_NULL),
        @Mapping(target = "noCheckPrimitive", sourceValuePresenceCheckStrategy = IS_NULL),
        @Mapping(target = "someLong1", sourceValuePresenceCheckStrategy = IS_NULL),
    } )
    TargetWtCheck sourceToTargetWithIsNullCheck(SourceWtCheck source);

    @Mappings( {
        @Mapping(target = "noCheckObject", sourceValuePresenceCheckStrategy = IS_NULL),
        @Mapping(target = "noCheckPrimitive", sourceValuePresenceCheckStrategy = IS_NULL),
        @Mapping(target = "someLong2", sourceValuePresenceCheckStrategy = IS_NULL_INLINE),
    } )
    TargetWtCheck sourceToTargetWithIsNullInlineCheck(SourceWtCheck source);

    /*
     * Seeing exception below since there is no presence check method on source.
     *
     * org.junit.ComparisonFailure: [Compilation failed. Diagnostics: [DiagnosticDescriptor:
     * ERROR SourceTargetPresenceCheckMapper.java:53 Using custom source value presence checking strategy,
     * but no presence checker found for property "int noCheckPrimitive" in source type.]]
     * expected:<[SUCCEED]ED> but was:<[FAIL]ED>
     *
    @Mappings( {
        @Mapping(target = "someDouble", defaultValue = "111.1"),
        @Mapping(target = "someInteger", defaultValue = "222"),
        @Mapping(target = "someLong", defaultValue = "333"),
    } )
    TargetWtCheck sourceToTargetWithConfigOn(SourceWtCheck source);
    */
}
