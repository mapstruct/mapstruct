/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@IssueKey("695")
@WithClasses({
    CoolBeerDTO.class,
    ShelveDTO.class,
    Fridge.class,
    FridgeDTO.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class MappingControlTest {

    /**
     * Baseline Test, normal, direct allowed
     */
    @Test
    @WithClasses(DirectMapper.class)
    public void directSelectionAllowed() {

        FridgeDTO in = createFridgeDTO();
        FridgeDTO out = DirectMapper.INSTANCE.map( in );

        assertThat( out ).isNotNull();
        assertThat( out.getShelve() ).isNotNull();
        assertThat( out.getShelve() ).isEqualTo( in.getShelve() );
    }

    /**
     * Test the deep cloning annotation
     */
    @Test
    @WithClasses(CloningMapper.class)
    public void testDeepCloning() {

        FridgeDTO in = createFridgeDTO();
        FridgeDTO out = CloningMapper.INSTANCE.clone( in );

        assertThat( out ).isNotNull();
        assertThat( out.getShelve() ).isNotNull();
        assertThat( out.getShelve() ).isNotEqualTo( in.getShelve() );
        assertThat( out.getShelve().getCoolBeer() ).isNotEqualTo( in.getShelve().getCoolBeer() );
        assertThat( out.getShelve().getCoolBeer().getBeerCount() ).isEqualTo( "5" );
    }

    /**
     * This is a nice test. MapStruct looks for a way to map ShelveDto to ShelveDto.
     * <p>
     * MapStruct gets too creative when we allow complex (2 step mappings) to convert if we also allow
     * it to forge methods (which is contradiction with the fact that we do not allow methods on this mapper)
     */
    @Test
    @WithClasses(ErroneousDirectMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousDirectMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 18,
                messageRegExp = "Can't map property \".*\\.ShelveDTO shelve\" to \".*\\.ShelveDTO shelve\".*"
            )
        })
    public void directSelectionNotAllowed() {
    }

    /**
     * Baseline Test, normal, method allowed
     */
    @Test
    @WithClasses(MethodMapper.class)
    public void methodSelectionAllowed() {
        Fridge fridge = MethodMapper.INSTANCE.map( createFridgeDTO() );

        assertThat( fridge ).isNotNull();
        assertThat( fridge.getBeerCount() ).isEqualTo( 5 );
    }

    @Test
    @WithClasses(ErroneousMethodMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMethodMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 18,
                messageRegExp = "Can't map property \".*\\.ShelveDTO shelve\" to \"int beerCount\".*"
            )
        })
    public void methodSelectionNotAllowed() {
    }

    /**
     * Baseline Test, normal, conversion allowed
     */
    @Test
    @WithClasses(ConversionMapper.class)
    public void conversionSelectionAllowed() {
        Fridge fridge = ConversionMapper.INSTANCE.map( createFridgeDTO().getShelve().getCoolBeer() );

        assertThat( fridge ).isNotNull();
        assertThat( fridge.getBeerCount() ).isEqualTo( 5 );
    }

    @Test
    @WithClasses(ErroneousConversionMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousConversionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                messageRegExp = "Can't map property \".*\\.String beerCount\" to \"int beerCount\".*"
            )
        })
    public void conversionSelectionNotAllowed() {
    }

    /**
     * Baseline Test, normal, complex mapping allowed
     */
    @Test
    @WithClasses(ComplexMapper.class)
    public void complexSelectionAllowed() {
        Fridge fridge = ComplexMapper.INSTANCE.map( createFridgeDTO() );

        assertThat( fridge ).isNotNull();
        assertThat( fridge.getBeerCount() ).isEqualTo( 5 );
    }

    @Test
    @WithClasses(ErroneousComplexMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousComplexMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 18,
                messageRegExp = "Can't map property \".*\\.ShelveDTO shelve\" to \"int beerCount\".*"
            )
        })
    public void complexSelectionNotAllowed() {
    }

    @Test
    @WithClasses({ Config.class, ErroneousComplexMapperWithConfig.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousComplexMapperWithConfig.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                messageRegExp = "Can't map property \".*\\.ShelveDTO shelve\" to \"int beerCount\".*"
            )
        })
    public void complexSelectionNotAllowedWithConfig() {
    }

    private FridgeDTO createFridgeDTO() {
        FridgeDTO fridgeDTO = new FridgeDTO();
        ShelveDTO shelveDTO = new ShelveDTO();
        CoolBeerDTO coolBeerDTO = new CoolBeerDTO();
        fridgeDTO.setShelve( shelveDTO );
        shelveDTO.setCoolBeer( coolBeerDTO );
        coolBeerDTO.setBeerCount( "5" );
        return fridgeDTO;
    }
}
