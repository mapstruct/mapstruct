/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context.objectfactory;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey( "1398" )
@WithClasses({
    Valve.class,
    ValveDto.class,
    ContextObjectFactory.class,
    ContextWithObjectFactoryMapper.class})
public class ContextWithObjectFactoryTest {

    @ProcessorTest
    public void testFactoryCalled( ) {
        ValveDto dto = new ValveDto();
        dto.setOneWay( true );

        Valve result = ContextWithObjectFactoryMapper.INSTANCE.map( dto, new ContextObjectFactory() );

        assertThat( result ).isNotNull();
        assertThat( result.isOneWay() ).isTrue();
    }

}
