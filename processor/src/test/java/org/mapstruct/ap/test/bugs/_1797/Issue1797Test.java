/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1797;

import java.util.EnumSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1797")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Customer.class,
    CustomerDto.class,
    Issue1797Mapper.class
})
public class Issue1797Test {

    @Test
    public void shouldCorrectlyMapEnumSetToEnumSet() {

        Customer customer = new Customer( EnumSet.of( Customer.Type.ONE ) );

        CustomerDto customerDto = Issue1797Mapper.INSTANCE.map( customer );

        assertThat( customerDto.getTypes() ).containsExactly( CustomerDto.Type.ONE );
    }
}
