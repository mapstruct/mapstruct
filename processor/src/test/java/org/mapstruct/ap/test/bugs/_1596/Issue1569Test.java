/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1596;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.ImmutablesAccessorNamingStrategy;
import org.mapstruct.ap.test.bugs._1596.domain.ImmutableItem;
import org.mapstruct.ap.test.bugs._1596.domain.Item;
import org.mapstruct.ap.test.bugs._1596.dto.ImmutableItemDTO;
import org.mapstruct.ap.test.bugs._1596.dto.ItemDTO;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.WithServiceImplementations;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@WithClasses({
    ItemMapper.class,
    Item.class,
    ImmutableItem.class,
    ItemDTO.class,
    ImmutableItemDTO.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1596")
@WithServiceImplementations( {
    @WithServiceImplementation( provides = BuilderProvider.class, value = Issue1569BuilderProvider.class),
    @WithServiceImplementation( provides = AccessorNamingStrategy.class, value = ImmutablesAccessorNamingStrategy.class)
})
public class Issue1569Test {

    @Test
    public void shouldIncludeBuildeType() {

        ItemDTO item = ImmutableItemDTO.builder().id( "test" ).build();

        Item target = ItemMapper.INSTANCE.map( item );

        assertThat( target ).isNotNull();
        assertThat( target.getId() ).isEqualTo( "test" );
    }
}
