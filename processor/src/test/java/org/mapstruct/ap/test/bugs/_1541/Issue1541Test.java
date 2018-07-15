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
package org.mapstruct.ap.test.bugs._1541;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Christian Bandowski
 */
@WithClasses({
    Issue1541Mapper.class,
    Target.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1541")
public class Issue1541Test {

    @Test
    public void testMappingWithVarArgs() {
        Target target = Issue1541Mapper.INSTANCE.mapWithVarArgs( "code", "1", "2" );

        assertThat( target ).isNotNull();
        assertThat( target.getCode() ).isEqualTo( "code" );
        assertThat( target.getParameters() ).contains( "1", "2" );
        assertThat( target.isAfterMappingWithArrayCalled() ).isFalse();
        assertThat( target.isAfterMappingWithVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsArrayCalled() ).isFalse();
    }

    @Test
    public void testMappingWithArray() {
        Target target = Issue1541Mapper.INSTANCE.mapWithArray( "code", new String[] { "1", "2" } );

        assertThat( target ).isNotNull();
        assertThat( target.getCode() ).isEqualTo( "code" );
        assertThat( target.getParameters() ).contains( "1", "2" );
        assertThat( target.getParameters2() ).isNull();
        assertThat( target.isAfterMappingWithArrayCalled() ).isFalse();
        assertThat( target.isAfterMappingWithVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsArrayCalled() ).isFalse();
    }

    @Test
    public void testMappingWithVarArgsReassignment() {
        Target target = Issue1541Mapper.INSTANCE.mapWithReassigningVarArgs( "code", "1", "2" );

        assertThat( target ).isNotNull();
        assertThat( target.getCode() ).isEqualTo( "code" );
        assertThat( target.getParameters() ).isNull();
        assertThat( target.getParameters2() ).contains( "1", "2" );
        assertThat( target.isAfterMappingWithArrayCalled() ).isFalse();
        assertThat( target.isAfterMappingWithVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsArrayCalled() ).isFalse();
    }

    @Test
    public void testMappingWithArrayAndVarArgs() {
        Target target = Issue1541Mapper.INSTANCE.mapWithArrayAndVarArgs( "code", new String[] { "1", "2" }, "3", "4" );

        assertThat( target ).isNotNull();
        assertThat( target.getCode() ).isEqualTo( "code" );
        assertThat( target.getParameters() ).contains( "1", "2" );
        assertThat( target.getParameters2() ).contains( "3", "4" );
        assertThat( target.isAfterMappingWithArrayCalled() ).isFalse();
        assertThat( target.isAfterMappingWithVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsArrayCalled() ).isFalse();
    }

    @Test
    public void testVarArgsInAfterMappingAsArray() {
        Target target = Issue1541Mapper.INSTANCE.mapParametersAsArrayInAfterMapping( "code", "1", "2" );

        assertThat( target ).isNotNull();
        assertThat( target.getCode() ).isEqualTo( "code" );
        assertThat( target.getParameters() ).contains( "1", "2" );
        assertThat( target.getParameters2() ).isNull();
        assertThat( target.isAfterMappingWithArrayCalled() ).isTrue();
        assertThat( target.isAfterMappingWithVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsArrayCalled() ).isFalse();
    }

    @Test
    public void testVarArgsInAfterMappingAsVarArgs() {
        Target target = Issue1541Mapper.INSTANCE.mapParametersAsVarArgsInAfterMapping( "code", "1", "2" );

        assertThat( target ).isNotNull();
        assertThat( target.getCode() ).isEqualTo( "code" );
        assertThat( target.getParameters() ).contains( "1", "2" );
        assertThat( target.getParameters2() ).isNull();
        assertThat( target.isAfterMappingWithArrayCalled() ).isFalse();
        assertThat( target.isAfterMappingWithVarArgsCalled() ).isTrue();
        assertThat( target.isAfterMappingContextWithVarArgsAsVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsArrayCalled() ).isFalse();
    }

    @Test
    public void testVarArgsInContextWithVarArgsAfterMapping() {
        Target target = Issue1541Mapper.INSTANCE.mapContextWithVarArgsInAfterMappingWithVarArgs(
            "code",
            new String[] { "1", "2" },
            "3",
            "4"
        );

        assertThat( target ).isNotNull();
        assertThat( target.getCode() ).isEqualTo( "code" );
        assertThat( target.getParameters() ).contains( "1", "2" );
        assertThat( target.getParameters2() ).contains( "3", "4" );
        assertThat( target.isAfterMappingWithArrayCalled() ).isFalse();
        assertThat( target.isAfterMappingWithVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsVarArgsCalled() ).isTrue();
        assertThat( target.isAfterMappingContextWithVarArgsAsArrayCalled() ).isFalse();
    }

    @Test
    public void testVarArgsInContextWithArrayAfterMapping() {
        Target target = Issue1541Mapper.INSTANCE.mapContextWithVarArgsInAfterMappingWithArray(
            "code",
            new String[] { "1", "2" },
            "3",
            "4"
        );

        assertThat( target ).isNotNull();
        assertThat( target.getCode() ).isEqualTo( "code" );
        assertThat( target.getParameters() ).contains( "1", "2" );
        assertThat( target.getParameters2() ).contains( "3", "4" );
        assertThat( target.isAfterMappingWithArrayCalled() ).isFalse();
        assertThat( target.isAfterMappingWithVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsVarArgsCalled() ).isFalse();
        assertThat( target.isAfterMappingContextWithVarArgsAsArrayCalled() ).isTrue();
    }
}
