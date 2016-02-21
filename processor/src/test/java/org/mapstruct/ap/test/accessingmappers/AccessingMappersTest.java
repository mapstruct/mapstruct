/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.accessingmappers;

import java.math.BigDecimal;
import java.util.Arrays;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey("751")
@WithClasses({InventoryDto.class, RadioDto.class, RadioEntity.class, RadioMapper.class, TVDto.class, TVEntity.class,
    TvMapper.class, InventoryMapper.class, InventoryEntity.class, InventorySequence.class, InventoryItemEntity.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class AccessingMappersTest {

    @Test
    public void shouldMapTwoArraysToCollections() {
        InventoryDto inventory = new InventoryDto();
        RadioDto radio = new RadioDto();
        radio.setSerialNumber( "54321" );
        inventory.setRadios( Arrays.asList( radio ) );
        TVDto tv = new TVDto();
        tv.setSerialNumber( "12345" );
        inventory.setTvs( Arrays.asList( tv ) );

        InventoryEntity target = InventoryMapper.INSTANCE.mapInventory( inventory );

        assertThat( target ).isNotNull();
        assertThat( target.getRadios() ).isNotEmpty();
        assertThat( target.getRadios().get( 0 ).getSerialNumber() ).isEqualTo( new BigDecimal( "54321" ) );
        assertThat( target.getTvs() ).isNotEmpty();
        assertThat( target.getTvs().get( 0 ).getSerialNumber() ).isEqualTo( new BigDecimal( "12345" ) );
        assertThat( target.getTvs().get( 0 ).getInventorySequenceItem() )
            .isNotEqualTo( target.getRadios().get( 0 ).getInventorySequenceItem() );

    }


    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = ErroneousMapperWithNonUsedReferenceMapper.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 45,
                        messageRegExp = "The getter method is refering a used mapper which is not acually used in "
                            + "the mappings." )
            }
    )
    @Test
    @WithClasses( ErroneousMapperWithNonUsedReferenceMapper.class )
    public void shouldEmitWaringOnNonUsedMapper() {
    }
}
