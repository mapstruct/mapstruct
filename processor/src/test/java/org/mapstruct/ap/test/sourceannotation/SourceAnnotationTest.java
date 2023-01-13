/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.sourceannotation;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Source.class,
    SourceTargetMapper.class,
    StringConversion.class,
    Target.class,
})
public class SourceAnnotationTest {

    @ProcessorTest
    public void sourceAnnotation() {
        Source source = new Source( "Hello", "World" );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFirstProperty() ).isEqualTo( "HELLO" );
        assertThat( target.getSecondProperty() ).isEqualTo( "world" );
    }

}
