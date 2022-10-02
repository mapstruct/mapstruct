/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.composition;

import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author orange add
 */
@IssueKey("3037")
public class ValueCompositionTest {

    @ProcessorTest
    @WithClasses({
        ValueMappingCompositionMapper.class,
        ExternalOrderType.class,
        OrderType.class,
        CustomValueAnnotation.class
    })
    public void shouldValueCompositionSuccess() {
        ValueMappingCompositionMapper compositionMapper = Mappers.getMapper( ValueMappingCompositionMapper.class );
        assertThat( compositionMapper.orderTypeToExternalOrderType( OrderType.EXTRA ) )
            .isEqualTo( ExternalOrderType.SPECIAL );
        assertThat( compositionMapper.orderTypeToExternalOrderType( OrderType.NORMAL ) )
            .isEqualTo( ExternalOrderType.DEFAULT );
    }

    @ProcessorTest
    @WithClasses({
        ValueMappingCompositionMapper.class,
        ExternalOrderType.class,
        OrderType.class,
        CustomValueAnnotation.class
    })
    public void duplicateValueMappingAnnotation() {
        ValueMappingCompositionMapper compositionMapper = Mappers.getMapper( ValueMappingCompositionMapper.class );
        assertThat( compositionMapper.duplicateAnnotation( OrderType.EXTRA ) )
            .isEqualTo( ExternalOrderType.SPECIAL );
        assertThat( compositionMapper.duplicateAnnotation( OrderType.STANDARD ) )
            .isEqualTo( ExternalOrderType.SPECIAL );
        assertThat( compositionMapper.duplicateAnnotation( OrderType.NORMAL ) )
            .isEqualTo( ExternalOrderType.DEFAULT );

    }
}
