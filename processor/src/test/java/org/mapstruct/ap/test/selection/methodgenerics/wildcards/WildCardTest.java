/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.wildcards;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.Compiler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 *
 */
public class WildCardTest {

    @ProcessorTest
    @WithClasses( SourceWildCardExtendsMapper.class )
    public void testExtendsRelation() {

        // prepare source
        SourceWildCardExtendsMapper.TypeB typeB = new SourceWildCardExtendsMapper.TypeB();
        SourceWildCardExtendsMapper.Wrapper wrapperB = new SourceWildCardExtendsMapper.Wrapper( typeB );
        SourceWildCardExtendsMapper.TypeC typeC = new SourceWildCardExtendsMapper.TypeC();
        SourceWildCardExtendsMapper.Wrapper wrapperC = new SourceWildCardExtendsMapper.Wrapper( typeC );
        SourceWildCardExtendsMapper.Source source = new SourceWildCardExtendsMapper.Source( wrapperB, wrapperC );

        // action
        SourceWildCardExtendsMapper.Target target = SourceWildCardExtendsMapper.INSTANCE.map( source );

        // verify target
        assertThat( target ).isNotNull();
        assertThat( target.getPropB() ).isEqualTo( typeB );
        assertThat( target.getPropC() ).isEqualTo( typeC );
    }

    // Eclipse does not handle intersection types correctly (TODO: worthwhile to investigate?)
    @ProcessorTest(Compiler.JDK)
    @WithClasses( IntersectionMapper.class )
    public void testIntersectionRelation() {

        // prepare source
        IntersectionMapper.TypeC typeC = new IntersectionMapper.TypeC();
        IntersectionMapper.Wrapper wrapper = new IntersectionMapper.Wrapper( typeC );
        IntersectionMapper.Source source = new IntersectionMapper.Source( wrapper );

        // action
        IntersectionMapper.Target target = IntersectionMapper.INSTANCE.map( source );

        // verify target
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( typeC );
    }

    // Eclipse does not handle intersection types correctly (TODO: worthwhile to investigate?)
    @ProcessorTest(Compiler.JDK)
    @WithClasses(LifecycleIntersectionMapper.class)
    @IssueKey("3036")
    public void testLifecycleIntersection() {

        LifecycleIntersectionMapper.RealmTarget realmTarget = LifecycleIntersectionMapper.INSTANCE.mapRealm( "test" );
        assertThat( realmTarget.getRealm() ).isNull();

        LifecycleIntersectionMapper.UniqueRealmTarget uniqueRealmTarget =
            LifecycleIntersectionMapper.INSTANCE.mapUniqueRealm( "test" );
        assertThat( uniqueRealmTarget.getUniqueRealm() ).isNull();

        LifecycleIntersectionMapper.BothRealmsTarget bothRealmsTarget =
            LifecycleIntersectionMapper.INSTANCE.mapBothRealms( "test" );
        assertThat( bothRealmsTarget.getRealm() ).isEqualTo( "realm_test" );
        assertThat( bothRealmsTarget.getUniqueRealm() ).isEqualTo( "uniqueRealm_test" );
    }
}
