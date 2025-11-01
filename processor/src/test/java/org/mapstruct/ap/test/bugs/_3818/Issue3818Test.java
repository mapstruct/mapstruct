package org.mapstruct.ap.test.bugs._3818;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3818")
@WithClasses({
        Issue3818Mapper.class
})
public class Issue3818Test {

    @ProcessorTest
    public void shouldCompile() {
        Issue3818Mapper.Sample target = Issue3818Mapper.INSTANCE.
                map(
                        new Issue3818Mapper.Sample( "first", "second", "third" ),
                        new Issue3818Mapper.Sample( "other first", "other second", "other third")
                );

        assertThat( target.getFirst() ).isEqualTo( "first" );
        assertThat( target.getSecond() ).isEqualTo( "second" );
        assertThat( target.getThird() ).isEqualTo( "other third" );
    }
}
