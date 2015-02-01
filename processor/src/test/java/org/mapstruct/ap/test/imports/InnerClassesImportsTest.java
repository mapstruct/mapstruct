/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.imports;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.imports.innerclasses.BeanFacade;
import org.mapstruct.ap.test.imports.innerclasses.BeanWithInnerEnum;
import org.mapstruct.ap.test.imports.innerclasses.BeanWithInnerEnum.InnerEnum;
import org.mapstruct.ap.test.imports.innerclasses.BeanWithInnerEnumMapper;
import org.mapstruct.ap.test.imports.innerclasses.InnerClassMapper;
import org.mapstruct.ap.test.imports.innerclasses.SourceWithInnerClass;
import org.mapstruct.ap.test.imports.innerclasses.SourceWithInnerClass.SourceInnerClass;
import org.mapstruct.ap.test.imports.innerclasses.TargetWithInnerClass;
import org.mapstruct.ap.test.imports.innerclasses.TargetWithInnerClass.TargetInnerClass;
import org.mapstruct.ap.test.imports.innerclasses.TargetWithInnerClass.TargetInnerClass.TargetInnerInnerClass;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Test for generating a mapper which references nested types (static inner classes).
 *
 * @author Ewald Volkert
 */
@WithClasses({
 SourceWithInnerClass.class, TargetWithInnerClass.class, InnerClassMapper.class, //
    BeanFacade.class, BeanWithInnerEnum.class, BeanWithInnerEnumMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class InnerClassesImportsTest {

    private final GeneratedSource generatedSource = new GeneratedSource();

    @Rule
    public GeneratedSource getGeneratedSource() {
        return generatedSource;
    }

    @Test
    @IssueKey( "412" )
    public void mapperRequiresInnerClassImports() {
        SourceWithInnerClass source = new SourceWithInnerClass();
        source.setInnerClassMember( new SourceInnerClass( 412 ) );

        TargetWithInnerClass target = InnerClassMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getInnerClassMember().getValue() ).isEqualTo( 412 );
        generatedSource.forMapper( InnerClassMapper.class ).containsImportFor( SourceInnerClass.class );
        generatedSource.forMapper( InnerClassMapper.class ).containsImportFor( TargetInnerClass.class );
    }

    @Test
    @IssueKey( "412" )
    public void mapperRequiresInnerInnerClassImports() {
        SourceInnerClass source = new SourceInnerClass();
        source.setValue( 412 );

        TargetInnerInnerClass target = InnerClassMapper.INSTANCE.innerSourceToInnerInnerTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( 412 );
        generatedSource.forMapper( InnerClassMapper.class ).containsImportFor( SourceInnerClass.class );
        generatedSource.forMapper( InnerClassMapper.class ).containsImportFor( TargetInnerInnerClass.class );
    }

    @Test
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

        generatedSource.forMapper( BeanWithInnerEnumMapper.class ).containsImportFor( InnerEnum.class );
    }
}
