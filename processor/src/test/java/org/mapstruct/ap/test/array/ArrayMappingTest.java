/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.array;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.array._target.ScientistDto;
import org.mapstruct.ap.test.array.source.Scientist;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@WithClasses( { Scientist.class, ScientistDto.class, ScienceMapper.class } )
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("108")
public class ArrayMappingTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( ScienceMapper.class );

    @Test
    public void shouldCopyArraysInBean() {

        Scientist source = new Scientist("Bob");
        source.setPublications( new String[]{ "the Lancet", "Nature" } );
        source.publicPublications = new String[] { "public the Lancet", "public Nature" };

        ScientistDto dto = ScienceMapper.INSTANCE.scientistToDto( source );

        assertThat( dto ).isNotNull();
        assertThat( dto ).isNotEqualTo( source );
        assertThat( dto.getPublications() ).containsOnly( "the Lancet", "Nature" );
        assertThat( dto.publicPublications ).containsOnly( "public the Lancet", "public Nature" );
    }

    @Test
    public void shouldForgeMappingForIntToString() {

        Scientist source = new Scientist("Bob");
        source.setPublicationYears( new String[]{"1993", "1997"} );
        source.publicPublicationYears = new String[]{"1994", "1998"};

        ScientistDto dto = ScienceMapper.INSTANCE.scientistToDto( source );

        assertThat( dto ).isNotNull();
        assertThat( dto.getPublicationYears() ).containsOnly( 1993, 1997 );
        assertThat( dto.publicPublicationYears ).containsOnly( 1994, 1998 );
    }

    @Test
    public void shouldMapArrayToArray() {
        ScientistDto[] dtos = ScienceMapper.INSTANCE
            .scientistsToDtos( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) } );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );
    }

    @Test
    public void shouldMapListToArray() {
        ScientistDto[] dtos = ScienceMapper.INSTANCE
            .scientistsToDtos( Arrays.asList( new Scientist( "Bob" ), new Scientist( "Larry" ) ) );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );
    }

    @Test
    public void shouldMapArrayToList() {
        List<ScientistDto> dtos = ScienceMapper.INSTANCE
            .scientistsToDtosAsList( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) } );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );
    }

    @Test
    public void shouldMapArrayToArrayExistingSmallerSizedTarget() {

        ScientistDto[] existingTarget =  new ScientistDto[]{ new ScientistDto( "Jim" ) };

        ScientistDto[] target = ScienceMapper.INSTANCE
            .scientistsToDtos( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) }, existingTarget );

        assertThat( target ).isNotNull();
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( target ).extracting( "name" ).containsOnly( "Bob" );
    }

    @Test
    public void shouldMapArrayToArrayExistingEqualSizedTarget() {

        ScientistDto[] existingTarget =  new ScientistDto[]{ new ScientistDto( "Jim" ), new ScientistDto( "Bart" ) };

        ScientistDto[] target = ScienceMapper.INSTANCE
            .scientistsToDtos( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) }, existingTarget );

        assertThat( target ).isNotNull();
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( target ).extracting( "name" ).containsOnly( "Bob", "Larry"  );
    }

    @Test
    public void shouldMapArrayToArrayExistingLargerSizedTarget() {

        ScientistDto[] existingTarget =
                new ScientistDto[]{ new ScientistDto( "Jim" ), new ScientistDto( "Bart" ), new ScientistDto( "John" ) };

        ScientistDto[] target = ScienceMapper.INSTANCE
            .scientistsToDtos( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) }, existingTarget );

        assertThat( target ).isNotNull();
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( target ).extracting( "name" ).containsOnly( "Bob", "Larry", "John"  );
    }

    @Test
    public void shouldMapTargetToNullWhenNullSource() {
        // TODO: What about existing target?

        ScientistDto[] existingTarget =
                new ScientistDto[]{ new ScientistDto( "Jim" ) };

        ScientistDto[] target = ScienceMapper.INSTANCE.scientistsToDtos( null, existingTarget );

        assertThat( target ).isNull();
        assertThat( existingTarget ).extracting( "name" ).containsOnly( "Jim" );
    }

    @IssueKey("534")
    @Test
    public void shouldMapbooleanWhenReturnDefault() {

        boolean[] existingTarget = new boolean[]{true};
        boolean[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( false );
        assertThat( existingTarget ).containsOnly( false );

        assertThat( ScienceMapper.INSTANCE.nvmMapping( null ) ).isEmpty();
    }

    @Test
    public void shouldMapshortWhenReturnDefault() {
        short[] existingTarget = new short[]{ 5 };
        short[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( new short[] { 0 } );
        assertThat( existingTarget ).containsOnly( new short[] { 0 } );
    }

    @Test
    public void shouldMapcharWhenReturnDefault() {
        char[] existingTarget = new char[]{ 'a' };
        char[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( new char[] { 0 } );
        assertThat( existingTarget ).containsOnly( new char[] { 0 } );
    }

    @Test
    public void shouldMapintWhenReturnDefault() {
        int[] existingTarget = new int[]{ 5 };
        int[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( new int[] { 0 } );
        assertThat( existingTarget ).containsOnly( new int[] { 0 } );
    }

    @Test
    public void shouldMaplongWhenReturnDefault() {
        long[] existingTarget = new long[]{ 5L };
        long[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( new long[] { 0L } );
        assertThat( existingTarget ).containsOnly( new long[] { 0L } );
    }

    @Test
    public void shouldMapfloatWhenReturnDefault() {
        float[] existingTarget = new float[]{ 3.1f };
        float[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( new float[] { 0.0f } );
        assertThat( existingTarget ).containsOnly( new float[] { 0.0f } );
    }

    @Test
    public void shouldMapdoubleWhenReturnDefault() {
        double[] existingTarget = new double[]{ 5.0d };
        double[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( new double[] { 0.0d } );
        assertThat( existingTarget ).containsOnly( new double[] { 0.0d } );
    }

    @Test
    public void shouldVoidMapintWhenReturnNull() {
        long[] existingTarget = new long[]{ 5L };
        ScienceMapper.INSTANCE.nvmMappingVoidReturnNull( null, existingTarget );
        assertThat( existingTarget ).containsOnly( new long[] { 5L } );
    }

    @Test
    public void shouldVoidMapintWhenReturnDefault() {
        long[] existingTarget = new long[]{ 5L };
        ScienceMapper.INSTANCE.nvmMappingVoidReturnDefault( null, existingTarget );
        assertThat( existingTarget ).containsOnly( new long[] { 0L } );
    }

    @Test
    @IssueKey( "999" )
    public void shouldNotContainFQNForStringArray() {
        generatedSource.forMapper( ScienceMapper.class ).content().doesNotContain( "java.lang.String[]" );
    }
}
