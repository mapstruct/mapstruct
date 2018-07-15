/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.hybrid;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.selection.qualifier.annotation.EnglishToGerman;
import org.mapstruct.ap.test.selection.qualifier.annotation.NonQualifierAnnotated;
import org.mapstruct.ap.test.selection.qualifier.annotation.TitleTranslator;
import org.mapstruct.ap.test.selection.qualifier.handwritten.SomeOtherMapper;
import org.mapstruct.ap.test.selection.qualifier.handwritten.Titles;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey( "750" )
@WithClasses( {
    SourceRelease.class,
    TargetRelease.class,
    ReleaseMapper.class,
    SomeOtherMapper.class,
    NonQualifierAnnotated.class,
    Titles.class,
    EnglishToGerman.class,
    TitleTranslator.class
} )
@RunWith( AnnotationProcessorTestRunner.class )
public class HybridTest {

    @Test
    public void shouldMatchClassAndMethod() {

        SourceRelease foreignMovies = new SourceRelease();
        foreignMovies.setTitle( "Sixth Sense, The" );

        TargetRelease germanMovies = ReleaseMapper.INSTANCE.toGerman( foreignMovies );
        assertThat( germanMovies ).isNotNull();
        assertThat( germanMovies.getTitle() ).isEqualTo( "Der sechste Sinn" );
    }

}
