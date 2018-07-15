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
package org.mapstruct.ap.test.bugs._1541;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Bandowski
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // IGNORE to keep this test-mapper small and clean
public abstract class Issue1541Mapper {

    public static final Issue1541Mapper INSTANCE = Mappers.getMapper( Issue1541Mapper.class );

    public abstract Target mapWithVarArgs(String code, String... parameters);

    public abstract Target mapWithArray(String code, String[] parameters);

    @Mapping(target = "parameters2", source = "parameters")
    public abstract Target mapWithReassigningVarArgs(String code, String... parameters);

    public abstract Target mapWithArrayAndVarArgs(String code, String[] parameters, String... parameters2);

    @Mappings({
        @Mapping(target = "parameters", ignore = true)
    })
    @BeanMapping(qualifiedByName = "afterMappingParametersAsArray")
    public abstract Target mapParametersAsArrayInAfterMapping(String code, String... parameters);

    @AfterMapping
    @Named( "afterMappingParametersAsArray" )
    protected void afterMappingParametersAsArray(@MappingTarget Target target, String[] parameters) {
        target.setAfterMappingWithArrayCalled( true );
        target.setParameters( parameters );
    }

    @Mappings({
        @Mapping(target = "parameters", ignore = true)
    })
    @BeanMapping(qualifiedByName = "afterMappingParametersAsVarArgs")
    public abstract Target mapParametersAsVarArgsInAfterMapping(String code, String... parameters);

    @AfterMapping
    @Named( "afterMappingParametersAsVarArgs" )
    protected void afterMappingParametersAsVarArgs(@MappingTarget Target target, String... parameters) {
        target.setAfterMappingWithVarArgsCalled( true );
        target.setParameters( parameters );
    }

    @Mapping(target = "parameters2", ignore = true)
    @BeanMapping(qualifiedByName = "afterMappingContextAsVarArgsUsingVarArgs")
    public abstract Target mapContextWithVarArgsInAfterMappingWithVarArgs(String code, String[] parameters,
                                                                          @Context String... context);

    @AfterMapping
    @Named( "afterMappingContextAsVarArgsUsingVarArgs" )
    protected void afterMappingContextAsVarArgsUsingVarArgs(@MappingTarget Target target, @Context String... context) {
        target.setAfterMappingContextWithVarArgsAsVarArgsCalled( true );
        target.setParameters2( context );
    }

    @Mapping(target = "parameters2", ignore = true)
    @BeanMapping(qualifiedByName = "afterMappingContextAsVarArgsUsingArray")
    public abstract Target mapContextWithVarArgsInAfterMappingWithArray(String code, String[] parameters,
                                                                        @Context String... context);

    @AfterMapping
    @Named( "afterMappingContextAsVarArgsUsingArray" )
    protected void afterMappingContextAsVarArgsUsingArray(@MappingTarget Target target, @Context String[] context) {
        target.setAfterMappingContextWithVarArgsAsArrayCalled( true );
        target.setParameters2( context );
    }
}
