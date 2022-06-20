/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.assertj.core.api.Assertions;
import org.mapstruct.ap.test.subclassmapping.mappables.Source;
import org.mapstruct.ap.test.subclassmapping.mappables.SourceSubclass;
import org.mapstruct.ap.test.subclassmapping.mappables.Target;
import org.mapstruct.factory.Mappers;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@IssueKey( "2438" )
@WithClasses( { Source.class, SourceSubclass.class, Target.class } )
class MappingInheritanceTest {

    @ProcessorTest
    @WithClasses( { SubclassMapper.class } )
    void inheritance() {
        SubclassMapper mapper = Mappers.getMapper( SubclassMapper.class );
        SourceSubclass sourceSubclass = new SourceSubclass( "f1", "f2", "f3", "f4", "f5" );

        Target result = mapper.mapSuperclass( sourceSubclass );

        Assertions.assertThat( result.getTarget1() ).isEqualTo( "f1" );
        Assertions.assertThat( result.getTarget2() ).isEqualTo( "f2" );
        Assertions.assertThat( result.getTarget3() ).isEqualTo( "f3" );
        Assertions.assertThat( result.getTarget4() ).isEqualTo( "f4" );
        Assertions.assertThat( result.getTarget5() ).isEqualTo( "f5" );
    }

    @ProcessorTest
    @WithClasses( { SubclassMapper.class } )
    void superclass() {
        SubclassMapper mapper = Mappers.getMapper( SubclassMapper.class );
        Source source = new Source( "f1", "f2", "f3" );

        Target result = mapper.mapSuperclass( source );

        Assertions.assertThat( result.getTarget1() ).isEqualTo( "f1" );
        Assertions.assertThat( result.getTarget2() ).isEqualTo( "f2" );
        Assertions.assertThat( result.getTarget3() ).isEqualTo( "f3" );
        Assertions.assertThat( result.getTarget4() ).isNull();
        Assertions.assertThat( result.getTarget5() ).isNull();
    }
}
