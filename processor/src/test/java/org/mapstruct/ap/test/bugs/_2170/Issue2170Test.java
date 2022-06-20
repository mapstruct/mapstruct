/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2170;

import java.util.Collections;

import org.mapstruct.ap.test.bugs._2170.dto.AddressDto;
import org.mapstruct.ap.test.bugs._2170.dto.PersonDto;
import org.mapstruct.ap.test.bugs._2170.entity.Address;
import org.mapstruct.ap.test.bugs._2170.entity.Person;
import org.mapstruct.ap.test.bugs._2170.mapper.AddressMapper;
import org.mapstruct.ap.test.bugs._2170.mapper.EntityMapper;
import org.mapstruct.ap.test.bugs._2170.mapper.PersonMapper;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2170")
@WithClasses({
    Address.class,
    Person.class,
    AddressDto.class,
    PersonDto.class,
    AddressMapper.class,
    PersonMapper.class,
    EntityMapper.class,
})
public class Issue2170Test {

    @ProcessorTest
    public void shouldGenerateCodeThatCompiles() {

        AddressDto addressDto = AddressMapper.INSTANCE.toDto( new Address(
            "10000",
            Collections.singletonList( new Person( "Tester" ) )
        ) );

        assertThat( addressDto ).isNotNull();
        assertThat( addressDto.getZipCode() ).isEqualTo( "10000" );
        assertThat( addressDto.getPeople() )
            .extracting( PersonDto::getName )
            .containsExactly( "Tester" );
    }
}
