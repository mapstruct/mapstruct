/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.Arrays;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("4035")
@WithJSpecify
class JSpecifyTypeUseTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        JSpecifyTypeUseMapper.class
    );

    @ProcessorTest
    @WithClasses({
        JSpecifyTypeUseSourceBean.class,
        JSpecifyTypeUseTargetBean.class,
        JSpecifyTypeUseSourceElement.class,
        JSpecifyTypeUseTargetElement.class,
        JSpecifyTypeUseMapper.class
    })
    void typeUseNullnessIsPreservedForArraysAndListElements() {
        JSpecifyTypeUseSourceBean source = new JSpecifyTypeUseSourceBean();
        source.setNullableArray( new String[] { "array" } );
        source.setNullableArrayElements( new String[] { null } );
        JSpecifyTypeUseSourceElement element = new JSpecifyTypeUseSourceElement();
        element.setValue( "list" );
        source.setNullableElementArray( new JSpecifyTypeUseSourceElement[] { null, element } );
        source.setNullableListElements( Arrays.asList( null, element ) );
        source.setNestedNullableListElements( Arrays.asList( Arrays.asList( null, element ) ) );

        JSpecifyTypeUseTargetBean target = JSpecifyTypeUseMapper.INSTANCE.map( source );

        assertThat( target.getNullableArray() ).containsExactly( "array" );
        assertThat( target.getNullableArrayElements() ).containsExactly( (String) null );
        assertThat( target.getNullableElementArray()[0] ).isNull();
        assertThat( target.getNullableElementArray()[1].getValue() ).isEqualTo( "list" );
        assertThat( target.getNullableListElements().get( 0 ) ).isNull();
        assertThat( target.getNullableListElements().get( 1 ).getValue() ).isEqualTo( "list" );
        assertThat( target.getNestedNullableListElements() ).hasSize( 1 );
        assertThat( target.getNestedNullableListElements().get( 0 ).get( 0 ) ).isNull();
        assertThat( target.getNestedNullableListElements().get( 0 ).get( 1 ).getValue() )
            .isEqualTo( "list" );
    }
}
