/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1541;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 *
 * @author Christian Bandowski
 */
@WithClasses({
    Issue1541Mapper.class,
    Target.class
})
@IssueKey("1541")
public class Issue1541Test {

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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
