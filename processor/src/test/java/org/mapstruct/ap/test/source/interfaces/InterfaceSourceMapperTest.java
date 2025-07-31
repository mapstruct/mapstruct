package org.mapstruct.ap.test.source.interfaces;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

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
