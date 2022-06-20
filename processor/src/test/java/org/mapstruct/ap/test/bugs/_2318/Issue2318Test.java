/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2318;

import org.mapstruct.ap.test.bugs._2318.Issue2318Mapper.SourceChild;
import org.mapstruct.ap.test.bugs._2318.Issue2318Mapper.TargetChild;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2318")
public class Issue2318Test {

    @ProcessorTest
    @WithClasses( Issue2318Mapper.class )
    public void shouldMap() {

        SourceChild source = new SourceChild();
        source.setValue( "From child" );
        source.setHolder( new Issue2318Mapper.SourceParent.Holder() );
        source.getHolder().setParentValue1( "From parent" );
        source.getHolder().setParentValue2( 12 );

        TargetChild target = Issue2318Mapper.INSTANCE.mapChild( source );

        assertThat( target.getParentValue1() ).isEqualTo( "From parent" );
        assertThat( target.getParentValue2() ).isEqualTo( 12 );
        assertThat( target.getChildValue() ).isEqualTo( "From child" );
    }
}
