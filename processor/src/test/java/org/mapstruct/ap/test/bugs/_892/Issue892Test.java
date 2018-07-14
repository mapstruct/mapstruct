/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._892;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._892.Issue892Mapper.Source;
import org.mapstruct.ap.test.bugs._892.Issue892Mapper.Target;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

/**
 * When having two setter methods with the same name, choose the one with the argument type matching the getter method.
 *
 * @author Andreas Gudian
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({ Issue892Mapper.class })
public class Issue892Test {
    @Test
    public void compiles() {
        Source src = new Source();
        src.setType( 42 );

        Target target = Mappers.getMapper( Issue892Mapper.class ).toTarget( src );
        assertThat( target.getType() ).isEqualTo( 42 );
    }
}
