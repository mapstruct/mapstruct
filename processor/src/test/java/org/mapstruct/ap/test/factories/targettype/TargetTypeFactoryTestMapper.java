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
package org.mapstruct.ap.test.factories.targettype;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Remo Meier
 */
@Mapper( uses = { Bar9Factory.class } )
public abstract class TargetTypeFactoryTestMapper {
    public static final TargetTypeFactoryTestMapper INSTANCE = Mappers.getMapper( TargetTypeFactoryTestMapper.class );

    public abstract Bar9Base foo9BaseToBar9Base(Foo9Base foo9);

    public abstract Bar9Child foo9ChildToBar9Child(Foo9Child foo9);
}
