/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1215;

import org.mapstruct.ap.test.bugs._1215.dto.EntityDTO;
import org.mapstruct.ap.test.bugs._1215.entity.AnotherTag;
import org.mapstruct.ap.test.bugs._1215.entity.Entity;
import org.mapstruct.ap.test.bugs._1215.entity.Tag;
import org.mapstruct.ap.test.bugs._1215.mapper.Issue1215Mapper;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses( {
    EntityDTO.class,
    Entity.class,
    Tag.class,
    AnotherTag.class,
    Issue1215Mapper.class
} )
@IssueKey( "1215" )
public class Issue1215Test {

    @ProcessorTest
    public void shouldCompile() {
    }
}
