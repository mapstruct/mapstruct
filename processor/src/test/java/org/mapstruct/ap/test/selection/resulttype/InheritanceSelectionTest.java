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
package org.mapstruct.ap.test.selection.resulttype;

import javax.tools.Diagnostic.Kind;
import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey("385")
@WithClasses({
    Fruit.class,
    FruitDto.class,
    Apple.class,
    AppleDto.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class InheritanceSelectionTest {


    @Test
    @WithClasses( { ConflictingFruitFactory.class, ErroneousFruitMapper.class, Banana.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousFruitMapper.class,
                kind = Kind.ERROR,
                line = 36,
                messageRegExp = "Ambiguous mapping methods found for factorizing .*Fruit: "
                    + ".*Apple .*ConflictingFruitFactory\\.createApple\\(\\), "
                    + ".*Banana .*ConflictingFruitFactory\\.createBanana\\(\\)\\.")
        }
    )
    public void testForkedInheritanceHierarchyShouldResultInAmbigousMappingMethod() {
    }

    @Test
    @WithClasses( { ConflictingFruitFactory.class, TargetTypeSelectingFruitMapper.class, Banana.class } )
    public void testForkedInheritanceHierarchyButDefinedTargetType() {

        FruitDto fruitDto = new FruitDto( null );
        Fruit fruit = TargetTypeSelectingFruitMapper.INSTANCE.map( fruitDto );
        assertThat( fruit ).isNotNull();
        assertThat( fruit.getType() ).isEqualTo( "apple" );

    }

    @Test
    @IssueKey("433")
    @WithClasses( {
        FruitFamilyMapper.class,
        GoldenDeliciousDto.class,
        GoldenDelicious.class,
        AppleFamily.class,
        AppleFamilyDto.class,
        AppleFactory.class,
        Banana.class
    } )
    public void testShouldSelectResultTypeInCaseOfAmbiguity() {

        AppleFamilyDto appleFamilyDto = new AppleFamilyDto();
        appleFamilyDto.setApple( new AppleDto("AppleDto") );

        AppleFamily result = FruitFamilyMapper.INSTANCE.map( appleFamilyDto );
        assertThat( result ).isNotNull();
        assertThat( result.getApple() ).isNotNull();
        assertThat( result.getApple() ).isInstanceOf( GoldenDelicious.class );
        assertThat( result.getApple().getType() ).isEqualTo( "AppleDto" );

    }

}
