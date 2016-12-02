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
package org.mapstruct.ap.internal.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * @author Filip Hrisafov
 */
public class StringsTest {
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
        assertThat( Strings.getSaveVariableName( "Extends" ) ).isEqualTo( "extends_" );
        assertThat( Strings.getSaveVariableName( "class" ) ).isEqualTo( "class_" );
        assertThat( Strings.getSaveVariableName( "Class" ) ).isEqualTo( "class_" );
        assertThat( Strings.getSaveVariableName( "Case" ) ).isEqualTo( "case_" );
        assertThat( Strings.getSaveVariableName( "Synchronized" ) ).isEqualTo( "synchronized_" );
        assertThat( Strings.getSaveVariableName( "prop", "prop", "prop_" ) ).isEqualTo( "prop__" );
    }

    @Test
    public void testGetSaveVariableNameWithCollection() throws Exception {
        assertThat( Strings.getSaveVariableName( "int[]", new ArrayList<String>() ) ).isEqualTo( "intArray" );
        assertThat( Strings.getSaveVariableName( "Extends", new ArrayList<String>() ) ).isEqualTo( "extends_" );
        assertThat( Strings.getSaveVariableName( "prop", Arrays.asList( "prop", "prop_" ) ) ).isEqualTo( "prop__" );
        assertThat( Strings.getSaveVariableName( "prop.font", Arrays.asList( "propFont", "propFont_" ) ) )
            .isEqualTo( "propFont__" );
    }

    @Test
    public void testSanitizeIdentifierName() throws Exception {
        assertThat( Strings.sanitizeIdentifierName( "test" ) ).isEqualTo( "test" );
        assertThat( Strings.sanitizeIdentifierName( "int[]" ) ).isEqualTo( "intArray" );
    }

}
