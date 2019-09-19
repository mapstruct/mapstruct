/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.prism;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.InjectionStrategyPrism;
import org.mapstruct.ap.internal.prism.MappingInheritanceStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.ReportingPolicyPrism;

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
    public void mappingInheritanceStrategyPrismIsCorrect() {
        assertThat( namesOf( MappingInheritanceStrategy.values() ) ).isEqualTo(
            namesOf( MappingInheritanceStrategyPrism.values() ) );
    }

    @Test
    public void nullValueCheckStrategyPrismIsCorrect() {
        assertThat( namesOf( NullValueCheckStrategy.values() ) ).isEqualTo(
            namesOf( NullValueCheckStrategyPrism.values() ) );
    }

    @Test
    public void nullValueMappingStrategyPrismIsCorrect() {
        assertThat( namesOf( NullValueMappingStrategy.values() ) ).isEqualTo(
            namesOf( NullValueMappingStrategyPrism.values() ) );
    }

    @Test
    public void reportingPolicyPrismIsCorrect() {
        assertThat( namesOf( ReportingPolicy.values() ) ).isEqualTo(
            namesOf( ReportingPolicyPrism.values() ) );
    }

    @Test
    public void injectionStrategyPrismIsCorrect() {
        assertThat( namesOf( InjectionStrategy.values() ) ).isEqualTo(
            namesOf( InjectionStrategyPrism.values() ) );
    }

    private static List<String> namesOf(Enum<?>[] values) {
        return Stream.of( values )
            .map( Enum::name )
            .collect( Collectors.toList() );
    }
}
