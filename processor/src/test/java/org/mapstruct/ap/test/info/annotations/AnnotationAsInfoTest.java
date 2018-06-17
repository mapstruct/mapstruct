/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.info.annotations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("1470")
@WithClasses({
    WheelAnnotation.class,
    SlotMachineMapper.class,
    Sign.class,
    SlotMachineDto.class,
    SlotMachineEntity.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class AnnotationAsInfoTest {

    @Test
    public void testMe() {

        SlotMachineDto dto = new SlotMachineDto();
        SlotMachineDto.ConsoleDto console = new SlotMachineDto.ConsoleDto();
        dto.setConsole( console );

        // place your bet and start
        console.setCoinage( 5 );
        console.setSign1( Sign.BAR );
        console.setSign2( Sign.SEVEN );
        console.setSign3( Sign.SEVEN );

        SlotMachineEntity entity = SlotMachineMapper.INSTANCE.mapToTarget( dto );

        assertThat( entity ).isNotNull();
        assertThat( entity.getConsole() ).isNotNull();
        assertThat( entity.getConsole().getCoinage() ).isEqualTo( 4 );

        // place your bet and start again
        console.setCoinage( 4 );
        console.setSign1( Sign.SEVEN );
        console.setSign2( Sign.SEVEN );
        console.setSign3( Sign.SEVEN );
        entity = SlotMachineMapper.INSTANCE.mapToTarget( dto );

        // jackpot
        assertThat( entity.getConsole().getCoinage() ).isEqualTo( 40 );

    }

}
