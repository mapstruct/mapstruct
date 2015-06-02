/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.itest.cdi;

import static org.fest.assertions.Assertions.assertThat;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.itest.cdi.other.DateMapper;

/**
 * Test for generation of CDI-based mapper implementations.
 *
 * @author Gunnar Morling
 */
@RunWith( Arquillian.class )
public class CdiBasedMapperTest {

    @Inject
    private SourceTargetMapper mapper;

    @Inject
    private DecoratedSourceTargetMapper decoratedMapper;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create( JavaArchive.class )
            .addPackage( SourceTargetMapper.class.getPackage() )
            .addPackage( DateMapper.class.getPackage() )
            .addAsManifestResource(
                    new StringAsset("<decorators><class>org.mapstruct.itest.cdi.SourceTargetMapperDecorator</class></decorators>"),
                    "beans.xml" );
    }

    @Test
    public void shouldCreateCdiBasedMapper() {
        Source source = new Source();

        Target target = mapper.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 42 ) );
        assertThat( target.getDate() ).isEqualTo( "1980" );
    }

    @Test
    public void shouldInjectDecorator() {
        Source source = new Source();

        Target target = decoratedMapper.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 43 ) );
    }
}
