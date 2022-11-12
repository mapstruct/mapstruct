/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluemapping;

import org.mapstruct.ap.test.nullvaluemapping._target.CarDto;
import org.mapstruct.ap.test.nullvaluemapping.source.Car;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    CarDto.class,
    Car.class
})
@IssueKey("2953")
public class NullValueMapMappingStrategyTest {

    @ProcessorTest
    @ProcessorOption(name = "mapstruct.nullValueMapMappingStrategy", value = "return_default")
    @WithClasses({
        CarMapMapper.class
    })
    void globalNullMapMappingStrategy() {
        assertThat( CarMapMapper.INSTANCE.carsToCarDtoMap( null ) ).isEmpty();
    }

    @ProcessorTest
    @ProcessorOption(name = "mapstruct.nullValueMapMappingStrategy", value = "return_default")
    @WithClasses({
        CarMapMapperSettingOnMapper.class
    })
    void globalNullMapMappingStrategyWithOverrideInMapper() {
        // Explicit definition in @Mapper should override global
        assertThat( CarMapMapperSettingOnMapper.INSTANCE.carsToCarDtoMap( null ) ).isNull();
    }
}
