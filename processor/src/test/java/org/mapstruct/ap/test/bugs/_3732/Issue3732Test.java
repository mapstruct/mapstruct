package org.mapstruct.ap.test.bugs._3732;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Issue3732Mapper.class,
    ModelA.class,
    ModelB.class,
})
@IssueKey("3732")
public class Issue3732Test {

    @ProcessorTest(org.mapstruct.ap.testutil.runner.Compiler.JDK)
    public void testUnusedImport() {
        ModelA source = new ModelA();
        source.setDatetime( LocalDateTime.now() );

        ModelB target = Issue3732Mapper.INSTANCE.map( source );

        assertThat( target.getDatetime() ).isNotNull();
    }
}
