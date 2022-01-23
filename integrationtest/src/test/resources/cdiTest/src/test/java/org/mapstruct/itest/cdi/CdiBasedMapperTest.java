/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.cdi;

import static org.assertj.core.api.Assertions.assertThat;

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
                    new StringAsset("<beans version=\"1.1\">" +
                        "<decorators><class>org.mapstruct.itest.cdi.SourceTargetMapperDecorator</class></decorators>" +
                        "</beans>"),
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
