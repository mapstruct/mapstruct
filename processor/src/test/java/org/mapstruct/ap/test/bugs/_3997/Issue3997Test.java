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

    /**
     * Parameter name equals a source property, but the full path is not a property path
     * (Client has no id). SourceReference strips; inverse must strip too so dtoId → id.
     */
    @ProcessorTest
    @WithClasses(Issue3997ParameterNameCollidesWithPropertyMapper.class)
    public void inverseStripsParameterPrefixWhenFullPathDoesNotResolveAsProperties() {
        Issue3997ParameterNameCollidesWithPropertyMapper.OrderDto dto =
            new Issue3997ParameterNameCollidesWithPropertyMapper.OrderDto();
        dto.setDtoId( 99L );

        Issue3997ParameterNameCollidesWithPropertyMapper.Order entity =
            Issue3997ParameterNameCollidesWithPropertyMapper.INSTANCE.toEntity( dto );

        assertThat( entity ).isNotNull();
        assertThat( entity.getId() ).isEqualTo( 99L );
        assertThat( entity.getClient() ).isNull();
    }

    /**
     * Parameter name equals a property and the full path resolves ({@code client.id} on Order).
     * Inverse keeps the property path: dtoId maps to client.id, not Order.id.
     */
    @ProcessorTest
    @WithClasses(Issue3997ParameterNameIsPropertyPathMapper.class)
    public void inverseKeepsPathWhenFullPathResolvesAsProperties() {
        Issue3997ParameterNameIsPropertyPathMapper.OrderDto dto =
            new Issue3997ParameterNameIsPropertyPathMapper.OrderDto();
        dto.setDtoId( 77L );

        Issue3997ParameterNameIsPropertyPathMapper.Order entity =
            Issue3997ParameterNameIsPropertyPathMapper.INSTANCE.toEntity( dto );

        assertThat( entity ).isNotNull();
        assertThat( entity.getId() ).isNull();
        assertThat( entity.getClient() ).isNotNull();
        assertThat( entity.getClient().getId() ).isEqualTo( 77L );
    }
}
