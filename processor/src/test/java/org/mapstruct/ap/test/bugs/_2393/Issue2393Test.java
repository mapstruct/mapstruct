/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2393;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2393")
@WithClasses({
    Address.class,
    AddressDto.class,
    Country.class,
    CountryDto.class,
})
public class Issue2393Test {

    @ProcessorTest
    public void shouldUseCorrectImport() {
        AddressDto dto = AddressDto.Converter.INSTANCE.convert( new Address(
            "Zurich",
            new Country( "Switzerland" )
        ) );

        assertThat( dto.getCity() ).isEqualTo( "Zurich" );
        assertThat( dto.getCountry().getName() ).isEqualTo( "Switzerland" );
        assertThat( dto.getCountry().getCode() ).isEqualTo( "UNKNOWN" );
    }
}
