/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.prism;

import net.java.dev.hickory.prism.GeneratePrism;
import net.java.dev.hickory.prism.GeneratePrisms;

import org.mapstruct.IterableMapping;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

/**
 * Triggers the generation of prism types using <a href="https://java.net/projects/hickory">Hickory</a>.
 *
 * @author Gunnar Morling
 */
@GeneratePrisms({
    @GeneratePrism(value = Mapper.class, publicAccess = true),
    @GeneratePrism(value = Mapping.class, publicAccess = true),
    @GeneratePrism(value = Mappings.class, publicAccess = true),
    @GeneratePrism(value = IterableMapping.class, publicAccess = true),
    @GeneratePrism(value = MapMapping.class, publicAccess = true),
    @GeneratePrism(value = MappingTarget.class, publicAccess = true)
})
public class PrismGenerator {

}
