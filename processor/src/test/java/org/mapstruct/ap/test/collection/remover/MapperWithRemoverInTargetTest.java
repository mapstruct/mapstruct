/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.remover;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jarle Sætre
 */
@WithClasses({
        MapperWithRemoverInTarget.class,
        TargetWithRemover.class,
        Source.class})
public class MapperWithRemoverInTargetTest {

    @ProcessorTest
    @ExpectedCompilationOutcome(CompilationResult.SUCCEEDED)
    void removerOnTargetShouldBeIgnored() {
        Source src = new Source();
        src.getStrings().add( "myString" );

        TargetWithRemover target = MapperWithRemoverInTarget.INSTANCE.map( src );

        assertThat( target.getStrings() ).containsExactly( "myString" );
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(CompilationResult.SUCCEEDED)
    void propertiesStartingWithTheWordRemoveShouldNotBeIgnored() {
        Source src = new Source();
        src.setRemoved( true );

        TargetWithRemover target = MapperWithRemoverInTarget.INSTANCE.map( src );

        assertThat( target.isRemoved() ).isTrue();
    }
}
