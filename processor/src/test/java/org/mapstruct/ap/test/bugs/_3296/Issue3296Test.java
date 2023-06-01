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
@WithClasses( { SpecificEntity.class, CommonMapperConfig.class, SpecificMapper.class, SpecificPayload.class } )
public class Issue3296Test {

    @ProcessorTest
    public void shouldNotRaiseErrorIfThereIsADefaultAfterOrBeforeMethodImplementation() {
        SpecificEntity entity = Mappers.getMapper( SpecificMapper.class ).toEntity( new SpecificPayload() );
        assertThat( entity.getName() ).isEqualTo( "AfterMapping called" );
    }
}
