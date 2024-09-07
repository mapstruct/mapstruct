/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluepropertymapping.clear;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

class BeanMapperTest {

    @RegisterExtension
    GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({BeanMapper.class,
            Bean.class,
            BeanDTO.class,
            NestedBean.class})
    void generatedMapperMethodsShouldCallClear() {
        generatedSource.addComparisonToFixtureFor( BeanMapper.class );
    }

    @ProcessorTest
    @WithClasses({BeanMapperWithNullValuePropertyMappingStrategy.class,
            Bean.class,
            BeanDTO.class,
            NestedBean.class
    })
    void generatedMapperWithMappingDefinedInConfigMethodsShouldCallClear() {
        generatedSource.addComparisonToFixtureFor( BeanMapperWithNullValuePropertyMappingStrategy.class );
    }

    @ProcessorTest
    @WithClasses({
            BeanMapperWithClearOnNonCollectionTypes.class,
            Bean.class,
            BeanWithId.class,
            BeanDTO.class,
            BeanDTOWithId.class,
    })
    void nonCollectionTypesWithClearStrategyShouldUseDefaultBehaviour() {
        generatedSource.addComparisonToFixtureFor( BeanMapperWithClearOnNonCollectionTypes.class );
    }
}
