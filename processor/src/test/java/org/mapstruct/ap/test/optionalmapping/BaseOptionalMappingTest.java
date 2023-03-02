package org.mapstruct.ap.test.optionalmapping;

import java.util.Optional;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseOptionalMappingTest {
    
    private final Class<? extends BaseOptionalTestMapper> mapperClass;
    private BaseOptionalTestMapper mapper;

    protected BaseOptionalMappingTest(Class<? extends BaseOptionalTestMapper> mapperClass) {
        this.mapperClass = mapperClass;
    }
    
    private BaseOptionalTestMapper getMapper() {
        if (this.mapper == null) {
            this.mapper = Mappers.getMapper(mapperClass);
        }
        return this.mapper;
    }

    @ProcessorTest
    public void testMappingSimpleOptionalToNonOptional() {
        Source source = new Source( null, null, null, null, null, null );
        source.setOptionalToNonOptional( Optional.of( "some value" ) );

        Target target = getMapper().toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    public void testMappingEmptySimpleOptionalToNonOptional() {
        Source source = new Source( null, null, null, null, null, null );
        source.setOptionalToNonOptional( Optional.empty() );

        Target target = getMapper().toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void testReverseMappingSimpleOptionalFromNonOptional() {
        Target target = new Target( null, null, null, null, null, null );
        target.setOptionalToNonOptional( "some value" );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getOptionalToNonOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void testReverseMappingSimpleOptionalFromNullNonOptional() {
        Target target = new Target( null, null, null, null, null, null );
        target.setOptionalToNonOptional( null );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getOptionalToNonOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testMappingNonOptionalToSimpleOptional() {
        Source source = new Source( null, null, null, null, null, null );
        source.setNonOptionalToOptional( "some value" );

        Target target = getMapper().toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void testMappingNullNonOptionalToSimpleOptional() {
        Source source = new Source( null, null, null, null, null, null );
        source.setNonOptionalToOptional( null );

        Target target = getMapper().toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testReverseMappingNonOptionalFromSimpleOptional() {
        Target target = new Target( null, null, null, null, null, null );
        target.setNonOptionalToOptional( Optional.of( "some value" ) );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getNonOptionalToOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    public void testReverseMappingNonOptionalFromEmptySimpleOptional() {
        Target target = new Target( null, null, null, null, null, null );
        target.setNonOptionalToOptional( Optional.empty() );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getNonOptionalToOptional() ).isNull();
    }

    @ProcessorTest
    public void testMappingSimpleOptionalToOptional() {
        Source source = new Source( null, null, null, null, null, null );
        source.setOptionalToOptional( Optional.of( "some value" ) );

        Target target = getMapper().toTarget( source );
        assertThat( target.getOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void testMappingEmptySimpleOptionalToOptional() {
        Source source = new Source( null, null, null, null, null, null );
        source.setOptionalToOptional( Optional.empty() );

        Target target = getMapper().toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testReverseMappingSimpleOptionalFromOptional() {
        Target target = new Target( null, null, null, null, null, null );
        target.setOptionalToOptional( Optional.of( "some value" ) );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void testReverseMappingSimpleOptionalFromEmptyOptional() {
        Target target = new Target( null, null, null, null, null, null );
        target.setOptionalToOptional( Optional.empty() );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testMappingSubTypeOptionalToNonOptional() {
        Source source = new Source( null, null, null, null, null, null );
        source.setOptionalSubTypeToNonOptional( Optional.of( new Source.SubType( 94 ) ) );

        Target target = getMapper().toTarget( source );
        assertThat( target.getOptionalSubTypeToNonOptional() ).isEqualTo( new Target.SubType( 94 ) );
    }

    @ProcessorTest
    public void testMappingEmptySubTypeOptionalToNonOptional() {
        Source source = new Source( null, null, null, null, null, null );
        source.setOptionalSubTypeToNonOptional( Optional.empty() );

        Target target = getMapper().toTarget( source );
        assertThat( target.getOptionalSubTypeToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void testReverseMappingSubTypeOptionalFromNonOptional() {
        Target target = new Target( null, null, null, null, null, null );
        target.setOptionalSubTypeToNonOptional( new Target.SubType( 94 ) );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getOptionalSubTypeToNonOptional() ).contains( new Source.SubType( 94 ) );
    }

    @ProcessorTest
    public void testReverseMappingSubTypeOptionalFromNullNonOptional() {
        Target target = new Target( null, null, null, null, null, null );
        target.setOptionalSubTypeToNonOptional( null );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getOptionalSubTypeToNonOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testMappingNonOptionalToSubTypeOptional() {
        Source source = new Source( null, null, null, null, null, null );
        source.setNonOptionalSubTypeToOptional( new Source.SubType( 94 ) );

        Target target = getMapper().toTarget( source );
        assertThat( target.getNonOptionalSubTypeToOptional() ).contains( new Target.SubType( 94 ) );
    }

    @ProcessorTest
    public void testMappingNullNonOptionalToSubTypeOptional() {
        Source source = new Source( null, null, null, null, null, null );
        source.setNonOptionalSubTypeToOptional( null );

        Target target = getMapper().toTarget( source );
        assertThat( target.getNonOptionalSubTypeToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testReverseMappingNonOptionalFromSubTypeOptional() {
        Target target = new Target( null, null, null, null, null, null );
        target.setNonOptionalSubTypeToOptional( Optional.of( new Target.SubType( 94 ) ) );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getNonOptionalSubTypeToOptional() ).isEqualTo( new Source.SubType( 94 ) );
    }

    @ProcessorTest
    public void testReverseMappingNonOptionalFromEmptySubTypeOptional() {
        Target target = new Target( null, null, null, null, null, null );
        target.setNonOptionalSubTypeToOptional( Optional.empty() );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getNonOptionalSubTypeToOptional() ).isNull();
    }

    @ProcessorTest
    public void testMappingSubTypeOptionalToOptional() {
        Source source = new Source( null, null, null, null, null, null );
        source.setOptionalSubTypeToOptional( Optional.of( new Source.SubType( 94 ) ) );

        Target target = getMapper().toTarget( source );
        assertThat( target.getOptionalSubTypeToOptional() ).contains( new Target.SubType( 94 ) );
    }

    @ProcessorTest
    public void testMappingEmptySubTypeOptionalToOptional() {
        Source source = new Source( null, null, null, null, null, null );
        source.setOptionalSubTypeToOptional( Optional.empty() );

        Target target = getMapper().toTarget( source );
        assertThat( target.getOptionalSubTypeToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testReverseMappingSubTypeOptionalFromOptional() {
        Target target = new Target( null, null, null, null, null, null );
        target.setOptionalSubTypeToOptional( Optional.of( new Target.SubType( 94 ) ) );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getOptionalSubTypeToOptional() ).contains( new Source.SubType( 94 ) );
    }

    @ProcessorTest
    public void testReverseMappingSubTypeOptionalFromEmptyOptional() {
        Target target = new Target( null, null, null, null, null, null );
        target.setOptionalSubTypeToOptional( Optional.empty() );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getOptionalSubTypeToOptional() ).isEmpty();
    }
    
    @ProcessorTest
    public void testConstructorMappingSimpleOptionalToNonOptional() {
        Source source = new Source( Optional.of( "some value" ), null, null, null, null, null );

        Target target = getMapper().toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    public void testConstructorMappingEmptySimpleOptionalToNonOptional() {
        Source source = new Source( Optional.empty(), null, null, null, null, null );

        Target target = getMapper().toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void testReverseConstructorMappingSimpleOptionalFromNonOptional() {
        Target target = new Target( "some value", null, null, null, null, null );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getConstructorOptionalToNonOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void testReverseConstructorMappingSimpleOptionalFromNullNonOptional() {
        Target target = new Target( null, null, null, null, null, null );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getConstructorOptionalToNonOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testConstructorMappingNonOptionalToSimpleOptional() {
        Source source = new Source( null, "some value", null, null, null, null );

        Target target = getMapper().toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void testConstructorMappingNullNonOptionalToSimpleOptional() {
        Source source = new Source( null, null, null, null, null, null );

        Target target = getMapper().toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testReverseConstructorMappingNonOptionalFromSimpleOptional() {
        Target target = new Target( null, Optional.of( "some value" ), null, null, null, null );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getConstructorNonOptionalToOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    public void testReverseConstructorMappingNonOptionalFromEmptySimpleOptional() {
        Target target = new Target( null, Optional.empty(), null, null, null, null );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getConstructorNonOptionalToOptional() ).isNull();
    }

    @ProcessorTest
    public void testConstructorMappingSimpleOptionalToOptional() {
        Source source = new Source( null, null, Optional.of( "some value" ), null, null, null );

        Target target = getMapper().toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void testConstructorMappingEmptySimpleOptionalToOptional() {
        Source source = new Source( null, null, Optional.empty(), null, null, null );

        Target target = getMapper().toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testReverseConstructorMappingSimpleOptionalFromOptional() {
        Target target = new Target( null, null, Optional.of( "some value" ), null, null, null );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getConstructorOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void testReverseConstructorMappingSimpleOptionalFromEmptyOptional() {
        Target target = new Target( null, null, Optional.empty(), null, null, null );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getConstructorOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testConstructorMappingSubTypeOptionalToNonOptional() {
        Source source = new Source( null, null, null, Optional.of( new Source.SubType( 94 ) ), null, null );

        Target target = getMapper().toTarget( source );
        assertThat( target.getConstructorOptionalSubTypeToNonOptional() ).isEqualTo( new Target.SubType( 94 ) );
    }

    @ProcessorTest
    public void testConstructorMappingEmptySubTypeOptionalToNonOptional() {
        Source source = new Source( null, null, null, Optional.empty(), null, null );

        Target target = getMapper().toTarget( source );
        assertThat( target.getConstructorOptionalSubTypeToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void testReverseConstructorMappingSubTypeOptionalFromNonOptional() {
        Target target = new Target( null, null, null, new Target.SubType( 94 ), null, null );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getConstructorOptionalSubTypeToNonOptional() ).contains( new Source.SubType( 94 ) );
    }

    @ProcessorTest
    public void testReverseConstructorMappingSubTypeOptionalFromNullNonOptional() {
        Target target = new Target( null, null, null, null, null, null );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getConstructorOptionalSubTypeToNonOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testConstructorMappingNonOptionalToSubTypeOptional() {
        Source source = new Source( null, null, null, null, new Source.SubType( 94 ), null );

        Target target = getMapper().toTarget( source );
        assertThat( target.getConstructorNonOptionalSubTypeToOptional() ).contains( new Target.SubType( 94 ) );
    }

    @ProcessorTest
    public void testConstructorMappingNullNonOptionalToSubTypeOptional() {
        Source source = new Source( null, null, null, null, null, null );

        Target target = getMapper().toTarget( source );
        assertThat( target.getConstructorNonOptionalSubTypeToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testReverseConstructorMappingNonOptionalFromSubTypeOptional() {
        Target target = new Target( null, null, null, null, Optional.of( new Target.SubType( 94 ) ), null );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getConstructorNonOptionalSubTypeToOptional() ).isEqualTo( new Source.SubType( 94 ) );
    }

    @ProcessorTest
    public void testReverseConstructorMappingNonOptionalFromEmptySubTypeOptional() {
        Target target = new Target( null, null, null, null, Optional.empty(), null );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getConstructorNonOptionalSubTypeToOptional() ).isNull();
    }

    @ProcessorTest
    public void testConstructorMappingSubTypeOptionalToOptional() {
        Source source = new Source( null, null, null, null, null, Optional.of( new Source.SubType( 94 ) ) );

        Target target = getMapper().toTarget( source );
        assertThat( target.getConstructorOptionalSubTypeToOptional() ).contains( new Target.SubType( 94 ) );
    }

    @ProcessorTest
    public void testConstructorMappingEmptySubTypeOptionalToOptional() {
        Source source = new Source( null, null, null, null, null, Optional.empty() );

        Target target = getMapper().toTarget( source );
        assertThat( target.getConstructorOptionalSubTypeToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testReverseConstructorMappingSubTypeOptionalFromOptional() {
        Target target = new Target( null, null, null, null, null, Optional.of( new Target.SubType( 94 ) ) );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getConstructorOptionalSubTypeToOptional() ).contains( new Source.SubType( 94 ) );
    }

    @ProcessorTest
    public void testReverseConstructorMappingSubTypeOptionalFromEmptyOptional() {
        Target target = new Target( null, null, null, null, null, Optional.empty() );

        Source source = getMapper().fromTarget( target );
        assertThat( source.getConstructorOptionalSubTypeToOptional() ).isEmpty();
    }
    
}
