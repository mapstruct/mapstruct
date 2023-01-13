/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.sourceannotation.context;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Source.class,
    SourceTargetMapper.class,
    Confidential.class,
    Target.class,
})
public class SourceAnnotationContextTest {

    @ProcessorTest
    public void noPrivileges() {
        Source source = new Source( "John Doe", "21 Jump Street", "50000" );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source, null );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "John Doe" );
        assertThat( target.getAddress() ).isEqualTo( "<confidential>" );
        assertThat( target.getSalary() ).isEqualTo( "<confidential>" );
    }

    @ProcessorTest
    public void companyPrivileges() {
        Source source = new Source( "John Doe", "21 Jump Street", "50000" );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source, "company" );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "John Doe" );
        assertThat( target.getAddress() ).isEqualTo( "21 Jump Street" );
        assertThat( target.getSalary() ).isEqualTo( "<confidential>" );
    }

    @ProcessorTest
    public void managementPrivileges() {
        Source source = new Source( "John Doe", "21 Jump Street", "50000" );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source, "management" );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "John Doe" );
        assertThat( target.getAddress() ).isEqualTo( "21 Jump Street" );
        assertThat( target.getSalary() ).isEqualTo( "50000" );
    }

}
