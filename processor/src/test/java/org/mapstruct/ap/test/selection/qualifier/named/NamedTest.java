/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.named;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.ap.test.selection.qualifier.annotation.CreateGermanRelease;
import org.mapstruct.ap.test.selection.qualifier.annotation.EnglishToGerman;
import org.mapstruct.ap.test.selection.qualifier.annotation.NonQualifierAnnotated;
import org.mapstruct.ap.test.selection.qualifier.annotation.TitleTranslator;
import org.mapstruct.ap.test.selection.qualifier.bean.AbstractEntry;
import org.mapstruct.ap.test.selection.qualifier.bean.GermanRelease;
import org.mapstruct.ap.test.selection.qualifier.bean.OriginalRelease;
import org.mapstruct.ap.test.selection.qualifier.bean.ReleaseFactory;
import org.mapstruct.ap.test.selection.qualifier.handwritten.Facts;
import org.mapstruct.ap.test.selection.qualifier.handwritten.PlotWords;
import org.mapstruct.ap.test.selection.qualifier.handwritten.SomeOtherMapper;
import org.mapstruct.ap.test.selection.qualifier.handwritten.Titles;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey( "750" )
@WithClasses( {
    OriginalRelease.class,
    GermanRelease.class,
    AbstractEntry.class,
    SomeOtherMapper.class,
    NonQualifierAnnotated.class
} )
public class NamedTest {

    @ProcessorTest
    @WithClasses( {
        Titles.class,
        Facts.class,
        PlotWords.class,
        OriginalRelease.class,
        EnglishToGerman.class,
        TitleTranslator.class,
        MovieMapper.class,
        KeyWordMapper.class,
        FactMapper.class } )
    public void shouldMatchClassAndMethod() {

        OriginalRelease foreignMovies = new OriginalRelease();
        foreignMovies.setTitle( "Sixth Sense, The" );
        foreignMovies.setKeyWords( Arrays.asList( "evergreen", "magnificent" ) );
        Map<String, List<String>> facts = new HashMap<>();
        facts.put( "director", Arrays.asList( "M. Night Shyamalan" ) );
        facts.put( "cast", Arrays.asList( "Bruce Willis", "Haley Joel Osment", "Toni Collette" ) );
        facts.put( "plot keywords", Arrays.asList( "boy", "child psychologist", "I see dead people" ) );
        foreignMovies.setFacts( facts );

        GermanRelease germanMovies = MovieMapper.INSTANCE.toGerman( foreignMovies );
        assertThat( germanMovies ).isNotNull();
        assertThat( germanMovies.getTitle() ).isEqualTo( "Der sechste Sinn" );
        assertThat( germanMovies.getKeyWords() ).isNotNull();
        assertThat( germanMovies.getKeyWords().size() ).isEqualTo( 2 );
        assertThat( germanMovies.getKeyWords() ).containsSequence( "Evergreen", "Großartig" );

        assertThat( germanMovies.getFacts() ).isNotNull();
        assertThat( germanMovies.getFacts() ).hasSize( 3 );
        assertThat( germanMovies.getFacts() ).contains(
            entry( "Regisseur", Arrays.asList( "M. Night Shyamalan" ) ),
            entry( "Besetzung", Arrays.asList( "Bruce Willis", "Haley Joel Osment", "Toni Collette" ) ),
            entry( "Handlungstichwörter", Arrays.asList( "Jungen", "Kinderpsychologe", "Ich sehe tote Menschen" ) )
        );
    }

    @ProcessorTest
    @WithClasses( {
        MovieFactoryMapper.class,
        ReleaseFactory.class,
        CreateGermanRelease.class
    })
    @IssueKey( "342")
    public void testFactorySelectionWithQualifier() {

        OriginalRelease foreignMovies = new OriginalRelease();
        foreignMovies.setTitle( "Sixth Sense, The" );
        foreignMovies.setKeyWords( Arrays.asList( "evergreen", "magnificent" ) );
        Map<String, List<String>> facts = new HashMap<>();
        facts.put( "director", Arrays.asList( "M. Night Shyamalan" ) );
        facts.put( "cast", Arrays.asList( "Bruce Willis", "Haley Joel Osment", "Toni Collette" ) );
        facts.put( "plot keywords", Arrays.asList( "boy", "child psychologist", "I see dead people" ) );
        foreignMovies.setFacts( facts );

        AbstractEntry abstractEntry = MovieFactoryMapper.INSTANCE.toGerman( foreignMovies );
        assertThat( abstractEntry ).isNotNull();
        assertThat( abstractEntry ).isInstanceOf( GermanRelease.class );
        assertThat( abstractEntry.getTitle() ).isEqualTo( "Sixth Sense, The" );
        assertThat( abstractEntry.getKeyWords() ).isNotNull();
        assertThat( abstractEntry.getKeyWords().size() ).isEqualTo( 2 );
        assertThat( abstractEntry.getKeyWords() ).containsSequence( "evergreen", "magnificent" );

        assertThat( abstractEntry.getFacts() ).isNotNull();
        assertThat( abstractEntry.getFacts() ).hasSize( 3 );
        assertThat( abstractEntry.getFacts() ).contains(
                entry( "director", Arrays.asList( "M. Night Shyamalan" ) ),
                entry( "cast", Arrays.asList( "Bruce Willis", "Haley Joel Osment", "Toni Collette" ) ),
                entry( "plot keywords", Arrays.asList( "boy", "child psychologist", "I see dead people" ) )
        );
    }

}
