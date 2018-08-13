/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1569.java8;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Issue1569Mapper.class,
    Source.class,
    Target.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1569")
public class Issue1569Test {

    @Test
    public void shouldGenerateCorrectMapping() {
        Source source = new Source();
        Date date = Date.from( LocalDate.of( 2018, Month.AUGUST, 5 ).atTime( 20, 30 ).toInstant( ZoneOffset.UTC ) );
        source.setPromotionDate( date );

        Target target = Issue1569Mapper.INSTANCE.map( source );

        assertThat( target.getPromotionDate() ).isEqualTo( LocalDate.of( 2018, Month.AUGUST, 5 ) );
    }
}
