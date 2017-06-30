/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.bugs._1131;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public abstract class Issue1131Mapper {
    public static final Issue1131Mapper INSTANCE = Mappers.getMapper( Issue1131Mapper.class );

    public abstract void merge(Source source, @MappingTarget Target target);

    @ObjectFactory
    protected Target.Nested create(Source.Nested source) {
        return new Target.Nested( "from object factory" );
    }
//TODO Should we report ambiguous methods if we use the ones below/
//    @ObjectFactory
//    protected Target.Nested createWithObject(Object source) {
//        throw new IllegalArgumentException( "Should not use create with object" );
//    }
//
//    @ObjectFactory
//    protected Target.Nested createDefault() {
//        throw new IllegalArgumentException( "Should not use create default" );
//    }
}
