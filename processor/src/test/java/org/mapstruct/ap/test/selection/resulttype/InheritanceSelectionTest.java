/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.resulttype;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Sjaak Derksen
 */
@IssueKey("385")
@WithClasses({
    IsFruit.class,
    Fruit.class,
    FruitDto.class,
    Apple.class,
    AppleDto.class
})
public class InheritanceSelectionTest {

    @ProcessorTest
    @WithClasses({ ConflictingFruitFactory.class, ErroneousFruitMapper.class, Banana.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousFruitMapper.class,
                kind = Kind.ERROR,
                line = 23,
                message = "Ambiguous factory methods found for creating Fruit: " +
                    "Apple ConflictingFruitFactory.createApple(), " +
                    "Banana ConflictingFruitFactory.createBanana(). " +
                    "See https://mapstruct.org/faq/#ambiguous for more info."
            )
        }
    )
    public void testForkedInheritanceHierarchyShouldResultInAmbigousMappingMethod() {
    }

    @IssueKey("1283")
    @ProcessorTest
    @WithClasses({ ErroneousResultTypeNoAccessibleConstructorMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousResultTypeNoAccessibleConstructorMapper.class,
                kind = Kind.ERROR,
                line = 18,
                message =
                    "ErroneousResultTypeNoAccessibleConstructorMapper.Banana does not have an accessible constructor.")
        }
    )
    public void testResultTypeHasNoSuitableAccessibleConstructor() {
    }

    @ProcessorTest
    @WithClasses({ ConflictingFruitFactory.class, ResultTypeSelectingFruitMapper.class, Banana.class })
    public void testResultTypeBasedFactoryMethodSelection() {

        FruitDto fruitDto = new FruitDto( null );
        Fruit fruit = ResultTypeSelectingFruitMapper.INSTANCE.map( fruitDto );
        assertThat( fruit ).isNotNull();
        assertThat( fruit.getType() ).isEqualTo( "apple" );

    }

    @ProcessorTest
    @IssueKey("434")
    @WithClasses({ ResultTypeConstructingFruitMapper.class })
    public void testResultTypeBasedConstructionOfResult() {

        FruitDto fruitDto = new FruitDto( null );
        Fruit fruit = ResultTypeConstructingFruitMapper.INSTANCE.map( fruitDto );
        assertThat( fruit ).isNotNull();
        assertThat( fruit.getType() ).isEqualTo( "constructed-by-constructor" );
    }

    @ProcessorTest
    @IssueKey("657")
    @WithClasses({ ResultTypeConstructingFruitInterfaceMapper.class })
    public void testResultTypeBasedConstructionOfResultForInterface() {

        FruitDto fruitDto = new FruitDto( null );
        IsFruit fruit = ResultTypeConstructingFruitInterfaceMapper.INSTANCE.map( fruitDto );
        assertThat( fruit ).isNotNull();
        assertThat( fruit.getType() ).isEqualTo( "constructed-by-constructor" );
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ResultTypeConstructingFruitInterfaceErroneousMapper.class,
                kind = Kind.ERROR,
                line = 23,
                message = "The return type IsFruit is an abstract class or interface. " +
                    "Provide a non abstract / non interface result type or a factory method."
            )
        }
    )
    @IssueKey("657")
    @WithClasses({ ResultTypeConstructingFruitInterfaceErroneousMapper.class })
    public void testResultTypeBasedConstructionOfResultForInterfaceErroneous() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousFruitMapper2.class,
                kind = Kind.ERROR,
                line = 22,
                message = "Banana not assignable to: Apple.")
        }
    )
    @IssueKey("434")
    @WithClasses({ ErroneousFruitMapper2.class, Banana.class })
    public void testResultTypeBasedConstructionOfResultNonAssignable() {
    }

    @ProcessorTest
    @IssueKey("433")
    @WithClasses({
        FruitFamilyMapper.class,
        GoldenDeliciousDto.class,
        GoldenDelicious.class,
        AppleFamily.class,
        AppleFamilyDto.class,
        AppleFactory.class,
        Banana.class
    })
    public void testShouldSelectResultTypeInCaseOfAmbiguity() {

        AppleFamilyDto appleFamilyDto = new AppleFamilyDto();
        appleFamilyDto.setApple( new AppleDto( "AppleDto" ) );

        AppleFamily result = FruitFamilyMapper.INSTANCE.map( appleFamilyDto );
        assertThat( result ).isNotNull();
        assertThat( result.getApple() ).isNotNull();
        assertThat( result.getApple() ).isInstanceOf( GoldenDelicious.class );
        assertThat( result.getApple().getType() ).isEqualTo( "AppleDto" );

    }

    @ProcessorTest
    @IssueKey("433")
    @WithClasses({
        FruitFamilyMapper.class,
        GoldenDeliciousDto.class,
        GoldenDelicious.class,
        AppleFamily.class,
        AppleFamilyDto.class,
        AppleFactory.class,
        Banana.class
    })
    public void testShouldSelectResultTypeInCaseOfAmbiguityForIterable() {

        List<AppleDto> source = Arrays.asList( new AppleDto( "AppleDto" ) );

        List<Apple> result = FruitFamilyMapper.INSTANCE.mapToGoldenDeliciousList( source );
        assertThat( result ).isNotNull();
        assertThat( result ).isNotEmpty();
        assertThat( result.get( 0 ) ).isInstanceOf( GoldenDelicious.class );
        assertThat( result.get( 0 ).getType() ).isEqualTo( "AppleDto" );
    }

    @ProcessorTest
    @IssueKey("433")
    @WithClasses({
        FruitFamilyMapper.class,
        GoldenDeliciousDto.class,
        GoldenDelicious.class,
        AppleFamily.class,
        AppleFamilyDto.class,
        AppleFactory.class,
        Banana.class
    })
    public void testShouldSelectResultTypeInCaseOfAmbiguityForMap() {

        Map<AppleDto, AppleDto> source = new HashMap<>();
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

    @ProcessorTest
    @IssueKey("73")
    @WithClasses({
        Citrus.class,
        ResultTypeWithConstructorConstructingFruitInterfaceMapper.class
    })
    public void testShouldUseConstructorFromResultTypeForInterface() {
        FruitDto orange = new FruitDto( "orange" );
        IsFruit citrus = ResultTypeWithConstructorConstructingFruitInterfaceMapper.INSTANCE.map( orange );

        assertThat( citrus ).isInstanceOf( Citrus.class );
        assertThat( citrus.getType() ).isEqualTo( "orange" );
        assertThatThrownBy( () -> citrus.setType( "lemon" ) )
            .hasMessage( "Not allowed to change citrus type" );
        assertThat( citrus.getType() ).isEqualTo( "orange" );
    }

    @ProcessorTest
    @IssueKey("73")
    @WithClasses({
        Citrus.class,
        ResultTypeWithConstructorConstructingFruitInterfaceMapper.class
    })
    public void testShouldUseConstructorFromResultType() {
        FruitDto lemon = new FruitDto( "lemon" );
        IsFruit citrus = ResultTypeWithConstructorConstructingFruitInterfaceMapper.INSTANCE.map( lemon );

        assertThat( citrus ).isInstanceOf( Citrus.class );
        assertThat( citrus.getType() ).isEqualTo( "lemon" );
        assertThatThrownBy( () -> citrus.setType( "orange" ) )
            .hasMessage( "Not allowed to change citrus type" );
        assertThat( citrus.getType() ).isEqualTo( "lemon" );
    }

    @ProcessorTest
    @IssueKey("1216")
    @WithClasses({
        Citrus.class,
        GoldenDelicious.class,
        FruitFamily.class,
        AppleFamily.class,
        MostSpecificResultTypeSelectingMapper.class,
        Citrus.class
    })
    public void testShouldUseMethodWithMostSpecificReturnType() {
        FruitFamily fruitFamily = new FruitFamily();
        fruitFamily.setFruit( new Citrus( "citrus" ) );
        AppleFamily appleFamily = MostSpecificResultTypeSelectingMapper.INSTANCE.map( fruitFamily );

        assertThat( appleFamily.getApple() ).isExactlyInstanceOf( Apple.class );
        assertThat( appleFamily.getApple().getType() ).isEqualTo( "citrus" );
    }

    @ProcessorTest
    @IssueKey("1216")
    @WithClasses({
        Citrus.class,
        FruitFamily.class,
        GoldenDelicious.class,
        MostSpecificResultTypeSelectingUpdateMapper.class,
        Citrus.class
    })
    public void testShouldUseMethodWithMostSpecificReturnTypeForUpdateMappings() {
        FruitFamily fruitFamily = new FruitFamily();
        fruitFamily.setFruit( new Citrus( "citrus" ) );
        MostSpecificResultTypeSelectingUpdateMapper.Target target =
            new MostSpecificResultTypeSelectingUpdateMapper.Target(
                new Apple( "from_test" ),
                new GoldenDelicious( "from_test" )
            );
        MostSpecificResultTypeSelectingUpdateMapper.INSTANCE.update( target, fruitFamily );

        assertThat( target.getApple() ).isExactlyInstanceOf( Apple.class );
        assertThat( target.getApple().getType() ).isEqualTo( "apple updated citrus" );
        assertThat( target.getGoldenApple() ).isExactlyInstanceOf( GoldenDelicious.class );
        assertThat( target.getGoldenApple().getType() ).isEqualTo( "golden updated citrus" );
    }

    @ProcessorTest
    @IssueKey("1216")
    @WithClasses({
        GoldenDelicious.class,
        FruitFamily.class,
        AppleFamily.class,
        ErroneousAmbiguousMostSpecificResultTypeSelectingMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousAmbiguousMostSpecificResultTypeSelectingMapper.class,
                kind = Kind.ERROR,
                line = 17,
                message = "Ambiguous mapping methods found for mapping property \"IsFruit fruit\" to Apple: " +
                    "Apple toApple1(IsFruit fruit), Apple toApple2(IsFruit fruit). " +
                    "See https://mapstruct.org/faq/#ambiguous for more info."
            )
        }
    )
    public void testAmbiguousMostSpecificResultTypeErroneous() {
    }
}
