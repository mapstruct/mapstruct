/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.fluent.getters;

import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.ImmutablesAccessorNamingStrategy;
import org.mapstruct.ap.test.fluent.getters.domain.ImmutableItem;
import org.mapstruct.ap.test.fluent.getters.domain.Item;
import org.mapstruct.ap.test.fluent.getters.dto.ImmutableItemDto;
import org.mapstruct.ap.test.fluent.getters.dto.ItemDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.WithServiceImplementations;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    ItemMapper.class,
    Item.class,
    ItemDto.class,
    ImmutableItem.class,
    ImmutableItemDto.class
})
@IssueKey("1601")
@WithServiceImplementations({
    @WithServiceImplementation(provides = BuilderProvider.class, value = Issue1601BuilderProvider.class),
    @WithServiceImplementation(provides = AccessorNamingStrategy.class, value = ImmutablesAccessorNamingStrategy.class)
})
public class Issue1601Test {

    @ProcessorTest
    public void shouldIncludeBuildType() {
        var item = ImmutableItemDto.builder().id( "test" ).build();

        var target = ItemMapper.INSTANCE.map( item );

        assertThat( target ).isNotNull();
        assertThat( target.id() ).isEqualTo( "test" );
    }

}
