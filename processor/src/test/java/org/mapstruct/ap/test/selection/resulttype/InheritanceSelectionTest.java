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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

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
                messageRegExp = "Ambiguous factory methods found for creating .*Fruit: "
                    + ".*Apple .*ConflictingFruitFactory\\.createApple\\(\\), "
                    + ".*Banana .*ConflictingFruitFactory\\.createBanana\\(\\)\\.")
        }
    )
    public void testForkedInheritanceHierarchyShouldResultInAmbigousMappingMethod() {
    }

    @Test
    @WithClasses( { ConflictingFruitFactory.class, ResultTypeSelectingFruitMapper.class, Banana.class } )
    public void testResultTypeBasedFactoryMethodSelection() {

        FruitDto fruitDto = new FruitDto( null );
        Fruit fruit = ResultTypeSelectingFruitMapper.INSTANCE.map( fruitDto );
        assertThat( fruit ).isNotNull();
        assertThat( fruit.getType() ).isEqualTo( "apple" );

    }

    @Test
    @IssueKey("434")
    @WithClasses( { ResultTypeConstructingFruitMapper.class } )
    public void testResultTypeBasedConstructionOfResult() {

        FruitDto fruitDto = new FruitDto( null );
        Fruit fruit = ResultTypeConstructingFruitMapper.INSTANCE.map( fruitDto );
        assertThat( fruit ).isNotNull();
        assertThat( fruit.getType() ).isEqualTo( "constructed-by-constructor" );
    }

    @Test
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousFruitMapper2.class,
                kind = Kind.ERROR,
                line = 35,
                messageRegExp = ".*\\.Banana not assignable to: .*\\.Apple.")
        }
    )
    @IssueKey("434")
    @WithClasses( { ErroneousFruitMapper2.class, Banana.class } )
    public void testResultTypeBasedConstructionOfResultNonAssignable() {
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
    public void testShouldSelectResultTypeInCaseOfAmbiguityForIterable() {

        List<AppleDto> source = Arrays.asList( new AppleDto( "AppleDto" ) );

        List<Apple> result = FruitFamilyMapper.INSTANCE.mapToGoldenDeliciousList( source );
        assertThat( result ).isNotNull();
        assertThat( result ).isNotEmpty();
        assertThat( result.get( 0 ) ).isInstanceOf( GoldenDelicious.class );
        assertThat( result.get( 0 ).getType() ).isEqualTo( "AppleDto" );
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
    public void testShouldSelectResultTypeInCaseOfAmbiguityForMap() {

        Map<AppleDto, AppleDto> source = new HashMap<AppleDto, AppleDto>();
        source.put( new AppleDto( "GoldenDelicious" ), new AppleDto( "AppleDto" ) );

        Map<Apple, Apple> result = FruitFamilyMapper.INSTANCE.mapToGoldenDeliciousMap( source );
        assertThat( result ).isNotNull();
        assertThat( result ).isNotEmpty();
        Map.Entry<Apple, Apple> entry = result.entrySet().iterator().next();

        assertThat( entry.getKey() ).isInstanceOf( GoldenDelicious.class );
        assertThat( entry.getKey().getType() ).isEqualTo( "GoldenDelicious" );
        assertThat( entry.getValue() ).isInstanceOf( Apple.class );
        assertThat( entry.getValue().getType() ).isEqualTo( "AppleDto" );

    }
}
