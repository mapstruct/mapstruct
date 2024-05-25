/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1830;

import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Bean.class,
    BeanDTO.class,
    BeanMapper.class,
    NestedBean.class,
    BeanMapperWithStrategyDefinedInConfig.class
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
    void generatedMapperWithMappingDefinedInConfigMethodsShouldCallClear() {
        generatedSource.forMapper( BeanMapperWithStrategyDefinedInConfig.class )
            .hasSameMapperContent( FileUtils.getFile( "src/test/resources/fixtures/org/mapstruct/ap/test/bugs/_1830/BeanMapperWithStrategyDefinedInConfigImplClear.java" ) );
    }

    @ProcessorTest
    @WithClasses({
        BeanMapperWithClearOnNonCollectionTypes.class,
        BeanWithId.class,
        BeanDTOWithId.class,
    })
    void nonCollectionTypesWithClearStrategyShouldUseDefaultBehaviour() {
        generatedSource.forMapper( BeanMapperWithClearOnNonCollectionTypes.class )
            .hasSameMapperContent( FileUtils.getFile( "src/test/resources/fixtures/org/mapstruct/ap/test/bugs/_1830/BeanMapperWithClearStrategyOnNonCollectionTypes.java" ) );
    }



}