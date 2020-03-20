/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2047;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2023")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Issue2047Mapper.class,
    SourcePerson.class,
    TargetPerson.class
})

public class Issue2047Test {

    @Test
    public void shouldGenerateCorrectCode() {
        SourcePerson source = new SourcePerson(); // The field 'name' remains null
        assertThat( source.getName() ).isNull();

        TargetPerson target = Mappers.getMapper(Issue2047Mapper.class).map( source );

        assertThat( target.getName() ).isEqualTo("DEFAULT");
    }

}
