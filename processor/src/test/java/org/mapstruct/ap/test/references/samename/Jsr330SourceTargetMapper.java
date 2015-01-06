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
package org.mapstruct.ap.test.references.samename;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.references.samename.a.CustomMapper;
import org.mapstruct.ap.test.references.samename.model.Source;
import org.mapstruct.ap.test.references.samename.model.Target;
import org.mapstruct.factory.Mappers;

/**
 * @author Gunnar Morling
 */
@Mapper(
    componentModel = "jsr330",
    uses = {
        CustomMapper.class,
        org.mapstruct.ap.test.references.samename.b.CustomMapper.class
    })
public interface Jsr330SourceTargetMapper {

    Jsr330SourceTargetMapper INSTANCE = Mappers.getMapper( Jsr330SourceTargetMapper.class );

    Target sourceToTarget(Source source);
}
