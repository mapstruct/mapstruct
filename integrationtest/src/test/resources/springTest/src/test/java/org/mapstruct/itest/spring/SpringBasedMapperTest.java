/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.itest.spring.SpringBasedMapperTest.SpringTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for generation of Spring-based Mapper implementations
 *
 * @author Andreas Gudian
 */
@ContextConfiguration(classes = SpringTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBasedMapperTest {

    @Configuration
    @ComponentScan(basePackageClasses = SpringBasedMapperTest.class)
    public static class SpringTestConfig {
    }

    @Autowired
    private SourceTargetMapper mapper;

    @Autowired
    private DecoratedSourceTargetMapper decoratedMapper;

    @Autowired
    private SecondDecoratedSourceTargetMapper secondDecoratedMapper;

    @Test
    public void shouldInjectSpringBasedMapper() {
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
        assertThat( target.getDate() ).isEqualTo( "1980" );

        target = decoratedMapper.undecoratedSourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 42 ) );
        assertThat( target.getDate() ).isEqualTo( "1980" );
    }

    @Test
    public void shouldInjectSecondDecorator() {
        Source source = new Source();

        Target target = secondDecoratedMapper.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 43 ) );
        assertThat( target.getDate() ).isEqualTo( "1980" );

        target = secondDecoratedMapper.undecoratedSourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 42 ) );
        assertThat( target.getDate() ).isEqualTo( "1980" );
    }
}
