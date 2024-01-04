/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3296;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@IssueKey( "3296" )
@WithClasses( { Entity.class, Payload.class } )
public class Issue3296Test {

    @ProcessorTest
    @WithClasses( { MapperExtendingConfig.class, MapperConfigWithPayloadArgument.class } )
    public void shouldNotRaiseErrorForDefaultAfterMappingMethodImplementation() {
        Payload payload = new Payload();
        payload.setName( "original" );

        Entity entity = Mappers.getMapper( MapperExtendingConfig.class ).toEntity( payload );

        assertThat( entity.getName() ).isEqualTo( "AfterMapping called" );
    }

    @ProcessorTest
    @WithClasses( { MapperNotExtendingConfig.class, MapperConfigWithoutPayloadArgument.class } )
    public void shouldNotRaiseErrorRequiringArgumentsForDefaultMethods() {
        Payload payload = new Payload();
        payload.setName( "original" );

        Entity entity = Mappers.getMapper( MapperNotExtendingConfig.class ).toEntity( payload );

        assertThat( entity.getName() ).isEqualTo( "original" );
    }
}
