/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.gem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.ConditionStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.gem.ConditionStrategyGem;
import org.mapstruct.ap.internal.gem.InjectionStrategyGem;
import org.mapstruct.ap.internal.gem.MappingInheritanceStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for manually created gems on enumeration types
 *
 * @author Andreas Gudian
 */
public class EnumGemsTest {
    @Test
    public void collectionMappingStrategyGemIsCorrect() {
        assertThat( namesOf( CollectionMappingStrategy.values() ) ).isEqualTo(
            namesOf( CollectionMappingStrategyGem.values() ) );
    }

    @Test
    public void mappingInheritanceStrategyGemIsCorrect() {
        assertThat( namesOf( MappingInheritanceStrategy.values() ) ).isEqualTo(
            namesOf( MappingInheritanceStrategyGem.values() ) );
    }

    @Test
    public void nullValueCheckStrategyGemIsCorrect() {
        assertThat( namesOf( NullValueCheckStrategy.values() ) ).isEqualTo(
            namesOf( NullValueCheckStrategyGem.values() ) );
    }

    @Test
    public void nullValueMappingStrategyGemIsCorrect() {
        assertThat( namesOf( NullValueMappingStrategy.values() ) ).isEqualTo(
            namesOf( NullValueMappingStrategyGem.values() ) );
    }

    @Test
    public void reportingPolicyGemIsCorrect() {
        assertThat( namesOf( ReportingPolicy.values() ) ).isEqualTo(
            namesOf( ReportingPolicyGem.values() ) );
    }

    @Test
    public void injectionStrategyGemIsCorrect() {
        assertThat( namesOf( InjectionStrategy.values() ) ).isEqualTo(
            namesOf( InjectionStrategyGem.values() ) );
    }

    @Test
    public void conditionStrategyGemIsCorrect() {
        assertThat( namesOf( ConditionStrategy.values() ) ).isEqualTo(
            namesOf( ConditionStrategyGem.values() ) );
    }

    private static List<String> namesOf(Enum<?>[] values) {
        return Stream.of( values )
            .map( Enum::name )
            .collect( Collectors.toList() );
    }
}
