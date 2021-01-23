package org.mapstruct.ap.test.bugs._2322;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@IssueKey( "2322" )
@RunWith( AnnotationProcessorTestRunner.class )
public class Issue2322Test {

    @Test
    @WithClasses( Issue2322Mapper.class )
    public void testShouldCreateBaseMappings() {

        Issue2322Mapper.A source = new Issue2322Mapper.A();
        source.field1A = 11;
        source.field2 = 2;
        source.field3 = 3;

        Issue2322Mapper.Wrap target = Issue2322Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.b ).isNotNull();
     //   assertThat( target.b.field1B ).isEqualTo( 11 );
        assertThat( target.b.field2 ).isEqualTo( 2 );
        assertThat( target.b.field3 ).isEqualTo( 3 );
    }

}
