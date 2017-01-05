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
package org.mapstruct.ap.test.factories.assignment;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Remo Meier
 */
@Mapper( uses = { Bar5Factory.class, Bar6Factory.class, Bar7Factory.class } )
public abstract class ParameterAssignmentFactoryTestMapper {
    public static final ParameterAssignmentFactoryTestMapper INSTANCE =
        Mappers.getMapper( ParameterAssignmentFactoryTestMapper.class );

    public abstract Bar5 foos5ToBar5(Foo5A foo5A, Foo5B foo5B);

    public abstract Bar6 foos6ToBar6(Foo6A foo6A, Foo6B foo6B);

    public abstract Bar7 foos7ToBar7(Foo7A foo7A, Foo7B foo7B);
}
