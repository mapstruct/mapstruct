/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2352;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._2352.dto.TheDto;
import org.mapstruct.ap.test.bugs._2352.dto.TheModel;
import org.mapstruct.ap.test.bugs._2352.dto.TheModels;
import org.mapstruct.ap.test.bugs._2352.mapper.TheModelMapper;
import org.mapstruct.ap.test.bugs._2352.mapper.TheModelsMapper;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2352")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    TheDto.class,
    TheModel.class,
    TheModels.class,
    TheModelMapper.class,
    TheModelsMapper.class,
})
public class Issue2352Test {

    @Test
    public void shouldGenerateValidCode() {
        TheModels theModels = new TheModels();
        theModels.add( new TheModel( "1" ) );
        theModels.add( new TheModel( "2" ) );

        List<TheDto> theDtos = TheModelsMapper.INSTANCE.convert( theModels );

        assertThat( theDtos )
            .extracting( TheDto::getId )
            .containsExactly( "1", "2" );
    }
}
