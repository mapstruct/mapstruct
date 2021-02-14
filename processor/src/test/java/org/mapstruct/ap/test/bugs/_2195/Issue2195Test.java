/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2195;

import org.mapstruct.ap.test.bugs._2195.dto.Source;
import org.mapstruct.ap.test.bugs._2195.dto.Target;
import org.mapstruct.ap.test.bugs._2195.dto.TargetBase;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2195")
@WithClasses( { Source.class, Target.class, TargetBase.class } )
public class Issue2195Test {

    @ProcessorTest
    @WithClasses( Issue2195Mapper.class )
    public void test() {

        Source source = new Source();
        source.setName( "JohnDoe" );

        TargetBase target = Issue2195Mapper.INSTANCE.map( source );

        assertThat( target ).isInstanceOf( Target.class );
    }
}
