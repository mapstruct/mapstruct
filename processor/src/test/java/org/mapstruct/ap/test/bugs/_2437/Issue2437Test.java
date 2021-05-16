/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2437;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2437")
@WithClasses({
    Phone.class,
    PhoneDto.class,
    PhoneMapper.class,
    PhoneParent1Mapper.class,
    PhoneParent2Mapper.class,
    PhoneSuperMapper.class,
})
class Issue2437Test {

    @ProcessorTest
    void shouldGenerateValidCode() {

    }
}
