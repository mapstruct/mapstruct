/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.array;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.array._target.GenericScientistDto;
import org.mapstruct.ap.test.array._target.ScientistDto;
import org.mapstruct.ap.test.array.source.GenericScientist;
import org.mapstruct.ap.test.array.source.Scientist;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses( { Scientist.class, ScientistDto.class, GenericScientist.class, GenericScientistDto.class,
        ScienceMapper.class } )
@IssueKey("108")
public class ArrayMappingTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( ScienceMapper.class );

    @ProcessorTest
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

    @ProcessorTest
    public void shouldForgeMappingForIntToString() {

        Scientist source = new Scientist("Bob");
        source.setPublicationYears( new String[]{"1993", "1997"} );
        source.publicPublicationYears = new String[]{"1994", "1998"};

        ScientistDto dto = ScienceMapper.INSTANCE.scientistToDto( source );

        assertThat( dto ).isNotNull();
        assertThat( dto.getPublicationYears() ).containsOnly( 1993, 1997 );
        assertThat( dto.publicPublicationYears ).containsOnly( 1994, 1998 );
    }

    @ProcessorTest
    public void shouldMapArrayToArrayAndNullToNull() {
        ScientistDto[] dtos = ScienceMapper.INSTANCE
            .scientistsToDtosReturnNull( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) } );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );

        assertThat( ScienceMapper.INSTANCE.scientistsToDtosReturnNull( (Scientist[]) null ) ).isNull();
    }

    @ProcessorTest
    public void shouldMapArrayToArrayAndNullToDefault() {
        ScientistDto[] dtos = ScienceMapper.INSTANCE
                .scientistsToDtosReturnDefault( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) } );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );

        assertThat( ScienceMapper.INSTANCE.scientistsToDtosReturnDefault( (Scientist[]) null ) ).isEmpty();
    }

    @ProcessorTest
    public void shouldMapListToArrayAndNullToNull() {
        ScientistDto[] dtos = ScienceMapper.INSTANCE
            .scientistsToDtosReturnNull( Arrays.asList( new Scientist( "Bob" ), new Scientist( "Larry" ) ) );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );

        assertThat( ScienceMapper.INSTANCE.scientistsToDtosReturnNull( (List<Scientist>) null ) ).isNull();
    }

    @ProcessorTest
    public void shouldMapListToArrayAndNullToDefault() {
        ScientistDto[] dtos = ScienceMapper.INSTANCE
                .scientistsToDtosReturnDefault( Arrays.asList( new Scientist( "Bob" ), new Scientist( "Larry" ) ) );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );

        assertThat( ScienceMapper.INSTANCE.scientistsToDtosReturnDefault( (List<Scientist>) null ) ).isEmpty();
    }

    @ProcessorTest
    public void shouldMapArrayToListAndNullToNull() {
        List<ScientistDto> dtos = ScienceMapper.INSTANCE
            .scientistsToDtosAsListReturnNull( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) } );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );

        assertThat( ScienceMapper.INSTANCE.scientistsToDtosAsListReturnNull( null ) ).isNull();
    }

    @ProcessorTest
    public void shouldMapArrayToListAndNullToDefault() {
        List<ScientistDto> dtos = ScienceMapper.INSTANCE
                .scientistsToDtosAsListReturnDefault(
                        new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) } );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );

        assertThat( ScienceMapper.INSTANCE.scientistsToDtosAsListReturnDefault( null ) ).isEmpty();
    }

    @ProcessorTest
    @SuppressWarnings("unchecked")
    public void shouldMapGenericArrayToGenericArrayAndNullToNull() {
        GenericScientistDto<String>[] dtos = ScienceMapper.INSTANCE
                .genericScientistToDtosReturnNull(
                        new GenericScientist[]{ new GenericScientist<>( "Bob" ), new GenericScientist<>( "Larry" ) } );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );

        assertThat( ScienceMapper.INSTANCE.genericScientistToDtosReturnNull( (GenericScientist<String>[]) null ) )
                .isNull();
    }

    @ProcessorTest
    @SuppressWarnings("unchecked")
    public void shouldMapGenericArrayToGenericArrayAndNullToDefault() {
        GenericScientistDto<String>[] dtos = ScienceMapper.INSTANCE.genericScientistToDtosReturnDefault(
                new GenericScientist[]{ new GenericScientist<>( "Bob" ), new GenericScientist<>( "Larry" ) } );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );

        assertThat( ScienceMapper.INSTANCE.genericScientistToDtosReturnDefault( (GenericScientist<String>[]) null ) )
                .isEmpty();
    }

    @ProcessorTest
    public void shouldMapListToGenericArrayAndNullToNull() {
        GenericScientistDto<String>[] dtos = ScienceMapper.INSTANCE.genericScientistToDtosReturnNull(
                Arrays.asList( new GenericScientist<>( "Bob" ), new GenericScientist<>( "Larry" ) ) );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );

        assertThat( ScienceMapper.INSTANCE.genericScientistToDtosReturnNull( (List<GenericScientist<String>>) null ) )
                .isNull();
    }

    @ProcessorTest
    public void shouldMapListToGenericArrayAndNullToDefault() {
        GenericScientistDto<String>[] dtos = ScienceMapper.INSTANCE
                .genericScientistToDtosReturnDefault(
                        Arrays.asList( new GenericScientist<>("Bob"), new GenericScientist<>( "Larry" ) ) );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );

        assertThat( ScienceMapper.INSTANCE
                .genericScientistToDtosReturnDefault( (List<GenericScientist<String>>) null ) ).isEmpty();
    }

    @ProcessorTest
    @SuppressWarnings("unchecked")
    public void shouldMapGenericArrayToListAndNullToNull() {
        List<GenericScientist<String>> dtos = ScienceMapper.INSTANCE.genericScientistToDtosAsList(
                new GenericScientist[]{ new GenericScientist<>( "Bob" ), new GenericScientist<>( "Larry" ) } );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).extracting( "name" ).containsOnly( "Bob", "Larry" );

        assertThat( ScienceMapper.INSTANCE.genericScientistToDtosAsList( null ) ).isNull();
    }

    @ProcessorTest
    public void shouldMapArrayToArrayExistingSmallerSizedTarget() {

        ScientistDto[] existingTarget =  new ScientistDto[]{ new ScientistDto( "Jim" ) };

        ScientistDto[] target = ScienceMapper.INSTANCE
            .scientistsToDtos( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) }, existingTarget );

        assertThat( target ).isNotNull();
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( target ).extracting( "name" ).containsOnly( "Bob" );
    }

    @ProcessorTest
    public void shouldMapArrayToArrayExistingEqualSizedTarget() {

        ScientistDto[] existingTarget =  new ScientistDto[]{ new ScientistDto( "Jim" ), new ScientistDto( "Bart" ) };

        ScientistDto[] target = ScienceMapper.INSTANCE
            .scientistsToDtos( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) }, existingTarget );

        assertThat( target ).isNotNull();
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( target ).extracting( "name" ).containsOnly( "Bob", "Larry"  );
    }

    @ProcessorTest
    public void shouldMapArrayToArrayExistingLargerSizedTarget() {

        ScientistDto[] existingTarget =
                new ScientistDto[]{ new ScientistDto( "Jim" ), new ScientistDto( "Bart" ), new ScientistDto( "John" ) };

        ScientistDto[] target = ScienceMapper.INSTANCE
            .scientistsToDtos( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) }, existingTarget );

        assertThat( target ).isNotNull();
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( target ).extracting( "name" ).containsOnly( "Bob", "Larry", "John"  );
    }

    @ProcessorTest
    public void shouldReturnMapTargetWhenNullSource() {

        ScientistDto[] existingTarget =
                new ScientistDto[]{ new ScientistDto( "Jim" ) };

        ScientistDto[] target = ScienceMapper.INSTANCE.scientistsToDtos( null, existingTarget );

        assertThat( target ).isNotNull();
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( existingTarget ).extracting( "name" ).containsOnly( "Jim" );
    }

    @IssueKey("534")
    @ProcessorTest
    public void shouldMapBooleanWhenReturnDefault() {

        boolean[] existingTarget = new boolean[]{true};
        boolean[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( false );
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( existingTarget ).containsOnly( false );

        assertThat( ScienceMapper.INSTANCE.nvmMapping( null ) ).isEmpty();
    }

    @ProcessorTest
    public void shouldMapShortWhenReturnDefault() {
        short[] existingTarget = new short[]{ 5 };
        short[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( new short[] { 0 } );
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( existingTarget ).containsOnly( new short[] { 0 } );
    }

    @ProcessorTest
    public void shouldMapCharWhenReturnDefault() {
        char[] existingTarget = new char[]{ 'a' };
        char[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( new char[] { 0 } );
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( existingTarget ).containsOnly( new char[] { 0 } );
    }

    @ProcessorTest
    public void shouldMapIntWhenReturnDefault() {
        int[] existingTarget = new int[]{ 5 };
        int[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( 0 );
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( existingTarget ).containsOnly( 0 );
    }

    @ProcessorTest
    public void shouldMapLongWhenReturnDefault() {
        long[] existingTarget = new long[]{ 5L };
        long[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( 0L );
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( existingTarget ).containsOnly( 0L );
    }

    @ProcessorTest
    public void shouldMapFloatWhenReturnDefault() {
        float[] existingTarget = new float[]{ 3.1f };
        float[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( 0.0f );
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( existingTarget ).containsOnly( 0.0f );
    }

    @ProcessorTest
    public void shouldMapDoubleWhenReturnDefault() {
        double[] existingTarget = new double[]{ 5.0d };
        double[] target = ScienceMapper.INSTANCE.nvmMapping( null, existingTarget );

        assertThat( target ).containsOnly( 0.0d );
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( existingTarget ).containsOnly( 0.0d );
    }

    @ProcessorTest
    public void shouldVoidMapIntWhenReturnNull() {
        long[] existingTarget = new long[]{ 5L };
        ScienceMapper.INSTANCE.nvmMappingVoidReturnNull( null, existingTarget );
        assertThat( existingTarget ).containsOnly( 5L );
    }

    @ProcessorTest
    public void shouldVoidMapIntWhenReturnDefault() {
        long[] existingTarget = new long[]{ 5L };
        ScienceMapper.INSTANCE.nvmMappingVoidReturnDefault( null, existingTarget );
        assertThat( existingTarget ).containsOnly( 0L );
    }

    @ProcessorTest
    @IssueKey( "999" )
    public void shouldNotContainFQNForStringArray() {
        generatedSource.forMapper( ScienceMapper.class ).content().doesNotContain( "java.lang.String[]" );
    }
}
