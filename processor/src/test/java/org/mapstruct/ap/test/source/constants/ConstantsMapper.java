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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ConstantsMapper {

    ConstantsMapper INSTANCE = Mappers.getMapper( ConstantsMapper.class );

    @Mappings({
        @Mapping(target = "booleanValue", constant = "true"),
        @Mapping(target = "booleanBoxed", constant = "false"),
        @Mapping(target = "charValue", constant = "'b'"),
        @Mapping(target = "charBoxed", constant = "'a'"),
        @Mapping(target = "byteValue", constant = "20"),
        @Mapping(target = "byteBoxed", constant = "-128"),
        @Mapping(target = "shortValue", constant = "1996"),
        @Mapping(target = "shortBoxed", constant = "-1996"),
        @Mapping(target = "intValue", constant = "-03777777"),
        @Mapping(target = "intBoxed", constant = "15"),
        @Mapping(target = "longValue", constant = "0x7fffffffffffffffL"),
        @Mapping(target = "longBoxed", constant = "0xCAFEBABEL"),
        @Mapping(target = "floatValue", constant = "1.40e-45f"),
        @Mapping(target = "floatBoxed", constant = "3.4028235e38f"),
        @Mapping(target = "doubleValue", constant = "1e137"),
        @Mapping(target = "doubleBoxed", constant = "0x0.001P-1062d"),
        @Mapping(target = "doubleBoxedZero", constant = "0.0")
    })
    ConstantsTarget mapFromConstants( String dummy );
}
