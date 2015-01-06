/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.template;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface SourceTargetMapperMultiple {

    SourceTargetMapperMultiple INSTANCE = Mappers.getMapper( SourceTargetMapperMultiple.class );

    @Mappings({
        @Mapping(target = "stringPropY", source = "stringPropX" ),
        @Mapping(target = "integerPropY", source = "integerPropX" ),
        @Mapping(target = "nestedResultProp", source = "nestedSourceProp.nested"),
        @Mapping(target = "constantProp", constant = "constant"),
        @Mapping(target = "expressionProp", expression = "java(\"expression\")"),
    })
    Target forwardCreate(Source source);

    @Mappings({
        @Mapping(target = "stringPropY", source = "stringPropX" ),
        @Mapping(target = "integerPropY", source = "integerPropX" ),
        @Mapping(target = "nestedResultProp", source = "nestedSourceProp.nested"),
        @Mapping(target = "constantProp", constant = "constant"),
        @Mapping(target = "expressionProp", expression = "java(\"expression\")"),
    })
    Target forwardCreate2(Source source);

    @InheritConfiguration(  name = "forwardCreate" )
    void forwardUpdate(Source source, @MappingTarget Target target);
}
