package org.mapstruct.ap.test.bugs._3707;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@WithClasses({
    Issue3707Mapper.class,
    AbstractDto.class,
    BigIntegerDto.class,
    StringDto.class,
})
@IssueKey("3707")
public class Issue3707Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource()
            .addComparisonToFixtureFor( Issue3707Mapper.class );

    @ProcessorTest
    void shouldCompile() {
    }
}
