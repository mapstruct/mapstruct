/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2807.spring;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ap.test.bugs._2807.spring.after.AfterMethod;
import org.mapstruct.ap.test.bugs._2807.spring.before.BeforeMethod;
import org.mapstruct.ap.test.bugs._2807.spring.beforewithtarget.BeforeWithTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper( componentModel = "spring", uses = { BeforeMethod.class, AfterMethod.class,
    BeforeWithTarget.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface SpringLifeCycleMapper {
    SpringLifeCycleMapper INSTANCE = Mappers.getMapper( SpringLifeCycleMapper.class );

    List<String> map(List<Integer> list);
}
