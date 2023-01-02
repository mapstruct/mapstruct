/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.lombok;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    LombokImmutablePath.class,
    MutablePath.class
})
public class LombokBuilderAdderTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({ PathMapper.class })
    @WithServiceImplementation(
        provides = AccessorNamingStrategy.class,
        value = LombokBuilderAccessorNamingStrategy.class
    )
    public void testSimpleImmutableBuilderWithLombokNamingStrategy() {
        PathMapper mapper = Mappers.getMapper( PathMapper.class );
        MutablePath source = new MutablePath();
        source.setEdges( Arrays.asList( "e1", "e2" ) );

        LombokImmutablePath targetObject = mapper.map( source );

        // assert that target properties were added by builder's adder (edge method), which upper-cases arguments
        assertThat( targetObject.getEdges() ).contains( "E1", "E2" );
    }

    @ProcessorTest
    @WithClasses({ PathMapper2.class, LombokImmutablePath2.class })
    public void testSimpleImmutableBuilderWithDefaultNamingStrategy() {
        PathMapper mapper = Mappers.getMapper( PathMapper.class );
        MutablePath source = new MutablePath();
        source.setEdges( Arrays.asList( "e1", "e2" ) );

        LombokImmutablePath targetObject = mapper.map( source );

        // assert that target properties were added by builder's adder (addEdge method), which upper-cases arguments
        assertThat( targetObject.getEdges() ).contains( "E1", "E2" );
    }
}
