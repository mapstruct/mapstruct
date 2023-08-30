/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1801;

import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.ImmutablesAccessorNamingStrategy;
import org.mapstruct.ap.test.bugs._1801.domain.ImmutableItem;
import org.mapstruct.ap.test.bugs._1801.domain.Item;
import org.mapstruct.ap.test.bugs._1801.dto.ImmutableItemDTO;
import org.mapstruct.ap.test.bugs._1801.dto.ItemDTO;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.WithServiceImplementations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Zhizhi Deng
 */
@WithClasses({
    ItemMapper.class,
    Item.class,
    ImmutableItem.class,
    ItemDTO.class,
    ImmutableItemDTO.class
})
@IssueKey("1801")
@WithServiceImplementations( {
    @WithServiceImplementation( provides = BuilderProvider.class, value = Issue1801BuilderProvider.class),
    @WithServiceImplementation( provides = AccessorNamingStrategy.class, value = ImmutablesAccessorNamingStrategy.class)
})
public class Issue1801Test {

    @ProcessorTest
    public void shouldIncludeBuilderType() {

        ItemDTO item = ImmutableItemDTO.builder().id( "test" ).build();

        Item target = ItemMapper.INSTANCE.map( item );

        assertThat( target ).isNotNull();
        assertThat( target.getId() ).isEqualTo( "test" );
    }
}
