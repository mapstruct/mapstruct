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
package org.mapstruct.itest.simple;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper( uses = ReferencedCustomMapper.class )
public abstract class SourceTargetAbstractMapper {

    public static SourceTargetAbstractMapper INSTANCE = Mappers.getMapper( SourceTargetAbstractMapper.class );

    @Mappings({
        @Mapping(source = "qax", target = "baz"),
        @Mapping(source = "baz", target = "qax"),
        @Mapping(source = "forNested.value", target = "fromNested")
    })
    public abstract Target sourceToTarget(Source source);

    @Mappings({
        @Mapping(target = "forNested", ignore = true),
        @Mapping(target = "extendsBound", ignore = true)
    })
    public abstract Source targetToSource(Target target);

    protected void isNeverCalled(Source source) {
        throw new RuntimeException("not expected to be called");
    }
}
