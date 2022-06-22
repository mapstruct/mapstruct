/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2122;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Christian Bandowski
 */
@IssueKey("2122")
public class Issue2122Test {

    @ProcessorTest
    @WithClasses( Issue2122Method2MethodMapper.class )
    public void shouldMapMethod2Method() {
        Issue2122Method2MethodMapper.Source source = new Issue2122Method2MethodMapper.Source();
        source.setValue( "value" );

        Issue2122Method2MethodMapper.Target target = Issue2122Method2MethodMapper.INSTANCE.toTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getEmbeddedTarget() ).isNotNull();
        assertThat( target.getEmbeddedTarget() ).hasSize( 1 )
            .element( 0 )
            .extracting( Issue2122Method2MethodMapper.EmbeddedTarget::getValue ).isEqualTo( "value" );
        assertThat( target.getEmbeddedMapTarget() ).isNotNull();
        assertThat( target.getEmbeddedMapTarget() ).hasSize( 1 );
        assertThat( target.getEmbeddedMapTarget().get( "test" ) )
            .extracting( Issue2122Method2MethodMapper.EmbeddedTarget::getValue ).isEqualTo( "value" );
        assertThat( target.getEmbeddedListListTarget() ).isNotNull();
        assertThat( target.getEmbeddedListListTarget() ).hasSize( 1 );
        assertThat( target.getEmbeddedListListTarget().get( 0 ) ).hasSize( 1 )
            .element( 0 )
            .extracting( Issue2122Method2MethodMapper.EmbeddedTarget::getValue ).isEqualTo( "value" );
    }

    @ProcessorTest
    @WithClasses( Issue2122TypeConversion2MethodMapper.class )
    public void shouldMapTypeConversion2Method() {
        Issue2122TypeConversion2MethodMapper.Source source = new Issue2122TypeConversion2MethodMapper.Source();
        source.setValue( 5 );

        Issue2122TypeConversion2MethodMapper.Target target =
            Issue2122TypeConversion2MethodMapper.INSTANCE.toTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStrings() ).isNotNull();
        assertThat( target.getStrings() ).hasSize( 1 )
            .element( 0 )
            .isEqualTo( "5" );
    }

    @ProcessorTest
    @WithClasses( Issue2122Method2TypeConversionMapper.class )
    public void shouldMapMethod2TypeConversion() {
        Issue2122Method2TypeConversionMapper.Source source = new Issue2122Method2TypeConversionMapper.Source();
        source.setStrings( Collections.singletonList( "5" )  );

        Issue2122Method2TypeConversionMapper.Target target =
            Issue2122Method2TypeConversionMapper.INSTANCE.toTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( 5 );
    }

}
