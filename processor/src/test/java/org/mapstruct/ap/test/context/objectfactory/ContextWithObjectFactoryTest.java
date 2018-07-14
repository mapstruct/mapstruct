/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context.objectfactory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

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
@RunWith(AnnotationProcessorTestRunner.class)
public class ContextWithObjectFactoryTest {

    @Test
    public void testFactoryCalled( ) {
        ValveDto dto = new ValveDto();
        dto.setOneWay( true );

        Valve result = ContextWithObjectFactoryMapper.INSTANCE.map( dto, new ContextObjectFactory() );

        assertThat( result ).isNotNull();
        assertThat( result.isOneWay() ).isTrue();
    }

}
