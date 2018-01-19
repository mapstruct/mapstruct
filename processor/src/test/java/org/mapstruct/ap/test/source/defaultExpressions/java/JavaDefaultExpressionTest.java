package org.mapstruct.ap.test.source.defaultExpressions.java;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jeffrey Smyth
 */
@RunWith ( AnnotationProcessorTestRunner.class )
public class JavaDefaultExpressionTest {

    @Test
    @WithClasses ( {Source.class, Target.class, SourceTargetMapper.class} )
    public void testJavaDefaultExpressionWithValues() {
        Source source = new Source();
        source.setId( "TestId123" );
        source.setDate( new Date(  0L ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getSourceId() ).isEqualTo( source.getId() );
        assertThat( target.getSourceDate() ).isEqualTo( source.getDate() );
    }

    @Test
    @WithClasses ( {Source.class, Target.class, SourceTargetMapper.class} )
    public void testJavaDefaultExpressionWithNoValues() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getSourceId() ).isNotNull();
        assertThat( target.getSourceDate() ).isNotNull();
    }

//    @IssueKey ( "255" )
//    @Test
//    @WithClasses ( {
//        Source.class, Source2.class, Target.class, TimeAndFormat.class, SourceTargetMapperSeveralSources.class
//    } )
//    public void testJavaExpressionInsertionWithSeveralSources() throws ParseException {
//        Source source1 = new Source();
//        String format = "dd-MM-yyyy,hh:mm:ss";
//        Date time = getTime( format, "09-01-2014,01:35:03" );
//
//        source1.setFormat( format );
//        source1.setTime( time );
//
//        Source2 source2 = new Source2();
//        source2.setAnotherProp( "test" );
//
//        Target target = SourceTargetMapperSeveralSources.INSTANCE.sourceToTarget( source1, source2 );
//
//        assertThat( target ).isNotNull();
//        assertThat( target.getTimeAndFormat().getTime() ).isEqualTo( time );
//        assertThat( target.getTimeAndFormat().getFormat() ).isEqualTo( format );
//        assertThat( target.getAnotherProp() ).isEqualTo( "test" );
//    }
//
//    private Date getTime( String format, String date ) throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat( format );
//        Date result = dateFormat.parse( date );
//        return result;
//    }
//
//    @Test
//    @WithClasses ( {Source.class, Target.class, TimeAndFormat.class, SourceTargetMapper.class} )
//    public void testJavaExpressionInsertionWithExistingTarget() throws ParseException {
//        Source source = new Source();
//        String format = "dd-MM-yyyy,hh:mm:ss";
//        Date time = getTime( format, "09-01-2014,01:35:03" );
//
//        source.setFormat( format );
//        source.setTime( time );
//        Target target = new Target();
//
//        Target target2 = SourceTargetMapper.INSTANCE.sourceToTargetWithMappingTarget( source, target );
//
//        assertThat( target ).isNotNull();
//        assertThat( target.getTimeAndFormat().getTime() ).isEqualTo( time );
//        assertThat( target.getTimeAndFormat().getFormat() ).isEqualTo( format );
//        assertThat( target.getAnotherProp() ).isNull();
//        assertThat( target ).isEqualTo( target2 );
//    }
//
//    @IssueKey ( "278" )
//    @Test
//    @WithClasses ( {
//        SourceBooleanWorkAround.class, TargetBooleanWorkAround.class, BooleanWorkAroundMapper.class
//    } )
//    public void testBooleanGetterWorkAround() throws ParseException {
//        SourceBooleanWorkAround source = new SourceBooleanWorkAround();
//        source.setVal( Boolean.TRUE );
//
//        TargetBooleanWorkAround target = BooleanWorkAroundMapper.INSTANCE.mapST( source );
//        assertThat( target ).isNotNull();
//        assertThat( target.isVal() ).isTrue();
//
//        SourceBooleanWorkAround source2 = BooleanWorkAroundMapper.INSTANCE.mapTS( target );
//        assertThat( source2 ).isNotNull();
//        assertThat( source2.isVal() ).isTrue();
//    }
//
//    @IssueKey ( "305" )
//    @Test
//    @WithClasses ( {
//        SourceList.class, TargetList.class, SourceTargetListMapper.class
//    } )
//    public void testGetterOnly() throws ParseException {
//        SourceList source = new SourceList();
//        source.setList( Arrays.asList( "test1" ) );
//
//        TargetList target = SourceTargetListMapper.INSTANCE.map( source );
//        assertThat( target ).isNotNull();
//        assertThat( target.getList() ).isEqualTo( Arrays.asList( "test2" ) );
//    }
}
