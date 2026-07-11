/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3997;

import org.mapstruct.ap.test.bugs._3997.Issue3997Mapper.Order;
import org.mapstruct.ap.test.bugs._3997.Issue3997Mapper.OrderDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Kamil Krzywanski
 */
@IssueKey("3997")
public class Issue3997Test {

    @ProcessorTest
    @WithClasses(Issue3997Mapper.class)
    public void inverseIgnoreShouldRespectNestedSourcesWithParameterPrefix() {
        OrderDto dto = new OrderDto();
        dto.setId( 1L );
        dto.setClientId( 42L );
        dto.setName( "Acme" );
        dto.setCity( "Paris" );

        Order entity = Issue3997Mapper.INSTANCE.toEntity( dto );

        assertThat( entity ).isNotNull();
        assertThat( entity.getId() ).isEqualTo( 1L );
        assertThat( entity.getClient() ).isNull();
        assertThat( entity.getName() ).isNull();
        assertThat( entity.getAddress() ).isNull();
    }

    @ProcessorTest
    @WithClasses({ Issue3997Mapper.class, Issue3997MapperWithoutIgnores.class })
    public void inverseNestedMappingWithParameterPrefixStillWorksWithoutIgnores() {
        OrderDto dto = new OrderDto();
        dto.setId( 1L );
        dto.setClientId( 42L );
        dto.setName( "Acme" );
        dto.setCity( "Paris" );

        Order entity = Issue3997MapperWithoutIgnores.INSTANCE.toEntity( dto );

        assertThat( entity.getId() ).isEqualTo( 1L );
        assertThat( entity.getClient() ).isNotNull();
        assertThat( entity.getClient().getId() ).isEqualTo( 42L );
        assertThat( entity.getName() ).isEqualTo( "Acme" );
        assertThat( entity.getAddress() ).isNotNull();
        assertThat( entity.getAddress().getCity() ).isEqualTo( "Paris" );
    }
}
