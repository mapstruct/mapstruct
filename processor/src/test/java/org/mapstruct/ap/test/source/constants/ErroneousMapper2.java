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
package org.mapstruct.ap.test.source.constants;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(uses = StringListMapper.class)
public interface ErroneousMapper2 {

    ErroneousMapper2 INSTANCE = Mappers.getMapper( ErroneousMapper2.class );

    @Mappings({
        @Mapping(target = "stringConstant", constant = "stringConstant"),
        @Mapping(target = "integerConstant"),
        @Mapping(target = "longWrapperConstant", constant = "3001"),
        @Mapping(target = "dateConstant", dateFormat = "dd-MM-yyyy", constant = "09-01-2014"),
        @Mapping(target = "nameConstants", constant = "jack-jill-tom")
    })
    Target sourceToTarget(Source s);
}
