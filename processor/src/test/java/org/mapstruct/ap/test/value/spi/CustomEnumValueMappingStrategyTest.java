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
        // Enum 2 enum

        // Check forward
        assertEquals( CheeseTypePostfixed.BRIE_CHEESE_TYPE, CheeseTypeMapper.INSTANCE.mapFromCheeseType( CheeseType.BRIE ) );

        // And back again
        assertEquals( CheeseType.BRIE, CheeseTypeMapper.INSTANCE.mapFromCheeseTypePostfixed( CheeseTypePostfixed.BRIE_CHEESE_TYPE ) );

        // Null
        assertNull( CheeseTypeMapper.INSTANCE.mapFromCheeseTypePostfixed( CheeseTypePostfixed.DEFAULT_CHEESE_TYPE ) );

        // Default value
        assertEquals( CheeseTypePostfixed.DEFAULT_CHEESE_TYPE, CheeseTypeMapper.INSTANCE.mapFromCheeseType( null ) );

        // Enum 2 string
        assertEquals( "BRIE", CheeseTypeMapper.INSTANCE.mapToStringFromPostfixed( CheeseTypePostfixed.BRIE_CHEESE_TYPE ) );
        assertEquals( null, CheeseTypeMapper.INSTANCE.mapToStringFromPostfixed( CheeseTypePostfixed.DEFAULT_CHEESE_TYPE ) );
        assertEquals( "BRIE", CheeseTypeMapper.INSTANCE.mapToFromCheeseType( CheeseType.BRIE ) );

        // String 2 enum
        assertEquals( CheeseTypePostfixed.BRIE_CHEESE_TYPE, CheeseTypeMapper.INSTANCE.mapFromStringToCheeseTypePostfixed( "BRIE" ) );
        assertEquals( CheeseTypePostfixed.DEFAULT_CHEESE_TYPE, CheeseTypeMapper.INSTANCE.mapFromStringToCheeseTypePostfixed( null ) );
        assertEquals( CheeseType.BRIE, CheeseTypeMapper.INSTANCE.mapFromStringToCheeseType( "BRIE" ) );

    }
}
