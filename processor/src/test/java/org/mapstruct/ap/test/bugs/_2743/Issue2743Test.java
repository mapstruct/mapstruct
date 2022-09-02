package org.mapstruct.ap.test.bugs._2743;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2743")
@WithClasses({
    Issue2743Mapper.class
})
class Issue2743Test {

    @ProcessorTest
    void shouldCompile() {

    }
}
