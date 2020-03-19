/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.value.spi.dto.CheeseTypePostfixed;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test do demonstrate the usage of custom implementations of {@link org.mapstruct.ap.spi.EnumValueMappingStrategy}.
 *
 * @author Arne Seime
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({ CheeseTypePostfixed.class, CheeseType.class, CheeseTypeMapper.class })
@WithServiceImplementation(CustomEnumValueMappingStrategy.class)
public class CustomEnumValueMappingStrategyTest {
    @Test
    public void shouldApplyCustomEnumMappingStrategy() {
        // Check forward
        assertEquals( CheeseTypePostfixed.BRIE_CHEESE_TYPE, CheeseTypeMapper.INSTANCE.mapToPostfixed( CheeseType.BRIE ) );

        // And back again
        assertEquals( CheeseType.BRIE, CheeseTypeMapper.INSTANCE.mapFromPostfixed( CheeseTypePostfixed.BRIE_CHEESE_TYPE ) );

        // Null
        assertNull( CheeseTypeMapper.INSTANCE.mapFromPostfixed( CheeseTypePostfixed.DEFAULT_CHEESE_TYPE ) );

        // Default value
        assertEquals( CheeseTypePostfixed.DEFAULT_CHEESE_TYPE, CheeseTypeMapper.INSTANCE.mapToPostfixed( null ) );

    }
}
