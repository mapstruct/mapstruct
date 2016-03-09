/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.selection.qualifier.named;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

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
@RunWith( AnnotationProcessorTestRunner.class )
public class NamedTest {

    @Test
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
        Map<String, List<String>> facts = new HashMap<String, List<String>>();
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
        assertThat( germanMovies.getFacts() ).includes(
            entry( "Regisseur", Arrays.asList( "M. Night Shyamalan" ) ),
            entry( "Besetzung", Arrays.asList( "Bruce Willis", "Haley Joel Osment", "Toni Collette" ) ),
            entry( "Handlungstichwörter", Arrays.asList( "Jungen", "Kinderpsychologe", "Ich sehe tote Menschen" ) )
        );
    }

    @Test
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
        Map<String, List<String>> facts = new HashMap<String, List<String>>();
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
        assertThat( abstractEntry.getFacts() ).includes(
                entry( "director", Arrays.asList( "M. Night Shyamalan" ) ),
                entry( "cast", Arrays.asList( "Bruce Willis", "Haley Joel Osment", "Toni Collette" ) ),
                entry( "plot keywords", Arrays.asList( "boy", "child psychologist", "I see dead people" ) )
        );
    }

}
