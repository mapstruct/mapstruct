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
package org.mapstruct.ap.test.source.presencecheck.spi;

import java.util.Arrays;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


/**
 * @author Sean Huang
 */
@Mapper
public abstract class SourceTargetMapper {

    public static final SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    //@Mapping( target = "someInteger2", source = "someNested.someInteger2" )
    abstract Target sourceToTarget(Source source);

    abstract void sourceToTarget(Source source, @MappingTarget Target target);

    @Mappings( {
        @Mapping(target = "somePrimitiveDouble", defaultValue = "111.1"),
        @Mapping(target = "someInteger", defaultValue = "222"),
        @Mapping(target = "someList", defaultValue = "a,b"),
        @Mapping(target = "someArray", defaultValue = "u,v")
    } )
    abstract Target sourceToTargetWitDefaults(Source source);

    protected List<String> toList( String in ) {
        return Arrays.asList( in.split( "," ) );
    }

    protected String[] toArray( String in ) {
        return in.split( "," );
    }
}
