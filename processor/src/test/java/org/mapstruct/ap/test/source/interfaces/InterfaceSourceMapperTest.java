/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.interfaces;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Diego Pedregal
 */
@WithClasses({
    InterfaceMapper.class,
    A.class,
    E.class
})
@IssueKey("3909")
public class InterfaceSourceMapperTest {

    @ProcessorTest
    public void shouldNotReportErrorOnInterfaceSource() {

    }

}
