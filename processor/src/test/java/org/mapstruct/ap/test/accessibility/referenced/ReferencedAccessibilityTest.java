/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.accessibility.referenced;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.accessibility.referenced.a.ReferencedMapperDefaultOther;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Test for different accessibility modifiers
 *
 * @author Sjaak Derksen
 */
@WithClasses( { Source.class, Target.class, ReferencedSource.class, ReferencedTarget.class } )
public class ReferencedAccessibilityTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @IssueKey( "206" )
    @WithClasses( { SourceTargetMapperPrivate.class, ReferencedMapperPrivate.class } )
    public void shouldNotBeAbleToAccessPrivateMethodInReferenced() {
        generatedSource.addComparisonToFixtureFor( SourceTargetMapperPrivate.class );
        Source source = createSourceWithReferencedSourceAndField( "Foo" );

        Target target = SourceTargetMapperPrivate.INSTANCE.toTarget( source );

        assertThat( target.getReferencedTarget() ).isNotNull();
        assertThat( target.getReferencedTarget().getBar() ).isNull();
    }

    @ProcessorTest
    @IssueKey( "206" )
    @WithClasses( { SourceTargetMapperDefaultOther.class, ReferencedMapperDefaultOther.class } )
    public void shouldNotBeAbleToAccessDefaultMethodInReferencedInOtherPackage() {
        generatedSource.addComparisonToFixtureFor( SourceTargetMapperDefaultOther.class );
        Source source = createSourceWithReferencedSourceAndField( "Foo" );

        Target target = SourceTargetMapperDefaultOther.INSTANCE.toTarget( source );

        assertThat( target.getReferencedTarget() ).isNotNull();
        assertThat( target.getReferencedTarget().getBar() ).isNull();
    }

    @ProcessorTest
    @IssueKey( "206" )
    @WithClasses( { AbstractSourceTargetMapperPrivate.class, SourceTargetmapperPrivateBase.class } )
    public void shouldNotBeAbleToAccessPrivateMethodInBase() {
        generatedSource.addComparisonToFixtureFor( AbstractSourceTargetMapperPrivate.class );
        Source source = createSourceWithReferencedSourceAndField( "Foo" );

        Target target = AbstractSourceTargetMapperPrivate.INSTANCE.toTarget( source );

        assertThat( target.getReferencedTarget() ).isNotNull();
        assertThat( target.getReferencedTarget().getBar() ).isNull();
    }

    @ProcessorTest
    @IssueKey( "206" )
    @WithClasses( { SourceTargetMapperDefaultSame.class, ReferencedMapperDefaultSame.class } )
    public void shouldBeAbleToAccessDefaultMethodInReferencedInSamePackage() {
        Source source = createSourceWithReferencedSourceAndField( "Foo" );

        Target target = SourceTargetMapperDefaultSame.INSTANCE.toTarget( source );

        assertThat( target.getReferencedTarget() ).isNotNull();
        assertThat( target.getReferencedTarget().getBar() ).isEqualTo( "Foo" );
    }

    @ProcessorTest
    @IssueKey( "206" )
    @WithClasses( { SourceTargetMapperProtected.class, ReferencedMapperProtected.class } )
    public void shouldBeAbleToAccessProtectedMethodInReferencedInSamePackage() {
        Source source = createSourceWithReferencedSourceAndField( "Foo" );

        Target target = SourceTargetMapperProtected.INSTANCE.toTarget( source );

        assertThat( target.getReferencedTarget() ).isNotNull();
        assertThat( target.getReferencedTarget().getBar() ).isEqualTo( "Foo" );
    }

    @ProcessorTest
    @IssueKey( "206" )
    @WithClasses( { AbstractSourceTargetMapperProtected.class, SourceTargetmapperProtectedBase.class } )
    public void shouldBeAbleToAccessProtectedMethodInBase() {
        Source source = createSourceWithReferencedSourceAndField( "Foo" );

        Target target = AbstractSourceTargetMapperProtected.INSTANCE.toTarget( source );

        assertThat( target.getReferencedTarget() ).isNotNull();
        assertThat( target.getReferencedTarget().getBar() ).isEqualTo( "Foo" );
    }

    private Source createSourceWithReferencedSourceAndField(String fieldValue) {
        Source source = new Source();
        ReferencedSource referencedSource = new ReferencedSource();
        referencedSource.setFoo( fieldValue );
        source.setReferencedSource( referencedSource );
        return source;
    }
}
