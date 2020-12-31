/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2318;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._2318.Model.SourceChild;
import org.mapstruct.ap.test.bugs._2318.Model.TargetChild;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2318")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({ Issue2318Mapper.class, Model.class })
public class Issue2318Test {

    @Test
    public void shouldMap() {

        SourceChild source = new SourceChild();
        source.setValue( "From child" );
        source.setHolder( new Model.SourceParent.Holder() );
        source.getHolder().setParentValue1( "From parent" );
        source.getHolder().setParentValue2( 12 );

        TargetChild target = Issue2318Mapper.INSTANCE.mapChild( source );

        assertThat( target.getParentValue1() ).isEqualTo( "From parent" );
        assertThat( target.getParentValue2() ).isEqualTo( 12 );
        assertThat( target.getChildValue() ).isEqualTo( "From child" );
    }
}
