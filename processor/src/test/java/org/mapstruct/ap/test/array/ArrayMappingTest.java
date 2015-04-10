/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.array;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.array._target.ScientistDto;
import org.mapstruct.ap.test.array.source.Scientist;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

@WithClasses( { Scientist.class, ScientistDto.class, ScienceMapper.class } )
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("108")
public class ArrayMappingTest {

    @Test
    public void shouldCopyArraysInBean() {

        Scientist source = new Scientist("Bob");
        source.setPublications( new String[]{ "the Lancet", "Nature" } );

        ScientistDto dto = ScienceMapper.INSTANCE.scientistToDto( source );

        assertThat( dto ).isNotNull();
        assertThat( dto ).isNotEqualTo( source );
        assertThat( dto.getPublications() ).containsOnly( "the Lancet", "Nature");
    }

    @Test
    public void shouldForgeMappingForIntToString() {

        Scientist source = new Scientist("Bob");
        source.setPublicationYears( new String[]{"1993", "1997"} );

        ScientistDto dto = ScienceMapper.INSTANCE.scientistToDto( source );

        assertThat( dto ).isNotNull();
        assertThat( dto.getPublicationYears() ).containsOnly( 1993, 1997 );
    }


    @Test
    public void shouldMapArrayToArray() {
        ScientistDto[] dtos = ScienceMapper.INSTANCE
            .scientistsToDtos( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) } );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).onProperty( "name" ).containsOnly( "Bob", "Larry" );
    }

    @Test
    public void shouldMapListToArray() {
        ScientistDto[] dtos = ScienceMapper.INSTANCE
            .scientistsToDtos( Arrays.asList( new Scientist( "Bob" ), new Scientist( "Larry" ) ) );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).onProperty( "name" ).containsOnly( "Bob", "Larry" );
    }

    @Test
    public void shouldMapArrayToList() {
        List<ScientistDto> dtos = ScienceMapper.INSTANCE
            .scientistsToDtosAsList( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) } );

        assertThat( dtos ).isNotNull();
        assertThat( dtos ).onProperty( "name" ).containsOnly( "Bob", "Larry" );
    }

    @Test
    public void shouldMapArrayToArrayExistingSmallerSizedTarget() {

        ScientistDto[] existingTarget =  new ScientistDto[]{ new ScientistDto( "Jim" ) };

        ScientistDto[] target = ScienceMapper.INSTANCE
            .scientistsToDtos( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) }, existingTarget );

        assertThat( target ).isNotNull();
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( target ).onProperty( "name" ).containsOnly( "Bob" );
    }

    @Test
    public void shouldMapArrayToArrayExistingEqualSizedTarget() {

        ScientistDto[] existingTarget =  new ScientistDto[]{ new ScientistDto( "Jim" ), new ScientistDto( "Bart" ) };

        ScientistDto[] target = ScienceMapper.INSTANCE
            .scientistsToDtos( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) }, existingTarget );

        assertThat( target ).isNotNull();
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( target ).onProperty( "name" ).containsOnly( "Bob", "Larry"  );
    }

    @Test
    public void shouldMapArrayToArrayExistingLargerSizedTarget() {

        ScientistDto[] existingTarget =
                new ScientistDto[]{ new ScientistDto( "Jim" ), new ScientistDto( "Bart" ), new ScientistDto( "John" ) };

        ScientistDto[] target = ScienceMapper.INSTANCE
            .scientistsToDtos( new Scientist[]{ new Scientist( "Bob" ), new Scientist( "Larry" ) }, existingTarget );

        assertThat( target ).isNotNull();
        assertThat( target ).isEqualTo( existingTarget );
        assertThat( target ).onProperty( "name" ).containsOnly( "Bob", "Larry", "John"  );
    }

    @Test
    public void shouldMapTargetToNullWhenNullSource() {
        // TODO: What about existing target?

        ScientistDto[] existingTarget =
                new ScientistDto[]{ new ScientistDto( "Jim" ) };

        ScientistDto[] target = ScienceMapper.INSTANCE.scientistsToDtos( null, existingTarget );

        assertThat( target ).isNull();
        assertThat( existingTarget ).onProperty( "name" ).containsOnly( "Jim" );
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

}
