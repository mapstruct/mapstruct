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

package org.mapstruct.ap.test.source.constants;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ErroneousConstantMapper {

    ErroneousConstantMapper INSTANCE = Mappers.getMapper( ErroneousConstantMapper.class );

    @BeanMapping( ignoreByDefault = true )
    @Mappings({
        @Mapping(target = "booleanValue", constant = "zz"),
        @Mapping(target = "charValue", constant = "'ba'"),
        @Mapping(target = "byteValue", constant = "200"),
        @Mapping(target = "intValue", constant = "0xFFFF_FFFF_FFFF"),
        @Mapping(target = "longValue", constant = "1"),
        @Mapping(target = "floatValue", constant = "1.40e-_45f"),
        @Mapping(target = "doubleValue", constant = "1e-137000")
    })
    ConstantsTarget mapFromConstants( String dummy );
}
