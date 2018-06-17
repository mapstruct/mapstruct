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

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.TargetAnnotation;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class SlotMachineMapper {

    public static final SlotMachineMapper INSTANCE = Mappers.getMapper( SlotMachineMapper.class );

    public abstract SlotMachineEntity mapToTarget(SlotMachineDto slotMachineDto);

    // perhaps we should select the method with the hightest number of matching parameters..
    @Named( "doNotSelect" )
    public abstract SlotMachineEntity.ConsoleEntity mapToNestedTargetPlain(SlotMachineDto.ConsoleDto source);

    public SlotMachineEntity.ConsoleEntity mapToNestedTarget(SlotMachineDto.ConsoleDto source,
                                                             @TargetAnnotation WheelAnnotation winSign) {
        SlotMachineEntity.ConsoleEntity result = mapToNestedTargetPlain( source );
        if ( source.getSign1().equals( winSign.winningSign()[0] )
            && source.getSign2().equals( winSign.winningSign()[1] )
            && source.getSign3().equals( winSign.winningSign()[2] ) ) {
            result.setCoinage( source.getCoinage() * 10 );
        }
        else {
            result.setCoinage( source.getCoinage() - 1 );
        }
        return result;
    }

}




