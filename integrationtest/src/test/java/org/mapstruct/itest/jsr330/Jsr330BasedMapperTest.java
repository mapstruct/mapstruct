/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.itest.jsr330;

import static org.fest.assertions.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.itest.jsr330.Jsr330BasedMapperTest.SpringTestConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test for generation of JSR-330-based Mapper implementations
 *
 * @author Andreas Gudian
 */
@ContextConfiguration(classes = SpringTestConfig.class )
@RunWith( SpringJUnit4ClassRunner.class )
public class Jsr330BasedMapperTest {
    @Configuration
    @ComponentScan(basePackageClasses = Jsr330BasedMapperTest.class)
    public static class SpringTestConfig {
    }

    @Inject
    private SourceTargetMapper mapper;


    @Test
    public void shouldCreateSpringBasedMapper() {
        Source source = new Source();

        Target target = mapper.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 42 ) );
        assertThat( target.getDate() ).isEqualTo( "1980" );
    }
}
