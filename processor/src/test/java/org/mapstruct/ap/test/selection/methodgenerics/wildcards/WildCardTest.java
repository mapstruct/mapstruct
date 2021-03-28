/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.wildcards;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.Compiler;
import org.mapstruct.ap.testutil.runner.DisabledOnCompiler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 *
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class WildCardTest {

    @Test
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

    @Test
    @WithClasses( IntersectionMapper.class )
    // Eclipse does not handle intersection types correctly (TODO: worthwhile to investigate?)
    @DisabledOnCompiler( Compiler.ECLIPSE )
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
}
