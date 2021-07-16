/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.dagger2;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for generation of Dagger2-based Mapper implementations
 */
public class Dagger2BasedMapperTest {

    SourceTargetMapper mapper;

    DecoratedSourceTargetMapper decoratedMapper;

    SecondDecoratedSourceTargetMapper secondDecoratedMapper;

    @Before
    public void init() {
        AppComponent appComponent = DaggerAppComponent.create();
        mapper = appComponent.sourceTargetMapper();
        decoratedMapper = appComponent.decoratedSourceTargetMapper();
        secondDecoratedMapper = appComponent.secondDecoratedSourceTargetMapper();
    }

    @Test
    public void shouldInjectDagger2BasedMapper() {
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
