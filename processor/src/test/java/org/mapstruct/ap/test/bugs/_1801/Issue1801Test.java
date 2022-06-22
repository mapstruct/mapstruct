/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1801;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.ImmutablesAccessorNamingStrategy;
import org.mapstruct.ap.test.bugs._1801.domain.ImmutableItem;
import org.mapstruct.ap.test.bugs._1801.domain.Item;
import org.mapstruct.ap.test.bugs._1801.dto.ImmutableItemDTO;
import org.mapstruct.ap.test.bugs._1801.dto.ItemDTO;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.WithServiceImplementation;
import org.mapstruct.testutil.WithServiceImplementations;

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
    public void shouldIncludeBuildeType() {

        ItemDTO item = ImmutableItemDTO.builder().id( "test" ).build();

        Item target = ItemMapper.INSTANCE.map( item );

        assertThat( target ).isNotNull();
        assertThat( target.getId() ).isEqualTo( "test" );
    }
}
