/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.hybrid;

import org.mapstruct.ap.test.selection.qualifier.annotation.EnglishToGerman;
import org.mapstruct.ap.test.selection.qualifier.annotation.NonQualifierAnnotated;
import org.mapstruct.ap.test.selection.qualifier.annotation.TitleTranslator;
import org.mapstruct.ap.test.selection.qualifier.handwritten.SomeOtherMapper;
import org.mapstruct.ap.test.selection.qualifier.handwritten.Titles;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

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
public class HybridTest {

    @ProcessorTest
    public void shouldMatchClassAndMethod() {

        SourceRelease foreignMovies = new SourceRelease();
        foreignMovies.setTitle( "Sixth Sense, The" );

        TargetRelease germanMovies = ReleaseMapper.INSTANCE.toGerman( foreignMovies );
        assertThat( germanMovies ).isNotNull();
        assertThat( germanMovies.getTitle() ).isEqualTo( "Der sechste Sinn" );
    }

}
