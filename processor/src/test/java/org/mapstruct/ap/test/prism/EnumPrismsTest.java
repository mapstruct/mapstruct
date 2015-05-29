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
package org.mapstruct.ap.test.prism;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.MappingInheritanceStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test for manually created prisms on enumeration types
 *
 * @author Andreas Gudian
 */
public class EnumPrismsTest {
    @Test
    public void collectionMappingStrategyPrismIsCorrect() {
        assertThat( namesOf( CollectionMappingStrategy.values() ) ).isEqualTo(
            namesOf( CollectionMappingStrategyPrism.values() ) );
    }

    @Test
    public void mapNullToDefaultStrategyPrismIsCorrect() {
        assertThat( namesOf( NullValueMappingStrategy.values() ) ).isEqualTo(
            namesOf( NullValueMappingStrategyPrism.values() ) );
    }

    @Test
    public void mapMappingInheritanceStrategyPrismIsCorrect() {
        assertThat( namesOf( MappingInheritanceStrategy.values() ) ).isEqualTo(
            namesOf( MappingInheritanceStrategyPrism.values() ) );
    }

    private static List<String> namesOf(Enum<?>[] values) {
        List<String> names = new ArrayList<String>( values.length );

        for ( Enum<?> e : values ) {
            names.add( e.name() );
        }

        return names;
    }
}
