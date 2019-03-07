/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mapasbean;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses( Map2BeanMapper.class )
@IssueKey( "1075" )
@RunWith(AnnotationProcessorTestRunner.class)
public class MapAsBeanTest {


    // TODO.. good name
    @Test
    public void test(){

        Map2BeanMapper.Propulsion propulsion = new Map2BeanMapper.Propulsion();

        Map<String, Object> source = new HashMap<>(  );
        source.put( "manned", Boolean.TRUE );
        source.put( "propulsion", propulsion );
        source.put( "fuel", "solid" );

        Map2BeanMapper.RocketDto rocket = Map2BeanMapper.INSTANCE.map( source );

        assertThat( rocket ).isNotNull();
        assertThat( rocket.isManned() ).isTrue();
        assertThat( rocket.getPropulsion() ).isEqualTo( propulsion );
        assertThat( rocket.getFuel() ).isEqualTo( "solid" );
    }


    // TODO.. multiarg, errors, etc.
}


