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

import java.util.ArrayList;
import java.util.List;

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

    public static final List<String> CALLED_METHODS = new ArrayList<String>();

    public abstract void merge(Source source, @MappingTarget Target target);

    public abstract void mergeNested(List<Source.Nested> source, @MappingTarget List<Target.Nested> target);

    @ObjectFactory
    protected Target.Nested create(Source.Nested source) {
        CALLED_METHODS.add( "create(Source.Nested)" );
        return new Target.Nested( "from object factory" );
    }

    @ObjectFactory
    protected Target.Nested createWithSource(Source source) {
        throw new IllegalArgumentException( "Should not use create with source" );
    }

    @ObjectFactory
    protected List<Target.Nested> createWithSourceList(List<Source.Nested> source) {
        CALLED_METHODS.add( "create(List<Source.Nested>)" );
        List<Target.Nested> result = new ArrayList<Target.Nested>();
        result.add( new Target.Nested( "from createWithSourceList" ) );
        return result;
    }
}
