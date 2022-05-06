/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2839;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Hakan Özkan
 */
@IssueKey("2839")
@WithClasses({ Car.class, CarDto.class, Id.class, CarMapper.class })
public class Issue2839Test {

    @ProcessorTest
    void shouldCompile() {
    }
}
