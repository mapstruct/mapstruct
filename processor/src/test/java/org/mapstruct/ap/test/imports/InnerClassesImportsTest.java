/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.imports.innerclasses.BeanFacade;
import org.mapstruct.ap.test.imports.innerclasses.BeanWithInnerEnum;
import org.mapstruct.ap.test.imports.innerclasses.BeanWithInnerEnum.InnerEnum;
import org.mapstruct.ap.test.imports.innerclasses.BeanWithInnerEnumMapper;
import org.mapstruct.ap.test.imports.innerclasses.InnerClassMapper;
import org.mapstruct.ap.test.imports.innerclasses.SourceWithInnerClass;
import org.mapstruct.ap.test.imports.innerclasses.SourceWithInnerClass.SourceInnerClass;
import org.mapstruct.ap.test.imports.innerclasses.TargetWithInnerClass;
import org.mapstruct.ap.test.imports.innerclasses.TargetWithInnerClass.TargetInnerClass.TargetInnerInnerClass;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for generating a mapper which references nested types (static inner classes).
 *
 * @author Ewald Volkert
 */
@WithClasses({
 SourceWithInnerClass.class, TargetWithInnerClass.class, InnerClassMapper.class, //
    BeanFacade.class, BeanWithInnerEnum.class, BeanWithInnerEnumMapper.class
})
public class InnerClassesImportsTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @IssueKey( "412" )
    public void mapperRequiresInnerClassImports() {
        SourceWithInnerClass source = new SourceWithInnerClass();
        source.setInnerClassMember( new SourceInnerClass( 412 ) );

        TargetWithInnerClass target = InnerClassMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getInnerClassMember().getValue() ).isEqualTo( 412 );
        generatedSource.addComparisonToFixtureFor( InnerClassMapper.class );
    }

    @ProcessorTest
    @IssueKey( "412" )
    public void mapperRequiresInnerInnerClassImports() {
        SourceInnerClass source = new SourceInnerClass();
        source.setValue( 412 );

        TargetInnerInnerClass target = InnerClassMapper.INSTANCE.innerSourceToInnerInnerTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( 412 );
        generatedSource.addComparisonToFixtureFor( InnerClassMapper.class );
    }

    @ProcessorTest
    @IssueKey( "209" )
    public void mapperRequiresInnerEnumImports() {
        BeanWithInnerEnum source = new BeanWithInnerEnum();
        source.setTest( "whatever" );
        source.setInnerEnum( InnerEnum.A );

        BeanFacade target = BeanWithInnerEnumMapper.INSTANCE.toFacade( source );

        assertThat( target ).isNotNull();
        assertThat( target.getInnerEnum() ).isEqualTo( "A" );

        BeanWithInnerEnum sourceAgain = BeanWithInnerEnumMapper.INSTANCE.fromFacade( target );

        assertThat( sourceAgain ).isNotNull();
        assertThat( sourceAgain.getInnerEnum() ).isEqualTo( InnerEnum.A );

        generatedSource.addComparisonToFixtureFor( BeanWithInnerEnumMapper.class );
    }
}
