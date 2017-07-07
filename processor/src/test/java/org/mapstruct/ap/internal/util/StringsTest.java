/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Filip Hrisafov
 */
public class StringsTest {

    private static final Locale TURKEY_LOCALE = getTurkeyLocale();
    private Locale defaultLocale;

    @Before
    public void before() {
        defaultLocale = Locale.getDefault();
    }

    @After
    public void after() {
        Locale.setDefault( defaultLocale );
    }

    @Test
    public void testCapitalize() throws Exception {
        assertThat( Strings.capitalize( null ) ).isNull();
        assertThat( Strings.capitalize( "c" ) ).isEqualTo( "C" );
        assertThat( Strings.capitalize( "capitalize" ) ).isEqualTo( "Capitalize" );
        assertThat( Strings.capitalize( "AlreadyCapitalized" ) ).isEqualTo( "AlreadyCapitalized" );
        assertThat( Strings.capitalize( "notCapitalized" ) ).isEqualTo( "NotCapitalized" );
    }

    @Test
    public void testDecapitalize() throws Exception {
        assertThat( Strings.decapitalize( null ) ).isNull();
        assertThat( Strings.decapitalize( "c" ) ).isEqualTo( "c" );
        assertThat( Strings.decapitalize( "capitalize" ) ).isEqualTo( "capitalize" );
        assertThat( Strings.decapitalize( "AlreadyCapitalized" ) ).isEqualTo( "alreadyCapitalized" );
        assertThat( Strings.decapitalize( "notCapitalized" ) ).isEqualTo( "notCapitalized" );
    }

    @Test
    public void testJoin() throws Exception {
        assertThat( Strings.join( new ArrayList<String>(), "-" ) ).isEqualTo( "" );
        assertThat( Strings.join( Arrays.asList( "Hello", "World" ), "-" ) ).isEqualTo( "Hello-World" );
        assertThat( Strings.join( Arrays.asList( "Hello" ), "-" ) ).isEqualTo( "Hello" );
    }

    @Test
    public void testJoinAndCamelize() throws Exception {
        assertThat( Strings.joinAndCamelize( new ArrayList<String>() ) ).isEqualTo( "" );
        assertThat( Strings.joinAndCamelize( Arrays.asList( "Hello", "World" ) ) ).isEqualTo( "HelloWorld" );
        assertThat( Strings.joinAndCamelize( Arrays.asList( "Hello", "world" ) ) ).isEqualTo( "HelloWorld" );
        assertThat( Strings.joinAndCamelize( Arrays.asList( "hello", "world" ) ) ).isEqualTo( "helloWorld" );
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertThat( Strings.isEmpty( null ) ).isTrue();
        assertThat( Strings.isEmpty( "" ) ).isTrue();
        assertThat( Strings.isEmpty( " " ) ).isFalse();
        assertThat( Strings.isEmpty( "not empty" ) ).isFalse();
    }

    @Test
    public void testGetSaveVariableNameWithArrayExistingVariables() throws Exception {
        assertThat( Strings.getSaveVariableName( "int[]" ) ).isEqualTo( "intArray" );
        assertThat( Strings.getSaveVariableName( "Extends" ) ).isEqualTo( "extends1" );
        assertThat( Strings.getSaveVariableName( "class" ) ).isEqualTo( "class1" );
        assertThat( Strings.getSaveVariableName( "Class" ) ).isEqualTo( "class1" );
        assertThat( Strings.getSaveVariableName( "Case" ) ).isEqualTo( "case1" );
        assertThat( Strings.getSaveVariableName( "Synchronized" ) ).isEqualTo( "synchronized1" );
        assertThat( Strings.getSaveVariableName( "prop", "prop", "prop_" ) ).isEqualTo( "prop1" );
    }

    @Test
    public void testGetSaveVariableNameVariablesEndingOnNumberVariables() throws Exception {
        assertThat( Strings.getSaveVariableName( "prop1", "prop1" ) ).isEqualTo( "prop1_1" );
        assertThat( Strings.getSaveVariableName( "prop1", "prop1", "prop1_1" ) ).isEqualTo( "prop1_2" );
    }

    @Test
    public void testGetSaveVariableNameWithCollection() throws Exception {
        assertThat( Strings.getSaveVariableName( "int[]", new ArrayList<String>() ) ).isEqualTo( "intArray" );
        assertThat( Strings.getSaveVariableName( "Extends", new ArrayList<String>() ) ).isEqualTo( "extends1" );
        assertThat( Strings.getSaveVariableName( "prop", Arrays.asList( "prop", "prop1" ) ) ).isEqualTo( "prop2" );
        assertThat( Strings.getSaveVariableName( "prop.font", Arrays.asList( "propFont", "propFont_" ) ) )
            .isEqualTo( "propFont1" );
    }

    @Test
    public void testSanitizeIdentifierName() throws Exception {
        assertThat( Strings.sanitizeIdentifierName( "test" ) ).isEqualTo( "test" );
        assertThat( Strings.sanitizeIdentifierName( "int[]" ) ).isEqualTo( "intArray" );
    }

    @Test
    public void findMostSimilarWord() throws Exception {
        String mostSimilarWord = Strings.getMostSimilarWord(
            "fulName",
            Arrays.asList( "fullAge", "fullName", "address", "status" )
        );
        assertThat( mostSimilarWord ).isEqualTo( "fullName" );
    }

    @Test
    public void capitalizeEnglish() {
        Locale.setDefault( Locale.ENGLISH );
        String international = Strings.capitalize( "international" );
        assertThat( international ).isEqualTo( "International" );
    }

    @Test
    public void decapitalizeEnglish() {
        Locale.setDefault( Locale.ENGLISH );
        String international = Strings.decapitalize( "International" );
        assertThat( international ).isEqualTo( "international" );
    }

    @Test
    public void capitalizeTurkish() {
        Locale.setDefault( TURKEY_LOCALE );
        String international = Strings.capitalize( "international" );
        assertThat( international ).isEqualTo( "International" );
    }

    @Test
    public void decapitalizeTurkish() {
        Locale.setDefault( TURKEY_LOCALE );
        String international = Strings.decapitalize( "International" );
        assertThat( international ).isEqualTo( "international" );
    }

    private static Locale getTurkeyLocale() {
        Locale turkeyLocale = Locale.forLanguageTag( "tr" );

        if ( turkeyLocale == null ) {
            throw new IllegalStateException( "Can't find Turkey locale." );
        }

        return turkeyLocale;
    }
}
