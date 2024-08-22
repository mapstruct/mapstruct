/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3370;

import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.ImmutablesAccessorNamingStrategy;
import org.mapstruct.ap.test.bugs._3370.dto.ItemDTO;
import org.mapstruct.ap.test.bugs._3370.domain.ImmutableItem;
import org.mapstruct.ap.test.bugs._3370.domain.Item;
import org.mapstruct.ap.test.bugs._3370.dto.ImmutableItemDTO;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.WithServiceImplementations;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    ItemMapper.class,
    Item.class,
    ImmutableItem.class,
    ItemDTO.class,
    ImmutableItemDTO.class
})
@IssueKey("3370")
@WithServiceImplementations( {
        @WithServiceImplementation( provides = BuilderProvider.class, value = Issue3370BuilderProvider.class),
        @WithServiceImplementation( provides = AccessorNamingStrategy.class,
                value = ImmutablesAccessorNamingStrategy.class )
})
public class Issue3370Test {

    @ProcessorTest
    public void shouldUseSuperClassBuilderOfImmutableIncaseSubclassHasNoBuilder() {

        Map<String, String> attributesMap = new HashMap<>();
        attributesMap.put( "a", "b" );
        attributesMap.put( "c", "d" );

        ItemDTO item = ItemDTO.builder()
            .id( "test" )
            .attributes( attributesMap )
            .build();

        Item target = ItemMapper.INSTANCE.map( item );

        assertThat( target ).isNotNull();
        assertThat( target.getId() ).isEqualTo( "test" );
        assertThat( target.getAttributes() ).isEqualTo( attributesMap );
    }
}
