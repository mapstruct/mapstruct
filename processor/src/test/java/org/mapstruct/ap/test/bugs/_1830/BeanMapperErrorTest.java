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
    MapperWithErrorInConfig.class,
    BeanDTOWithId.class,
    BeanWithId.class,
    Bean.class,
    BeanDTO.class,
})
class BeanMapperErrorTest {

    private static final String EXPECTED_MAPPER_IMPL = "src/test/resources/fixtures/org/mapstruct/ap/test/bugs/_1830/BeanMapperImplClear.java";
    @RegisterExtension
    GeneratedSource generatedSource = new GeneratedSource();
    private final Bean bean = new Bean();
    private BeanDTO beanDTO = new BeanDTO();


    @ProcessorTest

    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = MapperWithErrorInConfig.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                message = "The strategy: \"CLEAR\" can't be used for: \"nullValuePropertyMapping\".")
        })
    void errorInConfig() {
    }

}