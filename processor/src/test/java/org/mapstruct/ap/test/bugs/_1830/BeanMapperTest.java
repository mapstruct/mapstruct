package org.mapstruct.ap.test.bugs._1830;

import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Bean.class,
    BeanDTO.class,
    BeanMapper.class,
    NestedBean.class
})
class BeanMapperTest {

    private static final String EXPECTED_MAPPER_IMPL = "src/test/resources/fixtures/org/mapstruct/ap/test/bugs/_1830/BeanMapperImplClear.java";
    @RegisterExtension
    GeneratedSource generatedSource = new GeneratedSource();
    private final Bean bean = new Bean();
    private BeanDTO beanDTO = new BeanDTO();

    @ProcessorTest
    void mapNull() {
        beanDTO = BeanMapper.INSTANCE.map( bean, beanDTO );
        assertThat( beanDTO.getList() ).isNull();
    }

    @ProcessorTest
    void mapEmptyList() {
        beanDTO.setList( new ArrayList<>() );
        beanDTO = BeanMapper.INSTANCE.map( bean, beanDTO );
        assertThat( beanDTO.getList() ).isEmpty();
    }

    @ProcessorTest
    void generatedMapperMethodsShouldCallClear() {
        generatedSource.forMapper( BeanMapper.class )
            .hasSameMapperContent( FileUtils.getFile( EXPECTED_MAPPER_IMPL ) );
    }

    @ProcessorTest
    @WithClasses({
        MapperWithErrorInTargetMapping.class,
        BeanWithId.class,
        BeanDTOWithId.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = MapperWithErrorInTargetMapping.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 15,
                message = "Target property: \"id\" is not a Collection or Map and can't be used with NullValuePropertyMappingStrategy: 'CLEAR'")
        })
    void mapNullWithCustomClearStrategy() {
    }


}